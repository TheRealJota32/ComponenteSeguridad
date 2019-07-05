package edu.ulatina.tester;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import edu.ulatina.usuario.Usuario;

public class TersterUsuario {
	private static EntityManagerFactory entityManagerFactory = null;
	private static EntityManager em = null;

	public static void main(String[] args) {
		startEntityManagerFactory();
		agregarCliente();
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
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Finalizo");

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
