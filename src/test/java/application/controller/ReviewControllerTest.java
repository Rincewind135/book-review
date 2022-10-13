package application.controller;

import application.util.DtoTestUtil;
import application.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureDataJpa
class ReviewControllerTest extends AbstractControllerTest {

    @Test
    void controllerErOppe() throws Exception {
        OpretReviewRequestDto opretRequestDto = DtoTestUtil.newOpretReviewRequestDto();
        HentReviewRequestDto hentRequestDto = DtoTestUtil.newHentReviewRequestDto();

        kaldOpretReviewEndpoint(opretRequestDto, status().isOk());
        kaldHentReviewEndpoint(hentRequestDto, status().isOk());
    }

    @Test
    void opretValidererInput() throws Exception {
        OpretReviewRequestDto requestDto;

        requestDto = DtoTestUtil.newOpretReviewRequestDto();
        requestDto.setBogId(null);
        kaldOpretReviewEndpoint(requestDto, status().isBadRequest());

        requestDto = DtoTestUtil.newOpretReviewRequestDto();
        requestDto.setBogId(requestDto.getBogId().substring(1));
        kaldOpretReviewEndpoint(requestDto, status().isBadRequest());

        requestDto = DtoTestUtil.newOpretReviewRequestDto();
        requestDto.setBogId(requestDto.getBogId() + "x");
        kaldOpretReviewEndpoint(requestDto, status().isBadRequest());

        requestDto = DtoTestUtil.newOpretReviewRequestDto();
        requestDto.setScore(-1);
        kaldOpretReviewEndpoint(requestDto, status().isBadRequest());

        requestDto = DtoTestUtil.newOpretReviewRequestDto();
        requestDto.setScore(6);
        kaldOpretReviewEndpoint(requestDto, status().isBadRequest());

        requestDto = DtoTestUtil.newOpretReviewRequestDto();
        requestDto.setBeskrivelse(null);
        kaldOpretReviewEndpoint(requestDto, status().isBadRequest());

        requestDto = DtoTestUtil.newOpretReviewRequestDto();
        requestDto.setReviewForfatter(null);
        kaldOpretReviewEndpoint(requestDto, status().isBadRequest());
    }

    @Test
    void writerServiceBrokkerSigOverInput() throws Exception {
        when(reviewWriterService.opret(any())).thenReturn(DtoTestUtil.newOpretReviewResponseDto(ResponseDto.StatusKode.INPUT_FEJL));
        OpretReviewRequestDto requestDto = DtoTestUtil.newOpretReviewRequestDto();
        kaldOpretReviewEndpoint(requestDto, status().isBadRequest());
    }

    @Test
    void writerServiceFejler() throws Exception {
        when(reviewWriterService.opret(any())).thenReturn(DtoTestUtil.newOpretReviewResponseDto(ResponseDto.StatusKode.TEKNISK_FEJL));
        OpretReviewRequestDto requestDto = DtoTestUtil.newOpretReviewRequestDto();
        kaldOpretReviewEndpoint(requestDto, status().isInternalServerError());
    }

    @Test
    void hentValidererInput() throws Exception {
        HentReviewRequestDto requestDto;

        requestDto = DtoTestUtil.newHentReviewRequestDto();
        requestDto.setReviewId(null);
        kaldHentReviewEndpoint(requestDto, status().isBadRequest());

        requestDto = DtoTestUtil.newHentReviewRequestDto();
        requestDto.setReviewId(requestDto.getReviewId().substring(1));
        kaldHentReviewEndpoint(requestDto, status().isBadRequest());

        requestDto = DtoTestUtil.newHentReviewRequestDto();
        requestDto.setReviewId(requestDto.getReviewId() + "x");
        kaldHentReviewEndpoint(requestDto, status().isBadRequest());
    }

    @Test
    void readerServiceBrokkerSigOverInput() throws Exception {
        when(reviewReaderService.hent(any())).thenReturn(DtoTestUtil.newHentReviewResponseDto(ResponseDto.StatusKode.INPUT_FEJL));
        HentReviewRequestDto requestDto = DtoTestUtil.newHentReviewRequestDto();
        kaldHentReviewEndpoint(requestDto, status().isBadRequest());
    }

    @Test
    void readerServiceFejler() throws Exception {
        when(reviewReaderService.hent(any())).thenReturn(DtoTestUtil.newHentReviewResponseDto(ResponseDto.StatusKode.TEKNISK_FEJL));
        HentReviewRequestDto requestDto = DtoTestUtil.newHentReviewRequestDto();
        kaldHentReviewEndpoint(requestDto, status().isInternalServerError());
    }
}