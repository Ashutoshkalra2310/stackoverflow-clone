package io.mountblue.stackoverflowclone.service;

import io.mountblue.stackoverflowclone.entity.Question;
import io.mountblue.stackoverflowclone.entity.User;
import io.mountblue.stackoverflowclone.entity.View;

public interface ViewService {
    View findByUserAndQuestion(Question question, User user);

    void save(View view);
}
