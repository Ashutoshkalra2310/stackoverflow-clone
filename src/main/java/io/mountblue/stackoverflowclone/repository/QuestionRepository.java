package io.mountblue.stackoverflowclone.repository;

import io.mountblue.stackoverflowclone.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q FROM Question q " +
            "LEFT JOIN  q.tags t " +
            "WHERE (:noAnswer = false OR q.answers IS EMPTY) " +
            "AND (:noAcceptedAnswer = false OR q.isAnswered IS NULL) " +
            "AND (:tagSearch IS NULL OR LOWER(t.name) LIKE CONCAT('%', LOWER(:tagSearch), '%')) " +
            "ORDER BY " +
            "CASE WHEN :sortBy = 'newest' THEN q.createdAt END DESC, " +
            "CASE WHEN :sortBy = 'oldest' THEN q.createdAt END ASC, " +
            "CASE WHEN :sortBy = 'recentActivity' THEN q.updatedAt END DESC")
    List<Question> filterQuestions(@Param("noAnswer") boolean noAnswer,
                                   @Param("noAcceptedAnswer") boolean noAcceptedAnswer,
                                   @Param("sortBy") String sortBy,
                                   @Param("tagSearch") String tagSearch);


    @Query("SELECT DISTINCT q FROM Question q " +
            "LEFT JOIN q.tags t " +
            "JOIN q.user u " +
            "WHERE " +
            "(LOWER(q.title) LIKE LOWER(concat('%', :keyword, '%')) OR " +
            "LOWER(q.content) LIKE LOWER(concat('%', :keyword, '%')) OR " +
            "LOWER(t.name) LIKE LOWER(concat('%', :keyword, '%'))) OR " +
            "(LOWER(u.name) LIKE LOWER(concat('%', :keyword, '%')))")
    List<Question> search(@Param("keyword") String keyword);

}
