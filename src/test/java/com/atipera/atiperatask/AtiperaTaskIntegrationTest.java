package com.atipera.atiperatask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.atipera.atiperatask.dto.BranchDto;
import com.atipera.atiperatask.dto.SummaryDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class AtiperaTaskIntegrationTest {

    private static final String TARGET_URL = "/github/michaegoroff/allrepos";   //used my own login here

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void happyPath_test() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(TARGET_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        SummaryDto[] summaryDtos = mapper.readValue(resultContent, SummaryDto[].class);
        for (SummaryDto summaryDto : summaryDtos) {
            assertEquals("michaegoroff", summaryDto.getOwnerLogin());
            assertNotNull(summaryDto.getRepositoryName());
            for (BranchDto branchDto : summaryDto.getBranches()) {
                assertNotNull(branchDto.getBranchName());
                assertEquals(40, branchDto.getLastSHA().length());
            }
        }
    }
}
