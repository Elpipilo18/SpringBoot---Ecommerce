package com.ecommerce.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("productos")
public class ProductoController {
	
	@GetMapping("")
	public String show() {
		return "productos/show";
	}
	
	@GetMapping("crear")
	public String create() {
		return "productos/create";
	}
	
}
