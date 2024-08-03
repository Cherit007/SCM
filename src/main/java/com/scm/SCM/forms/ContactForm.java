package com.scm.SCM.forms;
import com.scm.SCM.validators.ValidFile;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContactForm {
    @NotBlank(message = "Name is required")
    @Size(min = 3, message = "Min 3 characters required")
    private String name;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address")
    private String email;
    @ValidFile(message = "File is invalid")
    private MultipartFile contactImage;
    @Size(min = 3, message = "Min 3 characters required")
    private String description;
    @Size(min = 3, message = "Min 3 characters required")
    private String about;
    @NotBlank(message = "Phone number is required")
    @Size(min = 10, max = 10, message = "Min 10 digits required")
    private String phoneNumber;
    private boolean favourite = false;
//    private String websiteLink;
}


