package com.example.demo.dto.requests;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import lombok.*;

//@NoArgsConstructor
//@AllArgsConstructor
//@RequiredArgsConstructor
@Getter
@Setter
public class CustomerRequest {
    @Id
    @NotNull(message = "Id is required")
    @Min(value = 100000,message = "Id should be 6 digits")
    @Max(value = 999999,message = "Id should be 6 digits")
    private int id;          //always unique
    @NotBlank(message = "Name is required")
    private String name;
    @NotNull(message = "Age is required")
    @Min(18)
    @Max(60)
    private int age;
    @NotBlank(message = "Email is required")
    @Email(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
            message = "Invalid Email Format")
    private String email;
    
//@Lob
//private byte[] image;

    
//    public CustomerRequest(int id, String name, int age, String email,byte[] image)
    public CustomerRequest(int id, String name, int age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
//        this.image=image;
    }
    public CustomerRequest(){

    }
//
    @NotNull(message = "Id is required")
    public int getId() {
        return id;
    }
//
    public void setId(@NotNull(message = "Id is required") int id) {
        this.id = id;
    }

    public @NotBlank(message = "Name is required") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name is required") String name) {
        this.name = name;
    }

    @NotNull(message = "Age is required")
    public int getAge() {
        return age;
    }

    public void setAge(@NotNull(message = "Age is required") int age) {
        this.age = age;
    }

    public @NotBlank(message = "Email is required") @Email(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
            message = "Invalid Email Format") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email is required") @Email(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
            message = "Invalid Email Format") String email) {
        this.email = email;
    }

//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }
//
//        public byte[] getImage() {
//        return image;
//    }
//
//    public void setImage(byte[] image) {
//        this.image = image;
//    }
}
