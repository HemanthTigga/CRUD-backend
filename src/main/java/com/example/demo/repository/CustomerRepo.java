package com.example.demo.repository;

import com.example.demo.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {
    // Search by name (case-insensitive)
    Page<Customer> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Filter by age
    Page<Customer> findByAge(Integer age, Pageable pageable);

    // Search by name and filter by age
    Page<Customer> findByNameContainingIgnoreCaseAndAge(String name, Integer age, Pageable pageable);

    // Search by name OR email (case-insensitive)
    Page<Customer> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email, Pageable pageable);

    // Search by name OR email AND filter by age
    Page<Customer> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseAndAge(
            String name, String email, Integer age, Pageable pageable);
    @Query("SELECT c FROM Customer c WHERE CAST(c.id AS string) LIKE %:search%")
    Page<Customer> findByIdContaining(@Param("search") String search, Pageable pageable);

    @Query("SELECT c FROM Customer c WHERE CAST(c.id AS string) LIKE %:search% AND c.age = :age")
    Page<Customer> findByIdContainingAndAge(@Param("search") String search, @Param("age") Integer age, Pageable pageable);

}
