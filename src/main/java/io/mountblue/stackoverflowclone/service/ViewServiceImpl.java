package io.mountblue.stackoverflowclone.service;

import io.mountblue.stackoverflowclone.entity.Question;
import io.mountblue.stackoverflowclone.entity.View;
import io.mountblue.stackoverflowclone.repository.ViewRepository;
import org.springframework.stereotype.Service;

@Service
public class ViewServiceImpl implements ViewService{
    private final ViewRepository viewRepository;

    public ViewServiceImpl(ViewRepository viewRepository) {
        this.viewRepository = viewRepository;
    }

    @Override
    public View findByUserAndQuestion(Question question) {
        return viewRepository.findByUserAndQuestion(question);
    }

    @Override
    public void save(View view) {
        viewRepository.save(view);
    }

}
