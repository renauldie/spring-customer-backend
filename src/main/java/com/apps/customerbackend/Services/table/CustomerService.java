package com.apps.customerbackend.Services.table;

import com.apps.customerbackend.Models.request.DataTable;
import com.apps.customerbackend.Models.request.DataTableRequestDTO;
import com.apps.customerbackend.Models.table.Customer;
import com.apps.customerbackend.Repository.CustomerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings({"rawtypes"})
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Async
    public CompletableFuture<DataTable> get(DataTableRequestDTO request) {
        int start = request.getStart();
        int length = request.getLength();
        String search = request.getSearch().get("value").toString();
        int column = (int) request.getOrder().get(0).get("column");
        String dir = request.getOrder().get(0).get("dir").toString();

        DataTable dataTable = new DataTable();

        List<Map<String, Object>> response = customerRepository.get(search, start, length, column, dir);
        int rowcount = 0;

        if (response.size() != 0) {
            rowcount = (int) response.get(0).get("rowcount");
        }

        dataTable.setTotalRecords(rowcount);
        dataTable.setFilteredRecords(rowcount);
        dataTable.setStart(start);
        dataTable.setData(response);

        return CompletableFuture.completedFuture(dataTable);
    }

    public void save(Customer request) {
        request.setRegisterDate(LocalDateTime.now());
        customerRepository.save(request);
    }

    public Customer update(Long id, JsonPatch request) throws Exception {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new Exception("customer not found"));
        // simple post
//        customer.setName(request.getName());
//        customer.setAddress(request.getAddress());
//        customer.setCity(request.getCity());
//        customer.setProvince(request.getProvince());
//        customer.setStatus(request.getProvince());
//        customerRepository.save(customer);

        // using atch
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();
            JsonNode patched = request.apply(objectMapper.convertValue(customer, JsonNode.class));
            Customer updatedCustomer = objectMapper.treeToValue(patched, Customer.class);
            return customerRepository.save(updatedCustomer);
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new Exception("Request Invalid, Check your data!");
        }
    }

    public void delete(Long id) throws Exception {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new Exception("Customer not found"));
        customerRepository.deleteById(customer.getId());
    }

    public Optional<Customer> getById(Long id) throws Exception {
        return Optional.ofNullable(customerRepository.findById(id).orElseThrow(() -> new Exception("Customer not found")));
    }
}
