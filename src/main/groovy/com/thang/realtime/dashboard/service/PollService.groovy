package com.thang.realtime.dashboard.service

import com.thang.realtime.dashboard.domain.PollAnswer
import com.thang.realtime.dashboard.dto.ChoiceStats
import com.thang.realtime.dashboard.dto.PollStats
import com.thang.realtime.dashboard.repository.PollAnswerRepository
import com.thang.realtime.dashboard.repository.PollRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.persistence.EntityManager
import javax.persistence.Query

/**
 * Created by thangnguyen on 5/22/16.
 */
@Service
public class PollService {
    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private PollAnswerRepository pollAnswerRepository;

    @Autowired
    private EntityManager entityManager;

    def findAll() {
        return pollRepository.findAll();
    }

    def findById(Long id){
        return pollRepository.findOne(id);
    }

    def PollAnswer savePollAnswer(PollAnswer answer) {
        return pollAnswerRepository.save(answer)
    }

    def ArrayList<PollStats> getPollStats() {
        Query q1 = this.entityManager.createQuery("SELECT p.id,p.name,count(a.id) as totalVote FROM PollAnswer a"
                + " JOIN a.pollChoice c"
                + " JOIN c.poll p"
                + " GROUP BY p.id");

        ArrayList<ArrayList> results = q1.getResultList();
        ArrayList<PollStats> stats = new ArrayList<PollStats>();
        results.each { result ->
            //with each poll, we will query all the choices along with the statistics
            Query q2 = this.entityManager.createQuery("SELECT c.id,c.choice,count(a.id) as totalVote FROM PollAnswer a"
                    + " JOIN a.pollChoice c"
                    + " JOIN c.poll p"
                    + " WHERE p.id = :id"
                    + " GROUP BY c.id");
            ArrayList<ArrayList> choices = q2.setParameter("id",result[0]).getResultList()
            ArrayList<ChoiceStats> choiceStats = new ArrayList<>();
            choices.each { choice ->
                ChoiceStats choiceStat = new ChoiceStats([
                    id: choice[0],
                    choice: choice[1],
                    totalVote: choice[2]
                ]);
                choiceStats.add(choiceStat);
            }

            PollStats stat = new PollStats([
                id: result[0],
                name: result[1],
                totalVote: result[2],
                choices: choiceStats
            ])
            stats.add(stat);
        }

        return stats;
    }
}
