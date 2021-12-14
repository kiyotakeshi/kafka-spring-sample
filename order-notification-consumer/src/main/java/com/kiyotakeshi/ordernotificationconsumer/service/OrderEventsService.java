package com.kiyotakeshi.ordernotificationconsumer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiyotakeshi.ordernotificationconsumer.domain.NotificationEmail;
import com.kiyotakeshi.ordernotificationconsumer.domain.OrderEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@Slf4j
@AllArgsConstructor
public class OrderEventsService {

    private final ObjectMapper objectMapper;
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public void processOrderEvent(ConsumerRecord<Integer, String> consumerRecord) throws JsonProcessingException {
        var orderEvent = objectMapper.readValue(consumerRecord.value(), OrderEvent.class);
        log.info("orderEvent: {}", orderEvent);

        switch (orderEvent.getOrderEventType()) {
            case NEW:
                sendMail(new NotificationEmail("Accept new order", "test@example.com", "Thank you for your order please wait for ship: " + orderEvent.getOrder()));
                break;
            case UPDATE:
                validate(orderEvent);
                sendMail(new NotificationEmail("Accept order change", "test@example.com", "Your order change is accepted: " + orderEvent.getOrder()));
                break;
            default:
                log.info("Invalid Order Event Type");
        }
    }

    private void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparer = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setFrom("kafka-spring-sample@example.com");
            helper.setTo(notificationEmail.getRecipient());
            helper.setSubject(notificationEmail.getSubject());
            helper.setText(build(notificationEmail.getBody()), true);
        };
        try {
            mailSender.send(messagePreparer);
            log.info("Email sent!!");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new RuntimeException("Exception occurred when sending mail to " + notificationEmail.getRecipient(), e);
        }
    }

    private void validate(OrderEvent orderEvent) {
        if (orderEvent.getOrderEventId() == null) {
            throw new IllegalArgumentException("Order Event Id is missing");
        }
    }

    String build(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        return templateEngine.process("mailTemplate", context);
    }
}
