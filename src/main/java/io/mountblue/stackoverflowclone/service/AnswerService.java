package io.mountblue.stackoverflowclone.service;

import io.mountblue.stackoverflowclone.entity.Answer;

public interface AnswerService {
    void saveAnswer(Answer answer, Long questionId);
    void updateAnswer(Answer updatedAnswer);
    void deleteAnswer(Long id);
    Answer findById(Long id);
    void save(Answer answer);
    void markCorrectAnswer(Long id);
}
