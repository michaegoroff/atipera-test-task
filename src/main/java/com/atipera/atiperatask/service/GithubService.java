package com.atipera.atiperatask.service;

import com.atipera.atiperatask.dto.SummaryDto;
import java.util.List;
import org.springframework.web.util.UriComponentsBuilder;

public interface GithubService {

    List<SummaryDto> findAllUserRepositoriesByLogin(String login);

    default UriComponentsBuilder getURIBuilder() {
        return UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("api.github.com");
    }
}
