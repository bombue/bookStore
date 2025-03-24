package ru.akiselev.wsbook.payload;

import lombok.Getter;

@Getter
public class ExceptionMessage {
    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

}
