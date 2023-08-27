package com.apps.customerbackend.Exception;

import com.apps.customerbackend.Models.response.ResponseApi;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

import java.util.stream.Collectors;

public class ApiException {
    public static ResponseEntity<?> requestHandler(Errors errors) {
        ResponseApi result = new ResponseApi();
        result.setResult("0");
        result.setMessage(errors.getAllErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.joining(",")));
        return ResponseEntity.badRequest().body(result);
    }
}
