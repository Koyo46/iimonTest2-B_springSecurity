package com.example.demo;

import javax.servlet.http.HttpSession;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class LoginController {

  private final UserRepository userRepository;
  private final LoginService loginService;
  private final HttpSession session;


  @GetMapping("/login")
  public ModelAndView login(ModelAndView mv) {
    mv.setViewName("login");
    mv.addObject("loginData", new LoginData());
    return mv;
  }

  @PostMapping("/login")
  public String login(@ModelAttribute @Validated LoginData loginData, BindingResult result,
      Model model, RedirectAttributes redirectAttributes) {
    // バリデーション
    if (result.hasErrors()) {
      return "login";
    }
    // サービスでチェック
    if (!loginService.isValid(loginData, result)) {
      return "login";
    }
    session.invalidate();
    User user = userRepository.findByUserName(loginData.getUserName()).get();
    session.setAttribute("userName", user.getUserName());
    return "redirect:/post";
  }

  @GetMapping("/regist")
  public ModelAndView registForm(ModelAndView mv) {
    mv.setViewName("registForm");
    mv.addObject("loginData", new LoginData());
    return mv;
  }

  @PostMapping("/regist/do")
  public String registNewUser(@ModelAttribute @Validated RegistData registData,
      BindingResult result, Model model, RedirectAttributes redirectAttributes) {
    boolean isValid = loginService.isValid(registData, result);
    if (!result.hasErrors() && isValid) {
      User user = registData.toEntity();
      userRepository.saveAndFlush(user);
      return "redirect:/post";
    } else {
      return "registForm";
    }
  }


}
