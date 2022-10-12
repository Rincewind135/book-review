package application.service;

import application.dto.OpretReviewRequestDto;
import application.dto.OpretReviewResponseDto;
import application.dto.ResponseDto;
import application.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final BogService bogService;

    public String get(String bookId) {
        if (bookId == null) {
            return "Invalid ID!";
        }
        if (bookId.equals("The Dark Tower")) {
            return "A great book";
        }
        return "I don't know that book, sorry";
    }

    public OpretReviewResponseDto opret(OpretReviewRequestDto requestDto) {

        Optional<OpretReviewResponseDto> fejlFundet = validerInput(requestDto);

        if (fejlFundet.isPresent()) {
            return fejlFundet.get();
        }


        Review review = opretReview(requestDto);

        return OpretReviewResponseDto.builder()
                .reviewId(review.getId())
                .statusKode(ResponseDto.StatusKode.OK)
                .build();
    }

    private Optional<OpretReviewResponseDto> validerInput(OpretReviewRequestDto requestDto) {
        if (requestDto.getScore() < 0) {
            return fejlUgyldigScore("Score må mindst være 0");
        }
        if (requestDto.getScore() > 5) {
            return fejlUgyldigScore("Score må højest være 0");
        }
        if (bogService.findBogById(requestDto.getBogId()) == null) {
            return fejlUkendtBog(requestDto.getBogId());
        }

        return Optional.empty();
    }

    private static Optional<OpretReviewResponseDto> fejlUgyldigScore(String fejlBeskrivelse) {
        return Optional.of(
                OpretReviewResponseDto.builder()
                        .statusKode(ResponseDto.StatusKode.FEJL)
                        .statusSubKode(OpretReviewResponseDto.StatusSubKode.UGYLDIG_SCORE)
                        .fejlBeskrivelse(fejlBeskrivelse)
                        .build()
        );
    }

    private static Optional<OpretReviewResponseDto> fejlUkendtBog(String bogId) {
        return Optional.of(
                OpretReviewResponseDto.builder()
                        .statusKode(ResponseDto.StatusKode.FEJL)
                        .statusSubKode(OpretReviewResponseDto.StatusSubKode.UKENDT_BOG)
                        .fejlBeskrivelse("Kunne ikke finde en bog med ID " + bogId)
                        .build()
        );
    }

    private Review opretReview(OpretReviewRequestDto requestDto) {
        return Review.builder()
                .id(UUID. randomUUID().toString())
                .reviewAuthor(requestDto.getReviewForfatter())
                .score(requestDto.getScore())
                .description(requestDto.getBeskrivelse())
                .bog(bogService.findBogById(requestDto.getBogId()).orElse(null))
                .build();
    }
}
