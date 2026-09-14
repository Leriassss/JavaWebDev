package com.projetweb.hydroinfo.exceptions;

import lombok.Getter;


import java.util.List;
@Getter
public class CustomValidationException extends RuntimeException{
    private final List<String> errors;
    public CustomValidationException(List<String> errors) {
        super("Erreur(s) de validation détectée(s)");
        this.errors = errors;
    }

}
