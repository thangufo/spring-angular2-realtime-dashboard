package com.thang.realtime.dashboard.service

import com.thang.realtime.dashboard.domain.PollAnswer
import com.thang.realtime.dashboard.repository.PollAnswerRepository
import com.thang.realtime.dashboard.repository.PollRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by thangnguyen on 5/22/16.
 */
@Service
public class PollService {
    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private PollAnswerRepository pollAnswerRepository;

    def findAll() {
        return pollRepository.findAll();
    }

    def findById(Long id){
        return pollRepository.findOne(id);
    }

    def PollAnswer savePollAnswer(PollAnswer answer) {
        return pollAnswerRepository.save(answer)
    }
}
