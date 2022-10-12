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
public class ReviewService {

    private final BogService bogService;
    private final ReviewRepository reviewRepository;

    public Optional<Review> hent(String reviewId) {
        return reviewRepository.findById(reviewId);
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
        if (!bogService.findBogById(requestDto.getBogId()).isPresent()) {
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
        return reviewRepository.save(Review.builder()
                .id(UUID. randomUUID().toString())
                .reviewForfatter(requestDto.getReviewForfatter())
                .score(requestDto.getScore())
                .beskrivelse(requestDto.getBeskrivelse())
                .bog(bogService.findBogById(requestDto.getBogId()).orElse(null))
                .build());
    }
}
