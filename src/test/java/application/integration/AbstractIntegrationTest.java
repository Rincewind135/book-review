package application.integration;

import application.dto.*;
import application.util.DtoTestUtil;
import application.util.WebTestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;

public class AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    public void assertBogBlevIkkeFundet(HentBogResponseDto hentBogResponse) {
        assertNotNull(hentBogResponse);
        assertEquals(ResponseDto.StatusKode.INPUT_FEJL, hentBogResponse.getStatusKode());
        assertEquals(HentBogResponseDto.StatusSubKode.UKENDT_BOG, hentBogResponse.getStatusSubKode());
    }

    public void assertReviewBlevIkkeFundet(HentReviewResponseDto hentReviewResponse) {
        assertNotNull(hentReviewResponse);
        assertEquals(ResponseDto.StatusKode.INPUT_FEJL, hentReviewResponse.getStatusKode());
        assertEquals(HentReviewResponseDto.StatusSubKode.UKENDT_REVIEW, hentReviewResponse.getStatusSubKode());
    }

    public void assertBogBlevIkkeOprettet(OpretBogResponseDto opretBogResponse) {
        assertNull(opretBogResponse);
    }

    public void assertReviewBlevIkkeOprettet(OpretReviewResponseDto opretReviewResponse) {
        assertNotNull(opretReviewResponse);
        assertEquals(ResponseDto.StatusKode.INPUT_FEJL, opretReviewResponse.getStatusKode());
        assertEquals(OpretReviewResponseDto.StatusSubKode.UKENDT_BOG, opretReviewResponse.getStatusSubKode());
    }

    public static void assertBogBlevHentet(HentBogResponseDto hentBogResponse) {
        assertNotNull(hentBogResponse);
        assertEquals(ResponseDto.StatusKode.OK, hentBogResponse.getStatusKode());
    }

    public static void assertReviewBlevHentet(HentReviewResponseDto hentReviewResponse) {
        assertNotNull(hentReviewResponse);
        assertEquals(ResponseDto.StatusKode.OK, hentReviewResponse.getStatusKode());
    }

    public HentBogResponseDto hentBogMedTitel(String titel) throws Exception {
        HentBogRequestDto hentRequestDto = DtoTestUtil.newHentBogRequestDto();
        hentRequestDto.setTitel(titel);
        return hentBog(hentRequestDto);
    }

    public static void assertBogBlevOprettet(OpretBogResponseDto opretBogResponse) {
        assertNotNull(opretBogResponse);
        assertEquals(ResponseDto.StatusKode.OK, opretBogResponse.getStatusKode());
    }

    public static void assertReviewBlevOprettet(OpretReviewResponseDto opretReviewResponseDto) {
        assertNotNull(opretReviewResponseDto);
        assertEquals(ResponseDto.StatusKode.OK, opretReviewResponseDto.getStatusKode());
    }

    public OpretBogResponseDto opretBogMedTitel(String titel) throws Exception {
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

    public OpretReviewResponseDto opretReview(String bogId) throws Exception {
        OpretReviewRequestDto opretReviewRequestDto = DtoTestUtil.newOpretReviewRequestDto();
        opretReviewRequestDto.setBogId(bogId);
        ResultActions opretReviewResult = WebTestUtil.kaldOpretReviewEndpoint(mockMvc, opretReviewRequestDto);
        String responseBody = opretReviewResult.andReturn().getResponse().getContentAsString();
        if (StringUtils.isBlank(responseBody)) {
            return null;
        }
        return new ObjectMapper().readValue(responseBody, OpretReviewResponseDto.class);
    }

    public HentReviewResponseDto hentReview(String reviewId) throws Exception {
        HentReviewRequestDto hentRequestDto = DtoTestUtil.newHentReviewRequestDto();
        hentRequestDto.setReviewId(reviewId);
        ResultActions hentReviewResult = WebTestUtil.kaldHentReviewEndpoint(mockMvc, hentRequestDto);
        String responseBody = hentReviewResult.andReturn().getResponse().getContentAsString();
        if (StringUtils.isBlank(responseBody)) {
            return null;
        }
        return new ObjectMapper().readValue(responseBody, HentReviewResponseDto.class);
    }
}
