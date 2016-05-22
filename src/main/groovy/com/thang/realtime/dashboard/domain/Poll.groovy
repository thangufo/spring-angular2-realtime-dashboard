/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thang.realtime.dashboard.domain

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.OrderBy

/**
 *
 * @author thangnguyen
 */
@Entity
public class Poll {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long id;
    String name;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @OrderBy("id ASC")
    Set<PollChoice> choices;
}
