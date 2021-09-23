package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.utils.GuestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GuestsServiceTest {

    @Mock
    private GuestRepository guestRepository;

    @InjectMocks
    private GuestService guestService;

    @Test
    void whenGivenIdExistsThenReturnGuest() {
        Guest expectedGuest = GuestUtils.createFakeGuest();

        when(guestRepository.findById(expectedGuest.getId())).thenReturn(Optional.of(expectedGuest));

        GuestDTO foundGuestDTO = guestService.findGuestById(expectedGuest.getId());

        assertEquals(expectedGuest.getId(), foundGuestDTO.getId());
        assertEquals(expectedGuest.getName(), foundGuestDTO.getName());
    }

    @Test
    void whenGivenIdDoesNotExistsThenThrowEntityNotFoundException(){
        var invalidId = 10L;

        when(guestRepository.findById(invalidId)).thenReturn(Optional.ofNullable(any(Guest.class)));

        assertThrows(EntityNotFoundException.class, () -> guestService.findGuestById(invalidId));
    }
}
