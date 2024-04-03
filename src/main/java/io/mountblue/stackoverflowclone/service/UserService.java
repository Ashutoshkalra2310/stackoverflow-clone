package io.mountblue.stackoverflowclone.service;

import io.mountblue.stackoverflowclone.entity.Question;
import io.mountblue.stackoverflowclone.entity.User;

import java.util.List;

public interface UserService {

    boolean isUserExist(String username);
    void saveUser(User user);
    List<User> findAll();
    List<Question> findByUserId(Long id);
    User findByEmail(String email);
}
