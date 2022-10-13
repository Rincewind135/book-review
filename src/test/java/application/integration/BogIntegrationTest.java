package application.integration;

import application.dto.*;
import application.util.DtoTestUtil;
import application.util.WebTestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BogIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void opretOgHentBog() throws Exception {

        OpretBogRequestDto opretBogRequestDto = DtoTestUtil.newOpretBogRequestDto();
        ResultActions opretBogResult = WebTestUtil.kaldOpretBogEndpoint(mockMvc, opretBogRequestDto);
        opretBogResult.andExpect(status().isOk());
        String responseBody = opretBogResult.andReturn().getResponse().getContentAsString();
        OpretBogResponseDto opretBogResponse = new ObjectMapper().readValue(responseBody, OpretBogResponseDto.class);
        assertNotNull(opretBogResponse);
        assertEquals(ResponseDto.StatusKode.OK, opretBogResponse.getStatusKode());

        HentBogRequestDto hentRequestDto = DtoTestUtil.newHentBogRequestDto();
        hentRequestDto.setTitel(opretBogRequestDto.getTitel());
        ResultActions hentBogResult = WebTestUtil.kaldHentBogEndpoint(mockMvc, hentRequestDto);
        hentBogResult.andExpect(status().isOk());
        String responseBody2 = opretBogResult.andReturn().getResponse().getContentAsString();
        HentBogResponseDto hentBogResponse = new ObjectMapper().readValue(responseBody2, HentBogResponseDto.class);
        assertNotNull(hentBogResponse);
        assertEquals(ResponseDto.StatusKode.OK, hentBogResponse.getStatusKode());

    }
}
