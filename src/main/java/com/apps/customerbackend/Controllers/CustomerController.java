package com.apps.customerbackend.Controllers;

import com.apps.customerbackend.Exception.ApiException;
import com.apps.customerbackend.Models.request.DataTable;
import com.apps.customerbackend.Models.request.DataTableRequestDTO;
import com.apps.customerbackend.Models.response.ResponseApi;
import com.apps.customerbackend.Models.table.Customer;
import com.apps.customerbackend.Services.table.CustomerService;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@SuppressWarnings({"rawtypes"})
@RestController
@CrossOrigin
@RequestMapping("/api/customers")
@Validated
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/")
    public ResponseEntity<Object> get(@RequestBody DataTableRequestDTO request) {
        ResponseApi result = new ResponseApi();
        try {
            CompletableFuture<DataTable> data = customerService.get(request);
            result.setResult("1");
            result.setMessage("success");
            result.setData(data.get());
        } catch (Exception e) {
            result.setResult("0");
            result.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> save(@Valid @RequestBody Customer request, Errors errors) {
        ResponseApi result = new ResponseApi();

        if (errors.hasErrors()) {
            return (ResponseEntity<Object>) ApiException.requestHandler(errors);
        }

        try {
            customerService.save(request);
            result.setResult("1");
            result.setMessage("success");
            result.setData(request);
        } catch (Exception e) {
            result.setResult("0");
            result.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(result);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody JsonPatch request) {
        ResponseApi result = new ResponseApi();
        try {
            result.setResult("1");
            result.setMessage("success");
            result.setData(customerService.update(id, request));
        } catch (Exception e) {
            result.setResult("0");
            result.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(result);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        ResponseApi result = new ResponseApi();
        try {
            result.setResult("1");
            result.setMessage("success");
            customerService.delete(id);
        } catch (Exception e) {
            result.setResult("0");
            result.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<?> getDetail(@PathVariable Long id) {
        ResponseApi result = new ResponseApi();

        try {
            result.setResult("1");
            result.setMessage("success");
            result.setData(customerService.getById(id));
        } catch (Exception e) {
            result.setResult("0");
            result.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(result);
    }
}
