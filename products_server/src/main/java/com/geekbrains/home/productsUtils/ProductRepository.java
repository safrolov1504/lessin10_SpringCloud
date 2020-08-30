package com.geekbrains.home.productsUtils;


import com.geekbrains.home.DtoProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    List<DtoProduct> findAllBy();


    Product findByTitle(String name);
}
