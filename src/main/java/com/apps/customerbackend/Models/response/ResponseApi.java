package com.apps.customerbackend.Models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseApi {
    private String result;
    private String message;
    private Object data;
}
