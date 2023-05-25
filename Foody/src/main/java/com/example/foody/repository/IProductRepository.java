package com.example.foody.repository;
import com.example.foody.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
}
