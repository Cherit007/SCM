package com.scm.SCM.forms;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserForm {
    @NotBlank(message = "Name is required")
    @Size(min = 3,message = "Min 3 characters required")
    private String name;
    @Email(message = "Invalid email address")
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 5,message = "Min 5 characters required")
    private String password;
    @NotBlank(message = "Phone number is required")
    @Size(min = 10,message = "Min 10 digits required")
    private String phoneNumber;
    @NotBlank(message = "About is required")
    private String about;
}
