package io.mountblue.stackoverflowclone.service;

import io.mountblue.stackoverflowclone.entity.Question;
import io.mountblue.stackoverflowclone.entity.User;
import io.mountblue.stackoverflowclone.entity.View;
import io.mountblue.stackoverflowclone.repository.ViewRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ViewServiceImpl implements ViewService{
    private final ViewRepository viewRepository;
    private final QuestionService questionService;
    private final UserService userService;

    public ViewServiceImpl(ViewRepository viewRepository, QuestionService questionService, UserService userService) {
        this.viewRepository = viewRepository;
        this.questionService = questionService;
        this.userService = userService;
    }

    @Override
    public void addView(Question question) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        User user = userService.findByEmail(email);
        View view = viewRepository.findByUserAndQuestion(question, user);
        if (view == null) {
            view = new View();
            view.setQuestion(question);
            view.setUser(user);
            save(view);
            question.setViewCount(question.getViewCount() + 1);
            questionService.save(question);
        }

    }

    @Override
    public void save(View view) {
        viewRepository.save(view);
    }

}
