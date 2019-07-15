package edu.ulatina.tester;

import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import edu.ulatina.usuario.Usuario;

public class TersterUsuario {
	private static EntityManagerFactory entityManagerFactory = null;
	private static EntityManager em = null;
	private static String codigoEnviado = null;
	private static String codigoRecibido;
	private static Scanner in;

	public static void main(String[] args) {
		startEntityManagerFactory();

		stopEntityManagerFactory();
	}

	public static void agregarCliente() {

		try {
			Usuario item = new Usuario();

			item.setNombre("Diego");
			item.setApellido("Alfaro");
			item.setCorreo("diego@ulatina.net");
			item.setUsername("diego123");
			item.setPassword("9876");
			item.setRol(item.getRol());

			em.getTransaction().begin();
			em.persist(item);
			em.flush();
			em.getTransaction().commit();
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
			em.getTransaction().rollback();
		}
	}

	public static void loginClient(String username, String password) {
		Session session = em.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Usuario.class);
		criteria.add(Restrictions.eq("username", username));
		Usuario usuario = null;

		try {
			usuario = (Usuario) criteria.uniqueResult();
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
		}

		if (usuario == null) {
			System.out.println("¡Nombre de usuario no registrado!");
		} else if (usuario.isPasswordValid(password)) {
			System.out.println("El nombre de usuario y contraseña son validos.");
		} else {
			System.out.println("¡Contraseña no valida!");
		}
	}

	public static void verifyAccount() {
		in = new Scanner(System.in);
		String correo = in.nextLine();
		try {
			sendEmail(correo);
			codigoRecibido = in.nextLine();
			if (codigoRecibido.equals(codigoEnviado)) {
				modifyStatus(correo);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Finalizo");
	}

	public static void sendEmail(String correo) {
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

	public static void modifyStatus(String correo) {
		try {
			String query = "SELECT u FROM Usuario u where u.correo = :userCorreo";
			TypedQuery<Usuario> tq = em.createQuery(query, Usuario.class);
			tq.setParameter("userCorreo", correo);
			Usuario user = null;
			user = tq.getSingleResult();

			em.getTransaction().begin();
			user = em.find(Usuario.class, user.getId());
			user.setVerificado(true);
			em.persist(user);
			em.flush();
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void startEntityManagerFactory() {
		if (entityManagerFactory == null) {
			try {
				entityManagerFactory = Persistence.createEntityManagerFactory("componentesUlatina");
				em = entityManagerFactory.createEntityManager();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void stopEntityManagerFactory() {
		if (entityManagerFactory != null) {
			if (entityManagerFactory.isOpen()) {
				try {
					entityManagerFactory.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			em.close();
			entityManagerFactory = null;
		}
	}
}
