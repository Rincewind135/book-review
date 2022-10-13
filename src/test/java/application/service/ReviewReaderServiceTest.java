package application.service;

import application.dto.HentReviewRequestDto;
import application.dto.HentReviewResponseDto;
import application.dto.ResponseDto;
import application.entity.Bog;
import application.entity.Review;
import application.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ReviewReaderServiceTest {

    private ReviewReaderService reviewReaderService;
    @Mock
    private ReviewRepository reviewRepository;

    private HentReviewRequestDto request;
    private Review review;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        request = HentReviewRequestDto.builder()
                .reviewId(UUID.randomUUID().toString())
                .transaktionsId(UUID.randomUUID().toString())
                .build();

        reviewReaderService = new ReviewReaderService(reviewRepository);
        review = Review.builder()
                .id(UUID.randomUUID().toString())
                .reviewForfatter("Raistlin Majere")
                .score(3)
                .beskrivelse("A great spellbook")
                .bog(Bog.builder().id(UUID.randomUUID().toString()).build())
                .build();
        when(reviewRepository.findById(request.getReviewId()))
                .thenReturn(Optional.of(
                        review
                ));
    }

    @Test
    void solskin() {
        // Arrange

        // Act
        HentReviewResponseDto result = reviewReaderService.hent(request);

        // Assert
        assertResponseOK(result);
        assertEquals(review.getId(), result.getReviewId());
        assertEquals(review.getId(), result.getReviewId());
        assertEquals(review.getReviewForfatter(), result.getReviewForfatter());
        assertEquals(review.getScore(), result.getScore());
        assertEquals(review.getBeskrivelse(), result.getBeskrivelse());
    }

    @Test
    void reviewFindesIkke() {
        // Arrange
        when(reviewRepository.findById(request.getReviewId()))
                .thenReturn(Optional.empty());

        // Act
        HentReviewResponseDto result = reviewReaderService.hent(request);

        // Assert
        assertResponseFEJL(result, ResponseDto.StatusKode.INPUT_FEJL, HentReviewResponseDto.StatusSubKode.UKENDT_REVIEW);
    }

    @Test
    void exceptionUnderLaesning() {
        // Arrange
        when(reviewRepository.findById(any()))
                .thenThrow(new RuntimeException("Boom!"));

        // Act
        HentReviewResponseDto result = reviewReaderService.hent(request);

        // Assert
        assertResponseFEJL(result, ResponseDto.StatusKode.TEKNISK_FEJL, HentReviewResponseDto.StatusSubKode.EXCEPTION_THROWN);
    }

    private void assertResponseOK(HentReviewResponseDto result) {
        assertNotNull(result);
        assertEquals(ResponseDto.StatusKode.OK, result.getStatusKode());
        assertNull(result.getStatusSubKode());
        assertNotNull(result.getTransaktionsId());
        assertNotNull(result.getBogId());
    }

    private void assertResponseFEJL(HentReviewResponseDto result, ResponseDto.StatusKode statusKode, HentReviewResponseDto.StatusSubKode subKode) {
        assertNotNull(result);
        assertEquals(statusKode, result.getStatusKode());
        assertEquals(subKode, result.getStatusSubKode());
        assertNotNull(result.getFejlBeskrivelse());
        assertNull(result.getBogId());
        assertNotNull(result.getTransaktionsId());
    }
}