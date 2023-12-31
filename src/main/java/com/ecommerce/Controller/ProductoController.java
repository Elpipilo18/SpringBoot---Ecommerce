package com.ecommerce.Controller;

import java.util.Optional;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.Model.Producto;
import com.ecommerce.Model.Usuario;
import com.ecommerce.Service.ProductoService;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("productos")
public class ProductoController {
	
	//para imprimir en consola INFO ...
	private final Logger LOGGER = (Logger) LoggerFactory.getLogger(ProductoController.class);
	
	
	@Autowired
	private ProductoService productoService;
	
	@GetMapping("")
	public String show(ModelMap model) {
		model.addAttribute("productos",productoService.findAll());
		return "productos/show";
	}
	
	@GetMapping("crear")
	public String create() {
		return "productos/create";
	}
	
	@PostMapping("guardar")
	public String save(Producto producto) {
		LOGGER.info("este es el objeto producto {}",producto);
		Usuario user = new Usuario(2,"","","","","","","");
		producto.setUsuario(user);
		productoService.save(producto);
		return "redirect:/productos";
	}
	
	@GetMapping("editar/{id}")
	public String edit(@PathVariable Integer id, ModelMap model) {
		Producto producto = new Producto();
		Optional<Producto> optionalProducto = productoService.get(id);
		producto = optionalProducto.get();
		LOGGER.info("Producto Buscado : {}",producto);
		model.addAttribute("producto",producto);
		
		return "productos/edit";
	}
	@PostMapping("actualizar")
	public String update(Producto producto) {
		productoService.update(producto);
		return "redirect:/productos";
	}
	@GetMapping("eliminar/{id}")
	public String delete(@PathVariable Integer id) {
		productoService.delete(id);
		return "redirect:/productos";
	}
}
