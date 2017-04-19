/**
 *  Copyright (c) 2017 Dell Inc., or its subsidiaries.
 */

package com.emc.pravega.framework.mesos.model.v1;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class Framework {
    private final String id;
    private final String name;
    private final String hostname;
    private final List<Executor> executors;
    @SerializedName("completed_executors")
    private final List<Executor> completedExecutors;
    @SerializedName("completed_tasks")
    private final List<Task> completedTasks;
}
