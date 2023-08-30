package com.tads.manager.expection.dto;

import org.springframework.validation.FieldError;

public record ManagerInvalidDTO(String field, String message){
    public ManagerInvalidDTO(FieldError error){
        this(error.getField(), error.getDefaultMessage());
    }
}
