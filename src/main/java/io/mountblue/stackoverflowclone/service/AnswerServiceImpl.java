package io.mountblue.stackoverflowclone.service;

import io.mountblue.stackoverflowclone.entity.Answer;
import io.mountblue.stackoverflowclone.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = localDateTime.format(dateTimeFormatter);
        prevAnswer.setUpdatedAt(formattedDateTime);
        prevAnswer.setComments(updatedAnswer.getComments());
        answerRepository.save(prevAnswer);

    }

    public void deleteAnswer(Long id) {
        answerRepository.deleteById(id);
    }
}
