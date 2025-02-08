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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepo customerRepo;

//    private static final String UPLOAD_DIR = "src/main/resources/static/images/";

    //    add Customer details
    @Transactional
    public Customer addCustomer( Customer customer) {
        System.out.println("New customer details added");
//        System.out.println(customer.getId());
//        System.out.println(customer.getName());
//        System.out.println(customer.getAge());
//        System.out.println(customer.getEmail());
//        System.out.println(customer.getImgURL());
        if(customerRepo.existsById(customer.getId())){
            throw new ValidationException("Customer already exists");
        }
        return customerRepo.save(customer);
    }

//    public String saveImage(MultipartFile image) {
//        try {
//            Path uploadPath = Paths.get(UPLOAD_DIR);
//            if (!Files.exists(uploadPath)) {
//                Files.createDirectory(uploadPath);
//                System.out.println("Directory created" + uploadPath.toAbsolutePath());
//            } else {
//                System.out.println("Directory already exists" + uploadPath.toAbsolutePath());
//            }
////            saving file
//            String originalFile = StringUtils.cleanPath(image.getOriginalFilename());
//            String fileName = System.currentTimeMillis() + "_" + originalFile;
//            Path filePath = uploadPath.resolve(fileName);
//            Files.copy(image.getInputStream(), filePath);
//
//            return "/images/" + fileName;
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to save image", e);
//        }
//    }

    //    show Customer details
    @Transactional(readOnly = true)
//    public List<Customer> getCustomer() {
//        return customerRepo.findAll();
//    }
//    public List<Customer> getCustomer(String search, String sortBy, String order, Integer filterAge) {
//        List<Customer> customers = customerRepo.findAll();
////        Search functiionality
//        if (search != null && !search.isEmpty()) {
//            String searchLower = search.toLowerCase();
//            customers = customers.stream()
//                    .filter(c -> String.valueOf(c.getId()).contains(searchLower) || // Search by ID
//                            c.getName().toLowerCase().contains(searchLower) ||
//                            c.getEmail().toLowerCase().contains(searchLower))
//                    .collect(Collectors.toList());
//        }
////        Filter functionality
//        System.out.println("Age ="+filterAge);
//        if (filterAge!=null) {
//            customers = customers.stream()
//                    .filter(c -> c.getAge() == filterAge)
//                    .collect(Collectors.toList());
//        }
//        
////        Sorting functionality
//        System.out.println("SortBy: " + sortBy + ", Order: " + order);
//
//        if(sortBy != null){
//            boolean ascending = order == null || order.equalsIgnoreCase("asc");
//            customers.sort((c1,c2) -> {
//                int comparison = 0;
//                if(sortBy.equals("name")){
//                    comparison = c1.getName().compareToIgnoreCase(c2.getName());
//                }else if(sortBy.equals("id")){
//                    comparison = Integer.compare(c1.getId(), c2.getId());
//                }else if(sortBy.equals("age")){
//                    comparison = Integer.compare(c1.getAge(), c2.getAge());
//                }
//                return ascending? comparison : -comparison;
//            });
//        }
//        return customers;
//    }

    public Page<Customer> getCustomer(String search, String sortBy, String order, Integer filterAge, int page, int size) {
        Sort.Direction direction = order != null && order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, (sortBy != null && !sortBy.isEmpty()) ? sortBy : "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        // Search by ID, Name, or Email
        if (search != null && !search.isEmpty()) {
            try {
                // If search is a number, attempt partial ID search
                Integer.parseInt(search);
                return (filterAge != null)
                        ? customerRepo.findByIdContainingAndAge(search, filterAge, pageable)
                        : customerRepo.findByIdContaining(search, pageable);
            } catch (NumberFormatException ignored) {
                // Not a number, proceed with name and email search
                return (filterAge != null)
                        ? customerRepo.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseAndAge(search, search, filterAge, pageable)
                        : customerRepo.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(search, search, pageable);
            }
        }

        // Default case: return all customers with pagination
        return (filterAge != null)
                ? customerRepo.findByAge(filterAge, pageable)
                : customerRepo.findAll(pageable);
    }


    @Transactional
    public Customer getCustomerById(int id){
        return customerRepo.findById(id).orElse(null);
    }

    //    Update Customer details
    @Transactional
    public Customer updateCustomer( Customer updatedcustomer) {
        Optional<Customer> ct = customerRepo.findById(updatedcustomer.getId());
        if(ct.isPresent()) {


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
    public boolean deleteCustomer( int id) {
        if(id<=0){
            throw new ValidationException("Customer ID must be greater than 0.");
        }
        if(customerRepo.existsById(id)){
            customerRepo.deleteById(id);
            System.out.println("Customer details deleted");
            return true;
        } else {
            throw new ValidationException("Customer not found.");
        }

    }
}
