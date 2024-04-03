package io.mountblue.stackoverflowclone.service;

import io.mountblue.stackoverflowclone.entity.Question;
import io.mountblue.stackoverflowclone.entity.User;
import io.mountblue.stackoverflowclone.entity.View;
import io.mountblue.stackoverflowclone.repository.UserRepository;
import io.mountblue.stackoverflowclone.repository.ViewRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ViewServiceImpl implements ViewService{
    private final ViewRepository viewRepository;
    private final UserRepository userRepository;


    public ViewServiceImpl(ViewRepository viewRepository, UserRepository userRepository) {
        this.viewRepository = viewRepository;
        this.userRepository = userRepository;
    }

    @Override
    public View findByUserAndQuestion(Question question, User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        user = userRepository.findByEmail(authentication.getName()).get();
        return viewRepository.findByUserAndQuestion(question, user);
    }

    @Override
    public void save(View view) {
        viewRepository.save(view);
    }

}
