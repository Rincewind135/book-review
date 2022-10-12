package application.service;

import application.dto.*;
import application.entity.Bog;
import application.entity.Review;
import application.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class ReviewServiceTest {

    private ReviewService reviewService;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private BogReaderService bogReaderService;

    private OpretReviewRequestDto request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        request = OpretReviewRequestDto.builder()
                .bogId(UUID.randomUUID().toString())
                .reviewForfatter("Stephen King")
                .beskrivelse("A good book")
                .score(4)
                .build();

        when(bogReaderService.findBogById(request.getBogId())).thenReturn(Optional.of(Bog.builder().id(request.getBogId()).build()));
        when(reviewRepository.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
        reviewService = new ReviewService(bogReaderService, reviewRepository);
    }

    @Test
    void solskin() {
        // Arrange

        // Act
        OpretReviewResponseDto result = reviewService.opret(request);

        // Assert
        assertResponseOK(result);
        Review savedReview = getSavedReview();
        assertEquals(result.getReviewId(), savedReview.getId());
        assertEquals(request.getReviewForfatter(), savedReview.getReviewForfatter());
        assertEquals(request.getScore(), savedReview.getScore());
        assertEquals(request.getBeskrivelse(), savedReview.getBeskrivelse());
        assertNotNull(savedReview.getBog());
        assertEquals(request.getBogId(), savedReview.getBog().getId());
    }

    @Test
    void bogFindesIkke() {
        // Arrange
        when(bogReaderService.findBogById(request.getBogId()))
                .thenReturn(Optional.empty());

        // Act
        OpretReviewResponseDto result = reviewService.opret(request);

        // Assert
        assertResponseFEJL(result, OpretReviewResponseDto.StatusSubKode.UKENDT_BOG);
        verify(reviewRepository, times(0)).save(any());
    }

    @Test
    void scoreForlav() {
        // Arrange
        request.setScore(-1);

        // Act
        OpretReviewResponseDto result = reviewService.opret(request);

        // Assert
        assertResponseFEJL(result, OpretReviewResponseDto.StatusSubKode.UGYLDIG_SCORE);
        verify(reviewRepository, times(0)).save(any());
    }

    @Test
    void scoreForHoej() {
        // Arrange
        request.setScore(6);

        // Act
        OpretReviewResponseDto result = reviewService.opret(request);

        // Assert
        assertResponseFEJL(result, OpretReviewResponseDto.StatusSubKode.UGYLDIG_SCORE);
        verify(reviewRepository, times(0)).save(any());
    }

    private Review getSavedReview() {
        ArgumentCaptor<Review> reviewArgumentCaptor = ArgumentCaptor.forClass(Review.class);
        verify(reviewRepository).save(reviewArgumentCaptor.capture());
        return reviewArgumentCaptor.getValue();
    }

    private void assertResponseOK(OpretReviewResponseDto result) {
        assertNotNull(result);
        assertEquals(ResponseDto.StatusKode.OK, result.getStatusKode());
        assertNull(result.getStatusSubKode());
        assertNotNull(result.getReviewId());
    }

    private void assertResponseFEJL(OpretReviewResponseDto result, OpretReviewResponseDto.StatusSubKode subKode) {
        assertNotNull(result);
        assertEquals(ResponseDto.StatusKode.FEJL, result.getStatusKode());
        assertEquals(subKode, result.getStatusSubKode());
        assertNotNull(result.getFejlBeskrivelse());
        assertNull(result.getReviewId());
    }
}