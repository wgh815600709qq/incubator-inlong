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

package org.apache.inlong.tubemq.server.broker.metrics;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.inlong.tubemq.corebase.metric.AbsMetricItem;
import org.apache.inlong.tubemq.corebase.metric.CountMetricItem;
import org.apache.inlong.tubemq.corebase.metric.GaugeMaxMetricItem;
import org.apache.inlong.tubemq.corebase.metric.GaugeMinMetricItem;
import org.apache.inlong.tubemq.corebase.metric.GaugeNormMetricItem;
import org.apache.inlong.tubemq.corebase.metric.MetricValues;
import org.apache.inlong.tubemq.server.common.utils.WebParameterUtils;

public class BrokerMetrics implements BrokerMetricMXBean {

    private final AtomicLong lastResetTime =
            new AtomicLong(System.currentTimeMillis());
    // Delay statistics for syncing data to files
    protected final AbsMetricItem syncDataDurMin =
            new GaugeMinMetricItem("fSync_duration_min");
    protected final AbsMetricItem syncDataDurMax =
            new GaugeMaxMetricItem("fSync_duration_max");
    // Delay statistics for syncing data to Zookeeper
    protected final AbsMetricItem syncZkDurMin =
            new GaugeMinMetricItem("zkSync_duration_min");
    protected final AbsMetricItem syncZkDurMax =
            new GaugeMaxMetricItem("zkSync_duration_max");
    // Zookeeper Exception statistics
    protected final AbsMetricItem zkExceptionCnt =
            new CountMetricItem("zk_exception_cnt");
    // Broker 2 Master status statistics
    protected final AbsMetricItem masterNoNodeCnt =
            new CountMetricItem("online_timeout_cnt");
    protected final AbsMetricItem hbExceptionCnt =
            new CountMetricItem("hb_master_exception_cnt");
    // Disk IO Exception statistics
    protected final AbsMetricItem ioExceptionCnt =
            new CountMetricItem("io_exception_cnt");
    // Consumer client statistics
    protected final AbsMetricItem consumerOnlineCnt =
            new GaugeNormMetricItem("consumer_online_cnt");
    protected final AbsMetricItem consumerTmoTotCnt =
            new CountMetricItem("consumer_timeout_cnt");

    @Override
    public MetricValues getMetrics() {
        Map<String, Long> metricValues = new HashMap<>();
        metricValues.put(syncDataDurMin.getName(), syncDataDurMin.getValue());
        metricValues.put(syncDataDurMax.getName(), syncDataDurMax.getValue());
        metricValues.put(syncZkDurMin.getName(), syncZkDurMin.getValue());
        metricValues.put(syncZkDurMax.getName(), syncZkDurMax.getValue());
        metricValues.put(zkExceptionCnt.getName(), zkExceptionCnt.getValue());
        metricValues.put(masterNoNodeCnt.getName(), masterNoNodeCnt.getValue());
        metricValues.put(hbExceptionCnt.getName(), hbExceptionCnt.getValue());
        metricValues.put(ioExceptionCnt.getName(), ioExceptionCnt.getValue());
        metricValues.put(consumerOnlineCnt.getName(), consumerOnlineCnt.getValue());
        metricValues.put(consumerTmoTotCnt.getName(), consumerTmoTotCnt.getValue());
        return new MetricValues(WebParameterUtils.date2yyyyMMddHHmmss(
                new Date(lastResetTime.get())), metricValues);
    }

    @Override
    public MetricValues getAndReSetMetrics() {
        Map<String, Long> metricValues = new HashMap<>();
        metricValues.put(syncDataDurMin.getName(), syncDataDurMin.getAndSet());
        metricValues.put(syncDataDurMax.getName(), syncDataDurMax.getAndSet());
        metricValues.put(syncZkDurMin.getName(), syncZkDurMin.getAndSet());
        metricValues.put(syncZkDurMax.getName(), syncZkDurMax.getAndSet());
        metricValues.put(zkExceptionCnt.getName(), zkExceptionCnt.getAndSet());
        metricValues.put(masterNoNodeCnt.getName(), masterNoNodeCnt.getAndSet());
        metricValues.put(hbExceptionCnt.getName(), hbExceptionCnt.getAndSet());
        metricValues.put(ioExceptionCnt.getName(), ioExceptionCnt.getAndSet());
        metricValues.put(consumerOnlineCnt.getName(), consumerOnlineCnt.getAndSet());
        metricValues.put(consumerTmoTotCnt.getName(), consumerTmoTotCnt.getAndSet());
        long befTime = lastResetTime.getAndSet(System.currentTimeMillis());
        return new MetricValues(
                WebParameterUtils.date2yyyyMMddHHmmss(new Date(befTime)), metricValues);
    }

    public long getLastResetTime() {
        return lastResetTime.get();
    }

    public AbsMetricItem getSyncDataDurMin() {
        return syncDataDurMin;
    }

    public AbsMetricItem getSyncDataDurMax() {
        return syncDataDurMax;
    }

    public AbsMetricItem getSyncZkDurMin() {
        return syncZkDurMin;
    }

    public AbsMetricItem getSyncZkDurMax() {
        return syncZkDurMax;
    }

    public AbsMetricItem getZkExceptionCnt() {
        return zkExceptionCnt;
    }

    public AbsMetricItem getMasterNoNodeCnt() {
        return masterNoNodeCnt;
    }

    public AbsMetricItem getHbExceptionCnt() {
        return hbExceptionCnt;
    }

    public AbsMetricItem getIoExceptionCnt() {
        return ioExceptionCnt;
    }

    public AbsMetricItem getConsumerOnlineCnt() {
        return consumerOnlineCnt;
    }

    public AbsMetricItem getConsumerTmoTotCnt() {
        return consumerTmoTotCnt;
    }
}

