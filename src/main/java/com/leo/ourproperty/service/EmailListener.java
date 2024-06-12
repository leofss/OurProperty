package com.leo.ourproperty.service;

import com.leo.ourproperty.entity.EmailMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class EmailListener {

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = "emailQueue")
    public void receiveMessage(EmailMessage emailMessage) {
        emailService.sendEmail(emailMessage.getTo(), emailMessage.getSubject(), emailMessage.getText());
    }
}
