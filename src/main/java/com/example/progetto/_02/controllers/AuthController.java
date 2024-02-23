package com.example.progetto._02.controllers;
import com.example.progetto._02.entities.User;
import com.example.progetto._02.requests.UserRequest;
import com.example.progetto._02.exceptions.BadRequestException;
import com.example.progetto._02.exceptions.NotFoundException;
import com.example.progetto._02.requests.LoginRequest;
import com.example.progetto._02.security.JwtTools;
import com.example.progetto._02.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTools jwtTools;

    @PostMapping("/auth/register")
    public User register(@RequestBody @Validated UserRequest userRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw  new BadRequestException(bindingResult.getAllErrors().toString());
        }

        return userService.save(userRequest);

    }
    @PostMapping("/auth/login")
    public String login(@RequestBody @Validated LoginRequest loginRequest, BindingResult bindingResult) throws NotFoundException {
        if(bindingResult.hasErrors()){
            throw  new BadRequestException(bindingResult.getAllErrors().toString());
        }

        User utente = userService.getUtenteByUsername(loginRequest.getUsername());

        return jwtTools.createToken(utente);
    }

}
