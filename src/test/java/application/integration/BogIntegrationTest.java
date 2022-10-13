package application.integration;

import application.dto.*;
import application.util.DtoTestUtil;
import application.util.WebTestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BogIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void opretOgHentBog() throws Exception {

        String titel = UUID.randomUUID().toString();
        OpretBogResponseDto opretBogResponse = opretBogMedTitel(titel);
        assertBogBlevOprettet(opretBogResponse);

        HentBogResponseDto hentBogResponse = hentBogMedTitel(titel);
        assertBogBlevHentet(hentBogResponse);
    }

    @Test
    public void bogIkkeFundet() throws Exception {

        String titel = UUID.randomUUID().toString();
        OpretBogResponseDto opretBogResponse = opretBogMedTitel(titel);
        assertBogBlevOprettet(opretBogResponse);

        String nyTitel = UUID.randomUUID().toString();
        HentBogResponseDto hentBogResponse = hentBogMedTitel(nyTitel);
        assertBogBlevIkkeFundet(hentBogResponse);

        OpretBogResponseDto nyBogOprettetResponse = opretBogMedTitel(nyTitel);
        assertBogBlevOprettet(nyBogOprettetResponse);
    }

    @Test
    public void bogIkkeOprettetGrundetDaarligInput() throws Exception {

        String titel = null;
        OpretBogResponseDto opretBogResponse = opretBogMedTitel(titel);
        assertBogBlevIkkeOprettet(opretBogResponse);

        titel = UUID.randomUUID().toString();
        opretBogResponse = opretBogMedTitel(titel);
        assertBogBlevOprettet(opretBogResponse);
    }

    private void assertBogBlevIkkeFundet(HentBogResponseDto hentBogResponse) {
        assertNotNull(hentBogResponse);
        assertEquals(ResponseDto.StatusKode.INPUT_FEJL, hentBogResponse.getStatusKode());
        assertEquals(HentBogResponseDto.StatusSubKode.UKENDT_BOG, hentBogResponse.getStatusSubKode());
    }

    private void assertBogBlevIkkeOprettet(OpretBogResponseDto opretBogResponse) {
        assertNull(opretBogResponse);
    }

    private static void assertBogBlevHentet(HentBogResponseDto hentBogResponse) {
        assertNotNull(hentBogResponse);
        assertEquals(ResponseDto.StatusKode.OK, hentBogResponse.getStatusKode());
    }

    private HentBogResponseDto hentBogMedTitel(String titel) throws Exception {
        HentBogRequestDto hentRequestDto = DtoTestUtil.newHentBogRequestDto();
        hentRequestDto.setTitel(titel);
        return hentBog(hentRequestDto);
    }

    private static void assertBogBlevOprettet(OpretBogResponseDto opretBogResponse) {
        assertNotNull(opretBogResponse);
        assertEquals(ResponseDto.StatusKode.OK, opretBogResponse.getStatusKode());
    }

    private OpretBogResponseDto opretBogMedTitel(String titel) throws Exception {
        OpretBogRequestDto opretBogRequestDto = DtoTestUtil.newOpretBogRequestDto();
        opretBogRequestDto.setTitel(titel);
        return opretBog(opretBogRequestDto);
    }

    private OpretBogResponseDto opretBog(OpretBogRequestDto opretBogRequestDto) throws Exception {
        ResultActions opretBogResult = WebTestUtil.kaldOpretBogEndpoint(mockMvc, opretBogRequestDto);
        String responseBody = opretBogResult.andReturn().getResponse().getContentAsString();
        if (StringUtils.isBlank(responseBody)) {
            return null;
        }
        return new ObjectMapper().readValue(responseBody, OpretBogResponseDto.class);
    }

    private HentBogResponseDto hentBog(HentBogRequestDto hentRequestDto) throws Exception {
        ResultActions hentBogResult = WebTestUtil.kaldHentBogEndpoint(mockMvc, hentRequestDto);
        String responseBody = hentBogResult.andReturn().getResponse().getContentAsString();
        if (StringUtils.isBlank(responseBody)) {
            return null;
        }
        return new ObjectMapper().readValue(responseBody, HentBogResponseDto.class);
    }
}
