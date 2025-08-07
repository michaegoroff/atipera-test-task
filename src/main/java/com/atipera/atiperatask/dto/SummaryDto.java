package com.atipera.atiperatask.dto;

import java.util.List;
import lombok.Data;

@Data
public class SummaryDto {

    private String ownerLogin;

    private String repositoryName;

    private List<BranchDto> branches;
}
