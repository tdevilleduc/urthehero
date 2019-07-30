package com.tdevilleduc.urthehero.user.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping(value="/Users")
    public String getUsers() {
        return "Une liste de users";
    }

    @GetMapping(value="/User/{id}")
    public String getUserById(@PathVariable int id) {
        return "Vous avez demandé un user avec l'id  " + id;
    }
}
