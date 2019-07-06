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
		agregarlugares("Estados Unidos", "New York", "Estatua de la Libertad", "40.6892° N, 74.0445° W");
		stopEntityManagerFactory();

	}

	public static void agregarlugares(String nombrePais, String nombreCiudad, String nombreTuris, String cordenadas) {

		try {
			Pais pais = new Pais();
			pais.setNombre(nombrePais);
			pais.setCiudades(new HashSet<Ciudad>());

			Ciudad ciudad = new Ciudad();
			ciudad.setNombre(nombreCiudad);
			ciudad.setPais(pais);
			ciudad.setLugares(new HashSet<LugarTuristico>());

			LugarTuristico lugarTuristico = new LugarTuristico();
			lugarTuristico.setCiudad(ciudad);
			lugarTuristico.setNombre(nombreTuris);
			lugarTuristico.setGeoCorde(cordenadas);

			pais.getCiudades().add(ciudad);

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

	public static void borrarLugarTuristico(int id) {
		LugarTuristico lt = null;
		try {
			em.getTransaction().begin();
			lt = em.find(LugarTuristico.class, id);

			em.remove(lt);
			em.flush();
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Finalizo");
	}

	public static void modificarLugarTuristico(int id, String nombre) {
		LugarTuristico lt = null;
		try {
			em.getTransaction().begin();
			lt = em.find(LugarTuristico.class, id);
			lt.setNombre(nombre);

			em.persist(lt);
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
