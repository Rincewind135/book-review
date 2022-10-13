package application.persistence;

import application.entity.Bog;
import application.entity.Review;

import java.util.UUID;


public class AbstractEntityTest {
    public Bog opretBog() {
        return Bog.builder()
                .id(UUID.randomUUID().toString())
                .titel(UUID.randomUUID().toString())
                .forfatter(UUID.randomUUID().toString())
                .blurb(UUID.randomUUID().toString())
                .build();
    }

    public Review opretReview(Bog bog) {
        return Review.builder()
                .id(UUID.randomUUID().toString())
                .beskrivelse(UUID.randomUUID().toString())
                .reviewForfatter(UUID.randomUUID().toString())
                .score(3)
                .bog(bog)
                .build();
    }
}