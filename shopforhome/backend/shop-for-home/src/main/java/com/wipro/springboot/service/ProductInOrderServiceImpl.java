/**
 * @author PV Suryasathwik
 * * Modified date 30/8/2022
 * Description :Implementation of IProductInOrderService
 */
package com.wipro.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wipro.springboot.entity.ProductInOrder;
import com.wipro.springboot.entity.User;
import com.wipro.springboot.repository.IProductInOrderRepository;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class ProductInOrderServiceImpl implements IProductInOrderService {

	@Autowired
	IProductInOrderRepository productInOrderRepository;
	/**
	 * @author PV Suryasathwik
	 * Modified Date 30/8/2022
	 * Description: update the quantity of product in cart
	 * param itemid ,quantity ,user entity
	 * return type void
	 * Exception none
	 */
	@Override
	@Transactional
	public void update(String itemId, Integer quantity, User user) {
		var op = user.getCart().getProducts().stream().filter(e -> itemId.equals(e.getProductId())).findFirst();
		op.ifPresent(productInOrder -> {
			productInOrder.setCount(quantity);
			productInOrderRepository.save(productInOrder);
		});

	}
	/**
	 * @author PV Suryasathwik
	 * Modified Date 30/8/2022
	 * Description: to find a specific order
	 * param itemid  ,user entity
	 * return type ProductInOrder entity
	 * Exception none
	 */
	@Override
	public ProductInOrder findOne(String itemId, User user) {
		var op = user.getCart().getProducts().stream().filter(e -> itemId.equals(e.getProductId())).findFirst();
		AtomicReference<ProductInOrder> res = new AtomicReference<>();
		op.ifPresent(res::set);
		return res.get();
	}
}
