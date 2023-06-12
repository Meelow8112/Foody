package com.example.foody.repository;
import com.example.foody.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByAuthorContainingIgnoreCaseOrTitleContainingIgnoreCase(String keyword, String keyword1, Pageable pageable);
}
