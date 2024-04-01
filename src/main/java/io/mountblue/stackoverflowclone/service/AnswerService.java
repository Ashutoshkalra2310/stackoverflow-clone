package io.mountblue.stackoverflowclone.service;

import io.mountblue.stackoverflowclone.entity.Answer;
import io.mountblue.stackoverflowclone.repository.AnswerRepository;
import java.time.LocalDateTime;

public class AnswerService {
    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }
    public void saveAnswer(Answer answer) {
        answerRepository.save(answer);
    }
    public void updateAnswer(Long id,Answer updatedAnswer) {
        Answer prevAnswer=answerRepository.findById(id).get();
//        prevAnswer.setTitle(updatedAnswer.getTitle());
        prevAnswer.setContent(updatedAnswer.getContent());
        prevAnswer.setUpdatedAt(LocalDateTime.now());
        prevAnswer.setComments(updatedAnswer.getComments());
        answerRepository.save(prevAnswer);

    }

    public void deleteAnswer(Long id) {
        answerRepository.deleteById(id);
    }
}












