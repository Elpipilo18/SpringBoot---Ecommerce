package com.ecommerce.Controller;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.Model.Producto;
import com.ecommerce.Model.Usuario;
import com.ecommerce.Service.ProductoService;
import com.ecommerce.Service.UploadFileService;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("productos")
public class ProductoController {
	
	//para imprimir en consola INFO ...
	private final Logger LOGGER = (Logger) LoggerFactory.getLogger(ProductoController.class);
	
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private UploadFileService uploadFileService;
	
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
	public String save(Producto producto,@RequestParam("img") MultipartFile file) throws IOException {
		LOGGER.info("este es el objeto producto {}",producto);
		Usuario user = new Usuario(2,"","","","","","","");
		producto.setUsuario(user);
		
		//imagen
		if(producto.getId() == null) { //cuando se crea un producto
			String nombreImagen = uploadFileService.saveImage(file);
			producto.setImagen(nombreImagen);
		}
		
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
	public String update(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
		
		Producto p = new Producto();
		p = productoService.get(producto.getId()).get();
		
		if(file.isEmpty()) {//cuando editamos el producto pero no cambiamos la imagen
			producto.setImagen(p.getImagen());
		}else{//cuando se edita tambien la imagen
			
			//eliminar cuando no sea la imagen por defecto
			if(!p.getImagen().equals("default.jpg")) {
				uploadFileService.deleteImage(p.getImagen());
			}
			String nombreImagen = uploadFileService.saveImage(file);
			producto.setImagen(nombreImagen);
		}
		producto.setUsuario(p.getUsuario());
		productoService.update(producto);
		return "redirect:/productos";
	}
	
	@GetMapping("eliminar/{id}")
	public String delete(@PathVariable Integer id) {
		
		Producto p = new Producto();
		p = productoService.get(id).get();
		
		//eliminar cuando no sea la imagen por defecto
		if(!p.getImagen().equals("default.jpg")) {
			uploadFileService.deleteImage(p.getImagen());
		}
		
		productoService.delete(id);
		return "redirect:/productos";
	}
}
