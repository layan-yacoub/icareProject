package com.example.icare.service;

import com.example.icare.domain.Message;
import com.example.icare.domain.Nutritionist;
import com.example.icare.domain.Patient;
import com.example.icare.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class MessageService {
    @Autowired
    private final MessageRepository messageRepository;

    //send msg
    public void sendMessage(Nutritionist nutritionist, Patient patient, String content, byte[] attachmentData) {
        Message message = new Message();
        message.setNutritionist(nutritionist);
        message.setPatient(patient);
        message.setMessageText(content);
        message.setMessageDate(Timestamp.valueOf(LocalDateTime.now()));

        if (attachmentData != null && attachmentData.length > 0) {
            message.setHasAttachment(true);
            message.setAttachment(attachmentData);

        } else {
            message.setHasAttachment(false);
        }

        messageRepository.save(message);
    }
    // send msg
}
