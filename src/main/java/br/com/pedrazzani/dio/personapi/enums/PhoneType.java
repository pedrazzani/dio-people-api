package br.com.pedrazzani.dio.personapi.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public enum PhoneType {

    HOME("Home"),
    MOBILE("Mobile"),
    COMMERCIAL("Commercial");

    private String description;

    PhoneType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
