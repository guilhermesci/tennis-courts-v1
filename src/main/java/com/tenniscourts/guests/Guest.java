package com.tenniscourts.guests;

import com.tenniscourts.config.persistence.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Guest extends BaseEntity<Long> {

  @Column
  @NotNull
  private String name;

  @Builder
  public Guest(Long id, String name){
    super(id);
    this.name = name;
  }

}
