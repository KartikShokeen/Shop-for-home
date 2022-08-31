package com.wipro.springboot;

//import com.wipro.springboot.service.IUserService;
import com.wipro.springboot.entity.*;
import com.wipro.springboot.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.Service;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShopForHomeApplicationTests {
	@Autowired
	IUserService service;

	@Test
	void testAssertNull() {
		User user = new User();

		assertNull(user.getPhone());
	}

	@Test
	void testGetUserByName() {
		User user = new User();
		user.setName("sai");
		assertEquals(user.getName(), "sai");
	}

	@Test
	void testGetCartById() {
		Cart cart = new Cart();
		cart.setCartId(12);
		assertEquals(cart.getCartId(), 12);
	}
@Test
	void testAssertNotNull() {
		Order order = new Order();
	order.setBuyerEmail("saii@gmail.com");
		assertNotNull(order.getBuyerEmail());
	}

@Test
void testAssertSame() {
	Product product = new Product();
	product.setProductName("Dinning table");
	assertSame(product.getProductName(),"Dinning table");
}

	@Test
	void testGetCategoryByName() {
		ProductCategory category = new ProductCategory();
		category.setCategoryName("Lighting");
		assertEquals(category.getCategoryName(), "Lighting");
	}
	@Test
	void testGetWishListById() {
		WishList wishList = new WishList();
		wishList.setId(12L);
		assertEquals(wishList.getId(), 12L);
	}

}