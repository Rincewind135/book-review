package application.util;

import application.dto.*;

import java.util.UUID;

public class DtoTestUtil {


    public static OpretReviewRequestDto newOpretReviewRequestDto() {
        return OpretReviewRequestDto.builder()
                .bogId(UUID.randomUUID().toString())
                .score(3)
                .beskrivelse("aksjdl")
                .reviewForfatter("Michael")
                .transaktionsId(UUID.randomUUID().toString())
                .build();
    }

    public static HentReviewRequestDto newHentReviewRequestDto() {
        return HentReviewRequestDto.builder()
                .reviewId(UUID.randomUUID().toString())
                .transaktionsId(UUID.randomUUID().toString())
                .build();
    }

    public static OpretBogRequestDto newOpretBogRequestDto() {
        return OpretBogRequestDto.builder()
                .titel("The Dark Tower")
                .forfatter("Stephen King")
                .blurb("A good book")
                .transaktionsId(UUID.randomUUID().toString())
                .build();
    }

    public static HentBogRequestDto newHentBogRequestDto() {
        return HentBogRequestDto.builder()
                .titel("The Dark Tower")
                .transaktionsId(UUID.randomUUID().toString())
                .build();
    }

    public static OpretReviewResponseDto newOpretReviewResponseDto(ResponseDto.StatusKode ok) {
        OpretReviewResponseDto responseDto = new OpretReviewResponseDto();
        responseDto.setStatusKode(ok);
        return responseDto;
    }

    public static HentReviewResponseDto newHentReviewResponseDto(ResponseDto.StatusKode ok) {
        HentReviewResponseDto responseDto = new HentReviewResponseDto();
        responseDto.setStatusKode(ok);
        return responseDto;
    }

    public static OpretBogResponseDto newOpretBogResponseDto(ResponseDto.StatusKode ok) {
        OpretBogResponseDto responseDto = new OpretBogResponseDto();
        responseDto.setStatusKode(ok);
        return responseDto;
    }

    public static HentBogResponseDto newHentBogResponseDto(ResponseDto.StatusKode ok) {
        HentBogResponseDto responseDto = new HentBogResponseDto();
        responseDto.setStatusKode(ok);
        return responseDto;
    }
}
