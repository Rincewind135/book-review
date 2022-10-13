package application;

import application.dto.HentBogRequestDto;
import application.dto.HentReviewRequestDto;
import application.dto.OpretBogRequestDto;
import application.dto.OpretReviewRequestDto;

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
}
