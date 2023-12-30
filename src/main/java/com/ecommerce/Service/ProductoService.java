package com.ecommerce.Service;

import java.util.Optional;

import com.ecommerce.Model.Producto;

public interface ProductoService {
	public Producto save(Producto producto);
	public Optional<Producto> get(Integer id);
	public void update(Producto producto);
	public void delete(Integer id);
	
	
}
