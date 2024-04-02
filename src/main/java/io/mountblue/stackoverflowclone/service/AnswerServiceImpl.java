package io.mountblue.stackoverflowclone.service;

import io.mountblue.stackoverflowclone.entity.Answer;
import io.mountblue.stackoverflowclone.entity.Question;
import io.mountblue.stackoverflowclone.entity.User;
import io.mountblue.stackoverflowclone.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class AnswerServiceImpl implements AnswerService{
    private final AnswerRepository answerRepository;
    private final QuestionService questionService;
    private final UserService userService;

    public AnswerServiceImpl(AnswerRepository answerRepository, QuestionService questionService, UserService userService) {
        this.answerRepository = answerRepository;
        this.questionService = questionService;
        this.userService = userService;
    }
    public void saveAnswer(Answer answer, Long questionId) {
        Question question = questionService.findById(questionId);
        answer.setQuestion(question);
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = localDateTime.format(dateTimeFormatter);
        answer.setPublishedAt(formattedDateTime);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());
        answer.setUser(user);
        answerRepository.save(answer);
    }
    public void updateAnswer(Answer updatedAnswer) {
        Answer prevAnswer=answerRepository.findById(updatedAnswer.getId()).get();
        prevAnswer.setContent(updatedAnswer.getContent());
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = localDateTime.format(dateTimeFormatter);
        prevAnswer.setUpdatedAt(formattedDateTime);
        prevAnswer.setComments(updatedAnswer.getComments());
        answerRepository.save(prevAnswer);

    }

    public void deleteAnswer(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());
        if(answerRepository.findById(id).get().getUser().equals(user)){
            answerRepository.deleteById(id);
        }
    }

    @Override
    public Answer findById(Long id) {
        return answerRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Answer answer) {
        answerRepository.save(answer);
    }
}
