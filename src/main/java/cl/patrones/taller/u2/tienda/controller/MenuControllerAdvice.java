package cl.patrones.taller.u2.tienda.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import cl.patrones.taller.u2.catalogo.domain.Categoria;
import cl.patrones.taller.u2.catalogo.service.CategoriaService;
import cl.patrones.taller.u2.tienda.menu.CategoriaMenu;
import cl.patrones.taller.u2.tienda.menu.EnlaceItemMenu;
import cl.patrones.taller.u2.tienda.menu.ItemMenu;

@ControllerAdvice
public class MenuControllerAdvice {

	private final CategoriaService categoriaService;

	public MenuControllerAdvice(CategoriaService categoriaService) {
		this.categoriaService = categoriaService;
	}

	@ModelAttribute("menu")
	public List<ItemMenu> menu() {
		List<Categoria> todas = categoriaService.getCategorias();

		// Dropdown "Categorías" con subcategorías anidadas
		CategoriaMenu categoriasMenu = new CategoriaMenu("Categorías", "#");
		for (Categoria raiz : todas) {
			if (raiz.getPadre() != null) continue;

			CategoriaMenu raizMenu = new CategoriaMenu(raiz);
			todas.stream()
				.filter(c -> c.getPadre() != null && c.getPadre().getId().equals(raiz.getId()))
				.map(EnlaceItemMenu::new)
				.forEach(raizMenu::agregar);

			categoriasMenu.agregar(raizMenu);
		}

		List<ItemMenu> menu = new ArrayList<>();
		menu.add(new EnlaceItemMenu("Inicio", "/"));
		menu.add(categoriasMenu);
		menu.add(new EnlaceItemMenu("Ubicación", "/ubicacion"));
		menu.add(new EnlaceItemMenu("Contacto", "/contacto"));
		return menu;
	}

}
