package com.example.chatbot.controller;

import com.example.chatbot.model.ChatMessage;
import com.example.chatbot.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<ChatMessage> chat(@RequestBody Map<String, String> request) {
        String userMessage = request.get("message");
        String response = chatService.chat(userMessage);
        return ResponseEntity.ok(new ChatMessage("assistant", response));
    }

    @GetMapping("/history")
    public ResponseEntity<List<ChatMessage>> getChatHistory() {
        return ResponseEntity.ok(chatService.getChatHistory());
    }

    @PostMapping("/clear")
    public ResponseEntity<Void> clearHistory() {
        chatService.clearHistory();
        return ResponseEntity.ok().build();
    }
}
