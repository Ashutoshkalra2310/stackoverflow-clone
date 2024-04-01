package io.mountblue.stackoverflowclone.service;

import io.mountblue.stackoverflowclone.entity.Question;

import java.util.List;

public interface QuestionService {

    List<Question> getAllQuestions();
    void updateQuestion(Question question, String tagList);
    void deleteQuestion(Long id);
    void save(Question question);
    Question findById(Long questionId);
}
