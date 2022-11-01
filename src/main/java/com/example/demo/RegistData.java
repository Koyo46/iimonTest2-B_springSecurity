package com.example.demo;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegistData {

  @NotBlank
  private String userName;

  @NotBlank
  private String password;

  public User toEntity() {
    return new User(null, this.userName,this.password);
    }


}
