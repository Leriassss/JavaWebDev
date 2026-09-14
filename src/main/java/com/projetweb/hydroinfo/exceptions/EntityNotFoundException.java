package com.projetweb.hydroinfo.exceptions;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {
    private String entity;
    private String fieldName;
    private String fieldValue;

    public EntityNotFoundException(
            String entity, String fieldName, String fieldValue
    ) {
        super("Aucune entité " + entity + " n'a été trouvée avec la valeur " + fieldValue + " dans le champs " + fieldName);
        this.entity = entity;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
