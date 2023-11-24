package dev.project.amqp.mail.consumer;

import dev.project.amqp.mail.consumer.model.EmailMessage;
import dev.project.amqp.mail.email.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

  private final EmailService emailService;

  public MessageConsumer(EmailService emailService) {
    this.emailService = emailService;
  }

  @RabbitListener(queues = "emailVerification")
  public void receiveMessage(EmailMessage message) {
    emailService.sendVerificationEmail(message);
  }
}
