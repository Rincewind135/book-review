package application.controller;

import application.dto.*;
import application.service.BogReaderService;
import application.service.BogWriterService;
import application.service.ReviewReaderService;
import application.service.ReviewWriterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureDataJpa
class ReviewControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ReviewController reviewController;

    @MockBean
    private BogReaderService bogReaderService;
    @MockBean
    private BogWriterService bogWriterService;
    @MockBean
    private ReviewReaderService reviewReaderService;
    @MockBean
    private ReviewWriterService reviewWriterService;

    @BeforeEach
    void setUp() {
        when(reviewWriterService.opret(any())).thenReturn(OpretReviewResponseDto.builder().statusKode(ResponseDto.StatusKode.OK).build());
        when(reviewReaderService.hent(any())).thenReturn(HentReviewResponseDto.builder().statusKode(ResponseDto.StatusKode.OK).build());
        when(bogWriterService.opret(any())).thenReturn(OpretBogResponseDto.builder().statusKode(ResponseDto.StatusKode.OK).build());
        when(bogReaderService.hent(any())).thenReturn(HentBogResponseDto.builder().statusKode(ResponseDto.StatusKode.OK).build());
    }

    @Test
    void controllerErOppe() throws Exception {
        OpretReviewRequestDto opretRequestDto = newOpretReviewRequestDto();
        HentReviewRequestDto hentRequestDto = newHentReviewRequestDto();

        kaldOpretReviewEndpoint(opretRequestDto, status().isOk());
        kaldHentReviewEndpoint(hentRequestDto, status().isOk());
    }

    @Test
    void opretValidererInput() throws Exception {
        OpretReviewRequestDto requestDto;

        requestDto = newOpretReviewRequestDto();
        requestDto.setBogId(null);
        kaldOpretReviewEndpoint(requestDto, status().isBadRequest());

        requestDto = newOpretReviewRequestDto();
        requestDto.setBogId(requestDto.getBogId().substring(1));
        kaldOpretReviewEndpoint(requestDto, status().isBadRequest());

        requestDto = newOpretReviewRequestDto();
        requestDto.setBogId(requestDto.getBogId() + "x");
        kaldOpretReviewEndpoint(requestDto, status().isBadRequest());

        requestDto = newOpretReviewRequestDto();
        requestDto.setScore(-1);
        kaldOpretReviewEndpoint(requestDto, status().isBadRequest());

        requestDto = newOpretReviewRequestDto();
        requestDto.setScore(6);
        kaldOpretReviewEndpoint(requestDto, status().isBadRequest());

        requestDto = newOpretReviewRequestDto();
        requestDto.setBeskrivelse(null);
        kaldOpretReviewEndpoint(requestDto, status().isBadRequest());

        requestDto = newOpretReviewRequestDto();
        requestDto.setReviewForfatter(null);
        kaldOpretReviewEndpoint(requestDto, status().isBadRequest());
    }

    @Test
    void writerServiceBrokkerSigOverInput() throws Exception {
        when(reviewWriterService.opret(any())).thenReturn(OpretReviewResponseDto.builder().statusKode(ResponseDto.StatusKode.INPUT_FEJL).build());
        OpretReviewRequestDto requestDto = newOpretReviewRequestDto();
        kaldOpretReviewEndpoint(requestDto, status().isBadRequest());
    }

    @Test
    void writerServiceFejler() throws Exception {
        when(reviewWriterService.opret(any())).thenReturn(OpretReviewResponseDto.builder().statusKode(ResponseDto.StatusKode.TEKNISK_FEJL).build());
        OpretReviewRequestDto requestDto = newOpretReviewRequestDto();
        kaldOpretReviewEndpoint(requestDto, status().isInternalServerError());
    }

    private static OpretReviewRequestDto newOpretReviewRequestDto() {
        return OpretReviewRequestDto.builder()
                .bogId(UUID.randomUUID().toString())
                .score(3)
                .beskrivelse("aksjdl")
                .reviewForfatter("Michael")
                .build();
    }

    private HentReviewRequestDto newHentReviewRequestDto() {
        return HentReviewRequestDto.builder()
                .reviewId(UUID.randomUUID().toString())
                .build();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void kaldOpretReviewEndpoint(OpretReviewRequestDto requestDto, ResultMatcher expectedResult) throws Exception {
        mockMvc.perform(post("/review/opret")
                        .contentType("application/json")
                        .content(asJsonString(requestDto)))
                .andExpect(expectedResult);
    }

    private void kaldHentReviewEndpoint(HentReviewRequestDto requestDto, ResultMatcher expectedResult) throws Exception {
        mockMvc.perform(get("/review/hent")
                        .contentType("application/json")
                        .content(asJsonString(requestDto)))
                .andExpect(expectedResult);
    }
}