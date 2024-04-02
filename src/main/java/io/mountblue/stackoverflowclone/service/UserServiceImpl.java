package io.mountblue.stackoverflowclone.service;

import io.mountblue.stackoverflowclone.entity.User;
import io.mountblue.stackoverflowclone.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers(){
        return userRepository.findAll();

    }
}
