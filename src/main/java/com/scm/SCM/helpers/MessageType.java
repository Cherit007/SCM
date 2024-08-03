package com.scm.SCM.helpers;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageType {
    red("red"),
    blue("blue"),
    green("green");
    private final String value;
    @Override
    public String toString() {
        return value;
    }

}
