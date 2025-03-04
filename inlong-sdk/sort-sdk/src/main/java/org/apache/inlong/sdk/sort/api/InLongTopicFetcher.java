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

package org.apache.inlong.sdk.sort.api;

import org.apache.inlong.sdk.sort.entity.InLongTopic;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;

public abstract class InLongTopicFetcher {

    protected InLongTopic inLongTopic;
    protected ClientContext context;

    public InLongTopicFetcher(InLongTopic inLongTopic, ClientContext context) {
        this.inLongTopic = inLongTopic;
        this.context = context;
    }

    public abstract boolean init(PulsarClient pulsarClient);

    public abstract void ack(String msgOffset) throws Exception;

    public abstract void pause();

    public abstract void resume();

    public abstract boolean close();

    public abstract boolean isClosed();

    public abstract void stopConsume(boolean stopConsume);

    public abstract boolean isConsumeStop();

    public abstract InLongTopic getInLongTopic();

    public abstract long getConsumedDataSize();

    public abstract long getAckedOffset();

    public abstract void seek(long offset) throws Exception;

    public abstract void seek(MessageId messageId) throws PulsarClientException;

    public abstract String getFetcherType();
}
