package io.mountblue.stackoverflowclone.repository;

import io.mountblue.stackoverflowclone.entity.Vote;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface VoteRepository extends JpaRepository<Vote,Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Vote v SET v.voteType = 'UPVOTE' " +
            "WHERE v.question.id = :questionId AND v.user.id = :userId " +
            "AND NOT EXISTS (SELECT 1 FROM Vote v2 WHERE v2.question.id = :questionId AND v2.user.id = :userId)")
    boolean upVoteQuestion(Long questionId, Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE Vote v SET v.voteType = 'DOWNVOTE' " +
            "WHERE v.question.id = :questionId AND v.user.id = :userId " +
            "AND NOT EXISTS (SELECT 1 FROM Vote v2 WHERE v2.question.id = :questionId AND v2.user.id = :userId)")
    boolean downVoteQuestion(Long questionId, Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE Vote v SET v.voteType = 'UPVOTE' " +
            "WHERE v.answer.id = :answerId AND v.user.id = :userId " +
            "AND NOT EXISTS (SELECT 1 FROM Vote v2 WHERE v2.question.id = :questionId AND v2.user.id = :userId)")
    boolean upVoteAnswer(Long questionId, Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE Vote v SET v.voteType = 'DOWNVOTE' " +
            "WHERE v.question.id = :questionId AND v.user.id = :userId " +
            "AND NOT EXISTS (SELECT 1 FROM Vote v2 WHERE v2.question.id = :questionId AND v2.user.id = :userId)")
    boolean downVoteAnswrer(Long questionId, Long userId);
}
