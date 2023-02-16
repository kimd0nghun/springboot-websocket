package com.example.springwebsocket.chat.handler;


import com.example.springwebsocket.chat.dto.ChatMessage;
import com.example.springwebsocket.chat.dto.ChatRoom;
import com.example.springwebsocket.chat.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j

@RequiredArgsConstructor

@Component

public class WebSocketChatHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;

    private final ChatService chatService;



    @Override

    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String payload = message.getPayload();

        log.info("payload {}", payload);

// 삭제        TextMessage textMessage = new TextMessage("Welcome chatting sever~^^ ");

// 삭제       session.sendMessage(textMessage);

        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);

        ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());


        room.handleActions(session, chatMessage, chatService);

    }

}