package com.ecommerce.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.Model.Producto;
import com.ecommerce.Service.ProductoService;

@Controller
@RequestMapping("administrador")
public class AdministradorController {
	
	
	@Autowired
	private ProductoService productoService;
	
	@GetMapping("")
	public String home(ModelMap model) {
		List <Producto> productos = productoService.findAll();
		model.addAttribute("productos", productos);
		return "administrador/home";
	}
	
}
