package com.tenniscourts.tenniscourts;

import com.tenniscourts.schedules.ScheduleMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TennisCourtMapper {

    TennisCourtMapper INSTANCE = Mappers.getMapper(TennisCourtMapper.class);

    TennisCourtDTO map(TennisCourt source);

    @InheritInverseConfiguration
    TennisCourt map(TennisCourtDTO source);
}
