package com.cafe.example.cafeExample.utils;

import java.util.Iterator;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailUtils {
	
	@Autowired
	private JavaMailSender javaMailsender;

	public void sendSimpleMessage(String recievermail, String Subject, String text, List<String> ccmail) {

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom("devilip1999@gmail.com");
		mail.setSubject(Subject);
		mail.setText(text);
		mail.setTo(recievermail);
		if (!ccmail.isEmpty()) {
			mail.setCc(getArr(ccmail));
		}
		javaMailsender.send(mail);
	}
	
	@SuppressWarnings("unused")
	private String[] getArr(List<String> ccmail) {
		String[] arr= new String[ccmail.size()];
		    for (int i=0;i<ccmail.size();i++){
				arr[i]=ccmail.get(i);
				 }
		
		return arr;
		
	}
	
	public void forgetPasswordMail(String to,String subject,String password) throws MessagingException {
		
		MimeMessage message =  javaMailsender.createMimeMessage();
		MimeMessageHelper mhelper =  new MimeMessageHelper(message);
		
		mhelper.setFrom("devilip1999@gmail.com");
		mhelper.setTo(to);
		mhelper.setSubject(subject);
		String htmlMsg = "<p><b>Your Login details for Cafe Management System</b><br><b>Email: </b> " + to + " <br><b>Password: </b> " + password + "<br><a href=\"http://localhost:4200/\">Click here to login</a></p>";
        message.setContent(htmlMsg,"text/html");
        javaMailsender.send(message);
		
//		SimpleMailMessage mail = new SimpleMailMessage();
//		mail.setFrom("devilip1999@gmail.com");
//		mail.setSubject(subject);
//		mail.setTo(to);
//		mail.setText(htmlMsg);
//		javaMailsender.send(mail);
	}

}
