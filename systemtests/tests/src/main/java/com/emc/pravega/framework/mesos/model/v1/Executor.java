/**
 *  Copyright (c) 2017 Dell Inc., or its subsidiaries.
 */

package com.emc.pravega.framework.mesos.model.v1;

import lombok.Data;

@Data
public class Executor {
    private final String id;
    private final String container;
    private final String directory;
}
