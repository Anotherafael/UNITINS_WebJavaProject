package lip.util;

import java.io.InputStream;
import java.util.Properties;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class JakartaEmail {

	private String fromAddress;
	private String password;
	
	private String toAddress;
	private String title;
	private String content;
	
	public JakartaEmail(String toAddress, String title, String content) {
		Properties prop = new Properties();
		
		try (InputStream is = this.getClass().getResourceAsStream("/config/prop.properties")) {
			prop.load(is);
		} catch (Exception e) {
			System.out.println("\n\n\n DEVE CRIAR O ARQUIVO (email.properties) EM (src/config) \n\n\n");
		}
		
		setFromAddress(prop.getProperty("address"));
		setPassword(prop.getProperty("password"));
		setToAddress(toAddress);
		setTitle(title);
		setContent(content);
	}
	
	public void send() {
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		
		Session session = Session.getInstance(prop, new jakarta.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(getFromAddress(), getPassword());
			}
		});
		
		try {
			Message message = new MimeMessage(session);
			System.out.println(getFromAddress());
			System.out.println(getPassword());
			message.setFrom(new InternetAddress(getFromAddress()));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(getToAddress()));
			message.setSubject(getTitle());
			message.setText(getContent());
			Transport.send(message);
			
			System.out.println("Email sent.");
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
