package application.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewControllerTest {
    @Autowired
    private ReviewController reviewController;

    @Test
    void sunshine() {
        // Arrange
        String bookId = "The Dark Tover";

        // Act
        String result = reviewController.hent();

        // Assert
        assertNotNull(result);
        assertEquals("A great book", result);
        String a = "bob";
        String b = "ks";
    }
}