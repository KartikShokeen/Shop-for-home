/**
 * @author Venkata Sai Chekuri
 * Modified date 30/8/2022
 * Description :Implementation of IProductService Interface
 */
package com.wipro.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wipro.springboot.entity.Product;
import com.wipro.springboot.enums.ProductStatusEnum;
import com.wipro.springboot.enums.ResultEnum;
import com.wipro.springboot.exception.MyException;
import com.wipro.springboot.repository.IProductRepository;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	IProductRepository productRepository;

	@Autowired
	IProductCategoryService categoryService;
	/**
	 * @author Venkata Sai Chekuri
	 * Modified Date 30/8/2022
	 * Description: to find a specific order by product id
	 * param productId
	 * return product entity
	 * Exception none
	 */
	@Override
	public Product findOne(String productId) {

		Product product = productRepository.findByProductId(productId);
		return product;
	}
	/**
	 * @author Venkata Sai Chekuri
	 * Modified Date 30/8/2022
	 * Description: to find all order by status of order
	 * param pageable
	 * return type page
	 * Exception none
	 */
	@Override
	public Page<Product> findUpAll(Pageable pageable) {
		return productRepository.findAllByProductStatusOrderByProductIdAsc(ProductStatusEnum.UP.getCode(), pageable);
	}
	
	/**
	 * @author Venkata Sai Chekuri
	 * Modified Date 30/8/2022
	 * Description: to find all orders
	 * param pageable
	 * return page
	 * Exception none
	 */
	@Override
	public Page<Product> findAll(Pageable pageable) {
		return productRepository.findAllByOrderByProductId(pageable);
	}
	/**
	 * @author Venkata Sai Chekuri
	 * Modified Date 30/8/2022
	 * Description: to find all product by category
	 * param categoryType,pageable
	 * return page
	 * Exception none
	 */
	@Override
	public Page<Product> findAllInCategory(Integer categoryType, Pageable pageable) {
		return productRepository.findAllByCategoryTypeOrderByProductIdAsc(categoryType, pageable);
	}
	/**
	 * @author Venkata Sai Chekuri
	 * Modified Date 30/8/2022
	 * Description: to increase the stock of product
	 * param productId , amount
	 * return void
	 * Exception run time exception
	 */
	@Override
	@Transactional
	public void increaseStock(String productId, int amount) {
		Product product = findOne(productId);
		if (product == null)
			throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);

		int update = product.getProductStock() + amount;
		product.setProductStock(update);
		productRepository.save(product);
	}
	/**
	 * @author Venkata Sai Chekuri
	 * Modified Date 30/8/2022
	 * Description: to decrease the stock of product
	 * param productId , amount
	 * return void
	 * Exception run time exception
	 */
	@Override
	@Transactional
	public void decreaseStock(String productId, int amount) {
		Product product = findOne(productId);
		if (product == null)
			throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);

		int update = product.getProductStock() - amount;
		if (update <= 0)
			throw new MyException(ResultEnum.PRODUCT_NOT_ENOUGH);

		product.setProductStock(update);
		productRepository.save(product);
	}
	/**
	 * @author Venkata Sai Chekuri
	 * Modified Date 30/8/2022
	 * Description: to set the status of product
	 * param productId 
	 * return void
	 * Exception run time exception
	 */
	@Override
	@Transactional
	public Product offSale(String productId) {
		Product product = findOne(productId);
		if (product == null)
			throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);

		if (product.getProductStatus() == ProductStatusEnum.DOWN.getCode()) {
			throw new MyException(ResultEnum.PRODUCT_STATUS_ERROR);
		}

		product.setProductStatus(ProductStatusEnum.DOWN.getCode());
		return productRepository.save(product);
	}
	/**
	 * @author Venkata Sai Chekuri
	 * Modified Date 30/8/2022
	 * Description: to set the status of product
	 * param productId 
	 * return void
	 * Exception run time exception
	 */
	@Override
	@Transactional
	public Product onSale(String productId) {
		Product product = findOne(productId);
		if (product == null)
			throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);

		if (product.getProductStatus() == ProductStatusEnum.UP.getCode()) {
			throw new MyException(ResultEnum.PRODUCT_STATUS_ERROR);
		}

		product.setProductStatus(ProductStatusEnum.UP.getCode());
		return productRepository.save(product);
	}
	/**
	 * @author Venkata Sai Chekuri
	 * Modified Date 30/8/2022
	 * Description: to update the product category
	 * param product entity
	 * return void
	 * Exception run time exception
	 */
	@Override
	public Product update(Product product) {

		categoryService.findByCategoryType(product.getCategoryType());
		if (product.getProductStatus() > 1) {
			throw new MyException(ResultEnum.PRODUCT_STATUS_ERROR);
		}

		return productRepository.save(product);
	}
	/**
	 * @author Venkata Sai Chekuri
	 * Modified Date 30/8/2022
	 * Description: to save a product
	 * param product entity 
	 * return void
	 * Exception none
	 */
	@Override
	public Product save(Product product) {
		return update(product);
	}
	/**
	 * @author Venkata Sai Chekuri
	 * Modified Date 30/8/2022
	 * Description: to delete a product
	 * param productId 
	 * return void
	 * Exception run time exception
	 */
	@Override
	public void delete(String productId) {
		Product product = findOne(productId);
		if (product == null)
			throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);
		productRepository.delete(product);

	}
	/**
	 * @author Venkata Sai Chekuri
	 * Modified Date 30/8/2022
	 * Description: to find all products
	 * param none 
	 * return list
	 * Exception 
	 */
	@Override
	@Transactional
	public List<Product> findAll() {

		return productRepository.findAll();
	}

}
