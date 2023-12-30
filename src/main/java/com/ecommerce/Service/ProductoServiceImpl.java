package com.ecommerce.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.Model.Producto;
import com.ecommerce.Repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService{

	@Autowired
	ProductoRepository productoRepo;
	
	public Producto save(Producto producto) {
		return productoRepo.save(producto);
	}

	public Optional<Producto> get(Integer id) {
		return productoRepo.findById(id);
	}

	public void update(Producto producto) {
		productoRepo.save(producto);
	}

	public void delete(Integer id) {
		productoRepo.deleteById(id);
	}
	
}
