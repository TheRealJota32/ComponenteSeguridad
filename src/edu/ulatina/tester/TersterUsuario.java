package edu.ulatina.tester;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import edu.ulatina.usuario.Usuario;

public class TersterUsuario {
	private static EntityManagerFactory entityManagerFactory = null;
	private static EntityManager em = null;

	public static void main(String[] args) {
		startEntityManagerFactory();
		
		agregarCliente();
		
		System.out.println("Test login 1: Intento de login con nombre de usuario invalido");
		loginClient("jose321", "hunter2");
		System.out.println("Test login 2: Intento de login con contraseña invalida");
		loginClient("jose123", "hunter2");
		System.out.println("Test login 3: Login valido");
		loginClient("jose123", "12345");
		
		stopEntityManagerFactory();
	}

	public static void agregarCliente() {

		try {
			Usuario item = new Usuario();

			item.setNombre("Jose");
			item.setApellido("Ramirez");
			item.setCorreo("jotaramirez.100@gmail.com");
			item.setUsername("jose123");
			item.setPassword("12345");
			item.setRol(1);

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
