/**
 *  * @author Dhanuja A
 * Modified date 30/8/2022
 * Description :The class IWishListRepository
 * Saves or fetches Wishlist in DB
 * 
 */
package com.wipro.springboot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.springboot.entity.WishList;

@Repository
public interface IWishListRepository extends JpaRepository<WishList, Integer> {

	Page<WishList> findAllByUserId(Long id, Pageable pageable);

}
