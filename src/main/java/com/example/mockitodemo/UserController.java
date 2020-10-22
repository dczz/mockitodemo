package com.example.mockitodemo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

  @Autowired
  UserService userService;

  @RequestMapping
  public String index (Model model) {
    model.addAttribute("name", "zhangsan");
    return "user/index";
  }

  @ResponseBody
  @RequestMapping("list/{power}")
  public ResponseEntity<?> userList (@PathVariable("power") boolean power) {
    if (power) {
      Map<String, List<String>> hashMap = new HashMap<>();
      hashMap.put("userList", Arrays.asList("zhangsan", "lisi", "wangwu", "zhaoliu"));
      return ResponseEntity.ok(hashMap);
    }
    return ResponseEntity.notFound().build();
  }

  @RequestMapping("testMock")
  public String testMock (Model model) {
    String msg = userService.say();
    System.err.println(msg);
    model.addAttribute("name", msg);
    return "user/index";
  }

}
