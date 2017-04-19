/**
 *  Copyright (c) 2017 Dell Inc., or its subsidiaries.
 */

package com.emc.pravega.framework.mesos.model.v1;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class SlaveState {

    private final String id;
    private final String hostname;
    private final List<Framework> frameworks;
    @SerializedName("completed_frameworks")
    private final List<Framework> completedFrameworks;
}
