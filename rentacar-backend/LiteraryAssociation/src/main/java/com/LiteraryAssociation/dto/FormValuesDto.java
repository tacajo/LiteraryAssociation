package com.LiteraryAssociation.dto;

import java.io.Serializable;
import java.util.List;

public class FormValuesDto implements Serializable{

    String fieldId;
    String fieldValue;
    List<String> fieldValues;

    public FormValuesDto() {
        super();
    }

    public FormValuesDto(String fieldId, String fieldValue) {
        super();
        this.fieldId = fieldId;
        this.fieldValue = fieldValue;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public List<String> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(List<String> fieldValues) {
        this.fieldValues = fieldValues;
    }
}
