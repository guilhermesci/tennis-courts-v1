package com.tenniscourts.reservations;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservationStatus {
  READY_TO_PLAY,
  CANCELLED,
  RESCHEDULED,
  MISSED,
  PLAYED
}
