package com.example.mockitodemo.event;

import org.springframework.context.ApplicationEvent;

public class RequestEvent extends ApplicationEvent {

  public RequestEvent (Object source) {
    super(source);
  }
}
