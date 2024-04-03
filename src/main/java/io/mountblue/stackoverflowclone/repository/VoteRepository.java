package io.mountblue.stackoverflowclone.repository;

import io.mountblue.stackoverflowclone.entity.Answer;
import io.mountblue.stackoverflowclone.entity.Question;
import io.mountblue.stackoverflowclone.entity.User;
import io.mountblue.stackoverflowclone.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote,Long> {

    Vote findByQuestionAndUser(Question question, User user);

    Vote findByAnswerAndUser(Answer answer, User user);
}
