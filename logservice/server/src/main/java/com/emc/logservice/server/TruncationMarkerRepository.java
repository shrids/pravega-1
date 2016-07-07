/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.emc.logservice.server;

/**
 * Defines a repository for Truncation Markers.
 * Truncation Markers are mappings (of Operation Sequence Numbers to DataFrame Sequence Numbers) where the DurableDataLog
 * can be truncated.
 */
public interface TruncationMarkerRepository {
    /**
     * Records a new Truncation Marker in the metadata.
     * A Truncation Marker is a particular position in the Log where we can execute truncation operations.
     *
     * @param operationSequenceNumber The Sequence Number of the Operation that can be used as a truncation argument.
     * @param dataFrameSequenceNumber The Sequence Number of the corresponding Data Frame that can be truncated (up to, and including).
     * @throws IllegalArgumentException If any of the arguments are invalid.
     */
    void recordTruncationMarker(long operationSequenceNumber, long dataFrameSequenceNumber);

    /**
     * Records the fact that the given Operation Sequence Number is a valid Truncation Point. We can only truncate on
     * valid Sequence Numbers. For instance, a valid Truncation Point refers to a MetadataCheckpointOperation, which
     * is a good place to start a recovery from.
     *
     * @param operationSequenceNumber The Sequence Number of the Operation that can be used as a truncation argument.
     */
    void setValidTruncationPoint(long operationSequenceNumber);

    /**
     * Gets a value indicating whether the given Operation Sequence Number is a valid Truncation Point, as set by
     * setValidTruncationPoint().
     *
     * @param operationSequenceNumber
     * @return
     */
    boolean isValidTruncationPoint(long operationSequenceNumber);

    /**
     * Removes all truncation markers up to, and including the given Operation Sequence Number.
     *
     * @param upToOperationSequenceNumber The Operation Sequence Number to remove Truncation Markers up to.
     * @throws IllegalStateException If the Metadata is in Recovery Mode.
     */
    void removeTruncationMarkers(long upToOperationSequenceNumber);

    /**
     * Gets the closest Truncation Marker to the given Operation Sequence Number that does not exceed it.
     *
     * @param operationSequenceNumber The Operation Sequence Number to query.
     * @return The requested Truncation Marker, or -1 if no such marker exists.
     * @throws IllegalStateException If the Metadata is in Recovery Mode.
     */
    long getClosestTruncationMarker(long operationSequenceNumber);
}