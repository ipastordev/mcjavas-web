package com.iti.mcjavas.beans.scheduling;

import java.io.Serializable;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpSession;

import com.iti.mcjavas.orm.dao.UsuariosDao;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1094801825228386363L;

	private String password;
	private String email;
	private boolean alertLoginIncorrect;
	private boolean logeado = false;
	private boolean userAdmin;

	public boolean isAlertLoginIncorrect() {
		return alertLoginIncorrect;
	}

	public void setAlertLoginIncorrect(boolean alertLoginIncorrect) {
		this.alertLoginIncorrect = alertLoginIncorrect;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isLogeado() {
		return logeado;
	}

	public void setLogeado(boolean logeado) {
		this.logeado = logeado;
	}

	public boolean isUserAdmin() {
		return userAdmin;
	}

	public void setUserAdmin(boolean userAdmin) {
		this.userAdmin = userAdmin;
	}

	// validate login
	public String validateUsernamePassword() {
		boolean valid = UsuariosDao.checkUserByEmailPassword(email, password);
		if (valid) {
			HttpSession session = SessionUtils.getSession();
			session.setAttribute("email", email);
			alertLoginIncorrect = false;
			logeado = true;
			if (UsuariosDao.isLogedUserAdmin(email)) {
				userAdmin = true;
			} else {
				userAdmin = false;
			}

			return "menu?faces-redirect=true";
		} else {
			alertLoginIncorrect = true;
			return "null";
		}
	}

	// logout event, invalidate session
	public String logout() {
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		userAdmin = false;
		logeado = false;
		alertLoginIncorrect = false;
		return "/login?faces-redirect=true";
	}

	// Un usuario logeado no puede ir a login.xhtml
	public void redirectLogedUser(ComponentSystemEvent event) {

		FacesContext fc = FacesContext.getCurrentInstance();

		if (logeado) {
			ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc
					.getApplication().getNavigationHandler();

			nav.performNavigation("menu.xhtml?faces-redirect=true");
		}
	}

	public void init() {
		setAlertLoginIncorrect(false);
	}

}
