package com.SuperemeAppealReporter.api.service;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.mail.SimpleMailMessage;

import com.SuperemeAppealReporter.api.pojo.Mail;
import com.SuperemeAppealReporter.api.pojo.StaffMail;

public interface NotificationService {

	public void sendEmailNotification(Mail mail)  throws IOException, MessagingException;
	
	public void sendEmail(SimpleMailMessage simpleMailMessage);

	public void sendStaffEmailNotification(StaffMail onBoardingMail, String updateFlag) throws MessagingException;
	
}
