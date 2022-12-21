package com.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author avinash.a.mishra
 */
@Data
public class DateValidatorRequest {
    @NotNull(message = "fromDate is required")
    private String fromDate;
    @NotNull(message = "toDate is required")
    private String toDate;
    @NotNull(message = "option is required")
    private String option;
}
