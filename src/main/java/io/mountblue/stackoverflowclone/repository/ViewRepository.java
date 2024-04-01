package io.mountblue.stackoverflowclone.repository;

import io.mountblue.stackoverflowclone.entity.Question;
import io.mountblue.stackoverflowclone.entity.View;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ViewRepository extends JpaRepository<View, Long> {

    @Query("SELECT v FROM View v WHERE v.question = :question ") //AND v.user = :user
    View findByUserAndQuestion(@Param("question")Question question);
}
