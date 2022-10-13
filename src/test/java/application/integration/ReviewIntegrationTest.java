package application.integration;

import application.dto.HentBogResponseDto;
import application.dto.HentReviewResponseDto;
import application.dto.OpretBogResponseDto;
import application.dto.OpretReviewResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void opretOgHentReview() throws Exception {
        String bogId = opretBog();
        OpretReviewResponseDto opretReviewResponse = opretReview(bogId);
        assertReviewBlevOprettet(opretReviewResponse);

        HentReviewResponseDto hentReviewResponse = hentReview(opretReviewResponse.getReviewId());
        assertReviewBlevHentet(hentReviewResponse);
    }

    @Test
    public void reviewIkkeFundet() throws Exception {
        String bogId = opretBog();
        OpretReviewResponseDto opretReviewResponse = opretReview(bogId);
        assertReviewBlevOprettet(opretReviewResponse);

        String nytReviewId = UUID.randomUUID().toString();
        HentReviewResponseDto hentReviewResponse = hentReview(nytReviewId);
        assertReviewBlevIkkeFundet(hentReviewResponse);
    }

    @Test
    public void reviewIkkeOprettetGrundetDaarligInput() throws Exception {
        String bogSomIkkeEksisterer = UUID.randomUUID().toString();
        OpretReviewResponseDto opretReviewResponse = opretReview(bogSomIkkeEksisterer);
        assertReviewBlevIkkeOprettet(opretReviewResponse);
    }

    private String opretBog() throws Exception {
        String titel = UUID.randomUUID().toString();
        OpretBogResponseDto opretBogResponse = opretBogMedTitel(titel);
        assertBogBlevOprettet(opretBogResponse);

        HentBogResponseDto hentBogResponse = hentBogMedTitel(titel);
        assertBogBlevHentet(hentBogResponse);
        return hentBogResponse.getBogId();
    }

}
