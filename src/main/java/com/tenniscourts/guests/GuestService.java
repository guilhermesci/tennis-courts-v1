package com.tenniscourts.guests;

import com.tenniscourts.exceptions.AlreadyExistsEntityException;
import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GuestService {

    private GuestRepository guestRepository;
    private final GuestMapper guestMapper;

    public GuestDTO addGuest(GuestDTO guestDTO) {
        verifyIfGuestAlreadyExistsByName(guestDTO.getName());

        Guest guest = guestMapper.map(guestDTO);
        Guest savedGuest = guestRepository.save(guest);

        return guestMapper.map(savedGuest);
    }

    private void verifyIfGuestAlreadyExistsByName(String name) {
        Optional<Guest> optSavedGuest = guestRepository.findByName(name);
        if (optSavedGuest.isPresent()) {
            throw new AlreadyExistsEntityException(String.format("Guest %s already exists",name));
        }
    }

    public List<GuestDTO> findAll() {
        return guestRepository.findAll()
                .stream()
                .map(guestMapper::map)
                .collect(Collectors.toList());
    }

    public GuestDTO findGuestById(Long guestId) {
        Guest guest = verifyIfGuestExistsById(guestId);

        return guestMapper.map(guest);
    }

    public GuestDTO findGuestByName(String name) {
        Guest guest = guestRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Guest nor found"));

        return guestMapper.map(guest);
    }

    public void deleteGuestById(Long guestId) {
        verifyIfGuestExistsById(guestId);

        guestRepository.deleteById(guestId);
    }

    public GuestDTO updateGuestById(Long guestId, GuestDTO guestDTO) {
        verifyIfGuestExistsById(guestId);
        guestDTO.setId(guestId);

        Guest guestToUpdate = guestMapper.map(guestDTO);
        Guest updatedGuest = guestRepository.save(guestToUpdate);

        return guestMapper.map(updatedGuest);
    }

    private Guest verifyIfGuestExistsById(Long guestId) {
        return guestRepository.findById(guestId).orElseThrow(() -> new EntityNotFoundException("Guest not found"));
    }
}
