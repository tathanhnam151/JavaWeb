package com.namta.ecom.dto;

import com.namta.ecom.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private long id;

    private String email;

    private String name;

    private UserRole userRole;
}
