package application.controller;

import application.dto.*;
import application.service.BogReaderService;
import application.service.BogWriterService;
import application.service.ReviewReaderService;
import application.service.ReviewWriterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public abstract class AbstractControllerTest {
    @Autowired
    public MockMvc mockMvc;

    @MockBean
    public BogReaderService bogReaderService;
    @MockBean
    public BogWriterService bogWriterService;
    @MockBean
    public ReviewReaderService reviewReaderService;
    @MockBean
    public ReviewWriterService reviewWriterService;

    @BeforeEach
    void setUp() {
        when(reviewWriterService.opret(any())).thenReturn(OpretReviewResponseDto.builder().statusKode(ResponseDto.StatusKode.OK).build());
        when(reviewReaderService.hent(any())).thenReturn(HentReviewResponseDto.builder().statusKode(ResponseDto.StatusKode.OK).build());
        when(bogWriterService.opret(any())).thenReturn(OpretBogResponseDto.builder().statusKode(ResponseDto.StatusKode.OK).build());
        when(bogReaderService.hent(any())).thenReturn(HentBogResponseDto.builder().statusKode(ResponseDto.StatusKode.OK).build());
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void kaldOpretBogEndpoint(OpretBogRequestDto requestDto, ResultMatcher expectedResult) throws Exception {
        mockMvc.perform(post("/bog/opret")
                        .contentType("application/json")
                        .content(asJsonString(requestDto)))
                .andExpect(expectedResult);
    }

    public void kaldHentBogEndpoint(HentBogRequestDto requestDto, ResultMatcher expectedResult) throws Exception {
        mockMvc.perform(get("/bog/hent")
                        .contentType("application/json")
                        .content(asJsonString(requestDto)))
                .andExpect(expectedResult);
    }

    public void kaldOpretReviewEndpoint(OpretReviewRequestDto requestDto, ResultMatcher expectedResult) throws Exception {
        mockMvc.perform(post("/review/opret")
                        .contentType("application/json")
                        .content(asJsonString(requestDto)))
                .andExpect(expectedResult);
    }

    public void kaldHentReviewEndpoint(HentReviewRequestDto requestDto, ResultMatcher expectedResult) throws Exception {
        mockMvc.perform(get("/review/hent")
                        .contentType("application/json")
                        .content(asJsonString(requestDto)))
                .andExpect(expectedResult);
    }
}