package application.controller;

import application.dto.HentReviewResponseDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewControllerTest {
    @Autowired
    private ReviewController reviewController;

    @Test
    @Disabled
    void sunshine() {
        // Arrange
        String bookId = "The Dark Tover";

        // Act
        HentReviewResponseDto result = reviewController.hent(null);

        // Assert
        assertNotNull(result);
        assertEquals("A great book", result);
        String a = "bob";
        String b = "ks";
    }
}