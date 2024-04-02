package io.mountblue.stackoverflowclone.repository;

import io.mountblue.stackoverflowclone.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByIsAnsweredFalse();
    List<Question> findByAnswersIsNull();
    List<Question> findByOrderByCreatedAtDesc();
    List<Question> findByOrderByCreatedAtAsc();
    List<Question> findByOrderByUpdatedAtDesc();
    List<Question> findByTagsNameContainingIgnoreCase(String tagSearch);
}
