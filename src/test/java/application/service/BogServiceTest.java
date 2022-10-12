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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class BogServiceTest {

    private BogService bogService;
    @Mock
    private BogRepository bogRepository;

    private OpretBogResponseDto result;
    private OpretBogRequestDto request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        request = OpretBogRequestDto.builder()
                .titel("The Dark Tower")
                .forfatter("Stephen King")
                .blurb("A good book")
                .build();

        when(bogRepository.save(any())).thenReturn(Optional.empty());

        bogService = new BogService(bogRepository);
    }

    @Test
    void sunshine() {
        // Arrange

        // Act
        OpretBogResponseDto result = bogService.opret(request);

        // Assert
        assertResponseOK(result);
//        verify(bogRepository.save(any()));
        Bog bogSaved = getSavedBog();
        assertEquals(result.getBogId(), bogSaved.getId());
        assertEquals(request.getTitel(), bogSaved.getTitel());
        assertEquals(request.getForfatter(), bogSaved.getForfatter());
        assertEquals(request.getBlurb(), bogSaved.getBlurb());
    }

    private Bog getSavedBog() {
        ArgumentCaptor<Bog> bogCaptor = ArgumentCaptor.forClass(Bog.class);
        verify(bogRepository).save(bogCaptor.capture());
        Bog bogSaved = bogCaptor.getValue();
        return bogSaved;
    }

    private void assertResponseOK(OpretBogResponseDto result) {
        assertNotNull(result);
        assertEquals(ResponseDto.StatusKode.OK, result.getStatusKode());
        assertNull(result.getStatusSubKode());
        assertNotNull(result.getBogId());
    }
}