/**
 * * @author Kartik Shokeen
 * Modified date 30/8/2022
 * Description :Implementation of IUserService Interface
 */
package com.wipro.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wipro.springboot.entity.Cart;
import com.wipro.springboot.entity.User;
import com.wipro.springboot.enums.ResultEnum;
import com.wipro.springboot.exception.MyException;
import com.wipro.springboot.repository.ICartRepository;
import com.wipro.springboot.repository.IUserRepository;

import java.util.Collection;
import java.util.List;

@Service
@DependsOn("passwordEncoder")
public class UserServiceImpl implements IUserService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	IUserRepository userRepository;

	@Autowired
	ICartRepository cartRepository;
	/**
	 * @author Kartik Shokeen
	 * Modified Date 30/8/2022
	 * Description: to find user by email
	 * param email
	 * return type user
	 * Exception none
	 */
	@Override
	public User findOne(String email) {
		return userRepository.findByEmail(email);
	}
	/**
	 * @author Kartik Shokeen
	 * Modified Date 30/8/2022
	 * Description: to find user by role
	 * param role
	 * return user
	 * Exception none
	 */
	@Override
	public Collection<User> findByRole(String role) {
		return userRepository.findAllByRole(role);
	}
	/**
	 * @author Kartik Shokeen
	 * Modified Date 30/8/2022
	 * Description: to save user
	 * param user
	 * return user
	 * Exception run time exception
	 */
	@Override
	@Transactional
	public User save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		try {
			User savedUser = userRepository.save(user);

			Cart savedCart = cartRepository.save(new Cart(savedUser));
			savedUser.setCart(savedCart);
			return userRepository.save(savedUser);

		} catch (Exception e) {
			throw new MyException(ResultEnum.VALID_ERROR);
		}

	}
	/**
	 * @author Kartik Shokeen
	 * Modified Date 30/8/2022
	 * Description: to update a user
	 * param user
	 * return user
	 * Exception run time exception
	 */
	@Override
	@Transactional
	public User update(User user) {
		User oldUser = userRepository.findByEmail(user.getEmail());
		oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
		oldUser.setName(user.getName());
		oldUser.setPhone(user.getPhone());
		oldUser.setAddress(user.getAddress());
		return userRepository.save(oldUser);
	}
	/**
	 * @author Kartik Shokeen
	 * Modified Date 30/8/2022
	 * Description: to find all the users
	 * param none
	 * return list
	 * Exception none
	 */
	@Override
	@Transactional
	public List<User> findAll() {
		return userRepository.findAll();
	}
	/**
	 * @author Kartik Shokeen
	 * Modified Date 30/8/2022
	 * Description: to update userID
	 * param userid
	 * return object
	 * Exception run time exception
	 */
	@Override
	@Transactional
	public Object update(Long userId) {
		return null;
	}
	/**
	 * @author Kartik Shokeen
	 * Modified Date 30/8/2022
	 * Description: to update role to manager(admin)
	 * param user
	 * return user
	 * Exception none
	 */
	@Override
	@Transactional
	public User update(String email) {
		User user = findOne(email);
		user.setRole("ROLE_MANAGER");

		return userRepository.save(user);
	}
	/**
	 * @author Kartik Shokeen
	 * Modified Date 30/8/2022
	 * Description: to find all the users
	 * param request
	 * return page
	 * Exception none
	 */
	@Override
	@Transactional
	public Page<User> findAll(PageRequest request) {
		return userRepository.findAll(request);
	}
	/**
	 * @author Kartik Shokeen
	 * Modified Date 30/8/2022
	 * Description: to update role to user
	 * param user
	 * return user
	 * Exception none
	 */
	@Override
	@Transactional
	public User removeAdmin(String email) {
		User user = findOne(email);
		user.setRole("ROLE_CUSTOMER");

		return userRepository.save(user);
	}
	/**
	 * @author Kartik Shokeen
	 * Modified Date 30/8/2022
	 * Description: to remove a user
	 * param email
	 * return void
	 * Exception none
	 */
	@Override
	public void removeUser(String email) {
		User user = findOne(email);
		if (user == null)
			throw new MyException(ResultEnum.USER_NOT_FOUNT);
		userRepository.delete(user);

	}

}
