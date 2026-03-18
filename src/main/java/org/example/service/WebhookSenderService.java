package org.example.service;

import org.example.entity.Payment;
import org.example.entity.Webhook;
import org.example.entity.WebhookDelivery;
import org.example.repository.WebhookDeliveryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class WebhookSenderService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebhookDeliveryRepository deliveryRepository;

    private static final Logger log =
            LoggerFactory.getLogger(WebhookSenderService.class);

    @Retryable(
            value = Exception.class,
            backoff = @Backoff(delay = 2000)
    )
    public void sendWebhook(Webhook webhook, Payment payment, WebhookDelivery delivery) {

        log.info("Sending webhook. webhookId={}, paymentId={}, attempt={}",
                webhook.getId(),
                payment.getId(),
                delivery.getRetryCount() + 1);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-Event-ID", payment.getId() + "-" + webhook.getId());
            headers.set("X-Correlation-ID", UUID.randomUUID().toString());

            HttpEntity<Payment> request = new HttpEntity<>(payment, headers);

            restTemplate.postForEntity(webhook.getUrl(), request, String.class);

            delivery.setStatus("SUCCESS");
            deliveryRepository.save(delivery);

            log.info("Webhook delivered successfully. webhookId={}, paymentId={}",
                    webhook.getId(),
                    payment.getId());

        } catch (Exception e) {

            delivery.setRetryCount(delivery.getRetryCount() + 1);
            delivery.setStatus("FAILED");
            deliveryRepository.save(delivery);

            throw new RuntimeException(e);
        }
    }

    @Recover
    public void recover(
            Exception ex,
            Webhook webhook,
            Payment payment,
            WebhookDelivery delivery) {

        delivery.setStatus("DEAD");
        delivery.setLastAttempt(LocalDateTime.now());

        deliveryRepository.save(delivery);

        log.error("Webhook permanently failed after retries. webhookId={}, paymentId={}",
                webhook.getId(),
                payment.getId(),
                ex);
    }
}