package com.model;


import com.model.common.BaseEntity;
import com.model.common.GenericEntity;

import lombok.Data;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author avinash.a.mishra
 */


@Entity
@Table(name = "date_validator")
@Data
public class DateValidator implements GenericEntity {
    @Id
    @SequenceGenerator(name = "date_validator_seq_id", sequenceName = "date_validator_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "date_validator_seq_id")
    private Long id;
    private LocalDate localDay;
    private LocalDateTime local;
    private Instant instant;
    private Date util;
    private DateTime joda;




    @Override
    public String toStringForLogger() {
        return "DateValidator{" +
                "id=" + id +
                ", localDay=" + localDay +
                ", local=" + local +
                ", instant=" + instant +
                ", util=" + util ;
    }
}
