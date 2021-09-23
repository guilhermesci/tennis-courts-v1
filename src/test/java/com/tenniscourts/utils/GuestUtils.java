package com.tenniscourts.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tenniscourts.guests.Guest;
import com.tenniscourts.guests.GuestDTO;

public class GuestUtils {

    public static GuestDTO createFakeGuestDTO() {
        return GuestDTO.builder()
                .id(1L)
                .name("Guest Fake")
                .build();
    }

    public static Guest createFakeGuest() {
        return Guest.builder()
                .id(1L)
                .name("Guest Fake")
                .build();
    }

    public static Guest createFakeGuestFrom(GuestDTO guestDTO) {
        return Guest.builder()
                .id(1L)
                .name("Guest Fake")
                .build();
    }

    public static String asJsonString(GuestDTO guestDTO) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.registerModules(new JavaTimeModule());

            return objectMapper.writeValueAsString(guestDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
