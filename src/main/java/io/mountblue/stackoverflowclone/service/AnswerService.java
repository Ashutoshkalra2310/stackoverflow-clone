package io.mountblue.stackoverflowclone.service;

import io.mountblue.stackoverflowclone.entity.Answer;

public interface AnswerService {
    void saveAnswer(Answer answer);
    void updateAnswer(Long id,Answer updatedAnswer);
    void deleteAnswer(Long id);
}
