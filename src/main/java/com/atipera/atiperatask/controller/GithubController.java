package com.atipera.atiperatask.controller;

import com.atipera.atiperatask.service.impl.GithubServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/github")
public class GithubController {

    private final GithubServiceImpl githubServiceImpl;

    @GetMapping(value = "/{login}/allrepos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllUserRepositories(@PathVariable String login) {
        return ResponseEntity.ok(githubServiceImpl.findAllUserRepositoriesByLogin(login));
    }
}
