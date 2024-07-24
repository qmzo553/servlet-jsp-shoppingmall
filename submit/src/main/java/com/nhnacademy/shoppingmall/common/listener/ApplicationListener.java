package com.nhnacademy.shoppingmall.common.listener;

import com.nhnacademy.shoppingmall.address.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.address.service.AddressService;
import com.nhnacademy.shoppingmall.address.service.impl.AddressServiceImpl;
import com.nhnacademy.shoppingmall.category.repository.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.category.sevice.CategoryService;
import com.nhnacademy.shoppingmall.category.sevice.impl.CategoryServiceImpl;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.order.repository.impl.OrderRepositoryImpl;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import com.nhnacademy.shoppingmall.order.service.impl.OrderServiceImpl;
import com.nhnacademy.shoppingmall.payment.repository.impl.PaymentRepositoryImpl;
import com.nhnacademy.shoppingmall.payment.service.PaymentService;
import com.nhnacademy.shoppingmall.payment.service.impl.PaymentServiceImpl;
import com.nhnacademy.shoppingmall.point.savedPoint.repository.impl.SavedPointRepositoryImpl;
import com.nhnacademy.shoppingmall.point.savedPoint.sevice.SavedPointService;
import com.nhnacademy.shoppingmall.point.savedPoint.sevice.impl.SavedPointServiceImpl;
import com.nhnacademy.shoppingmall.point.usedPoint.repository.impl.UsedPointRepositoryImpl;
import com.nhnacademy.shoppingmall.point.usedPoint.service.UsedPointService;
import com.nhnacademy.shoppingmall.point.usedPoint.service.impl.UsedPointServiceImpl;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.productOrder.repository.impl.ProductOrderRepositoryImpl;
import com.nhnacademy.shoppingmall.productOrder.service.ProductOrderService;
import com.nhnacademy.shoppingmall.productOrder.service.impl.ProductOrderServiceImpl;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Objects;

@Slf4j
@WebListener
public class ApplicationListener implements ServletContextListener {

    private UserService userService;
    private ProductService productService;
    private CategoryService categoryService;
    private OrderService orderService;
    private PaymentService paymentService;
    private SavedPointService savedPointService;
    private UsedPointService usedPointService;
    private ProductOrderService productOrderService;
    private AddressService addressService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // application 시작시 테스트 계정인 admin,user 등록합니다. 만약 존재하면 등록하지 않습니다.

        userService = new UserServiceImpl(new UserRepositoryImpl());
        productService = new ProductServiceImpl(new ProductRepositoryImpl());
        categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());
        orderService = new OrderServiceImpl(new OrderRepositoryImpl());
        paymentService = new PaymentServiceImpl(new PaymentRepositoryImpl());
        savedPointService = new SavedPointServiceImpl(new SavedPointRepositoryImpl());
        usedPointService = new UsedPointServiceImpl(new UsedPointRepositoryImpl());
        productOrderService = new ProductOrderServiceImpl(new ProductOrderRepositoryImpl());
        addressService = new AddressServiceImpl(new AddressRepositoryImpl());

        DbConnectionThreadLocal.initialize();
        initializeAccount();
        log.info("init Account complete");
//        for(int i = 0; i < 50; i++) {
//            orderService.saveOrder(new Order(OrderIdUtils.generateOrderId(), "user" + i, "광주", "user"));
//        }
//        for(int i = 0; i < 50; i++) {
//            paymentService.savePayment(new Payment(PaymentIdUtils.generatePaymentId(), 1000, 1000, 5000, 2000, "a"));
//        }
//        for(int i = 0; i < 50; i++) {
//            savedPointService.saveSavedPoint(new SavedPoint(SavedPointIdUtils.generateSavedPointId(), 1000, LocalDateTime.now(), "a","user"));
//        }
//        for(int i = 0; i < 50; i++) {
//            usedPointService.saveUsedPoint(new UsedPoint(UsedPointIdUtils.generateUsedPointId(), 1000, LocalDateTime.now(), "a", "user"));
//        }
//        for(int i = 0; i < 50; i++) {
//            productService.saveProduct(new Product("item" + i, 1000, 1000, null, 1));
//        }
//        for(int i = 0; i < 50; i++) {
//            userService.saveUser(new User("a" + i, "a" + i, "1", "19991022", User.Auth.ROLE_USER));
//        }
        DbConnectionThreadLocal.reset();

        sce.getServletContext().setAttribute("userService", userService);
        sce.getServletContext().setAttribute("productService", productService);
        sce.getServletContext().setAttribute("categoryService", categoryService);
        sce.getServletContext().setAttribute("orderService", orderService);
        sce.getServletContext().setAttribute("paymentService", paymentService);
        sce.getServletContext().setAttribute("savedPointService", savedPointService);
        sce.getServletContext().setAttribute("usedPointService", usedPointService);
        sce.getServletContext().setAttribute("productOrderService", productOrderService);
        sce.getServletContext().setAttribute("addressService", addressService);
    }

    private void initializeAccount() {

        User admin = new User("admin", "admin", "1234", "19991022", User.Auth.ROLE_ADMIN);
        User user = new User("user", "user", "1234", "19991022", User.Auth.ROLE_USER);

        if(Objects.isNull(userService.getUser(admin.getUserId()))) {
            userService.saveUser(admin);
        }

        if(Objects.isNull(userService.getUser(user.getUserId()))) {
            userService.saveUser(user);
        }
    }
}
