package io.mountblue.stackoverflowclone.repository;

import io.mountblue.stackoverflowclone.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,Long> {


}
