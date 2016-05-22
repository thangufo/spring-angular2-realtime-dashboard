/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thang.realtime.dashboard.domain

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.OrderBy;

/**
 *
 * @author thangnguyen
 */
@Entity
public class PollChoice {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long id;
    String choice;

    @JsonIgnore //don't export this one, or it will become an infiteloop
    @ManyToOne
    @JoinColumn(name = "poll_id")
    Poll poll

    @JsonIgnore
    @OneToMany(mappedBy = "pollChoice", cascade = CascadeType.ALL)
    Set<PollAnswer> answers
}
