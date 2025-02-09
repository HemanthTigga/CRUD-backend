package com.example.demo.service;

import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepo;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepo customerRepo;
    //    add Customer details
    @Transactional
    public Customer addCustomer(Customer customer) {
        System.out.println("New customer details added");
//        System.out.println(customer.getId());
//        System.out.println(customer.getName());
//        System.out.println(customer.getAge());
//        System.out.println(customer.getEmail());
//        System.out.println(customer.getImgURL());
        if (customerRepo.existsById(customer.getId())) {
            throw new ValidationException("Customer already exists");
        }
        return customerRepo.save(customer);
    }

    //    show Customer details
    @Transactional(readOnly = true)
    public Page<Map<String, Object>> getCustomer(String search, String sortBy, String order, Integer filterAge, int page, int size) {
        Sort.Direction direction = (order != null && order.equalsIgnoreCase("desc")) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, (sortBy != null && !sortBy.isEmpty()) ? sortBy : "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Customer> customerPage;

        // Search logic
        if (search != null && !search.isEmpty()) {
            try {
                Integer.parseInt(search);
                customerPage = (filterAge != null) ?
                        customerRepo.findByIdContainingAndAge(search, filterAge, pageable) :
                        customerRepo.findByIdContaining(search, pageable);
            } catch (NumberFormatException ignored) {
                customerPage = (filterAge != null) ?
                        customerRepo.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseAndAge(search, search, filterAge, pageable) :
                        customerRepo.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(search, search, pageable);
            }
        } else {
            customerPage = (filterAge != null) ? customerRepo.findByAge(filterAge, pageable) : customerRepo.findAll(pageable);
        }

        // Convert response to include Base64 images
        Page<Map<String, Object>> formattedPage = customerPage.map(customer -> {
            Map<String, Object> customerMap = new HashMap<>();
            customerMap.put("id", customer.getId());
            customerMap.put("name", customer.getName());
            customerMap.put("age", customer.getAge());
            customerMap.put("email", customer.getEmail());

            // Convert image bytes to Base64
            if (customer.getImage() != null) {
                String base64Image = Base64.getEncoder().encodeToString(customer.getImage());
                customerMap.put("image", "data:image/jpeg;base64," + base64Image);
            } else {
                customerMap.put("image", null);
            }

            return customerMap;
        });

        return formattedPage;
    }


    @Transactional
    public Customer getCustomerById(int id) {
        return customerRepo.findById(id).orElse(null);
    }

    //    Update Customer details
    @Transactional
    public Customer updateCustomer(Customer updatedcustomer) {
        Optional<Customer> ct = customerRepo.findById(updatedcustomer.getId());
        if (ct.isPresent()) {


            Customer oldcustomer = ct.get();

//        updating customer details
            oldcustomer.setAge(updatedcustomer.getAge());
            oldcustomer.setEmail(updatedcustomer.getEmail());
            oldcustomer.setName(updatedcustomer.getName());
//        oldcustomer.setImgURL(updatedcustomer.getImgURL());

            return customerRepo.save(oldcustomer);
        } else {
            throw new ValidationException("Customer not found.");
        }

    }

//    delete Customer details

    @Transactional
    public boolean deleteCustomer(int id) {
        if (id <= 0) {
            throw new ValidationException("Customer ID must be greater than 0.");
        }
        if (customerRepo.existsById(id)) {
            customerRepo.deleteById(id);
            System.out.println("Customer details deleted");
            return true;
        } else {
            throw new ValidationException("Customer not found.");
        }

    }
}
