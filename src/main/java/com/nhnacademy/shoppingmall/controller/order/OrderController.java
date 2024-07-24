package com.nhnacademy.shoppingmall.controller.order;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.util.OrderIdUtils;
import com.nhnacademy.shoppingmall.common.util.PaymentIdUtils;
import com.nhnacademy.shoppingmall.common.util.SavedPointIdUtils;
import com.nhnacademy.shoppingmall.common.util.UsedPointIdUtils;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.repository.impl.OrderRepositoryImpl;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import com.nhnacademy.shoppingmall.order.service.impl.OrderServiceImpl;
import com.nhnacademy.shoppingmall.payment.domain.Payment;
import com.nhnacademy.shoppingmall.payment.repository.impl.PaymentRepositoryImpl;
import com.nhnacademy.shoppingmall.payment.service.PaymentService;
import com.nhnacademy.shoppingmall.payment.service.impl.PaymentServiceImpl;
import com.nhnacademy.shoppingmall.point.savedPoint.domain.SavedPoint;
import com.nhnacademy.shoppingmall.point.savedPoint.repository.impl.SavedPointRepositoryImpl;
import com.nhnacademy.shoppingmall.point.savedPoint.sevice.SavedPointService;
import com.nhnacademy.shoppingmall.point.savedPoint.sevice.impl.SavedPointServiceImpl;
import com.nhnacademy.shoppingmall.point.usedPoint.domain.UsedPoint;
import com.nhnacademy.shoppingmall.point.usedPoint.repository.impl.UsedPointRepositoryImpl;
import com.nhnacademy.shoppingmall.point.usedPoint.service.UsedPointService;
import com.nhnacademy.shoppingmall.point.usedPoint.service.impl.UsedPointServiceImpl;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.exception.InsufficientStockException;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.productOrder.domain.ProductOrder;
import com.nhnacademy.shoppingmall.productOrder.service.ProductOrderService;
import com.nhnacademy.shoppingmall.thread.channel.RequestChannel;
import com.nhnacademy.shoppingmall.thread.request.ChannelRequest;
import com.nhnacademy.shoppingmall.thread.request.impl.PointChannelRequest;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.InsufficientPointException;
import com.nhnacademy.shoppingmall.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(method = RequestMapping.Method.POST, value = "/order/order.do")
public class OrderController implements BaseController {
    private final static int DELIVERY_COST = 5000;
    private final static double DISCOUNT_RATE = 0.10;
    private final static double POINT_RATE = 0.10;

    private UserService userService;
    private ProductService productService;
    private OrderService orderService;
    private PaymentService paymentService;
    private SavedPointService savedPointService;
    private UsedPointService usedPointService;
    private ProductOrderService productOrderService;

    private void init(HttpServletRequest req) {
        userService = (UserService) req.getServletContext().getAttribute("userService");
        productService = (ProductService) req.getServletContext().getAttribute("productService");
        orderService = (OrderService) req.getServletContext().getAttribute("orderService");
        paymentService = (PaymentService) req.getServletContext().getAttribute("paymentService");
        savedPointService = (SavedPointService) req.getServletContext().getAttribute("savedPointService");
        usedPointService = (UsedPointService) req.getServletContext().getAttribute("usedPointService");
        productOrderService = (ProductOrderService) req.getServletContext().getAttribute("productOrderService");
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        init(req);

        String userId = req.getSession().getAttribute("userId").toString();
        User user = userService.getUser(userId);
        String receiverName = req.getParameter("receiverName");
        String receiverAddress = req.getParameter("receiverAddress");
        Map<Product, Integer> productList = new HashMap<>();
        HttpSession session = req.getSession(false);
        List<Product> cart = (List<Product>) session.getAttribute("cart");
        List<ProductOrder> purchasedProducts = new ArrayList<>();

        Order order = generateOrder(userId, receiverName, receiverAddress);

        int totalPrice = 0;
        Product product = null;
        ProductOrder productOrder = null;
        String[] productIds = req.getParameterValues("productId[]");
        if (productIds != null) {
            for (String productId : productIds) {
                product = productService.getProduct(Integer.parseInt(productId));

                int price = Integer.parseInt(req.getParameter("price_" + productId));
                int quantity = Integer.parseInt(req.getParameter("quantity_" + productId));
                int stock = Integer.parseInt(req.getParameter("stock_" + productId));
                if(quantity > stock) {
                    throw new InsufficientStockException("Insufficient stock");
                }

                productOrder = new ProductOrder(product.getProductId(), order.getOrderId(), quantity, product.getProductPrice());
                purchasedProducts.add(productOrder);

                totalPrice += price * quantity;
                product.setProductStock(stock - quantity);
                productList.put(product, quantity);

                Product removeProduct = null;
                for(Product product1 : cart) {
                    if(product1.getProductId() == Integer.parseInt(productId)) {
                        removeProduct = product1;
                    }
                }
                cart.remove(removeProduct);
                productService.updateProduct(product);
            }
        }

        Payment payment = generatePayment(order.getOrderId(), totalPrice);
        SavedPoint savedPoint = generateSavedPoint(totalPrice, userId, payment.getPaymentId());
        UsedPoint usedPoint = generateUsedPoint(payment.getPaymentFinal(), userId, payment.getPaymentId());

        if(user.getUserPoint() < payment.getPaymentFinal()) {
            throw new InsufficientPointException("Insufficient point");
        }

        orderService.saveOrder(order);
        paymentService.savePayment(payment);
        usedPointService.saveUsedPoint(usedPoint);
        savedPointService.saveSavedPoint(savedPoint);
        productOrderService.saveProductOrders(purchasedProducts);
        saveUserPoint(req, user, savedPoint, usedPoint);

        session.setAttribute("cart", cart);
        req.setAttribute("productList", productList);
        req.setAttribute("payment", payment);
        req.setAttribute("savedPoint", savedPoint);

        return "/shop/order/order_checkout";
    }

    private Order generateOrder(String userId, String receiverName, String receiverAddress) {
        String orderId = OrderIdUtils.generateOrderId();

        return Order.createOrder(orderId, receiverName, receiverAddress, userId);
    }

    private Payment generatePayment(String orderId, int totalPrice) {
        String paymentId = PaymentIdUtils.generatePaymentId();
        int discountPrice = (int) (DISCOUNT_RATE * totalPrice);
        int finalPrice = totalPrice - discountPrice + DELIVERY_COST;

        if (finalPrice < 0) {
            finalPrice = 0;
        }

        return new Payment(paymentId, totalPrice, discountPrice, DELIVERY_COST, finalPrice, LocalDateTime.now(), orderId);
    }

    private SavedPoint generateSavedPoint(int totalPrice, String userId, String paymentId) {
        String savedPointId = SavedPointIdUtils.generateSavedPointId();
        int savedPointAmount = (int) (totalPrice * POINT_RATE);

        return new SavedPoint(savedPointId, savedPointAmount, LocalDateTime.now(), paymentId, userId);
    }

    private UsedPoint generateUsedPoint(int usedPointAmount, String userId, String paymentId) {
        String usedPointId = UsedPointIdUtils.generateUsedPointId();

        return new UsedPoint(usedPointId, usedPointAmount, LocalDateTime.now(), paymentId, userId);
    }

    private void saveUserPoint(HttpServletRequest req, User user, SavedPoint savedPoint, UsedPoint usedPoint) {
        RequestChannel requestChannel = (RequestChannel) req.getServletContext().getAttribute("requestChannel");
        ChannelRequest channelRequest = new PointChannelRequest(user, savedPoint, usedPoint);

        try {
            requestChannel.addRequest(channelRequest);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
