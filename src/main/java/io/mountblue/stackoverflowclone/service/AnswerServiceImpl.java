package io.mountblue.stackoverflowclone.service;

import io.mountblue.stackoverflowclone.entity.Answer;
import io.mountblue.stackoverflowclone.repository.AnswerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AnswerServiceImpl implements AnswerService{
    private final AnswerRepository answerRepository;

    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }
    public void saveAnswer(Answer answer) {
        answerRepository.save(answer);
    }
    public void updateAnswer(Long id,Answer updatedAnswer) {
        Answer prevAnswer=answerRepository.findById(id).get();
        prevAnswer.setContent(updatedAnswer.getContent());
        prevAnswer.setUpdatedAt(LocalDateTime.now());
        prevAnswer.setComments(updatedAnswer.getComments());
        answerRepository.save(prevAnswer);

    }

    public void deleteAnswer(Long id) {
        answerRepository.deleteById(id);
    }
}
