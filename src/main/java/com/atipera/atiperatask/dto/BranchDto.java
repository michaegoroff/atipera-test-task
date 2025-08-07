package com.atipera.atiperatask.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.Data;

@Data
public class BranchDto {

    @JsonProperty("name")
    private String branchName;

    private String lastSHA;

    @JsonProperty("commit")
    private void unpackCommit(Map<String, Object> commit) {
        this.lastSHA = commit.get("sha").toString();
    }
}
