package com.thang.realtime.dashboard;

import com.thang.realtime.dashboard.dto.Poll
import org.springframework.messaging.handler.annotation.Payload
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
@RequestMapping("/api")
public class PollController {
    private SimpMessagingTemplate template;
    static ArrayList<Poll> polls;

    @Inject
    public PollController(SimpMessagingTemplate template) {
        this.template = template;
        initData();
    }

    def initData() {
        this.polls = [
            [
                id       : 1,
                name     : "What is the best Rock band ?",
                questions: [
                    [id: 1, question: "Metallica"],
                    [id: 2, question: "Guns N Roses"],
                    [id: 3, question: "Queen"],
                    [id: 4, question: "Other"]
                ]
            ],
            [
                id       : 2,
                name     : "Which MVC framework do you like the most ?",
                questions: [
                    [id: 5, question: "Spring Boot/Spring MVC"],
                    [id: 6, question: "Ruby on Rails"],
                    [id: 7, question: "Django"],
                    [id: 8, question: "Symfony (PHP)"],
                    [id: 9, question: "Other"]
                ]
            ],
            [
                id       : 3,
                name     : "Which is the best Javascript framework ?",
                questions: [
                    [id: 5, question: "Meteor"],
                    [id: 6, question: "AngularJS 2"],
                    [id: 6, question: "EmberJS"],
                    [id: 6, question: "Backbone"],
                    [id: 6, question: "Other"]
                ]
            ]
        ];
    }
    
    @RequestMapping(value = "/poll", method = RequestMethod.GET)
    public Poll[] getPolls() {
        initData();
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
    }

    @RequestMapping(value = "/poll/{id}",method = RequestMethod.PUT)
    public void updatePoll(@PathVariable Long id, @RequestBody Poll input) {
        //refresh the poll list in all client
        template.convertAndSend("/queue/polls", this.polls);
    }

    @MessageMapping("/selectPoll")
    @SendTo("/queue/selectPoll")
    def Poll getPollList(@Payload Poll poll) {
        return poll
    }
}