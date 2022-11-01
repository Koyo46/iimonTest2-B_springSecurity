package com.example.demo;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginData {

@NotBlank
private String userName;

@NotBlank
private String password;
}
