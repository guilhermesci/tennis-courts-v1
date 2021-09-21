package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.AlreadyExistsEntityException;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.exceptions.InvalidScheduleStartDateException;
import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final TennisCourtRepository tennisCourtRepository;

    public ScheduleDTO addSchedule(CreateScheduleRequestDTO createScheduleRequestDTO) throws InvalidScheduleStartDateException {
        verifyIfScheduleStartDateTimeIsGreaterThanEqualToday(createScheduleRequestDTO.getStartDateTime());
        verifyIfScheduleIsAlreadyRegistered(createScheduleRequestDTO.getTennisCourtId(), createScheduleRequestDTO.getStartDateTime());

        Schedule schedule = scheduleMapper.map(createScheduleRequestDTO);
        schedule.setEndDateTime(createScheduleRequestDTO.getStartDateTime().plusHours(1));
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return scheduleMapper.map(savedSchedule);
    }

    private void verifyIfScheduleIsAlreadyRegistered(Long tennisCourtId, LocalDateTime startDateTime){
        Optional<Schedule> optSavedSchedule = scheduleRepository.findByTennisCourtIdAndStartDateTime(tennisCourtId, startDateTime);
        if (optSavedSchedule.isPresent()) {
            throw new AlreadyExistsEntityException(String.format("Schedule starting at %t already exists for tennis_court_id: %d",
                                                                 startDateTime,tennisCourtId));
        }
    }

    private void verifyIfScheduleStartDateTimeIsGreaterThanEqualToday(LocalDateTime startDateTime) throws InvalidScheduleStartDateException {
        LocalDate minimumStartDate = LocalDate.now();
        LocalDate startDate = LocalDate.of(startDateTime.getYear(), startDateTime.getMonth(), startDateTime.getDayOfMonth());
        if (startDate.isBefore(minimumStartDate)) {
            throw new InvalidScheduleStartDateException();
        }
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) throws InvalidScheduleStartDateException {
        verifyIfScheduleStartDateTimeIsGreaterThanEqualToday(startDate);
        return scheduleMapper.map(scheduleRepository.findByStartDateTimeBetweenOrderByTennisCourtIdAscStartDateTimeAsc(startDate, endDate));
    }

    public ScheduleDTO findScheduleById(Long scheduleId) {
        Optional<ScheduleDTO> scheduleDTO = scheduleRepository.findById(scheduleId).map(scheduleMapper::map);
        if ( !scheduleDTO.isPresent() ) {
            throw new EntityNotFoundException("Schedule not found");
        }
        return scheduleDTO.get();
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        Optional<TennisCourt> tennisCourt = tennisCourtRepository.findById(tennisCourtId);
        if ( !tennisCourt.isPresent() ) {
            throw new EntityNotFoundException("Tennis court not found");
        }
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }

//    public List<ScheduleDTO> findFreeSchedules() {
//        return scheduleRepository
//    }
}
