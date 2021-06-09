package com.altavik.cart;

import com.altavik.cart.core.ICartService;
import com.altavik.cart.core.impl.CartServiceImpl;
import com.altavik.cart.models.Cart;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CartApplicationTests {

	@Autowired
	ApplicationContext ac;


	@Test
	void contextLoads() {
		ICartService cart = ac.getBean(ICartService.class);
		assertTrue(cart instanceof CartServiceImpl);
	}

}
