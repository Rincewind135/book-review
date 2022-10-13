package application.controller;

import application.DtoTestUtil;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureDataJpa
class BogControllerTest {
    @Autowired
    private MockMvc mockMvc;

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
        OpretBogRequestDto opretRequestDto = newOpretBogRequestDto();
        HentBogRequestDto hentRequestDto = newHentBogRequestDto();

        kaldOpretBogEndpoint(opretRequestDto, status().isOk());
        kaldHentBogEndpoint(hentRequestDto, status().isOk());
    }

    @Test
    void opretValidererInput() throws Exception {
        OpretBogRequestDto requestDto;

        requestDto = newOpretBogRequestDto();
        requestDto.setTitel(null);
        kaldOpretBogEndpoint(requestDto, status().isBadRequest());

        requestDto = newOpretBogRequestDto();
        requestDto.setForfatter(null);
        kaldOpretBogEndpoint(requestDto, status().isBadRequest());

        requestDto = newOpretBogRequestDto();
        requestDto.setBlurb(null);
        kaldOpretBogEndpoint(requestDto, status().isBadRequest());
    }

    @Test
    void writerServiceBrokkerSigOverInput() throws Exception {
        when(bogWriterService.opret(any())).thenReturn(OpretBogResponseDto.builder().statusKode(ResponseDto.StatusKode.INPUT_FEJL).build());
        OpretBogRequestDto requestDto = newOpretBogRequestDto();
        kaldOpretBogEndpoint(requestDto, status().isBadRequest());
    }

    @Test
    void writerServiceFejler() throws Exception {
        when(bogWriterService.opret(any())).thenReturn(OpretBogResponseDto.builder().statusKode(ResponseDto.StatusKode.TEKNISK_FEJL).build());
        OpretBogRequestDto requestDto = newOpretBogRequestDto();
        kaldOpretBogEndpoint(requestDto, status().isInternalServerError());
    }

    @Test
    void hentValidererInput() throws Exception {
        HentBogRequestDto requestDto;

        requestDto = newHentBogRequestDto();
        requestDto.setTitel(null);
        kaldHentBogEndpoint(requestDto, status().isBadRequest());
    }

    @Test
    void readerServiceBrokkerSigOverInput() throws Exception {
        when(bogReaderService.hent(any())).thenReturn(HentBogResponseDto.builder().statusKode(ResponseDto.StatusKode.INPUT_FEJL).build());
        HentBogRequestDto requestDto = newHentBogRequestDto();
        kaldHentBogEndpoint(requestDto, status().isBadRequest());
    }

    @Test
    void readerServiceFejler() throws Exception {
        when(bogReaderService.hent(any())).thenReturn(HentBogResponseDto.builder().statusKode(ResponseDto.StatusKode.TEKNISK_FEJL).build());
        HentBogRequestDto requestDto = newHentBogRequestDto();
        kaldHentBogEndpoint(requestDto, status().isInternalServerError());
    }

    private static OpretBogRequestDto newOpretBogRequestDto() {
        return DtoTestUtil.newOpretBogRequestDto();
    }

    private HentBogRequestDto newHentBogRequestDto() {
        return DtoTestUtil.newHentBogRequestDto();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void kaldOpretBogEndpoint(OpretBogRequestDto requestDto, ResultMatcher expectedResult) throws Exception {
        mockMvc.perform(post("/bog/opret")
                        .contentType("application/json")
                        .content(asJsonString(requestDto)))
                .andExpect(expectedResult);
    }

    private void kaldHentBogEndpoint(HentBogRequestDto requestDto, ResultMatcher expectedResult) throws Exception {
        mockMvc.perform(get("/bog/hent")
                        .contentType("application/json")
                        .content(asJsonString(requestDto)))
                .andExpect(expectedResult);
    }
}