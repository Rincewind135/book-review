package application.service;

import application.dto.HentBogRequestDto;
import application.dto.HentBogResponseDto;
import application.dto.ResponseDto;
import application.entity.Bog;
import application.repository.BogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class BogReaderServiceTest {

    private BogReaderService bogReaderService;
    @Mock
    private BogRepository bogRepository;

    private HentBogRequestDto request;
    private Bog bog;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        request = HentBogRequestDto.builder()
                .titel("The Dark Tower")
                .build();

        bogReaderService = new BogReaderService(bogRepository);
        bog = Bog.builder()
                .id(UUID.randomUUID().toString())
                .titel(request.getTitel())
                .forfatter("Stephen King")
                .blurb("A great book")
                .build();
        when(bogRepository.findBogByTitel(request.getTitel()))
                .thenReturn(Optional.of(
                        bog
                ));
    }

    @Test
    void solskin() {
        // Arrange

        // Act
        HentBogResponseDto result = bogReaderService.hent(request);

        // Assert
        assertResponseOK(result);
        assertEquals(bog.getId(), result.getBogId());
        assertEquals(bog.getTitel(), result.getTitel());
        assertEquals(bog.getForfatter(), result.getForfatter());
        assertEquals(bog.getBlurb(), result.getBlurb());
    }

    @Test
    void bogFindesIkke() {
        // Arrange
        when(bogRepository.findBogByTitel(request.getTitel()))
                .thenReturn(Optional.empty());

        // Act
        HentBogResponseDto result = bogReaderService.hent(request);

        // Assert
        assertResponseFEJL(result, HentBogResponseDto.StatusSubKode.UKENDT_BOG);
    }

    private void assertResponseOK(HentBogResponseDto result) {
        assertNotNull(result);
        assertEquals(ResponseDto.StatusKode.OK, result.getStatusKode());
        assertNull(result.getStatusSubKode());
        assertNotNull(result.getBogId());
    }

    private void assertResponseFEJL(HentBogResponseDto result, HentBogResponseDto.StatusSubKode subKode) {
        assertNotNull(result);
        assertEquals(ResponseDto.StatusKode.INPUT_FEJL, result.getStatusKode());
        assertEquals(subKode, result.getStatusSubKode());
        assertNotNull(result.getFejlBeskrivelse());
        assertNull(result.getBogId());
    }
}