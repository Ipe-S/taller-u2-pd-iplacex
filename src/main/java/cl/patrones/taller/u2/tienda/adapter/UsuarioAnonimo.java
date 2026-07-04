package cl.patrones.taller.u2.tienda.adapter;

import cl.patrones.taller.u2.clientes.ClienteAnonimo;

public class UsuarioAnonimo extends Usuario {

	public UsuarioAnonimo() {
		super(new ClienteAnonimo());
	}
	
}
