package dev.project.amqp.mail.email;

import dev.project.amqp.mail.consumer.model.EmailMessage;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

  private final JavaMailSender emailSender;
  private final TemplateEngine templateEngine;

  @Value("${app.urlPrefix}")
  private String urlPrefix;

  public EmailService(JavaMailSender emailSender, TemplateEngine templateEngine) {
    this.emailSender = emailSender;
    this.templateEngine = templateEngine;
  }

  public void sendVerificationEmail(EmailMessage emailMessage) {
    MimeMessage message = emailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

    try {
      helper.setFrom("dernier.tp@rabbitmq.com");
      helper.setTo(emailMessage.email());
      helper.setSubject("Vérification d'e-mail");
      Context context = getContext(emailMessage);

      String emailContent = templateEngine.process("verification-email-template", context);
      helper.setText(emailContent, true);

      emailSender.send(message);
    } catch (MessagingException ignored) {
    }
  }

  private Context getContext(EmailMessage emailMessage) {
    Context context = new Context();
    context.setVariable("firstName", emailMessage.firstname());
    context.setVariable("lastName", emailMessage.lastname());
    context.setVariable("verificationLink", emailMessage.email());
    context.setVariable("urlPrefix", urlPrefix);
    return context;
  }

}
