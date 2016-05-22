package com.thang.realtime.dashboard.repository

import com.thang.realtime.dashboard.domain.PollAnswer
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * Created by thangnguyen on 5/22/16.
 */
@Repository
public interface PollAnswerRepository extends CrudRepository<PollAnswer,Long> {
}
