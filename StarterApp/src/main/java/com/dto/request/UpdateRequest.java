package com.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author avinash.a.mishra
 */
@Data
public class UpdateRequest {
    @NotNull(message = "className is required")
    private String className;
    @NotNull(message = "field is required")
    private String field;
    @NotNull(message = "fieldValue is required")
    private Object fieldValue;
    @NotNull(message = "clause is required")
    private String clause;
    @NotNull(message = "clauseValue is required")
    private String clauseValue;

}
