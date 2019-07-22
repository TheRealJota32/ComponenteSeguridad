package edu.seguridad.service;

import java.util.List;

import edu.seguridad.model.Aplicacion;

public class SeguridadApp {
	private static Conector ch = new Conector();

	public List<Aplicacion> getApp() {
		List<Aplicacion> apps = null;
		try {
			ch.startEntityManagerFactory();
			String query = "SELECT a FROM Aplicacion a";
			apps = ch.getEm().createQuery(query, Aplicacion.class).getResultList();

			if (apps == null) {
				System.out.println("No se encontro ninguna app");
			}

			System.out.println();
			ch.stopEntityManagerFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return apps;
	}

	public void updateApp(int id, String nombre) {
		Aplicacion item = null;
		try {
			ch.startEntityManagerFactory();
			ch.getEm().getTransaction().begin();
			item = ch.getEm().find(Aplicacion.class, id);
			item.setNombre(nombre);
			ch.getEm().persist(item);
			ch.getEm().flush();
			ch.getEm().getTransaction().commit();

			ch.stopEntityManagerFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeApp(int id) {
		Aplicacion item = null;
		try {
			ch.startEntityManagerFactory();
			ch.getEm().getTransaction().begin();
			item = ch.getEm().find(Aplicacion.class, id);
			ch.getEm().remove(item);
			ch.getEm().flush();
			ch.getEm().getTransaction().commit();

			ch.stopEntityManagerFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
