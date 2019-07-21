package edu.seguridad.service;

import java.util.List;

import edu.seguridad.model.Aplicacion;

public class SeguridadApp {
	private static Conector conectorHibernate = new Conector();

	public List<Aplicacion> getApp() {
		List<Aplicacion> apps = null;
		try {
			conectorHibernate.startEntityManagerFactory();
			String query = "SELECT a FROM Aplicacion a";
			apps = conectorHibernate.getEm().createQuery(query, Aplicacion.class).getResultList();

			if (apps == null) {
				System.out.println("No se encontro ninguna app");
			}

			System.out.println();
			conectorHibernate.stopEntityManagerFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return apps;
	}

	public void updateApp(int id, String nombre) {
		Aplicacion item = null;
		try {
			conectorHibernate.startEntityManagerFactory();
			conectorHibernate.getEm().getTransaction().begin();
			item = conectorHibernate.getEm().find(Aplicacion.class, id);
			item.setNombre(nombre);
			conectorHibernate.getEm().persist(item);
			conectorHibernate.getEm().flush();
			conectorHibernate.getEm().getTransaction().commit();

			conectorHibernate.stopEntityManagerFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeApp(int id) {
		Aplicacion item = null;
		try {
			conectorHibernate.startEntityManagerFactory();
			conectorHibernate.getEm().getTransaction().begin();
			item = conectorHibernate.getEm().find(Aplicacion.class, id);
			conectorHibernate.getEm().remove(item);
			conectorHibernate.getEm().flush();
			conectorHibernate.getEm().getTransaction().commit();

			conectorHibernate.stopEntityManagerFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
