package com.example.demo;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class PostData {

  @Autowired
  private HttpSession session;


  @NotBlank
  private String text;

//  @NotBlank
//  private String userName = (String) session.getAttribute("userName");

  public Post toEntity() {
    return new Post(null, //(String)session.getAttribute("userName")
        this.text);
    }


}
