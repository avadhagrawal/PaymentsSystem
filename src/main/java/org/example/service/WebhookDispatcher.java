package org.example.service;

import org.example.entity.Payment;
import org.example.entity.Webhook;
import org.example.entity.WebhookDelivery;
import org.example.repository.WebhookDeliveryRepository;
import org.example.repository.WebhookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WebhookDispatcher {

    private final static org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(WebhookDispatcher.class);
    @Autowired
    private WebhookRepository webhookRepository;

    @Autowired
    private WebhookDeliveryRepository webhookDeliveryRepository;

    @Autowired
    private WebhookSenderService senderService;

    @Async
    public void dispatch(Payment payment) {

        List<Webhook> webhooks = webhookRepository.findByActiveTrue();
        for (Webhook webhook : webhooks) {

            WebhookDelivery delivery = new WebhookDelivery();

            delivery.setWebhookId(webhook.getId());
            delivery.setPaymentId(payment.getId());
            delivery.setStatus("PENDING");
            delivery.setRetryCount(0);
            delivery.setLastAttempt(LocalDateTime.now());

            delivery = webhookDeliveryRepository.save(delivery);

            senderService.sendWebhook(webhook, payment, delivery);
        }
    }
}