package cl.patrones.taller.u2.tienda.menu;

import java.util.ArrayList;
import java.util.List;

import cl.patrones.taller.u2.catalogo.domain.Categoria;
import cl.patrones.taller.u2.tienda.menu.util.Slugger;

public class CategoriaMenu implements ItemMenu {

	private final String texto;
	private final String slug;
	private final String enlace;
	private final List<ItemMenu> hijos = new ArrayList<>();

	public CategoriaMenu(Categoria categoria) {
		this.texto = categoria.getNombre();
		this.slug = Slugger.toSlug(categoria.getNombre());
		this.enlace = "/categoria/" + categoria.getId() + "/" + this.slug;
	}

	public CategoriaMenu(String texto, String enlace) {
		this.texto = texto;
		this.slug = Slugger.toSlug(texto);
		this.enlace = enlace;
	}

	public CategoriaMenu agregar(ItemMenu item) {
		hijos.add(item);
		return this;
	}

	@Override
	public String getTexto() { return texto; }

	@Override
	public String getSlug() { return slug; }

	@Override
	public String getEnlace() { return enlace; }

	@Override
	public boolean tieneHijos() { return !hijos.isEmpty(); }

	@Override
	public List<ItemMenu> getHijos() { return hijos; }

}
