package com.example.mockitodemo.event;


import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EventHandler {

  @EventListener(RequestEvent.class)
  public void on (RequestEvent event) {
    System.err.println((String) event.getSource());
  }
}