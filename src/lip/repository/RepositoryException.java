package lip.repository;

import javax.faces.context.FacesContext;

import lip.util.Util;

public class RepositoryException extends Exception {
	
	private static final long serialVersionUID = -9176693987860586991L;

	public RepositoryException(String msg) {
		super(msg);
		Util.addErrorMessage(msg);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
	}
}
