package com.bridgelabz.fundoonotes.userutility;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
@Component
public class EmailUtil {
	@Autowired
	private MailSender mailSender;

	public void sendEmail(String toEmail, String subject, String body) {
		try {
			SimpleMailMessage msg = new SimpleMailMessage();

			msg.setFrom("prema.s2608@gmail.com");

			msg.setTo("prema.s2608@gmail.com");

			msg.setSubject(subject);

			msg.setText(body);

			msg.setSentDate(new Date());

			System.out.println("Message is ready");
			mailSender.send(msg);

			System.out.println("Email Sent Successfully!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}}


