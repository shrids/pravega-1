/**
 * Copyright (c) 2017 Dell Inc., or its subsidiaries.
 */

package io.pravega.stream;

import lombok.EqualsAndHashCode;
import org.apache.commons.lang.math.DoubleRange;
import org.apache.commons.lang.math.Range;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The successor segments of a given segment.
 */
@EqualsAndHashCode
public class StreamSegmentsWithPredecessors {
    private final Map<Segment, List<Integer>> segmentWithPredecessors;
    private final Map<Segment, Range> segmentWithKeyRange;

    public StreamSegmentsWithPredecessors(final Map<SegmentWithRange, List<Integer>> segments) {
        segmentWithPredecessors = Collections.unmodifiableMap(segments.entrySet().stream().collect(
                Collectors.toMap(entry -> entry.getKey().getSegment(), Map.Entry::getValue)));

        segmentWithKeyRange = Collections.unmodifiableMap(segments.entrySet().stream().collect(
                Collectors.toMap(entry -> entry.getKey().getSegment(),
                        entry -> new DoubleRange(entry.getKey().getLow(), entry.getKey().getHigh()))));
    }

    /**
     * Get Segment to Predecessor mapping.
     *
     * @return Map<Segment, List<Integer>> Segment to Predecessor mapping.
     */
    public Map<Segment, List<Integer>> getSegmentToPredecessor() {
        return segmentWithPredecessors;
    }

    /**
     * Get Segment to Key Range mapping.
     *
     * @return Map<Segment, Range> segment to range mapping.
     */
    public Map<Segment, Range> getSegmentToRange() {
        return segmentWithKeyRange;
    }
}
