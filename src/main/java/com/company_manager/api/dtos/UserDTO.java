package com.company_manager.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {
    @NotBlank(message = "username is required")
    @Size(max = 20)
    private String username;

    @NotBlank(message = "password is required")
    @Size(max = 10)
    private String password;
}
