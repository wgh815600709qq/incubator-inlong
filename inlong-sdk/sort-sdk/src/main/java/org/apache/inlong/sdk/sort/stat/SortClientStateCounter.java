/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.inlong.sdk.sort.stat;

import java.util.concurrent.atomic.AtomicLongArray;

public class SortClientStateCounter {

    private final AtomicLongArray count = new AtomicLongArray(20);
    public String sortId;
    public String clusterId;
    public String topic;
    public int partitionId;

    /**
     * SortClientStateCounter Constructor
     *
     * @param sortId String
     * @param clusterId String
     * @param topic String
     * @param partitionId int
     */
    public SortClientStateCounter(String sortId, String clusterId, String topic, int partitionId) {
        this.sortId = sortId;
        this.clusterId = clusterId;
        this.topic = topic;
        this.partitionId = partitionId;
    }

    /**
     * reset Counter
     *
     * @return SortClientStateCounter
     */
    public SortClientStateCounter reset() {
        SortClientStateCounter counter = new SortClientStateCounter(sortId, clusterId, topic, partitionId);
        for (int i = 0, len = counter.count.length(); i < len; i++) {
            counter.count.set(i, this.count.getAndSet(i, 0));
        }
        return counter;
    }

    /**
     * get double[] values
     *
     * @return double[]
     */
    public double[] getStatvalue() {
        double[] vals = new double[this.count.length()];
        for (int i = 0, len = this.count.length(); i < len; i++) {
            vals[i] = this.count.get(i);
        }
        return vals;
    }

    /**
     * consume byte size
     *
     * @param num long byte
     * @return SortClientStateCounter
     */
    public SortClientStateCounter addConsumeSize(long num) {
        count.getAndAdd(0, num);
        return this;
    }

    /**
     * count callbak times
     *
     * @param num int
     * @return SortClientStateCounter
     */
    public SortClientStateCounter addCallbackTimes(int num) {
        count.getAndAdd(1, num);
        return this;
    }

    /**
     * count callbak time cost
     *
     * @param num long
     * @return SortClientStateCounter
     */
    public SortClientStateCounter addCallbackTimeCost(long num) {
        count.getAndAdd(2, num);
        return this;
    }

    /**
     * count topic online times
     *
     * @param num int
     * @return SortClientStateCounter
     */
    public SortClientStateCounter addTopicOnlineTimes(int num) {
        count.getAndAdd(3, num);
        return this;
    }

    /**
     * count topic offline times
     *
     * @param num int
     * @return SortClientStateCounter
     */
    public SortClientStateCounter addTopicOfflineTimes(int num) {
        count.getAndAdd(4, num);
        return this;
    }

    /**
     * count request manager times
     *
     * @param num int
     * @return SortClientStateCounter
     */
    public SortClientStateCounter addRequestManagerTimes(int num) {
        count.getAndAdd(5, num);
        return this;
    }

    /**
     * count request manager time cost
     *
     * @param num long
     * @return SortClientStateCounter
     */
    public SortClientStateCounter addRequestManagerTimeCost(long num) {
        count.getAndAdd(6, num);
        return this;
    }

    /**
     * count request manager fail times
     *
     * @param num int
     * @return SortClientStateCounter
     */
    public SortClientStateCounter addRequestManagerFailTimes(int num) {
        count.getAndAdd(7, num);
        return this;
    }

    /**
     * count callbak error times
     *
     * @param num int
     * @return SortClientStateCounter
     */
    public SortClientStateCounter addCallbackErrorTimes(int num) {
        count.getAndAdd(8, num);
        return this;
    }

    /**
     * count ack fail times
     *
     * @param num int
     * @return SortClientStateCounter
     */
    public SortClientStateCounter addAckFailTimes(int num) {
        count.getAndAdd(9, num);
        return this;
    }

    /**
     * count ack succ times
     *
     * @param num int
     * @return SortClientStateCounter
     */
    public SortClientStateCounter addAckSuccTimes(int num) {
        count.getAndAdd(10, num);
        return this;
    }

    /**
     * count callbak done times
     *
     * @param num int
     * @return SortClientStateCounter
     */
    public SortClientStateCounter addCallbackDoneTimes(int num) {
        count.getAndAdd(11, num);
        return this;
    }

    /**
     * count receive event num
     *
     * @param num int
     * @return SortClientStateCounter
     */
    public SortClientStateCounter addMsgCount(int num) {
        count.getAndAdd(12, num);
        return this;
    }

    /**
     * count manager conf changed times
     *
     * @param num int
     * @return SortClientStateCounter
     */
    public SortClientStateCounter addManagerConfChangedTimes(int num) {
        count.getAndAdd(13, num);
        return this;
    }

    /**
     * count manager result code common error times
     *
     * @param num int
     * @return SortClientStateCounter
     */
    public SortClientStateCounter addRequestManagerCommonErrorTimes(int num) {
        count.getAndAdd(14, num);
        return this;
    }

    /**
     * count manager result param error times
     *
     * @param num int
     * @return SortClientStateCounter
     */
    public SortClientStateCounter addRequestManagerParamErrorTimes(int num) {
        count.getAndAdd(15, num);
        return this;
    }

}
