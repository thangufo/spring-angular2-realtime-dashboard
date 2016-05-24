package com.thang.realtime.dashboard.dto

import groovy.transform.CompileStatic

/**
 * Created by thangnguyen on 5/24/16.
 */
@CompileStatic
public class PollStats {
    Long id
    String name
    Long totalVote;
    ArrayList<ChoiceStats> choices
}
