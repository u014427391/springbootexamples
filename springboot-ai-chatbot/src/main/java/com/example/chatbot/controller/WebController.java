package com.example.chatbot.controller;

import com.example.chatbot.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    private final ChatService chatService;

    @Autowired
    public WebController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("chatHistory", chatService.getChatHistory());
        return "index";
    }
}
