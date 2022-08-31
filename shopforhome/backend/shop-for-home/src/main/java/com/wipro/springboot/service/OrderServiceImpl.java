/**
 * @author Kartik Shokeen
 * Modified date 30/8/2022
 * Description :Implementation of IOrderService Interface
 */
package com.wipro.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wipro.springboot.entity.Order;
import com.wipro.springboot.entity.Product;
import com.wipro.springboot.entity.ProductInOrder;
import com.wipro.springboot.enums.OrderStatusEnum;
import com.wipro.springboot.enums.ResultEnum;
import com.wipro.springboot.exception.MyException;
import com.wipro.springboot.repository.IOrderRepository;
import com.wipro.springboot.repository.IProductInOrderRepository;
import com.wipro.springboot.repository.IProductRepository;
import com.wipro.springboot.repository.IUserRepository;

@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	IOrderRepository orderRepository;

	@Autowired
	IUserRepository userRepository;

	@Autowired
	IProductRepository productInfoRepository;

	@Autowired
	IProductService productService;

	@Autowired
	IProductInOrderRepository productInOrderRepository;
	/**
	 * @author Kartik Shokeen
	 * Modified Date 30/8/2022
	 * Description: fetches all the placed order
	 * param pageable
	 * return type page
	 * Exception none
	 */
	@Override
	public Page<Order> findAll(Pageable pageable) {
		return orderRepository.findAllByOrderByOrderStatusAscCreateTimeDesc(pageable);
	}
	
	/**
	 * @author Kartik Shokeen
	 * Modified Date 30/8/2022
	 * Description: fetches all the placed order based on status
	 * param pageable
	 * return type page
	 * Exception none
	 */
	@Override
	public Page<Order> findByStatus(Integer status, Pageable pageable) {
		return orderRepository.findAllByOrderStatusOrderByCreateTimeDesc(status, pageable);
	}
	/**
	 * @author Kartik Shokeen
	 * Modified Date 30/8/2022
	 * Description: fetches all the placed order for a user
	 * param pageable
	 * return type page
	 * Exception none
	 */
	@Override
	public Page<Order> findByBuyerEmail(String email, Pageable pageable) {
		return orderRepository.findAllByBuyerEmailOrderByOrderStatusAscCreateTimeDesc(email, pageable);
	}

	@Override
	public Page<Order> findByBuyerPhone(String phone, Pageable pageable) {
		return orderRepository.findAllByBuyerPhoneOrderByOrderStatusAscCreateTimeDesc(phone, pageable);
	}
	/**
	 * @author Kartik Shokeen
	 * Modified Date 30/8/2022
	 * Description: fetches a specific user based on orderID
	 * param pageable
	 * return type page
	 * Exception Run time exception - order not found
	 */
	@Override
	public Order findOne(Long orderId) {
		Order orderMain = orderRepository.findByOrderId(orderId);
		if (orderMain == null) {
			throw new MyException(ResultEnum.ORDER_NOT_FOUND);
		}
		return orderMain;
	}
	/**
	 * @author Kartik Shokeen
	 * Modified Date 30/8/2022
	 * Description: find a order by order id and update status to finish
	 * param pageable
	 * return type page
	 * Exception Run time exception - order not found
	 */
	@Override
	@Transactional
	public Order finish(Long orderId) {
		Order order = findOne(orderId);
		if (!order.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
			throw new MyException(ResultEnum.ORDER_STATUS_ERROR);
		}

		order.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
		orderRepository.save(order);
		return orderRepository.findByOrderId(orderId);
	}
	/**
	 * @author Kartik Shokeen
	 * Modified Date 30/8/2022
	 * Description: find a order by order id and update status to cancel
	 * param pageable
	 * return type page
	 * Exception Run time exception - order status error
	 */
	@Override
	@Transactional
	public Order cancel(Long orderId) {
		Order orderMain = findOne(orderId);
		if (!orderMain.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
			throw new MyException(ResultEnum.ORDER_STATUS_ERROR);
		}

		orderMain.setOrderStatus(OrderStatusEnum.CANCELED.getCode());
		orderRepository.save(orderMain);

		Iterable<ProductInOrder> products = orderMain.getProducts();
		for (ProductInOrder productInOrder : products) {
			Product product = productInfoRepository.findByProductId(productInOrder.getProductId());
			if (product != null) {
				productService.increaseStock(productInOrder.getProductId(), productInOrder.getCount());
			}
		}
		return orderRepository.findByOrderId(orderId);

	}
}
