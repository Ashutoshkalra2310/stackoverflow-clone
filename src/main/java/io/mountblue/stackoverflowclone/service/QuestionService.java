package io.mountblue.stackoverflowclone.service;

import io.mountblue.stackoverflowclone.entity.Question;

public interface QuestionService {
    void updateQuestion(Question question, String tagList);
    void deleteQuestion(Long id);
    Question findById(Long id);
    void save(Question question);
}
