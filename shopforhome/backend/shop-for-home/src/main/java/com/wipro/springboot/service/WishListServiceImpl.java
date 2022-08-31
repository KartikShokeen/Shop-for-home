/**
 * @author Dhanuja A
 * Modified date 30/8/2022
 * Description :Implementation of IWishlistService Interface
 */
package com.wipro.springboot.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.wipro.springboot.entity.User;
import com.wipro.springboot.entity.WishList;
import com.wipro.springboot.repository.IWishListRepository;
import com.wipro.springboot.repository.WishListCustomRepository;

@Service
@Transactional
public class WishListServiceImpl {

	private final IWishListRepository wishListRepository;

	@Autowired
	private WishListCustomRepository wishListCustomRepository;

	public WishListServiceImpl(IWishListRepository wishListRepository) {
		this.wishListRepository = wishListRepository;
	}
	/**
	 * @author Dhanuja A
	 * Modified date 30/8/2022
	 * Description to create a wishlist
	 * param wishlist 
	 * return type wishlist
	 * exception none
	 * 
	 */
	public WishList createWishlist(WishList wishList) {
		return wishListRepository.save(wishList);
	}

	public List<WishList> readWishList(Long userId) {
		return null;
	}
	/**
	 * @author Dhanuja A
	 * Modified date 30/8/2022
	 * Description to delete a wishlist
	 * param user entity,productID
	 * return type boolean
	 * exception none
	 * 
	 */
	public Boolean deleteWishlist(User user, String productId) {
		return wishListCustomRepository.deleteWishlist(user, productId);
	}
	/**
	 * @author Dhanuja A
	 * Modified date 30/8/2022
	 * Description to find a wishlist
	 * param id,request 
	 * return type page
	 * exception none
	 */
	public Page<WishList> findByBuyerEmail(Long id, PageRequest request) {
		return wishListRepository.findAllByUserId(id, request);
	}
}
