package com.example.rag.controller;

import com.example.rag.service.RAGService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rag")
public class RAGController {

    @Autowired
    private RAGService ragService;

    @PostMapping("/ask")
    public String askQuestion(@RequestBody String question) {
        return ragService.ask(question);
    }
}