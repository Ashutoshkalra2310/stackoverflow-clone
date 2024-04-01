package io.mountblue.stackoverflowclone.service;

public interface AnswerService {
    public void saveAnswer(Answer answer);
    public void updateAnswer(Long id,Answer updatedAnswer);
    public void deleteAnswer(Long id);

}
