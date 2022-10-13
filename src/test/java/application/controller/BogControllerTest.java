package application.controller;

import application.DtoTestUtil;
import application.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureDataJpa
class BogControllerTest extends AbstractControllerTest {

    @Test
    void controllerErOppe() throws Exception {
        OpretBogRequestDto opretRequestDto = DtoTestUtil.newOpretBogRequestDto();
        HentBogRequestDto hentRequestDto = DtoTestUtil.newHentBogRequestDto();

        kaldOpretBogEndpoint(opretRequestDto, status().isOk());
        kaldHentBogEndpoint(hentRequestDto, status().isOk());
    }

    @Test
    void opretValidererInput() throws Exception {
        OpretBogRequestDto requestDto;

        requestDto = DtoTestUtil.newOpretBogRequestDto();
        requestDto.setTitel(null);
        kaldOpretBogEndpoint(requestDto, status().isBadRequest());

        requestDto = DtoTestUtil.newOpretBogRequestDto();
        requestDto.setForfatter(null);
        kaldOpretBogEndpoint(requestDto, status().isBadRequest());

        requestDto = DtoTestUtil.newOpretBogRequestDto();
        requestDto.setBlurb(null);
        kaldOpretBogEndpoint(requestDto, status().isBadRequest());
    }

    @Test
    void writerServiceBrokkerSigOverInput() throws Exception {
        when(bogWriterService.opret(any())).thenReturn(OpretBogResponseDto.builder().statusKode(ResponseDto.StatusKode.INPUT_FEJL).build());
        OpretBogRequestDto requestDto = DtoTestUtil.newOpretBogRequestDto();
        kaldOpretBogEndpoint(requestDto, status().isBadRequest());
    }

    @Test
    void writerServiceFejler() throws Exception {
        when(bogWriterService.opret(any())).thenReturn(OpretBogResponseDto.builder().statusKode(ResponseDto.StatusKode.TEKNISK_FEJL).build());
        OpretBogRequestDto requestDto = DtoTestUtil.newOpretBogRequestDto();
        kaldOpretBogEndpoint(requestDto, status().isInternalServerError());
    }

    @Test
    void hentValidererInput() throws Exception {
        HentBogRequestDto requestDto;

        requestDto = DtoTestUtil.newHentBogRequestDto();
        requestDto.setTitel(null);
        kaldHentBogEndpoint(requestDto, status().isBadRequest());
    }

    @Test
    void readerServiceBrokkerSigOverInput() throws Exception {
        when(bogReaderService.hent(any())).thenReturn(HentBogResponseDto.builder().statusKode(ResponseDto.StatusKode.INPUT_FEJL).build());
        HentBogRequestDto requestDto = DtoTestUtil.newHentBogRequestDto();
        kaldHentBogEndpoint(requestDto, status().isBadRequest());
    }

    @Test
    void readerServiceFejler() throws Exception {
        when(bogReaderService.hent(any())).thenReturn(HentBogResponseDto.builder().statusKode(ResponseDto.StatusKode.TEKNISK_FEJL).build());
        HentBogRequestDto requestDto = DtoTestUtil.newHentBogRequestDto();
        kaldHentBogEndpoint(requestDto, status().isInternalServerError());
    }
}