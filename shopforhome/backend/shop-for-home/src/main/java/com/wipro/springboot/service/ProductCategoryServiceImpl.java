/**
	 * @author PV Suryasathwik 
	 * Modified date 30/8/2022
	 * Description :Implementation of IProductCategoryService
	 */
	package com.wipro.springboot.service;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;
	import org.springframework.transaction.annotation.Transactional;
	
	import com.wipro.springboot.entity.ProductCategory;
	import com.wipro.springboot.enums.ResultEnum;
	import com.wipro.springboot.exception.MyException;
	import com.wipro.springboot.repository.IProductCategoryRepository;
	
	import java.util.List;
	
	@Service
	public class ProductCategoryServiceImpl implements IProductCategoryService {
		@Autowired
		IProductCategoryRepository productCategoryRepository;
		/**
		 * @author PV Suryasathwik 
		 * Modified Date 30/8/2022
		 * Description:find all products 
		 * param 
		 * return type list
		 * Exception none
		 */
		@Override
		public List<ProductCategory> findAll() {
			List<ProductCategory> res = productCategoryRepository.findAllByOrderByCategoryType();
			return res;
		}
		/**
		 * @author PV Suryasathwik 
		 * Modified Date 30/8/2022
		 * Description:find all products by a category
		 * param category type
		 * return type list
		 * Exception none
		 */
		@Override
		public ProductCategory findByCategoryType(Integer categoryType) {
			ProductCategory res = productCategoryRepository.findByCategoryType(categoryType);
			if (res == null)
				throw new MyException(ResultEnum.CATEGORY_NOT_FOUND);
			return res;
		}
		/**
		 * @author PV Suryasathwik 
		 * Modified Date 30/8/2022
		 * Description:find all products in a category
		 * param 
		 * return type list
		 * Exception none
		 */
		@Override
		public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
			List<ProductCategory> res = productCategoryRepository
					.findByCategoryTypeInOrderByCategoryTypeAsc(categoryTypeList);
			return res;
		}
		
		/**
		 * @author PV Suryasathwik 
		 * Modified Date 30/8/2022
		 * Description:save a new category
		 * param productcategory entity
		 * return type productcategory entity
		 * Exception none
		 */
		@Override
		@Transactional
		public ProductCategory save(ProductCategory productCategory) {
			return productCategoryRepository.save(productCategory);
		}
	
	}
	