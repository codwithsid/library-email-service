package com.example.email_service.controller;

import com.example.email_service.model.EmailRequest;
import com.example.email_service.model.EmailResponse;
import com.example.email_service.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/email-service")  // Base path for email-related operations
public class EmailController {

    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private EmailService emailService;

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome() {
        return ResponseEntity.ok("Hello! This is the email API.");
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Email API is running fine.");
    }

    @PostMapping("/send")
    public ResponseEntity<EmailResponse> sendEmail(@RequestBody EmailRequest request) {
        logger.info("Received email request: {}", request);

        boolean result = emailService.sendEmail(request.getSubject(), request.getMessage(), request.getTo());

        if (result) {
            return ResponseEntity.ok(new EmailResponse("Email Sent", true));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new EmailResponse("Email Not Sent..!", false));
        }
    }
}
