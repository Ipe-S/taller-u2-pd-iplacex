package cl.patrones.taller.u2.tienda.menu;

import java.util.List;

import cl.patrones.taller.u2.catalogo.domain.Categoria;
import cl.patrones.taller.u2.tienda.menu.util.Slugger;

public class EnlaceItemMenu implements ItemMenu {

	private final String texto;
	private final String slug;
	private final String enlace;

	public EnlaceItemMenu(Categoria categoria) {
		this.texto = categoria.getNombre();
		this.slug = Slugger.toSlug(categoria.getNombre());
		this.enlace = "/categoria/" + categoria.getId() + "/" + this.slug;
	}

	public EnlaceItemMenu(String texto, String enlace) {
		this.texto = texto;
		this.slug = Slugger.toSlug(texto);
		this.enlace = enlace;
	}

	@Override
	public String getTexto() { return texto; }

	@Override
	public String getSlug() { return slug; }

	@Override
	public String getEnlace() { return enlace; }

	@Override
	public boolean tieneHijos() { return false; }

	@Override
	public List<ItemMenu> getHijos() { return List.of(); }

}
