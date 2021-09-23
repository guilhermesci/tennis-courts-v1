package com.tenniscourts.schedules;

import com.tenniscourts.reservations.ReservationMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ScheduleMapper {

    ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);

    Schedule map(ScheduleDTO source);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    ScheduleDTO map(Schedule source);

    List<ScheduleDTO> map(List<Schedule> source);

    @Mapping(target = "tennisCourt.id", source = "tennisCourtId")
    @Mapping(target = "startDateTime", source = "startDateTime")
    Schedule map(CreateScheduleRequestDTO source);
}
