package application.service;

import application.dto.HentReviewRequestDto;
import application.dto.HentReviewResponseDto;
import application.dto.ResponseDto;
import application.entity.Review;
import application.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewReaderService {

    private final ReviewRepository reviewRepository;
    
    public HentReviewResponseDto hent(HentReviewRequestDto requestDto) {
        Optional<Review> reviewOptional = findReviewById(requestDto.getReviewId());

        if (reviewOptional.isPresent()) {
            Review review = reviewOptional.get();
            return reviewErHentet(review);
        } else {
            return fejlUkendtReview(requestDto);
        }
    }

    private static HentReviewResponseDto reviewErHentet(Review review) {
        return HentReviewResponseDto.builder()
                .statusKode(ResponseDto.StatusKode.OK)
                .reviewForfatter(review.getReviewForfatter())
                .beskrivelse(review.getBeskrivelse())
                .score(review.getScore())
                .reviewId(review.getId())
                .bogId(review.getBog().getId())
                .build();
    }

    private static HentReviewResponseDto fejlUkendtReview(HentReviewRequestDto requestDto) {
        return HentReviewResponseDto.builder()
                .statusKode(ResponseDto.StatusKode.INPUT_FEJL)
                .statusSubKode(HentReviewResponseDto.StatusSubKode.UKENDT_REVIEW)
                .fejlBeskrivelse("Kunne ikke finde en review med ID " + requestDto.getReviewId())
                .build();
    }
    
    private Optional<Review> findReviewById(String id) {
        return reviewRepository.findById(id);
    }
}
