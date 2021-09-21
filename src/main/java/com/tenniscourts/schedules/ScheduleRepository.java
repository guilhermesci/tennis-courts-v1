package com.tenniscourts.schedules;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByTennisCourt_IdOrderByStartDateTime(Long id);

    Optional<Schedule> findByTennisCourtIdAndStartDateTime(Long tennisCourtId, LocalDateTime startDateTime);

    List<Schedule> findByStartDateTimeBetweenOrderByTennisCourtIdAscStartDateTimeAsc(LocalDateTime startDateTime,
                                                                                     LocalDateTime endDateTime);
}