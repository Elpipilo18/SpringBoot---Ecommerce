package com.ecommerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.Model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer>{
	
}
