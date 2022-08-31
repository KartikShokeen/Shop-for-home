/**
 * @author Dhanuja A
 * Modified date 30/8/2022
 * Description :The class ICartRepository
 * Saves and gets data from DB
 
 */
package com.wipro.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.springboot.entity.Cart;

@Repository
public interface ICartRepository extends JpaRepository<Cart, Integer> {
}
