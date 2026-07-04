package cl.patrones.taller.u2.catalogo.domain;

import cl.patrones.taller.u2.bodegaje.domain.Producto;

public class ProductoAvisoAdapter extends Aviso {

	public ProductoAvisoAdapter(Producto producto, Categoria categoria) {
		setTitulo(producto.getNombre());
		setSku(producto.getSku());
		setImagen(producto.getImagen());
		setCategoria(categoria);
		setPrecio(Math.round(producto.getCosto() * 1.30));
		setStock(producto.getStocks().stream().mapToInt(s -> s.getCantidad()).sum());
	}

}
