package com.edu.controller.front;

import com.edu.common.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/websocket/")
public class WebSocketContorller {
    @Autowired
    WebSocket webSocket;
    @GetMapping("sendAllWebSocket")
    public String test(String text) {
//        String text = "你们好！这是websocket群体发送！";
        webSocket.sendAllMessage(text);
        return text;
    }

    @GetMapping("sendOneWebSocket/{userName}")
    public String sendOneWebSocket(@PathVariable("userName") String userName,String text) {
//        String text = userName + " 你好！ 这是websocket单人发送！";
        webSocket.sendOneMessage(userName, text);
        return text;

    }
}