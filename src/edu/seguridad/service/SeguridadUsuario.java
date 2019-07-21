package edu.seguridad.service;

import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import edu.seguridad.model.Rol;
import edu.seguridad.model.Usuario;

public class SeguridadUsuario {

	private static Conector conectorHibernate = new Conector();
	private static String codigoEnviado = null;
	private static String codigoRecibido;

	public void signUp(String nombre, String apellido, String correo, String username, String pass) {

		try {
			conectorHibernate.startEntityManagerFactory();
			Rol rol = new Rol();
			rol = conectorHibernate.getEm().find(Rol.class, 2);

			Usuario item = new Usuario();
			item.setNombre(nombre);
			item.setApellido(apellido);
			item.setCorreo(correo);
			item.setUsername(username);
			item.setPassword(pass);
			item.setRol(rol);

			conectorHibernate.getEm().getTransaction().begin();
			conectorHibernate.getEm().merge(item);
			conectorHibernate.getEm().getTransaction().commit();

			conectorHibernate.stopEntityManagerFactory();
			System.out.println("Finalizo");

		} catch (PersistenceException e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				ConstraintViolationException exception = (ConstraintViolationException) e.getCause();
				if (exception.getSQLException().getMessage().contains(" for key 'correo'")) {
					System.out.println("Ya existe un usuario registrado con ese correo.");
				} else if (exception.getSQLException().getMessage().contains(" for key 'username'")) {
					System.out.println("Ya existe un usuario registrado con ese nombre de usuario.");
				} else {
					exception.printStackTrace();
				}
			} else {
				e.printStackTrace();
			}
			conectorHibernate.getEm().getTransaction().rollback();
		}
	}

	public void loginClient(String username, String password) {
		Usuario usuario = null;
		try {
			conectorHibernate.startEntityManagerFactory();
			Session session = conectorHibernate.getEm().unwrap(Session.class);
			Criteria criteria = session.createCriteria(Usuario.class);
			criteria.add(Restrictions.eq("username", username));
			usuario = (Usuario) criteria.uniqueResult();
			conectorHibernate.stopEntityManagerFactory();
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
		}

		if (usuario == null) {
			System.out.println("¡Nombre de usuario no registrado!");
		} else if (usuario.isPasswordValid(password)) {
			System.out.println("Bienvenido al Sistema");
		} else {
			System.out.println("¡Contraseña no valida!");
		}
	}

	public void verifyAccount(String correo) {
		try {
			conectorHibernate.startEntityManagerFactory();
			sendEmail(correo);
			codigoRecibido = codigoEnviado;
			if (codigoRecibido.equals(codigoEnviado)) {
				modifyStatus(correo);
			}
			conectorHibernate.stopEntityManagerFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Finalizo");

		codigoEnviado = null;
		codigoRecibido = null;
	}

	public void verifyPass(String correo, String pass) {
		try {
			conectorHibernate.startEntityManagerFactory();
			sendEmail(correo);
			codigoRecibido = codigoEnviado;
			if (codigoRecibido.equals(codigoEnviado)) {
				modifyPassword(correo, pass);
			}
			conectorHibernate.stopEntityManagerFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Finalizo");
		codigoEnviado = null;
		codigoRecibido = null;
	}

	public void modifyStatus(String correo) {
		try {
			conectorHibernate.startEntityManagerFactory();
			String query = "SELECT u FROM Usuario u where u.correo = :userCorreo";
			TypedQuery<Usuario> tq = conectorHibernate.getEm().createQuery(query, Usuario.class);
			tq.setParameter("userCorreo", correo);
			Usuario user = null;
			user = tq.getSingleResult();

			conectorHibernate.getEm().getTransaction().begin();
			user = conectorHibernate.getEm().find(Usuario.class, user.getIdUsuario());
			user.setVerificado(true);
			conectorHibernate.getEm().persist(user);
			conectorHibernate.getEm().flush();
			conectorHibernate.getEm().getTransaction().commit();

			conectorHibernate.stopEntityManagerFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void modifyPassword(String correo, String pass) {
		try {
			conectorHibernate.startEntityManagerFactory();
			String query = "SELECT u FROM Usuario u where u.correo = :userCorreo";
			TypedQuery<Usuario> tq = conectorHibernate.getEm().createQuery(query, Usuario.class);
			tq.setParameter("userCorreo", correo);
			Usuario user = null;
			user = tq.getSingleResult();

			conectorHibernate.getEm().getTransaction().begin();
			user = conectorHibernate.getEm().find(Usuario.class, user.getIdUsuario());
			user.setPassword(pass);
			conectorHibernate.getEm().persist(user);
			conectorHibernate.getEm().flush();
			conectorHibernate.getEm().getTransaction().commit();
			conectorHibernate.stopEntityManagerFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendEmail(String correo) {
		codigoEnviado = Integer.toString((int) (Math.random() * ((9999 - 1010) + 1)));

		try {
			Email email = new SimpleEmail();
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("componentesUlatina10@gmail.com", "Componentes10Ulatina"));
			email.setSSLOnConnect(true);
			email.setFrom("componentesUlatina10@gmail.com");
			email.setSubject("Verificar Cuenta");
			email.setMsg("Su codigo de verificacion es: " + codigoEnviado);
			email.addTo(correo);
			email.send();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
