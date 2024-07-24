CREATE TABLE `users` (
  `user_id` varchar(50) NOT NULL COMMENT '아이디',
  `user_name` varchar(50) NOT NULL COMMENT '이름',
  `user_password` varchar(200) NOT NULL COMMENT 'mysql password 사용',
  `user_birth` varchar(8) NOT NULL COMMENT '생년월일 : 19840503',
  `user_auth` varchar(10) NOT NULL COMMENT '권한: ROLE_ADMIN,ROLE_USER',
  `user_point` int NOT NULL COMMENT 'default : 1000000',
  `created_at` datetime NOT NULL COMMENT '가입 일자',
  `latest_login_at` datetime DEFAULT NULL COMMENT '마지막 로그인 일자',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='회원';

CREATE TABLE `address` (
	`address_id` int NOT NULL auto_increment COMMENT '주소번호',
    `address_name` varchar(30) NOT NULL COMMENT '주소이름',
    `address_detail` varchar(50) NOT NULL COMMENT '상세주소',
    `user_id` varchar(50) NOT NULL COMMENT '아이디',
    PRIMARY KEY (`address_id`),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='주소';

CREATE TABLE `categories` (
	`category_id` int NOT NULL auto_increment COMMENT '카테고리 번호',
    `category_name` varchar(20) NOT NULL COMMENT '카테고리 이름',
    `parent_category_id` int COMMENT '상위 카테고리 번호',
	PRIMARY KEY (`category_id`),
    FOREIGN KEY (parent_category_id) REFERENCES categories(category_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='카테고리';

CREATE TABLE `products` (
	`product_id` int NOT NULL auto_increment COMMENT '상품번호',
    `product_name` varchar(20) NOT NULL COMMENT '상품이름',
    `product_price`int NOT NULL COMMENT '상품가격',
    `product_stock` int NOT NULL COMMENT '상품재고',
    `product_img` varchar(100)  COMMENT '상품이미지링크',
    `category_id` int NOT NULL COMMENT '카테고리번호',
    PRIMARY KEY (`product_id`),
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='상품';

CREATE TABLE `orders` (
	`order_id` varchar(10) NOT NULL COMMENT '주문번호',
    `receiver_name` varchar(20) NOT NULL COMMENT '받는사람이름',
    `receiver_address` varchar(50) NOT NULL COMMENT '받는사람주소',
    `order_status` varchar(15) NOT NULL COMMENT '주문상태',
    `created_at` datetime NOT NULL COMMENT '주문시점',
    `delivered_at` datetime DEFAULT NULL COMMENT '배달완료시점',
    `user_id` varchar(50) NOT NULL COMMENT '주문한사람',
    PRIMARY KEY (`order_id`),
	FOREIGN KEY (user_id) REFERENCES users(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='주문';

CREATE TABLE `products_orders` (
	`product_id` int NOT NULL COMMENT '상품번호',
    `order_id` varchar(10) NOT NULL COMMENT '주문번호',
    `product_count` int NOT NULL COMMENT '상품갯수',
    `product_price` int NOT NULL COMMENT '상품가격'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='주문상품';

CREATE TABLE `payments` (
	`payment_id` varchar(10) NOT NULL COMMENT '결제번호',
    `payment_total` int NOT NULL COMMENT '전체금액',
    `payment_discount` int NOT NULL COMMENT '할인금액',
    `payment_delivery` int NOT NULL COMMENT '배송금액',
    `payment_final` int NOT NULL COMMENT '최종금액',
    `payment_at` datetime NOT NULL COMMENT '결제시점',
    `order_id` varchar(10) NOT NULL COMMENT '주문번호',
    PRIMARY KEY (`payment_id`),
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='결제내역';

CREATE TABLE `saved_point` (
	`saved_point_id` varchar(10) NOT NULL COMMENT '적립내역번호',
    `saved_point_amount` int NOT NULL COMMENT '적립포인트',
    `saved_point_at` datetime NOT NULL COMMENT '적립시점',
    `payment_id` varchar(10) NOT NULL COMMENT '결제번호',
    `user_id` varchar(50) NOT NULL COMMENT '회원아이디',
    PRIMARY KEY (`saved_point_id`),
    FOREIGN KEY (payment_id) REFERENCES payments(payment_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='포인트적립내역';

CREATE TABLE `used_point` (
	`used_point_id` varchar(10) NOT NULL COMMENT '사용내역번호',
    `used_point_amount` int NOT NULL COMMENT '차감된포인트',
	`used_point_at` datetime NOT NULL COMMENT '사용시점',
    `payment_id` varchar(10) NOT NULL COMMENT '결제번호',
    `user_id` varchar(50) NOT NULL COMMENT '회원아이디',
    PRIMARY KEY (`used_point_id`),
    FOREIGN KEY (payment_id) REFERENCES payments(payment_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='포인트사용내역';
