package com.gulbalasalamov.bankloanapplication.controller;

import com.gulbalasalamov.bankloanapplication.model.dto.CustomerDTO;
import com.gulbalasalamov.bankloanapplication.model.entity.Notification;
import com.gulbalasalamov.bankloanapplication.service.NotificationService;
import org.aspectj.weaver.ast.Not;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/get/{notificationId}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long notificationId) {
        return new ResponseEntity(notificationService.getNotificationById(notificationId), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity addCustomer(@RequestBody Notification notification) {
        notificationService.createNotification(notification);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
