/**
 * @author Rongali Jaswant Kumar
 * Modified Date:30/8/2022
 */
package com.wipro.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.springboot.entity.Discount;

@Repository
public interface IDiscountRepository extends JpaRepository<Discount, String> {

}
