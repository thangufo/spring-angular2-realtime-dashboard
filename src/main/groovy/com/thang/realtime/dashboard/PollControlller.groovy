package com.thang.realtime.dashboard;

import com.thang.realtime.dashboard.dto.Poll
import com.thang.realtime.dashboard.dto.PollChoice
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
                choices: [
                    [id: 1, choice: "Metallica"],
                    [id: 2, choice: "Guns N Roses"],
                    [id: 3, choice: "Queen"],
                    [id: 4, choice: "Other"]
                ]
            ],
            [
                id       : 2,
                name     : "Which MVC framework do you like the most ?",
                choices: [
                    [id: 5, choice: "Spring Boot/Spring MVC"],
                    [id: 6, choice: "Ruby on Rails"],
                    [id: 7, choice: "Django"],
                    [id: 8, choice: "Symfony (PHP)"],
                    [id: 9, choice: "Other"]
                ]
            ],
            [
                id       : 3,
                name     : "Which is the best Javascript framework ?",
                choices: [
                    [id: 10, choice: "Meteor"],
                    [id: 11, choice: "AngularJS 2"],
                    [id: 12, choice: "EmberJS"],
                    [id: 13, choice: "Backbone"],
                    [id: 14, choice: "Other"]
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

    @MessageMapping("/selectChoice")
    @SendTo("/queue/selectChoice")
    def PollChoice selectPollChoice(@Payload PollChoice pollChoice) {
        return pollChoice
    }
}