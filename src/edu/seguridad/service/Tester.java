package edu.seguridad.service;

import edu.seguridad.model.Usuario;

public class Tester {

	public static void main(String[] args) {
		SeguridadUsuario su = new SeguridadUsuario();
		// SeguridadAppRol ar = new SeguridadAppRol();
		// SeguridadRol sr = new SeguridadRol();
		// SeguridadModulo sm = new SeguridadModulo();
		// SeguridadFuncionalidad sf = new SeguridadFuncionalidad();
		Usuario u = null;

		// ar.addAppRol("Turismo", "Administrador");
		// ar.addRol("Visitador");

		su.signUp("Sebas", "Vargas", "sebasVargas@gmail.com", "sebas", "1234");
		//System.out.println(u.getNombre() + u.getIdUsuario());
		// su.signUp("Kenneth", "Murillo", "kenneth@gmail.com", "kenneth12", "123");
		// su.verifyAccount("jotaramirez.100@gmail.com");
		u =  su.loginClient("sebas", "1234");
		su.addAppForClient(u.getIdUsuario(), 1);
		//System.out.println(u.getIdUsuario());
		// su.verifyPass("jotaramirez.100@gmail.com", "12345");
		// su.addAppForClient(2, 1);

		// sr.addRolForModulo(1, "Lugares");
		// sr.addRolForModulo(1, "Gestion");
		// sr.addOldRol(2, 3);

		// sm.updateModulo(3, "Lugares");
		// sm.addFunction(3, "Ver");
		// sm.addFunction(3, "Buscar");
		// sm.addFunction(3, "Compartir");
		// sm.addFunction(4, "Agregar");
		// sm.addFunction(4, "Modificar");
		// sm.addFunction(4, "Eliminar");

		// sf.updateFunction(1, "Ver");
	}

}
