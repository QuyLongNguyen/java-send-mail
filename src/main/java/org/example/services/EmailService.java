package org.example.services;

import org.example.dto.EmailDto;

public interface EmailService {

    boolean sendEmail(EmailDto emailDto);
}
