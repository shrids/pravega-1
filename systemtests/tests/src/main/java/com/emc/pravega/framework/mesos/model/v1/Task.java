/**
 *  Copyright (c) 2017 Dell Inc., or its subsidiaries.
 */

package com.emc.pravega.framework.mesos.model.v1;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Task {
    private final String id;
    private final String name;
    @SerializedName("framework_id")
    private final String frameworkId;
    @SerializedName("slave_id")
    private final String slaveId;
}
