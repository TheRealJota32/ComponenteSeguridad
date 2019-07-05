package edu.ulatina.tester;

import java.util.HashSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import edu.ulatina.lugares.*;

public class TesterLugaresTuristicos {
	private static EntityManagerFactory entityManagerFactory = null;
	private static EntityManager em = null;

	public static void main(String[] args) {
		startEntityManagerFactory();
		agregarlugares();
		stopEntityManagerFactory();

	}

	public static void agregarlugares() {

		try {
			Pais pais = new Pais();
			pais.setNombre("Costa Rica");
			pais.setCiudades(new HashSet<Ciudad>());

			Ciudad ciudad = new Ciudad();
			ciudad.setNombre("Guanacaste");
			ciudad.setPais(pais);
			ciudad.setLugares(new HashSet<LugarTuristico>());

			Ciudad ciudad2 = new Ciudad();
			ciudad2.setNombre("Limon");
			ciudad2.setPais(pais);

			LugarTuristico lugarTuristico = new LugarTuristico();
			lugarTuristico.setCiudad(ciudad);
			lugarTuristico.setNombre("IDK");
			lugarTuristico.setGeoCorde("9.7489° N, 83.7534° W");

			pais.getCiudades().add(ciudad);
			pais.getCiudades().add(ciudad2);

			ciudad.getLugares().add(lugarTuristico);

			em.getTransaction().begin();
			em.persist(pais);
			em.persist(ciudad);
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
