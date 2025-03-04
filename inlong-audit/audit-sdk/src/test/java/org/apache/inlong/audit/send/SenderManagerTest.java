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

package org.apache.inlong.audit.send;

import org.apache.inlong.audit.util.Config;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SenderManagerTest {
    private Config testConfig = new Config();

    @Test
    public void nextRequestId() {
        SenderManager testManager = new SenderManager(testConfig);
        Long requestId = testManager.nextRequestId();
        System.out.println(requestId);
        assertTrue(requestId == 0);

        requestId = testManager.nextRequestId();
        assertTrue(requestId == 1);

        requestId = testManager.nextRequestId();
        assertTrue(requestId == 2);
    }

    @Test
    public void clearBuffer() {
        SenderManager testManager = new SenderManager(testConfig);
        testManager.clearBuffer();
        int dataMapSize = testManager.getDataMapSize();
        assertTrue(dataMapSize == 0);
    }
}