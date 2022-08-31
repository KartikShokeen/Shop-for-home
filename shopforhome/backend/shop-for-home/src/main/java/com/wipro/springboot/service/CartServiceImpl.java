/**
 * @author Dhanuja A
 * Modified date 30/8/2022
 * Description :Implementation of ICartService Interface 
 */
package com.wipro.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wipro.springboot.entity.Cart;
import com.wipro.springboot.entity.Order;
import com.wipro.springboot.entity.ProductInOrder;
import com.wipro.springboot.entity.User;
import com.wipro.springboot.repository.ICartRepository;
import com.wipro.springboot.repository.IOrderRepository;
import com.wipro.springboot.repository.IProductInOrderRepository;
import com.wipro.springboot.repository.IUserRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class CartServiceImpl implements ICartService {

	@Autowired
	IProductService productService;

	@Autowired
	IUserService userService;

	@Autowired
	IOrderRepository orderRepository;

	@Autowired
	IUserRepository userRepository;

	@Autowired
	IProductInOrderRepository productInOrderRepository;

	@Autowired
	ICartRepository cartRepository;

	@Override
	public Cart getCart(User user) {
		return user.getCart();
	}
	
	/**
	 * @author Dhanuja A
	 * Modified date 30/8/2022
	 * Description saves a new/update cart
	 * param ProductInOrder entity and User Entity
	 * return type void
	 * exception none
	 * 
	 */
	@Override
	@Transactional
	public void mergeLocalCart(Collection<ProductInOrder> productInOrders, User user) {
		Cart finalCart = user.getCart();
		productInOrders.forEach(productInOrder -> {
			Set<ProductInOrder> set = finalCart.getProducts();
			Optional<ProductInOrder> old = set.stream()
					.filter(e -> e.getProductId().equals(productInOrder.getProductId())).findFirst();
			ProductInOrder prod;
			if (old.isPresent()) {
				prod = old.get();
				prod.setCount(productInOrder.getCount() + prod.getCount());
			} else {
				prod = productInOrder;
				prod.setCart(finalCart);
				finalCart.getProducts().add(prod);
			}
			productInOrderRepository.save(prod);
		});
		cartRepository.save(finalCart);

	}
	/**
	 * @author Dhanuja A
	 * Modified date 30/8/2022
	 * Description deletes a item from Cart
	 * param itemId, User entity
	 * return type void
	 * exception none
	 * 
	 */
	@Override
	@Transactional
	public void delete(String itemId, User user) {
		var op = user.getCart().getProducts().stream().filter(e -> itemId.equals(e.getProductId())).findFirst();
		op.ifPresent(productInOrder -> {
			productInOrder.setCart(null);
			productInOrderRepository.deleteById(productInOrder.getId());
		});
	}
	
	/**
	 * @author Dhanuja A
	 * Modified date 30/8/2022
	 * Description: clears cart and saves order in DB
	 * param: user entity
	 * return type void
	 * exception none
	 */
	@Override
	@Transactional
	public void checkout(User user) {
		Order order = new Order(user);
		orderRepository.save(order);

		user.getCart().getProducts().forEach(productInOrder -> {
			productInOrder.setCart(null);
			productInOrder.setOrder(order);
			productService.decreaseStock(productInOrder.getProductId(), productInOrder.getCount());
			productInOrderRepository.save(productInOrder);
		});

	}

}
