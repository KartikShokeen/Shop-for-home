/**
 * @author Venkata Sai Chekuri
 * Modified date 30/8/2022
 * Description :the Class IProductRepository
 * saves or fetches product into that are placed in order in DB

 */
package com.wipro.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.springboot.entity.ProductInOrder;

@Repository
public interface IProductInOrderRepository extends JpaRepository<ProductInOrder, Long> {

}
