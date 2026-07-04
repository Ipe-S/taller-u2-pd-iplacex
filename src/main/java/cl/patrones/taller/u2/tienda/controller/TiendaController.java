package cl.patrones.taller.u2.tienda.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import cl.patrones.taller.u2.bodegaje.service.BodegajeService;
import cl.patrones.taller.u2.catalogo.domain.Aviso;
import cl.patrones.taller.u2.catalogo.domain.Categoria;
import cl.patrones.taller.u2.catalogo.domain.ProductoAvisoAdapter;
import cl.patrones.taller.u2.catalogo.repository.ClasificacionRepository;
import cl.patrones.taller.u2.catalogo.service.CategoriaService;
import cl.patrones.taller.u2.pedidos.Carrito;

@Controller
public class TiendaController {

	private final BodegajeService bodegajeService;
	private final CategoriaService categoriaService;
	private final ClasificacionRepository clasificacionRepository;
	private final Carrito carrito;

	public TiendaController(
			BodegajeService bodegajeService,
			CategoriaService categoriaService,
			ClasificacionRepository clasificacionRepository,
			Carrito carrito) {
		this.bodegajeService = bodegajeService;
		this.categoriaService = categoriaService;
		this.clasificacionRepository = clasificacionRepository;
		this.carrito = carrito;
	}

	@GetMapping("/carrito")
	public String carrito(Model model) {
		model.addAttribute("carrito", carrito);
		return "carrito";
	}

	@GetMapping("/carrito/add")
	public String agregarAlCarrito(
			@RequestParam String sku,
			@RequestParam(defaultValue = "1") int cant) {

		Aviso aviso = bodegajeService.getProductoBySku(sku)
			.map(producto -> {
				Categoria categoria = clasificacionRepository
					.findFirstBySku(sku)
					.map(c -> c.getCategoria())
					.orElse(null);
				return (Aviso) new ProductoAvisoAdapter(producto, categoria);
			})
			.orElseThrow();

		carrito.agregar(aviso, cant);
		return "redirect:/carrito";
	}

	@GetMapping("/carrito/remove")
	public String quitarDelCarrito(@RequestParam String sku) {
		carrito.quitar(sku);
		return "redirect:/carrito";
	}

	@GetMapping("/")
	public String inicio(Model model) {
		List<Aviso> avisos = bodegajeService.getProductos().stream()
			.map(producto -> {
				Categoria categoria = clasificacionRepository
					.findFirstBySku(producto.getSku())
					.map(c -> c.getCategoria())
					.orElse(null);
				return new ProductoAvisoAdapter(producto, categoria);
			})
			.collect(Collectors.toList());

		model.addAttribute("avisos", avisos);
		return "inicio";
	}

	@GetMapping("/categoria/{categoriaId}/{slug}")
	public String categoria(
			@PathVariable Long categoriaId,
			@PathVariable String slug,
			Model model) {

		Categoria categoria = categoriaService.getCategoriaPorId(categoriaId).orElseThrow();

		// Determinar qué IDs consultar: subcategorías si las hay, la propia si es hoja
		List<Categoria> todas = categoriaService.getCategorias();
		List<Long> idsAConsultar = todas.stream()
			.filter(c -> c.getPadre() != null && c.getPadre().getId().equals(categoriaId))
			.map(c -> c.getId())
			.collect(Collectors.toList());

		if (idsAConsultar.isEmpty()) {
			idsAConsultar.add(categoriaId);
		}

		// Recolectar SKUs de todos los IDs (sin duplicados)
		Set<String> skus = idsAConsultar.stream()
			.flatMap(id -> clasificacionRepository.findByCategoriaId(id).stream())
			.map(c -> c.getSku())
			.collect(Collectors.toSet());

		List<Aviso> avisos = bodegajeService.getProductosBySku(skus.toArray(new String[0])).stream()
			.map(producto -> new ProductoAvisoAdapter(producto, categoria))
			.collect(Collectors.toList());

		model.addAttribute("categoria", categoria);
		model.addAttribute("avisos", avisos);
		return "categoria";
	}

	@GetMapping("/ingresar")
	public String login() {
		return "login";
	}

	@GetMapping("/ubicacion")
	public String ubicacion() { return "ubicacion"; }

	@GetMapping("/contacto")
	public String contacto() { return "contacto"; }

}
