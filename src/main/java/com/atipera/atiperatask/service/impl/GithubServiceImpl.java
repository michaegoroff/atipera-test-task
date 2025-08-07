package com.atipera.atiperatask.service.impl;

import com.atipera.atiperatask.dto.BranchDto;
import com.atipera.atiperatask.dto.RepositoryDto;
import com.atipera.atiperatask.dto.SummaryDto;
import com.atipera.atiperatask.service.GithubService;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class GithubServiceImpl implements GithubService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<SummaryDto> findAllUserRepositoriesByLogin(String login) {
        String url = getURIBuilder()
                .pathSegment("users")
                .pathSegment(login)
                .pathSegment("repos")
                .toUriString();

        ResponseEntity<RepositoryDto[]> responseEntity = restTemplate.getForEntity(url, RepositoryDto[].class);

        if (responseEntity.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }

        RepositoryDto[] body = responseEntity.getBody();
        if (body != null) {
            List<RepositoryDto> repos = new LinkedList<>(Arrays.asList(body));
            repos.removeIf(RepositoryDto::isFork);
            return summarize(repos);
        }
        return null;
    }

    private List<BranchDto> findAllBranchesByRepositoryUrl(String repoURL) {
        ResponseEntity<BranchDto[]> responseEntity = restTemplate.getForEntity(repoURL, BranchDto[].class);

        BranchDto[] body = responseEntity.getBody();
        if (body != null) {
            return new LinkedList<>(Arrays.asList(body));
        }
        return null;
    }

    private List<SummaryDto> summarize(List<RepositoryDto> repos) {
        List<SummaryDto> summaries = new LinkedList<>();

        for (RepositoryDto repo : repos) {
            String branchesUrl = repo.getBranchesUrl().replaceAll("\\{/branch}", "");

            List<BranchDto> repoBranches = findAllBranchesByRepositoryUrl(branchesUrl);

            SummaryDto summaryDto = new SummaryDto();
            summaryDto.setOwnerLogin(repo.getOwnerLogin());
            summaryDto.setRepositoryName(repo.getRepositoryName());
            summaryDto.setBranches(repoBranches);
            summaries.add(summaryDto);
        }
        return summaries;
    }
}
