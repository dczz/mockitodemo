package com.example.mockitodemo;

import com.example.mockitodemo.event.EventHandler;
import com.example.mockitodemo.event.RequestEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerPageITTest {

  @Autowired
  MockMvc mockMvc;

  /**
   * 断言返回的页面与数据
   */
  @Test
  @DisplayName("返回用户index页面和正确的属性")
  void should_get_view () throws Exception {
    mockMvc.perform(get("/user"))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(view().name("user/index"))
        .andExpect(model().attribute("name", "zhangsan"));
  }


  @MockBean
  UserService userService;

  /**
   * mock service 组件
   */
  @Test
  @DisplayName("mock一个service，并返回到页面上")
  void should_return_mock_msg () throws Exception {
    when(userService.say()).thenReturn("nihao mock service!");
    mockMvc.perform(get("/user/testMock"))
        .andDo(print())
        .andExpect(model().attribute("name", "nihao mock service!"));
    verify(userService, times(1)).say();
  }

  @Mock
  UserMapper userMapper;

  @Test
  @DisplayName("单独mock一个Object")
  void should_mock_mapper () {
    when(userMapper.selectById(1L)).thenReturn("hello world!");
    String s = userMapper.selectById(1L);
    Assertions.assertEquals(s, "hello world!");
  }


  @Autowired
  ApplicationEventPublisher publisher;
  @SpyBean
  EventHandler eventHandler;

  /**
   * 测试事件是否可以接收到
   */
  @Test
  @DisplayName("测试事件监听器是否被执行")
  void should_event_receiver () throws Exception {
    RequestEvent event = new RequestEvent("123");
    publisher.publishEvent(event);
    verify(eventHandler, times(1)).on(event);
  }
}
