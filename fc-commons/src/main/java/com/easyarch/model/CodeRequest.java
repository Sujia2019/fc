package com.easyarch.model;

import lombok.Data;

@Data
public class CodeRequest {

    private int status;
    private String phoneNumber;
    private String code;

}
