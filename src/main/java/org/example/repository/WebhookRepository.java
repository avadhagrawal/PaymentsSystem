package org.example.repository;

import org.example.entity.Webhook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WebhookRepository extends JpaRepository<Webhook, Long> {

    List<Webhook> findByActiveTrue();
}