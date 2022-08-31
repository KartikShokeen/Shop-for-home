/** 
 * @author 	RONGALI JASWANTH KUMAR
 * Modified date 30/8/2022
 * Description :The class Email
 * Instantiates a new Email
 
 */
package com.wipro.springboot.entity;

public class Email {

	private String recipient;
	private String msgBody;
	private String subject;

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}


	Email() {

	}

	Email(String recipient, String msgBody, String subject, String attachment) {
		this.recipient = recipient;
		this.msgBody = msgBody;
		this.subject = subject;
	}

}
