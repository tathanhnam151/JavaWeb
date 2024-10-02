package com.namta.ecom.services.auth;

import com.namta.ecom.dto.SignupRequest;
import com.namta.ecom.dto.UserDto;
import com.namta.ecom.entity.Order;
import com.namta.ecom.entity.User;
import com.namta.ecom.enums.OrderStatus;
import com.namta.ecom.enums.UserRole;
import com.namta.ecom.repository.OrderRepository;
import com.namta.ecom.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private OrderRepository orderRepository;

    public UserDto createUser(SignupRequest signupRequest) {
        User user = new User();

        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(bCryptPasswordEncoder.encode(signupRequest.getPassword()));
        user.setRole(UserRole.CUSTOMER);
        User createdUser = userRepository.save(user);

        Order order = new Order();
        order.setAmount(0L);
        order.setUser(createdUser);
        order.setOrderStatus(OrderStatus.Pending);
        orderRepository.save(order);

        UserDto userDto = new UserDto();
        userDto.setId(createdUser.getId());

        return userDto;
    }

    public boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }

//    @PostConstruct
//    public void createAdminAccount() {
//        User adminAccount = userRepository.findByRole(UserRole.ADMIN);
//        if (null == adminAccount) {
//            User user = new User();
//            user.setEmail("admin@test.com");
//            user.setName("admin");
//            user.setRole(UserRole.ADMIN);
//            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
//            userRepository.save(user);
//        }
//    }
//
//    @PostConstruct
//    public void createCustomerAccount() {
//        User customerAccount = userRepository.findByRole(UserRole.CUSTOMER);
//        if (null == customerAccount) {
//            User user = new User();
//            user.setEmail("customer@test.com");
//            user.setName("customer");
//            user.setRole(UserRole.CUSTOMER);
//            user.setPassword(new BCryptPasswordEncoder().encode("test123"));
//            userRepository.save(user);
//        }
//    }
}
