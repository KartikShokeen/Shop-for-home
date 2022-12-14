/**
 * @author Venkata Sai Chekuri
 * Modified date 30/8/2022
 * Description :the Class IProductCategoryRepository
 * saves or fetches category of products in DB
 * 
 *
 */
package com.wipro.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.springboot.entity.ProductCategory;

import java.util.List;

@Repository
public interface IProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
	List<ProductCategory> findByCategoryTypeInOrderByCategoryTypeAsc(List<Integer> categoryTypes);

	List<ProductCategory> findAllByOrderByCategoryType();

	ProductCategory findByCategoryType(Integer categoryType);
}
