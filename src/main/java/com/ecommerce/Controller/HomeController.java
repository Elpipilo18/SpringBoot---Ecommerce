package com.ecommerce.Controller;

import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.Model.Producto;
import com.ecommerce.Service.ProductoService;

@Controller
@RequestMapping("/")
public class HomeController {

	private final org.slf4j.Logger log = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private ProductoService productoService;

	@GetMapping("")
	public String home(ModelMap model) {
		model.addAttribute("productos", productoService.findAll());
		return "usuario/home";
	}

	@GetMapping("productoHome/{id}")
	public String productoHome(@PathVariable Integer id, ModelMap model) {
		log.info("id producto enviado como parametro {}",id);
		Producto producto = new  Producto();
		Optional<Producto> productoOptional = productoService.get(id);
		producto = productoOptional.get();
		model.addAttribute("producto",producto);
		return "usuario/productohome";
	}
}
