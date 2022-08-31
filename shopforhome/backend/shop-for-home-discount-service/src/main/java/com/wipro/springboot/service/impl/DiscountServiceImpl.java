/**
 * @author Rongali Jaswant Kumar
 */
package com.wipro.springboot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wipro.springboot.entity.Discount;
import com.wipro.springboot.repository.IDiscountRepository;
import com.wipro.springboot.service.IDiscountService;

@Service
public class DiscountServiceImpl implements IDiscountService {

	@Autowired
	IDiscountRepository discountRepository;
	/**
	 * @author Rongali Jaswant Kumar
	 * last modified 30/8/2022
	 * description: Creating New coupon
	 * param code
	 * return discount Entry
	 * exception none 
	 */
	@Override
	@Transactional
	public Discount createCoupon(String code) {
		Integer status = 0;
		Discount coupon = new Discount();
		coupon.setId(code);
		coupon.setStatus(status.longValue());
		return discountRepository.save(coupon);
	}
	/**
	 * @author Rongali Jaswant Kumar
	 * last modified 30/8/2022
	 * description: to find all coupons
	 * param request
	 * return Page
	 * exception none none
	 */
	@Override
	@Transactional
	public Page<Discount> findAll(PageRequest request) {
		return discountRepository.findAll(request);

	}
	/**
	 * @author Rongali Jaswant Kumar
	 * last modified 30/8/2022
	 * description: Deleting all coupons
	 * param code
	 * return void
	 * exception none
	 */
	@Override
	@Transactional
	public void deleteCoupon(String code) {
		discountRepository.deleteById(code);
	}
	/**
	 * @author Rongali Jaswant Kumar
	 * last modified 30/8/2022
	 * description: to find all coupons
	 * param 
	 * return list
	 * exception none none
	 */
	@Override
	@Transactional
	public List<Discount> findAll() {
		return discountRepository.findAll();
	}

}
