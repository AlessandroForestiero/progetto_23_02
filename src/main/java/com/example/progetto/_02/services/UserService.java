package com.example.progetto._02.services;

import com.example.progetto._02.entities.User;
import com.example.progetto._02.requests.UserRequest;
import com.example.progetto._02.responses.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    public User save(UserRequest userRequest) {
        User user = new User();
        user.setNome(userRequest.getNome());
        user.setCognome(userRequest.getCognome());
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());

        return userRepository.save(user);
    }

    public User getUtenteByUsername(String username)  {
        return userRepository.findByUsername(username).orElseThrow();
    }
}
