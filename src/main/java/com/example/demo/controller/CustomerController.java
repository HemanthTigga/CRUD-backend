package com.example.demo.controller;

import com.example.demo.dto.requests.CustomerRequest;
import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepo;
import com.example.demo.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.IdGeneratorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    private CustomerRepo customerRepo;

    @PostMapping("/addCustomer")
    @CrossOrigin(origins = "http://localhost:5173")
    public Customer addCustomer(
            @Valid @RequestBody CustomerRequest customerRequest
//            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        
//        String imgURL = null;
//        if (image != null && !image.isEmpty()) {
//            imgURL = customerService.saveImage(image);
//            System.out.println("\n Saved image path: " + imgURL);
//        }
        
        int id = customerRequest.getId();
        String name = customerRequest.getName();
        int age = customerRequest.getAge();
        String email = customerRequest.getEmail();
//        Customer customer = new Customer(id, name, age, email,  image.getBytes());
        Customer customer = new Customer(id, name, age, email);
        return customerService.addCustomer(customer);
    }

    @GetMapping("/getCustomer")
    @CrossOrigin(origins = "http://localhost:5173")
    public List<Customer> getCustomer() {
        return customerService.getCustomer();
    }

    @PostMapping("/updateCustomer")
    @CrossOrigin(origins = "http://localhost:5173")
    public Customer updateCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        int id = customerRequest.getId();
        String name = customerRequest.getName();
        int age = customerRequest.getAge();
        String email = customerRequest.getEmail();
        Customer customer = new Customer(id, name, age, email);
        return customerService.updateCustomer(customer);
    }

    @GetMapping("/deleteCustomer/{id}")
    @CrossOrigin(origins = "http://localhost:5173")
    public boolean deleteCustomer(@Valid @PathVariable int id) {
        return customerService.deleteCustomer(id);
    }
    
    @GetMapping("/viewCustomer/{id}")
    @CrossOrigin(origins = "http://localhost:5173")
    public Customer viewCustomer(@PathVariable int id){
        return customerService.getCustomerById(id);
    }
    
}
