package com.thang.realtime.dashboard.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

/**
 * Created by thangnguyen on 5/22/16.
 */
@Entity
public class PollAnswer {
    @Id
    @GeneratedValue
    Long id
    String user

    @ManyToOne
    @JoinColumn(name = "poll_choice_id")
    PollChoice pollChoice
}
