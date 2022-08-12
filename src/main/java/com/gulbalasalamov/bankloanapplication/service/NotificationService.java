package com.gulbalasalamov.bankloanapplication.service;

import com.gulbalasalamov.bankloanapplication.exception.LoanNotFoundException;
import com.gulbalasalamov.bankloanapplication.exception.NotificationNotFoundException;
import com.gulbalasalamov.bankloanapplication.model.dto.CustomerDTO;
import com.gulbalasalamov.bankloanapplication.model.entity.LoanApplication;
import com.gulbalasalamov.bankloanapplication.model.entity.Notification;
import com.gulbalasalamov.bankloanapplication.model.mapper.Mapper;
import com.gulbalasalamov.bankloanapplication.repository.NotificationRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    private Optional<Notification> findNotificationById(Long id) {
        return Optional.ofNullable(notificationRepository.findById(id).orElseThrow(() ->
                new NotificationNotFoundException("Related notification with id: " + id + " not found")));
    }

    public Notification getNotificationById(Long notificationId) {
        return findNotificationById(notificationId).get();
    }

    public void createNotification(Notification notification) {
        notificationRepository.save(notification);
    }
    //    public String sendMessageForResult(LoanApplication loanApplication) {
//        String resultMessage = "Your loan application is " + loanApplication.getLoanApplicationStatus() ;
//        if(loanApplication.getLo() == LoanResult.APPROVED) {
//            resultMessage += " and your credit limit is " + creditApplication.getCreditLimit() + " TL.";
//        }
//
//        return "Notification message is sent to " + creditApplication.getCustomer().getPhone() + " number with the message : " + resultMessage ;
//    }

}
