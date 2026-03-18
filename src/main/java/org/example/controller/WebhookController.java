package org.example.controller;

import org.example.entity.Webhook;
import org.example.repository.WebhookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhooks")
public class WebhookController {

    @Autowired
    private WebhookRepository webhookRepository;

    @PostMapping
    public Webhook register(@RequestBody Webhook webhook) {
        return webhookRepository.save(webhook);
    }
}