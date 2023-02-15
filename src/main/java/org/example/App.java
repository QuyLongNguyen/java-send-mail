package org.example;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.text.StringSubstitutor;
import org.example.dto.AttachmentDto;
import org.example.dto.EmailDto;
import org.example.dto.MailerDto;
import org.example.enums.ContentType;
import org.example.services.EmailService;
import org.example.services.impl.MailjetService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class App {
    public static void main(String[] args) throws IOException {
        EmailService emailService = new MailjetService();

        //Create host mail
        String hostEmail = ""; //your host email
        String hostName = ""; //your host name

        //Create "mail to" list
        MailerDto toMailer = new MailerDto("", ""); //mail to
        List<MailerDto> toMailerList = Arrays.asList(toMailer);

        //Create mail body
        String mailBody = FileUtils.readFileToString(new File("welcome-mail.html"), "UTF-8");
            //Pasting dynamic content to mail template
        Map<String, String> mailAttributes = new HashMap<>();
        mailAttributes.put("name", toMailer.getName());
        StringSubstitutor stringSubstitutor = new StringSubstitutor(mailAttributes);
        mailBody = stringSubstitutor.replace(mailBody);

        //Create attachments
        File attachedFile = new File("attachment.txt");
        byte[] fileBytes = IOUtils.toByteArray(new FileInputStream(attachedFile));
        String base64content = Base64.getEncoder().encodeToString(fileBytes);
        AttachmentDto attachment = new AttachmentDto(attachedFile.getName(), ContentType.TEXT_PLAIN, base64content);
        List<AttachmentDto> attachments = Arrays.asList(attachment);

        //Create email dto
        EmailDto email = EmailDto.builder()
                .from(new MailerDto(hostEmail, hostName))
                .to(toMailerList)
                .subject("Mail demo greetings")
                .htmlPart(mailBody)
                .attachments(attachments)
                .build();

        //Send email
        System.out.println(emailService.sendEmail(email) ? "Send success" : "Send failed");
    }
}

