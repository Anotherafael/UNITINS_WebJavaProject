package lip.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lip.util.JakartaEmail;
import lip.util.Util;

@Named
@ViewScoped
public class SendEmailController implements Serializable {

	private static final long serialVersionUID = 1698383644507714974L;

	private String toAddress;
	private String title;
	private String content;

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public void send() {
		try {
			JakartaEmail email = new JakartaEmail(getToAddress(), getTitle(), getContent());
			email.send();
			Util.addInfoMessage("Email enviado com sucesso.");
		} catch (Exception e) {
			e.printStackTrace();
			Util.addErrorMessage("Email não enviado");
		}
		clean();
	}
	
	public void clean() {
		this.toAddress = null;
		this.title = null;
		this.content = null;
	}
}
