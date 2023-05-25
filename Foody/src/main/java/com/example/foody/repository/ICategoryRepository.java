package com.example.foody.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.foody.entity.Category;
@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
}
