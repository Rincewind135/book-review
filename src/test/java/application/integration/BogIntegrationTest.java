package application.integration;

import application.dto.HentBogResponseDto;
import application.dto.OpretBogResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class BogIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void opretOgHentBog() throws Exception {

        String titel = UUID.randomUUID().toString();
        OpretBogResponseDto opretBogResponse = opretBogMedTitel(titel);
        assertBogBlevOprettet(opretBogResponse);

        HentBogResponseDto hentBogResponse = hentBogMedTitel(titel);
        assertBogBlevHentet(hentBogResponse);
    }

    @Test
    public void bogIkkeFundet() throws Exception {

        String titel = UUID.randomUUID().toString();
        OpretBogResponseDto opretBogResponse = opretBogMedTitel(titel);
        assertBogBlevOprettet(opretBogResponse);

        String nyTitel = UUID.randomUUID().toString();
        HentBogResponseDto hentBogResponse = hentBogMedTitel(nyTitel);
        assertBogBlevIkkeFundet(hentBogResponse);

        OpretBogResponseDto nyBogOprettetResponse = opretBogMedTitel(nyTitel);
        assertBogBlevOprettet(nyBogOprettetResponse);
    }

    @Test
    public void bogIkkeOprettetGrundetDaarligInput() throws Exception {

        String titel = null;
        OpretBogResponseDto opretBogResponse = opretBogMedTitel(titel);
        assertBogBlevIkkeOprettet(opretBogResponse);

        titel = UUID.randomUUID().toString();
        opretBogResponse = opretBogMedTitel(titel);
        assertBogBlevOprettet(opretBogResponse);
    }
}
