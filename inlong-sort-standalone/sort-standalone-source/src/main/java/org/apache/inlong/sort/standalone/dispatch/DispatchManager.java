/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.inlong.sort.standalone.dispatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.flume.Context;
import org.apache.inlong.sort.standalone.channel.BufferQueueChannel;
import org.apache.inlong.sort.standalone.channel.ProfileEvent;
import org.apache.inlong.sort.standalone.utils.InlongLoggerFactory;
import org.slf4j.Logger;

/**
 * DispatchManager
 */
public class DispatchManager {

    public static final Logger LOG = InlongLoggerFactory.getLogger(BufferQueueChannel.class);
    public static final String KEY_DISPATCH_TIMEOUT = "dispatchTimeout";
    public static final String KEY_DISPATCH_MAX_PACKCOUNT = "dispatchMaxPackCount";
    public static final String KEY_DISPATCH_MAX_PACKSIZE = "dispatchMaxPackSize";
    public static final long DEFAULT_DISPATCH_TIMEOUT = 2000;
    public static final long DEFAULT_DISPATCH_MAX_PACKCOUNT = 256;
    public static final long DEFAULT_DISPATCH_MAX_PACKSIZE = 327680;

    private LinkedBlockingQueue<DispatchProfile> dispatchQueue;
    private final long dispatchTimeout;
    private final long maxPackCount;
    private final long maxPackSize;
    private ConcurrentHashMap<String, DispatchProfile> profileCache = new ConcurrentHashMap<>();
    //
    private AtomicBoolean needOutputOvertimeData = new AtomicBoolean(false);

    /**
     * Constructor
     * 
     * @param context
     * @param dispatchQueue
     */
    public DispatchManager(Context context, LinkedBlockingQueue<DispatchProfile> dispatchQueue) {
        this.dispatchQueue = dispatchQueue;
        this.dispatchTimeout = context.getLong(KEY_DISPATCH_TIMEOUT, DEFAULT_DISPATCH_TIMEOUT);
        this.maxPackCount = context.getLong(KEY_DISPATCH_MAX_PACKCOUNT, DEFAULT_DISPATCH_MAX_PACKCOUNT);
        this.maxPackSize = context.getLong(KEY_DISPATCH_MAX_PACKSIZE, DEFAULT_DISPATCH_MAX_PACKSIZE);
    }

    /**
     * addEvent
     * 
     * @param event
     */
    public void addEvent(ProfileEvent event) {
        if (needOutputOvertimeData.get()) {
            this.outputOvertimeData();
            this.needOutputOvertimeData.set(false);
        }
        // parse
        String uid = event.getUid();
        //
        DispatchProfile dispatchProfile = this.profileCache.get(uid);
        if (dispatchProfile == null) {
            dispatchProfile = new DispatchProfile(uid, event.getInlongGroupId(), event.getInlongStreamId());
            this.profileCache.put(uid, dispatchProfile);
        }
        //
        boolean addResult = dispatchProfile.addEvent(event, maxPackCount, maxPackSize);
        if (!addResult) {
            DispatchProfile newDispatchProfile = new DispatchProfile(uid, event.getInlongGroupId(),
                    event.getInlongStreamId());
            DispatchProfile oldDispatchProfile = this.profileCache.put(uid, newDispatchProfile);
            this.dispatchQueue.offer(oldDispatchProfile);
            newDispatchProfile.addEvent(event, maxPackCount, maxPackSize);
        }
    }

    /**
     * outputOvertimeData
     * 
     * @return
     */
    public void outputOvertimeData() {
        LOG.info("start to outputOvertimeData profileCacheSize:{},dispatchQueueSize:{}",
                profileCache.size(), dispatchQueue.size());
        long currentTime = System.currentTimeMillis();
        long createThreshold = currentTime - dispatchTimeout;
        List<String> removeKeys = new ArrayList<>();
        long eventCount = 0;
        for (Entry<String, DispatchProfile> entry : this.profileCache.entrySet()) {
            DispatchProfile dispatchProfile = entry.getValue();
            eventCount += dispatchProfile.getCount();
            if (!dispatchProfile.isTimeout(createThreshold)) {
                continue;
            }
            removeKeys.add(entry.getKey());
        }
        // output
        removeKeys.forEach((key) -> {
            dispatchQueue.offer(this.profileCache.remove(key));
        });
        LOG.info("end to outputOvertimeData profileCacheSize:{},dispatchQueueSize:{},eventCount:{}",
                profileCache.size(), dispatchQueue.size(), eventCount);
    }

    /**
     * get dispatchTimeout
     * 
     * @return the dispatchTimeout
     */
    public long getDispatchTimeout() {
        return dispatchTimeout;
    }

    /**
     * get maxPackCount
     * 
     * @return the maxPackCount
     */
    public long getMaxPackCount() {
        return maxPackCount;
    }

    /**
     * get maxPackSize
     * 
     * @return the maxPackSize
     */
    public long getMaxPackSize() {
        return maxPackSize;
    }

    /**
     * setNeedOutputOvertimeData
     */
    public void setNeedOutputOvertimeData() {
        this.needOutputOvertimeData.set(true);
    }
}
