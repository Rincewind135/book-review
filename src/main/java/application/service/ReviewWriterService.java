package application.service;

import application.dto.OpretReviewRequestDto;
import application.dto.OpretReviewResponseDto;
import application.dto.ResponseDto;
import application.entity.Review;
import application.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewWriterService {

    private final BogReaderService bogReaderService;
    private final ReviewRepository reviewRepository;

    public OpretReviewResponseDto opret(OpretReviewRequestDto requestDto) {
        try {
            return opretHvisValid(requestDto);
        } catch (Exception e) {
            return tekniskFejl(e);
        }
    }

    private OpretReviewResponseDto opretHvisValid(OpretReviewRequestDto requestDto) {
        Optional<OpretReviewResponseDto> fejlFundet = validerInput(requestDto);
        if (fejlFundet.isPresent()) {
            return fejlFundet.get();
        }
        Review review = opretReview(requestDto);
        return reviewOprettetOkay(review);
    }

    private Optional<OpretReviewResponseDto> validerInput(OpretReviewRequestDto requestDto) {
        if (requestDto.getScore() < 0) {
            return fejlUgyldigScore("Score må mindst være 0");
        }
        if (requestDto.getScore() > 5) {
            return fejlUgyldigScore("Score må højest være 0");
        }
        if (!bogReaderService.findBogById(requestDto.getBogId()).isPresent()) {
            return fejlUkendtBog(requestDto.getBogId());
        }

        return Optional.empty();
    }

    private static Optional<OpretReviewResponseDto> fejlUgyldigScore(String fejlBeskrivelse) {
        return Optional.of(
                OpretReviewResponseDto.builder()
                        .statusKode(ResponseDto.StatusKode.INPUT_FEJL)
                        .statusSubKode(OpretReviewResponseDto.StatusSubKode.UGYLDIG_SCORE)
                        .fejlBeskrivelse(fejlBeskrivelse)
                        .build()
        );
    }

    private static Optional<OpretReviewResponseDto> fejlUkendtBog(String bogId) {
        return Optional.of(
                OpretReviewResponseDto.builder()
                        .statusKode(ResponseDto.StatusKode.INPUT_FEJL)
                        .statusSubKode(OpretReviewResponseDto.StatusSubKode.UKENDT_BOG)
                        .fejlBeskrivelse("Kunne ikke finde en bog med ID " + bogId)
                        .build()
        );
    }

    private Review opretReview(OpretReviewRequestDto requestDto) {
        return reviewRepository.save(Review.builder()
                .id(UUID. randomUUID().toString())
                .reviewForfatter(requestDto.getReviewForfatter())
                .score(requestDto.getScore())
                .beskrivelse(requestDto.getBeskrivelse())
                .bog(bogReaderService.findBogById(requestDto.getBogId()).orElse(null))
                .build());
    }

    private static OpretReviewResponseDto reviewOprettetOkay(Review review) {
        return OpretReviewResponseDto.builder()
                .reviewId(review.getId())
                .statusKode(ResponseDto.StatusKode.OK)
                .build();
    }

    private OpretReviewResponseDto tekniskFejl(Exception e) {
        return OpretReviewResponseDto.builder()
                .statusKode(ResponseDto.StatusKode.TEKNISK_FEJL)
                .statusSubKode(OpretReviewResponseDto.StatusSubKode.EXCEPTION_THROWN)
                .fejlBeskrivelse(e.getMessage())
                .build();

    }
}
