package application.controller;

import application.dto.*;
import application.service.BogReaderService;
import application.service.BogWriterService;
import application.service.ReviewReaderService;
import application.service.ReviewWriterService;
import application.util.DtoTestUtil;
import application.util.WebTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
        OpretReviewResponseDto opretReviewResponseDto = DtoTestUtil.newOpretReviewResponseDto(ResponseDto.StatusKode.OK);
        when(reviewWriterService.opret(any())).thenReturn(opretReviewResponseDto);

        HentReviewResponseDto hentReviewResponseDto = DtoTestUtil.newHentReviewResponseDto(ResponseDto.StatusKode.OK);
        when(reviewReaderService.hent(any())).thenReturn(hentReviewResponseDto);

        OpretBogResponseDto opretBogResponseDto = DtoTestUtil.newOpretBogResponseDto(ResponseDto.StatusKode.OK);
        when(bogWriterService.opret(any())).thenReturn(opretBogResponseDto);

        HentBogResponseDto hentBogResponseDto = DtoTestUtil.newHentBogResponseDto(ResponseDto.StatusKode.OK);
        when(bogReaderService.hent(any())).thenReturn(hentBogResponseDto);
    }

    public void kaldOpretBogEndpoint(OpretBogRequestDto requestDto, ResultMatcher expectedResult) throws Exception {
        WebTestUtil.kaldOpretBogEndpoint(mockMvc, requestDto)
                .andExpect(expectedResult);
    }

    public void kaldHentBogEndpoint(HentBogRequestDto requestDto, ResultMatcher expectedResult) throws Exception {
        WebTestUtil.kaldHentBogEndpoint(mockMvc, requestDto)
                .andExpect(expectedResult);
    }

    public void kaldOpretReviewEndpoint(OpretReviewRequestDto requestDto, ResultMatcher expectedResult) throws Exception {
        WebTestUtil.kaldOpretReviewEndpoint(mockMvc, requestDto)
                .andExpect(expectedResult);
    }

    public void kaldHentReviewEndpoint(HentReviewRequestDto requestDto, ResultMatcher expectedResult) throws Exception {
        WebTestUtil.kaldHentReviewEndpoint(mockMvc, requestDto)
                .andExpect(expectedResult);
    }
}