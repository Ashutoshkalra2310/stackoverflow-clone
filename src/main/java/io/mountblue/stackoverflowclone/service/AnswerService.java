package io.mountblue.stackoverflowclone.service;

import io.mountblue.stackoverflowclone.entity.Answer;

public interface AnswerService {
    public void saveAnswer(Answer answer, Long questionId);
    public void updateAnswer(Long id,Answer updatedAnswer);
    public void deleteAnswer(Long id);
    public Answer findById(Long id);
}
