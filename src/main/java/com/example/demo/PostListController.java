package com.example.demo;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import lombok.AllArgsConstructor;


@Controller
@AllArgsConstructor
public class PostListController {

  private final PostRepository postRepository;
  private final HttpSession session;


  @GetMapping("/post")
  public ModelAndView showPostList(ModelAndView mv) {
    mv.setViewName("postList");
    List<Post> postList = postRepository.findAll();
    mv.addObject("postList", postList);

    if(session.getAttribute("userName")!=null) {
      mv.addObject("user", true);
    }else {
      mv.addObject("user", false);
    }

    return mv;
  }

  @PostMapping("/post")
  public ModelAndView createPost(@ModelAttribute @Validated PostData postData, BindingResult result,
      ModelAndView mv) {

    Post post = postData.toEntity();
    postRepository.saveAndFlush(post);
    return showPostList(mv);
  }


  @GetMapping("/postForm")
  public ModelAndView postForm(ModelAndView mv) {
    mv.addObject("userName",session.getAttribute("userName"));
    mv.setViewName("postForm");
    return mv;
  }

}
