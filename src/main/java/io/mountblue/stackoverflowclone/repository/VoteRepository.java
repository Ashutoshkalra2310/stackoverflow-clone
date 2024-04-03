package io.mountblue.stackoverflowclone.repository;

import io.mountblue.stackoverflowclone.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote,Long> {

    @Query("SELECT v FROM Vote v WHERE v.question.id = ?1 AND v.user.id = ?2")
    Optional<Vote> findByQuestionIdAndUserId(Long questionId, Long userId);

    @Query("SELECT v FROM Vote v WHERE v.answer.id = ?1 AND v.user.id = ?2")
    Optional<Vote> findByAnswerIdAndUserId(Long answerId, Long userId);
}
