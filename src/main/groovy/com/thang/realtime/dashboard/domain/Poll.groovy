/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thang.realtime.dashboard.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 *
 * @author thangnguyen
 */
@groovy.transform.Canonical
@Entity
public class Poll {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    int id;
    String name;
    ArrayList<PollChoice> choices;
}
