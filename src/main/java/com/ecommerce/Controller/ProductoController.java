package com.ecommerce.Controller;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
	public String show() {
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
}
