package io.mountblue.stackoverflowclone.service;

import io.mountblue.stackoverflowclone.entity.Answer;
import io.mountblue.stackoverflowclone.repository.AnswerRepository;
import java.time.LocalDateTime;

public interface AnswerService {
    void saveAnswer(Answer answer);
    void updateAnswer(Long id,Answer updatedAnswer);
    void deleteAnswer(Long id);
}












