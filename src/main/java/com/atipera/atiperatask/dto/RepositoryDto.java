package com.atipera.atiperatask.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.Data;

@Data
public class RepositoryDto {

    @JsonProperty("name")
    private String repositoryName;

    private String ownerLogin;

    @JsonProperty("owner")
    private void unpackOwnerLogin(Map<String, Object> owner) {
        this.ownerLogin = owner.get("login").toString();
    }

    @JsonProperty("branches_url")
    private String branchesUrl;

    @JsonProperty("fork")
    private boolean isFork;
}
