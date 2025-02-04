package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.persistence.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TennisCourt extends BaseEntity<Long> {

    @Column
    @NotNull
    private String name;

    @Builder
    public TennisCourt(Long id, String name){
        super(id);
        this.name = name;
    }
}
