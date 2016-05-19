/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thang.realtime.dashboard.dto;

import java.util.ArrayList;

/**
 *
 * @author thangnguyen
 */
@groovy.transform.Canonical
public class Poll {
    int id;
    String name;
    ArrayList<PollChoice> choices;
}
