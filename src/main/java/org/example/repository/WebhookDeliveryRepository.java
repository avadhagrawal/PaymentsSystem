package org.example.repository;

import org.example.entity.WebhookDelivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebhookDeliveryRepository
        extends JpaRepository<WebhookDelivery, Long> {

}