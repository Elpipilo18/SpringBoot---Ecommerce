package com.ecommerce.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.Model.DetalleOrden;
import com.ecommerce.Model.Orden;
import com.ecommerce.Model.Producto;
import com.ecommerce.Service.ProductoService;

@Controller
@RequestMapping("/")
public class HomeController {

	private final org.slf4j.Logger log = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private ProductoService productoService;

	// para almacenar los detalles de la orden
	List<DetalleOrden> listaDetalles = new ArrayList<DetalleOrden>();

	// almacena datos de la orden
	Orden orden = new Orden();

	@GetMapping("")
	public String home(ModelMap model) {
		model.addAttribute("productos", productoService.findAll());
		return "usuario/home";
	}

	@GetMapping("/productoHome/{id}")
	public String productoHome(@PathVariable Integer id, ModelMap model) {
		log.info("id producto enviado como parametro {}", id);
		Producto producto = new Producto();
		Optional<Producto> productoOptional = productoService.get(id);
		producto = productoOptional.get();
		model.addAttribute("producto", producto);
		return "usuario/productohome";
	}

	@GetMapping("/carrito")
	public String cart(@RequestParam Integer id, @RequestParam Integer cantidad, ModelMap model) {
		// aqui se almacenara el detalle orden una vez presionado el boton de anadir al
		// carrito
		DetalleOrden detOrden = new DetalleOrden();
		// aqui obtendremos los valores del producto
		Producto producto = new Producto();
		// total de la orden
		double sumaTotal = 0;

		// buscamos el producto con el id
		Optional<Producto> optionalProducto = productoService.get(id);
		// guardamos el producto en la variable creada anteriormente
		producto = optionalProducto.get();

		// rellenamos el detalle con los datos del producto y la cantidad recibida
		detOrden.setCantidad(cantidad);
		detOrden.setPrecio(producto.getPrecio());
		detOrden.setNombre(producto.getNombre());
		detOrden.setTotal(producto.getPrecio() * cantidad);
		detOrden.setProducto(producto);

		// validar producto repetido para hacer un solo registro
		Integer idProducto = producto.getId();
		Boolean ingresado = listaDetalles.stream().anyMatch(p -> p.getProducto().getId() == idProducto);

		if (!ingresado) {
			// agregamos este objeto detOrden a la lista de detalles
			listaDetalles.add(detOrden);
		}

		// sumamos los totales de los detalles para obtener el total de la orden
		sumaTotal = listaDetalles.stream().mapToDouble(dt -> dt.getTotal()).sum();

		// en la variable global de orden guardamos el total
		orden.setTotal(sumaTotal);

		// usando modelmap pasamos los objetos a la vista carrito
		model.addAttribute("carrito", listaDetalles);
		model.addAttribute("orden", orden);

		// imprimimos por consola el producto y la cantidad
		log.info("producto anadido {}", optionalProducto.get());
		log.info("cantidad {}", cantidad);
		return "usuario/carrito";
	}

	// para quitar un producto del carrito
	@GetMapping("borrar/carrito/{id}")
	public String deleteProductCart(@PathVariable Integer id, ModelMap model) {
		List<DetalleOrden> ordenNueva = new ArrayList<DetalleOrden>();

		for (DetalleOrden detalleOrden : listaDetalles) {
			if (detalleOrden.getProducto().getId() != id) {
				ordenNueva.add(detalleOrden);
			}
		}

		// nueva lista con los productos restantes
		listaDetalles = ordenNueva;

		// realculando la suma total
		double sumaTotal = 0;

		sumaTotal = listaDetalles.stream().mapToDouble(dt -> dt.getTotal()).sum();

		// usando modelmap pasamos los objetos a la vista carrito
		model.addAttribute("carrito", listaDetalles);
		model.addAttribute("orden", orden);

		return "usuario/carrito";
	}

	@GetMapping("obtenerCarrito")
	public String getCart(ModelMap model) {
		
		// usando modelmap pasamos los objetos a la vista carrito
		model.addAttribute("carrito", listaDetalles);
		model.addAttribute("orden", orden);
		
		return "usuario/carrito";
	}
}
