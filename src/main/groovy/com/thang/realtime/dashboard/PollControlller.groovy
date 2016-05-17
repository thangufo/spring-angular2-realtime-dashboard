/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thang.realtime.dashboard;

import com.thang.realtime.dashboard.dto.Poll
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import javax.inject.Inject;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author thangnguyen
 */
@RestController
public class PollController {
    private SimpMessagingTemplate template;
    static ArrayList<Poll> polls;

    @Inject
    public PollController(SimpMessagingTemplate template) {
        this.template = template;
        
        this.polls = [
            [
                id: 1,
                name: "Poll1",
                questions: [
                    [id: 1, question: "Question 1"],
                    [id: 2, question: "Question 2"]
                ]
            ],
            [
                id: 2,
                name: "Poll 2",
                questions: [
                    [id: 3, question: "Question 21"],
                    [id: 4, question: "Question 22"]
                ]
            ],
            [
                id: 3,
                name: "Poll 3",
                questions: [
                    [id: 5, question: "Question 31"],
                    [id: 6, question: "Question 32"]
                ]
            ]
        ];
    }
    
    @RequestMapping(value = "/poll", method = RequestMethod.GET)
    public Poll[] getPolls() {
        return this.polls;
    }
    
    @RequestMapping(value = "/poll/{id}",method = RequestMethod.DELETE)
    public void deletePoll(@PathVariable Long id) {
        //delete the poll from the array (by id)
        Closure query = {it.id == id};
        int index = this.polls.findIndexOf(query);
        if (index >= 0 ) {
            this.polls.remove(index);
        }

        //refresh the poll list in all client
        template.convertAndSend("/queue/polls", this.polls);

        println "Here"
    }

    @RequestMapping(value = "/poll/{id}",method = RequestMethod.PUT)
    public void updatePoll(@PathVariable Long id, @RequestBody Poll input) {
        //refresh the poll list in all client
        template.convertAndSend("/queue/polls", this.polls);
    }

    @MessageMapping("/poll")
    def Poll[] getPollList() {
        return this.polls;
    }
}