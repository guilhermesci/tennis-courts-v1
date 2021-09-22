package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.AlreadyExistsEntityException;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.exceptions.InvalidScheduleDateException;
import com.tenniscourts.reservations.ReservationStatus;
import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final TennisCourtRepository tennisCourtRepository;

    public ScheduleDTO addSchedule(CreateScheduleRequestDTO createScheduleRequestDTO) throws InvalidScheduleDateException {
        verifyIfTennisCourtExistsWithGivenId(createScheduleRequestDTO.getTennisCourtId());
        verifyIfScheduleStartDateTimeIsGreaterThanEqualToday(createScheduleRequestDTO.getStartDateTime());
        verifyIfScheduleAlreadyExists(createScheduleRequestDTO.getTennisCourtId(), createScheduleRequestDTO.getStartDateTime());

        Schedule schedule = scheduleMapper.map(createScheduleRequestDTO);
        schedule.setEndDateTime(createScheduleRequestDTO.getStartDateTime().plusHours(1));
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return scheduleMapper.map(savedSchedule);
    }

    private void verifyIfScheduleAlreadyExists(Long tennisCourtId, LocalDateTime startDateTime){
        Optional<Schedule> optSavedSchedule = scheduleRepository.findByTennisCourtIdAndStartDateTime(tennisCourtId, startDateTime);
        if (optSavedSchedule.isPresent()) {
            throw new AlreadyExistsEntityException(String.format("Schedule starting at %t already exists for tennis_court_id: %d",
                                                                 startDateTime,tennisCourtId));
        }
    }

    private void verifyIfScheduleStartDateTimeIsGreaterThanEqualToday(LocalDateTime startDateTime) throws InvalidScheduleDateException {
        LocalDate minimumStartDate = LocalDate.now();
        LocalDate startDate = LocalDate.of(startDateTime.getYear(), startDateTime.getMonth(), startDateTime.getDayOfMonth());
        if (startDate.isBefore(minimumStartDate)) {
            throw new InvalidScheduleDateException("Start date must be greater than or equal to today");
        }
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) throws InvalidScheduleDateException {
        verifyIfScheduleStartDateTimeIsGreaterThanEqualToday(startDate);
        verifyIfEndDateIsBeforeStartDate(startDate, endDate);

        return scheduleMapper.map(scheduleRepository.findByStartDateTimeBetweenOrderByTennisCourtIdAscStartDateTimeAsc(startDate, endDate));
    }

    private void verifyIfEndDateIsBeforeStartDate(LocalDateTime startDate, LocalDateTime endDate) throws InvalidScheduleDateException {
        if (endDate.isBefore(startDate)) {
            throw new InvalidScheduleDateException("End date must be greater than or equal to start date");
        }
    }

    public ScheduleDTO findScheduleById(Long scheduleId) {
        Optional<ScheduleDTO> scheduleDTO = scheduleRepository.findById(scheduleId).map(scheduleMapper::map);
        if ( !scheduleDTO.isPresent() ) {
            throw new EntityNotFoundException("Schedule not found");
        }

        return scheduleDTO.get();
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        verifyIfTennisCourtExistsWithGivenId(tennisCourtId);

        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }

    private void verifyIfTennisCourtExistsWithGivenId(Long tennisCourtId) {
        Optional<TennisCourt> tennisCourt = tennisCourtRepository.findById(tennisCourtId);
        if ( !tennisCourt.isPresent() ) {
            throw new EntityNotFoundException("Tennis court not found");
        }
    }

    public List<ScheduleDTO> findFreeSchedulesByDate(LocalDateTime startDate, LocalDateTime endDate) throws InvalidScheduleDateException {
        verifyIfScheduleStartDateTimeIsGreaterThanEqualToday(startDate);
        List<Schedule> schedules = scheduleRepository.findByStartDateTimeBetweenOrderByTennisCourtIdAscStartDateTimeAsc(startDate, endDate);

        return schedules.stream()
                        .filter(schedule -> schedule.getReservations()
                                .stream()
                                .filter(x -> x.getReservationStatus()
                                        .equals(ReservationStatus.READY_TO_PLAY))
                                        .collect(Collectors.toList())
                                        .isEmpty())
                        .map(scheduleMapper::map)
                        .collect(Collectors.toList());
    }
}
