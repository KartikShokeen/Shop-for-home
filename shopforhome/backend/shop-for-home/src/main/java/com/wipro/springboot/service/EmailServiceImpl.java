/**
 * @author Rongali Jaswant Kumar
 * Modified date 30/8/2022
  */
  package com.wipro.springboot.service;

  import java.io.File;
  
  import javax.mail.MessagingException;
  import javax.mail.internet.MimeMessage;
  
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.beans.factory.annotation.Value;
  import org.springframework.core.io.FileSystemResource;
  import org.springframework.mail.SimpleMailMessage;
  import org.springframework.mail.javamail.JavaMailSender;
  import org.springframework.mail.javamail.MimeMessageHelper;
  import org.springframework.stereotype.Service;
  
  import com.wipro.springboot.entity.Email;
  
  @Service
  public class EmailServiceImpl {
  
	  @Autowired
	  private JavaMailSender javaMailSender;
  
	  @Value("${spring.mail.username}")
	  private String sender;
	  /**
	   * @author Rongali Jaswant Kumar
	   * last modified 30/8/2022
	   * description: Sending mail for low stock
	   * param email entity
	   * @return String
	   * exception Run time exception Error while Sending Mail 
	   */
	  public String sendSimpleMail(Email email) {
  
		  try {
  
			  SimpleMailMessage mailMessage = new SimpleMailMessage();
			  mailMessage.setFrom(sender);
			  mailMessage.setTo(email.getRecipient());
			  mailMessage.setText(email.getMsgBody());
			  mailMessage.setSubject(email.getSubject());
  
			  javaMailSender.send(mailMessage);
			  return "Mail Sent Successfully...";
		  }
  
		  catch (Exception e) {
			  return "Error while Sending Mail";
		  }
	  }
  
  
  }
  