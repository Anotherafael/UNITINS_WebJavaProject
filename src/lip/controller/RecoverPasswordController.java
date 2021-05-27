package lip.controller;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lip.model.RecoverPassword;
import lip.model.User;
import lip.repository.RecoverPasswordRepository;
import lip.repository.Repository;
import lip.repository.RepositoryException;
import lip.repository.UserRepository;
import lip.util.JakartaEmail;
import lip.util.Util;

@Named
@ViewScoped
public class RecoverPasswordController extends Controller<RecoverPassword> {

	private static final long serialVersionUID = -6957676461502646336L;

	private String email;
	private String code;
	private static User user = new User();
	private String password;

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public RecoverPassword getEntity() {
		if (entity == null) {
			entity = new RecoverPassword();
		}
		return entity;
	}


	public void send() {
		UserRepository repo = new UserRepository();
		User user = null;
		try {
			user = repo.findByEmail(getEmail());
		} catch (RepositoryException e) {
			e.printStackTrace();
		}

		if (user == null) {
			Util.addErrorMessage("Não foi encontrado esse email no sistema.");
			return;
		}

		double firstValue = Math.random() * 100;
		double secondValue = Math.random() * 100;

		DecimalFormat format = new DecimalFormat("000");
		String code = format.format(firstValue) + format.format(secondValue);

		JakartaEmail email = new JakartaEmail(getEmail(), "Esqueceu Senha", "Informe o seguinte código: " + code + " em http://localhost:8000/lip/views/auth/validation.xhtml");
		getEntity().setCode(code);
		getEntity().setUser(user);
		// gerando a data com 24 horas a mais
		LocalDateTime limitDate = LocalDateTime.now();
		getEntity().setLimitDate(limitDate.plusDays(1));
		getEntity().setUsed(false);

		try {
			if (addNewPassword()) {
				email.send();
				Util.addInfoMessage("Email enviado");
			} else {
				Util.addErrorMessage("Problemas ao salvar no banco. Tente novamente mais tarde.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Util.addErrorMessage("Problemas ao enviar o código por email.");
		}
	}

	public boolean addNewPassword() {
		Repository<RecoverPassword> repo = new Repository<RecoverPassword>();

		try {
			repo.beginTransaction();
			repo.save(getEntity());
			repo.commitTransaction();

			clean();
			return true;
		} catch (RepositoryException e) {
			repo.rollbackTransaction();
			return false;
		}
	}

	public boolean codeValidation() {
		
		UserRepository repo = new UserRepository();
		RecoverPasswordRepository passwordRepo = new RecoverPasswordRepository();
		
		User user = null;
		RecoverPassword recover = null;
		try {
			recover = passwordRepo.findByCode(getCode());
			user = repo.findUserByCode(getCode());
		} catch (RepositoryException e) {
			Util.addErrorMessage("Código inválido.");
			e.printStackTrace();
			return false;
		}

		if(recover.isUsed()) {
			Util.addErrorMessage("Código já utilizado.");
			return false;
		}
		
		if(recover.getLimitDate().isBefore(LocalDateTime.now())) {
			Util.addErrorMessage("Código expirado.");
			return false;
		}
		
		if (user == null) {
			Util.addErrorMessage("Não foi encontrado esse email no sistema.");
			return false;
		}
		
		System.out.println(user);
		RecoverPasswordController.user = user;
		recover.setUsed(true);
		
		Repository<RecoverPassword> repoRecover = new Repository<RecoverPassword>();
		try {
			repoRecover.beginTransaction();
			repoRecover.save(recover);
			repoRecover.commitTransaction();
		} catch (RepositoryException e) {
			repo.rollbackTransaction();
			Util.addErrorMessage("Não foi possível atualizar a senha.");
			return false;
		}
		
		Util.addInfoMessage("Usuário encontrado.");
		Util.redirect("/lip/views/auth/new_password.xhtml");
		return true;
		
	}
	
	public void saveNewPassword() {
		System.out.println(user);
		System.out.println(getPassword());
		user.setPassword(getPassword());
		Repository<User> repo = new Repository<User>();

		try {
			repo.beginTransaction();
			repo.save(user);
			repo.commitTransaction();
			Util.addInfoMessage("Senha atualizada com sucesso");
			user = null;
			clean();
		} catch (RepositoryException e) {
			repo.rollbackTransaction();
			Util.addErrorMessage("Não foi possível atualizar a senha.");
		}
	}
	
}
