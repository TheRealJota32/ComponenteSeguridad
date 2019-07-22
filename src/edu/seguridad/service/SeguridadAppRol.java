package edu.seguridad.service;

import java.util.HashSet;

import edu.seguridad.model.Aplicacion;
import edu.seguridad.model.Rol;

public class SeguridadAppRol {
	private static Conector ch = new Conector();

	public void addAppRol(String appParam, String rolParam) {
		try {
			ch.startEntityManagerFactory();
			Aplicacion app = new Aplicacion();
			app.setNombre(appParam);
			app.setRoles(new HashSet<Rol>());

			Rol rol = new Rol();
			rol.setNombre(rolParam);
			rol.setApp(app);

			app.getRoles().add(rol);

			ch.getEm().getTransaction().begin();
			ch.getEm().persist(app);
			ch.getEm().getTransaction().commit();

			ch.stopEntityManagerFactory();
			System.out.println("Finalizo");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addRol(String rolParam) {
		try {
			ch.startEntityManagerFactory();
			Aplicacion app = new Aplicacion();
			app = ch.getEm().find(Aplicacion.class, 1);

			Rol rol = new Rol();
			rol.setNombre(rolParam);
			rol.setApp(app);

			app.getRoles().add(rol);

			ch.getEm().getTransaction().begin();
			ch.getEm().merge(app);
			ch.getEm().getTransaction().commit();

			ch.stopEntityManagerFactory();
			System.out.println("Finalizo");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
