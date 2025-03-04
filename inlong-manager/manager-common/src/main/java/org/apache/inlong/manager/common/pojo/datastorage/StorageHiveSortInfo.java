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

package org.apache.inlong.manager.common.pojo.datastorage;

import lombok.Data;

/**
 * Hive info for Sort config
 */
@Data
public class StorageHiveSortInfo {

    private Integer id;
    private String inlongGroupId;
    private String inlongStreamId;

    // Hive server info
    private String jdbcUrl;
    private String username;
    private String password;

    // Hive db and table info
    private String dbName;
    private String tableName;
    private String hdfsDefaultFs;
    private String warehouseDir;

    private Integer partitionInterval;
    private String partitionUnit;
    private String primaryPartition;
    private String secondaryPartition;
    private String partitionCreationStrategy;

    private String fileFormat;
    private String dataEncoding;
    private String targetSeparator; // Target separator configured in the storage info
    private Integer status;
    private String creator;

    // Data stream info
    private String mqResourceObj;
    private String dataSourceType;
    private String dataType;
    private String description;
    private String sourceSeparator; // Target separator configured in the stream info
    private String dataEscapeChar;

}