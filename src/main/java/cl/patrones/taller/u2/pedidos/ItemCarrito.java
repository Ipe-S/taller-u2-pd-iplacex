package cl.patrones.taller.u2.pedidos;

import cl.patrones.taller.u2.catalogo.domain.Aviso;

public class ItemCarrito {

	private final Aviso aviso;
	private int cantidad;

	public ItemCarrito(Aviso aviso, int cantidad) {
		this.aviso = aviso;
		this.cantidad = cantidad;
	}

	public Long subtotal() {
		return aviso.getPrecio() * cantidad;
	}

	public Aviso getAviso() { return aviso; }
	public int getCantidad() { return cantidad; }
	public void setCantidad(int cantidad) { this.cantidad = cantidad; }

}
