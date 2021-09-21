package com.tenniscourts.schedules;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    Schedule map(ScheduleDTO source);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    ScheduleDTO map(Schedule source);

    List<ScheduleDTO> map(List<Schedule> source);

    @Mapping(target = "tennisCourt.id", source = "tennisCourtId")
    @Mapping(target = "startDateTime", source = "startDateTime")
    Schedule map(CreateScheduleRequestDTO source);
}
