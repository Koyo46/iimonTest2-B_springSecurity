package com.example.demo;

import java.util.Locale;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoginService {

  private final UserRepository userRepository;

  public boolean isValid(LoginData loginData, BindingResult result) {

    Optional<User> user = userRepository.findByUserName(loginData.getUserName());
    if (user.isEmpty()) {
      return false;
    }

    if (!user.get().getPassword().equals(loginData.getPassword())) {
      return false;
    }


    return true;

  }

  public boolean isValid(RegistData registData, BindingResult result) {
    Optional<User> account = userRepository.findByUserName(registData.getUserName());
    if (account.isPresent()) {
      return false;
    }
    return true;


  }

}
