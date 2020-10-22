package com.example.mockitodemo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerApiITTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  @DisplayName("显示json格式用户列表")
  void should_get_list () throws Exception {
    mockMvc.perform(get("/user/list/{power}", true))
        .andDo(print())
        .andExpect(jsonPath("$.userList").isArray())
        .andExpect(jsonPath("$.userList", hasSize(4)))
        .andExpect(jsonPath("$.userList", hasItems("zhangsan", "lisi")));
    mockMvc.perform(get("/user/list/{power}",false))
        .andDo(print());
  }

}
