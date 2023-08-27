package com.apps.customerbackend.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValuesController {

    @RequestMapping("/api/values")
    public ResponseEntity<?> value() {
        return ResponseEntity.ok("Management apps is running...");
    }
}
