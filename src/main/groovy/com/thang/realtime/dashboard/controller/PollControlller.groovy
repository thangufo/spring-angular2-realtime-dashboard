package com.thang.realtime.dashboard.controller;

import com.thang.realtime.dashboard.domain.Poll
import com.thang.realtime.dashboard.domain.PollAnswer
import com.thang.realtime.dashboard.domain.PollChoice
import com.thang.realtime.dashboard.dto.PollStats
import com.thang.realtime.dashboard.service.PollService

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

import javax.inject.Inject;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.RequestMapping
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

    @Autowired
    private PollService pollService;

    @Inject
    public PollController(SimpMessagingTemplate template) {
        this.template = template;
    }
    
    @RequestMapping(value = "/poll", method = RequestMethod.GET)
    public Set<Poll> getPolls() {
        Set<Poll> polls = pollService.findAll();
        return polls;
    }

    @RequestMapping(value = "/poll/stats", method = RequestMethod.GET)
    def ArrayList<PollStats> getPollStats() {
        ArrayList<PollStats> stats = pollService.getPollStats();
        return stats;
    }


    @RequestMapping(value = "/poll/{id:[\\d]+}",method = RequestMethod.PUT)
    public void updatePoll(@PathVariable Long id, @RequestBody Poll input) {
        //refresh the poll list in all client
        template.convertAndSend("/queue/polls", this.polls);
    }

    @RequestMapping(value = "/poll/{id}/submit",method = RequestMethod.POST)
    public void submitPoll(@PathVariable Long id, @RequestBody PollChoice choice) {
        Poll poll = pollService.findById(id);
        PollAnswer answer = new PollAnswer();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        answer.setUser(auth.getPrincipal().getUsername())
        answer.setPollChoice(choice)
        pollService.savePollAnswer(answer)

        //refresh the data in the Dashboard
        template.convertAndSend("/queue/answerSubmitted", answer);
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