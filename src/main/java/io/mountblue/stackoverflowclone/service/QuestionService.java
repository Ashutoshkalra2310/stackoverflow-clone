package io.mountblue.stackoverflowclone.service;

import io.mountblue.stackoverflowclone.entity.Question;

import java.util.List;

public interface QuestionService {

    List<Question> getAllQuestions();
    void updateQuestion(Question question, String tagList);
    void deleteQuestion(Long id);
    Question findById(Long id);
    void save(Question question, String tags);
    void save(Question question);
    List<Question> filterQuestion(boolean noAnswer, boolean noAcceptedAnswer, boolean newest, boolean oldest, boolean recentActivity, String tagSearch);
    List<Question> search(String keyword,String username);
}
