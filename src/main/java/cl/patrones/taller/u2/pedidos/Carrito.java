package cl.patrones.taller.u2.pedidos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import cl.patrones.taller.u2.catalogo.domain.Aviso;

@Component
@SessionScope
public class Carrito {

	private final List<ItemCarrito> items = new ArrayList<>();

	public void agregar(Aviso aviso, int cantidad) {
		for (ItemCarrito item : items) {
			if (item.getAviso().getSku().equals(aviso.getSku())) {
				item.setCantidad(cantidad);
				return;
			}
		}
		items.add(new ItemCarrito(aviso, cantidad));
	}

	public void quitar(String sku) {
		items.removeIf(i -> i.getAviso().getSku().equals(sku));
	}

	public List<ItemCarrito> getItems() { return items; }

	public int totalArticulos() {
		return items.stream().mapToInt(i -> i.getCantidad()).sum();
	}

	public Long total() {
		return items.stream().mapToLong(i -> i.subtotal()).sum();
	}

}
