package application.service;

import application.dto.OpretBogRequestDto;
import application.dto.OpretBogResponseDto;
import application.dto.ResponseDto;
import application.entity.Bog;
import application.repository.BogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class BogWriterServiceTest {

    private BogWriterService bogWriterService;
    @Mock
    private BogRepository bogRepository;
    @Mock
    private BogReaderService bogReaderService;

    private OpretBogRequestDto request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        request = OpretBogRequestDto.builder()
                .titel("The Dark Tower")
                .forfatter("Stephen King")
                .blurb("A good book")
                .build();

        when(bogRepository.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
        bogWriterService = new BogWriterService(bogRepository, bogReaderService);
    }

    @Test
    void solskin() {
        // Arrange

        // Act
        OpretBogResponseDto result = bogWriterService.opret(request);

        // Assert
        assertResponseOK(result);
        Bog bogSaved = getSavedBog();
        assertEquals(result.getBogId(), bogSaved.getId());
        assertEquals(request.getTitel(), bogSaved.getTitel());
        assertEquals(request.getForfatter(), bogSaved.getForfatter());
        assertEquals(request.getBlurb(), bogSaved.getBlurb());
    }

    @Test
    void bogEksistererAllerede() {
        // Arrange
        when(bogReaderService.findBogByTitel(request.getTitel()))
                .thenReturn(Optional.of(Bog.builder().build()));

        // Act
        OpretBogResponseDto result = bogWriterService.opret(request);

        // Assert
        assertResponseFEJL(result, OpretBogResponseDto.StatusSubKode.BOG_FINDES_ALLEREDE);
        verify(bogRepository, times(0)).save(any());
    }

    private Bog getSavedBog() {
        ArgumentCaptor<Bog> bogCaptor = ArgumentCaptor.forClass(Bog.class);
        verify(bogRepository).save(bogCaptor.capture());
        return bogCaptor.getValue();
    }

    private void assertResponseOK(OpretBogResponseDto result) {
        assertNotNull(result);
        assertEquals(ResponseDto.StatusKode.OK, result.getStatusKode());
        assertNull(result.getStatusSubKode());
        assertNotNull(result.getBogId());
    }

    private void assertResponseFEJL(OpretBogResponseDto result, OpretBogResponseDto.StatusSubKode subKode) {
        assertNotNull(result);
        assertEquals(ResponseDto.StatusKode.INPUT_FEJL, result.getStatusKode());
        assertEquals(subKode, result.getStatusSubKode());
        assertNotNull(result.getFejlBeskrivelse());
        assertNull(result.getBogId());
    }
}