# Ví dụ và các Điểm cuối API (Endpoints)

Tài liệu này cung cấp danh sách đầy đủ tất cả các điểm cuối (endpoint) API trong dự án, kèm theo mô tả và ví dụ cURL.

**Lưu ý:** Đối với các endpoint yêu cầu xác thực, hãy thay thế `YOUR_JWT_TOKEN` bằng một JSON Web Token (JWT) hợp lệ.

---

## 1. Xác thực (`/auth`)

Xử lý việc đăng ký và đăng nhập của người dùng và người bán.

### 1.1. Đăng ký Người dùng

- **Method:** `POST`
- **Path:** `/auth/signup`
- **Mô tả:** Đăng ký một người dùng mới vào hệ thống.
- **Ví dụ cURL:**
  ```bash
  curl -X POST http://localhost:8080/api/v1/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "John Doe",
    "email": "user@example.com",
    "password": "password123"
  }'
  ```

### 1.2. Đăng nhập Người dùng

- **Method:** `POST`
- **Path:** `/auth/login`
- **Mô tả:** Xác thực người dùng và trả về một JWT.
- **Ví dụ cURL:**
  ```bash
  curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123"
  }'
  ```

---

## 2. Giỏ hàng (`/cart`)

Quản lý giỏ hàng của người dùng. Yêu cầu xác thực.

### 2.1. Lấy Giỏ hàng của Người dùng

- **Method:** `GET`
- **Path:** `/cart`
- **Mô tả:** Truy xuất giỏ hàng của người dùng hiện tại.
- **Ví dụ cURL:**
  ```bash
  curl -X GET http://localhost:8080/api/v1/cart \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
  ```

### 2.2. Thêm sản phẩm vào giỏ hàng

- **Method:** `POST`
- **Path:** `/cart/add`
- **Mô tả:** Thêm một sản phẩm vào giỏ hàng của người dùng.
- **Ví dụ cURL:**
  ```bash
  curl -X POST http://localhost:8080/api/v1/cart/add \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "productId": 1,
    "size": "M",
    "quantity": 2
  }'
  ```

### 2.3. Xóa sản phẩm khỏi giỏ hàng

- **Method:** `DELETE`
- **Path:** `/cart/item/{cartItemId}`
- **Mô tả:** Xóa một mặt hàng cụ thể khỏi giỏ hàng.
- **Ví dụ cURL:**
  ```bash
  curl -X DELETE http://localhost:8080/api/v1/cart/item/123 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
  ```

### 2.4. Cập nhật sản phẩm trong giỏ hàng

- **Method:** `PUT`
- **Path:** `/cart/item/{cartItemId}`
- **Mô tả:** Cập nhật số lượng của một mặt hàng cụ thể trong giỏ hàng.
- **Ví dụ cURL:**
  ```bash
  curl -X PUT http://localhost:8080/api/v1/cart/item/123 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "quantity": 3
  }'
  ```
  _(Lưu ý: Dựa trên mã nguồn, endpoint này cần một đối tượng `CartItem`. Ví dụ này giả định bạn chỉ cần gửi `quantity` để cập nhật.)_

---

## 3. Danh mục (`/categories`)

Cung cấp các endpoint để truy xuất danh mục sản phẩm.

### 3.1. Lấy Danh mục bằng ID

- **Method:** `GET`
- **Path:** `/categories/{categoryId}`
- **Mô tả:** Truy xuất một danh mục bằng ID của nó.
- **Ví dụ cURL:**
  ```bash
  curl -X GET http://localhost:8080/api/v1/categories/1
  ```

### 3.2. Lấy tất cả Danh mục

- **Method:** `GET`
- **Path:** `/categories`
- **Mô tả:** Truy xuất danh sách tất cả các danh mục.
- **Ví dụ cURL:**
  ```bash
  curl -X GET http://localhost:8080/api/v1/categories
  ```

---

## 4. Thông báo (`/notifications`)

Xử lý thông báo của người dùng. Yêu cầu xác thực.

### 4.1. Lấy Thông báo của Người dùng

- **Method:** `GET`
- **Path:** `/notifications`
- **Mô tả:** Truy xuất tất cả thông báo cho người dùng hiện tại.
- **Ví dụ cURL:**
  ```bash
  curl -X GET http://localhost:8080/api/v1/notifications \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
  ```

### 4.2. Tạo thông báo

- **Method:** `POST`
- **Path:** `/notifications`
- **Mô tả:** Tạo một thông báo mới.
- **Ví dụ cURL:**
  ```bash
  curl -X POST http://localhost:8080/api/v1/notifications \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "type": PRODUCT, // PRODUCT or SYSTEM or ORDER
    "message": "Đơn hàng của bạn đã được vận chuyển!"
  }'
  ```

### 4.3. Đánh dấu thông báo đã đọc

- **Method:** `PUT`
- **Path:** `/notifications/{id}/read`
- **Mô tả:** Đánh dấu một thông báo cụ thể là đã đọc.
- **Ví dụ cURL:**
  ```bash
  curl -X PUT http://localhost:8080/api/v1/notifications/1/read \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
  ```

### 4.4. Xóa thông báo

- **Method:** `DELETE`
- **Path:** `/notifications/{id}`
- **Mô tả:** Xóa một thông báo cụ thể.
- **Ví dụ cURL:**
  ```bash
  curl -X DELETE http://localhost:8080/api/v1/notifications/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
  ```

---

## 5. Đơn hàng (`/order`)

Quản lý đơn hàng của người dùng. Yêu cầu xác thực.

### 5.1. Tạo đơn hàng

- **Method:** `POST`
- **Path:** `/order`
- **Mô tả:** Tạo một hoặc nhiều đơn hàng từ các mặt hàng trong giỏ hàng của người dùng bằng địa chỉ giao hàng được cung cấp.
- **Lưu ý:** Để tạo đơn hàng, bạn cần thêm sản phẩm vào giỏ hàng trước, sau đó order sẽ lấy thông tin trong giỏ hàng để tạo đơn hàng.
- **Ví dụ cURL:**
  ```bash
  curl -X POST http://localhost:8080/api/v1/order \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "locality": "123 Main St",
    "address": "Apt 4B",
    "city": "Anytown",
    "state": "CA",
    "zipCode": "12345",
    "phoneNumber": "555-123-4567"
  }'
  ```

### 5.2. Lấy Lịch sử Đơn hàng của Người dùng

- **Method:** `GET`
- **Path:** `/order/user`
- **Mô tả:** Truy xuất lịch sử đơn hàng của người dùng hiện tại.
- **Ví dụ cURL:**
  ```bash
  curl -X GET http://localhost:8080/api/v1/order/user \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
  ```

### 5.3. Lấy Đơn hàng bằng ID

- **Method:** `GET`
- **Path:** `/order/{orderId}`
- **Mô tả:** Truy xuất một đơn hàng cụ thể bằng ID của nó.
- **Ví dụ cURL:**
  ```bash
  curl -X GET http://localhost:8080/api/v1/order/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
  ```

### 5.4. Lấy Chi tiết Đơn hàng bằng ID

- **Method:** `GET`
- **Path:** `/order/item/{orderItemId}`
- **Mô tả:** Truy xuất một chi tiết đơn hàng cụ thể bằng ID của nó.
- **Ví dụ cURL:**
  ```bash
  curl -X GET http://localhost:8080/api/v1/order/item/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
  ```

### 5.5. Hủy đơn hàng

- **Method:** `PUT`
- **Path:** `/order/{orderId}/cancel`
- **Mô tả:** Hủy một đơn hàng cụ thể.
- **Ví dụ cURL:**
  ```bash
  curl -X PUT http://localhost:8080/api/v1/order/1/cancel \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
  ```

---

## 6. Sản phẩm (`/products`)

Xử lý tìm kiếm và truy xuất sản phẩm.

### 6.1. Lấy Sản phẩm bằng ID

- **Method:** `GET`
- **Path:** `/products/{productId}`
- **Mô tả:** Truy xuất một sản phẩm duy nhất bằng ID của nó.
- **Ví dụ cURL:**
  ```bash
  curl -X GET http://localhost:8080/api/v1/products/1
  ```

### 6.2. Tìm kiếm sản phẩm nâng cao

- **Method:** `GET`
- **Path:** `/products/search`
- **Mô tả:** Tìm kiếm sản phẩm bằng nhiều bộ lọc khác nhau. Tất cả các tham số đều là tùy chọn.
- **Ví dụ cURL:**
  ```bash
  curl -X GET "http://localhost:8080/api/v1/products/search?query=shirt&category=Men&color=blue&minPrice=10&maxPrice=50&minDiscount=15&sortBy=price_asc&sortByPopularity=true"
  ```

### 6.3. Tìm kiếm sản phẩm đơn giản

- **Method:** `GET`
- **Path:** `/products/search/simple`
- **Mô tả:** Thực hiện tìm kiếm sản phẩm dựa trên văn bản đơn giản.
- **Ví dụ cURL:**
  ```bash
  curl -X GET "http://localhost:8080/api/v1/products/search/simple?query=jeans"
  ```

### 6.4. Tìm kiếm sản phẩm khuyến mãi

- **Method:** `GET`
- **Path:** `/products/search/promotion`
- **Mô tả:** Tìm kiếm các sản phẩm có tỷ lệ giảm giá tối thiểu.
- **Ví dụ cURL:**
  ```bash
  curl -X GET "http://localhost:8080/api/v1/products/search/promotion?query=jacket&minDiscount=30"
  ```

### 6.5. Tìm kiếm sản phẩm phổ biến

- **Method:** `GET`
- **Path:** `/products/search/popular`
- **Mô tả:** Tìm kiếm các sản phẩm phổ biến.
- **Ví dụ cURL:**
  ```bash
  curl -X GET "http://localhost:8080/api/v1/products/search/popular?query=shoes"
  ```

### 6.6. Lấy tất cả sản phẩm

- **Method:** `GET`
- **Path:** `/products`
- **Mô tả:** Truy xuất danh sách tất cả các sản phẩm.
- **Ví dụ cURL:**
  ```bash
  curl -X GET http://localhost:8080/api/v1/products
  ```

---

## 7. Đánh giá (`/reviews`)

Quản lý đánh giá sản phẩm.

### 7.1. Lấy đánh giá cho một sản phẩm

- **Method:** `GET`
- **Path:** `/products/{productId}/reviews`
- **Mô tả:** Truy xuất tất cả các đánh giá cho một sản phẩm cụ thể.
- **Ví dụ cURL:**
  ```bash
  curl -X GET http://localhost:8080/api/v1/products/1/reviews
  ```

### 7.2. Viết một bài đánh giá

- **Method:** `POST`
- **Path:** `/products/{productId}/reviews`
- **Mô tả:** Thêm một đánh giá mới cho một sản phẩm. Yêu cầu xác thực.
- **Ví dụ cURL:**
  ```bash
  curl -X POST http://localhost:8080/api/v1/products/1/reviews \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "reviewText": "Đây là một sản phẩm tuyệt vời! Chất lượng rất cao.",
    "reviewRating": 5.0,
    "productImages": [
      "http://example.com/image1.jpg",
      "http://example.com/image2.jpg"
    ]
  }'
  ```

### 7.3. Cập nhật một bài đánh giá

- **Method:** `PATCH`
- **Path:** `/reviews/{reviewId}`
- **Mô tả:** Cập nhật một đánh giá đã có. Yêu cầu xác thực.
- **Ví dụ cURL:**
  ```bash
  curl -X PATCH http://localhost:8080/api/v1/reviews/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "reviewText": "Một bài đánh giá đã được cập nhật. Chất lượng vẫn tốt, nhưng không như mong đợi.",
    "reviewRating": 4.0
  }'
  ```

### 7.4. Xóa một bài đánh giá

- **Method:** `DELETE`
- **Path:** `/reviews/{reviewId}`
- **Mô tả:** Xóa một bài đánh giá. Yêu cầu xác thực.
- **Ví dụ cURL:**
  ```bash
  curl -X DELETE http://localhost:8080/api/v1/reviews/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
  ```

---

## 8. Người bán (`/sellers`)

Quản lý tài khoản và hồ sơ của người bán.

### 8.1. Đăng nhập của Người bán

- **Method:** `POST`
- **Path:** `/sellers/login`
- **Mô tả:** Xác thực người bán và trả về một JWT. Email được tự động thêm tiền tố `seller_` ở phía server.
- **Ví dụ cURL:**
  ```bash
  curl -X POST http://localhost:8080/api/v1/sellers/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "contact@bestgoods.com",
    "password": "sellerpassword"
  }'
  ```

### 8.2. Tạo tài khoản Người bán

- **Method:** `POST`
- **Path:** `/sellers`
- **Mô tả:** Đăng ký một tài khoản người bán mới. Request body phải khớp với mô hình `Seller`.
- **Ví dụ cURL:**
  ```bash
  curl -X POST http://localhost:8080/api/v1/sellers \
  -H "Content-Type: application/json" \
  -d '{
    "sellerName": "Best Goods Inc.",
    "phoneNumber": "555-0011-222",
    "email": "contact@bestgoods.com",
    "password": "sellerpassword",
    "businessDetails": {
      "businessName": "Official Best Goods Inc.",
      "businessEmail": "official@bestgoods.com",
      "businessPhoneNumber": "888-999-0000",
      "businessAddress": "456 Corporate Ave, Suite 100, Businesstown",
      "logo": "http://example.com/logo.png",
      "banner": "http://example.com/banner.png"
    },
    "pickupAddress": {
      "name": "Warehouse A",
      "locality": "100 Business Rd",
      "address": "Loading Bay 4",
      "city": "Businesstown",
      "state": "BS",
      "zipCode": "54321",
      "phoneNumber": "111-222-3333"
    }
  }'
  ```

### 8.3. Lấy thông tin Người bán bằng ID

- **Method:** `GET`
- **Path:** `/sellers/{id}`
- **Mô tả:** Truy xuất hồ sơ công khai của người bán bằng ID.
- **Ví dụ cURL:**
  ```bash
  curl -X GET http://localhost:8080/api/v1/sellers/1
  ```

### 8.4. Lấy hồ sơ Người bán (Cá nhân)

- **Method:** `GET`
- **Path:** `/sellers/profile`
- **Mô tả:** Truy xuất hồ sơ của người bán đang được xác thực.
- **Ví dụ cURL:**
  ```bash
  curl -X GET http://localhost:8080/api/v1/sellers/profile \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
  ```

### 8.5. Lấy tất cả Người bán

- **Method:** `GET`
- **Path:** `/sellers`
- **Mô tả:** Truy xuất danh sách tất cả người bán.
- **Ví dụ cURL:**
  ```bash
  curl -X GET http://localhost:8080/api/v1/sellers
  ```

### 8.6. Cập nhật hồ sơ Người bán

- **Method:** `PATCH`
- **Path:** `/sellers`
- **Mô tả:** Cập nhật hồ sơ của người bán đang được xác thực. Request body có thể chứa bất kỳ trường nào từ mô hình `Seller`.
- **Ví dụ cURL:**
  ```bash
  curl -X PATCH http://localhost:8080/api/v1/sellers \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "sellerName": "Updated Best Goods Inc.",
    "phoneNumber": "555-111-3333",
    "businessDetails": {
      "logo": "http://example.com/new_logo.png"
    },
    "pickupAddress": {
        "address": "New Loading Bay 8"
    }
  }'
  ```

### 8.7. Xóa tài khoản Người bán

- **Method:** `DELETE`
- **Path:** `/sellers/{id}`
- **Mô tả:** Xóa một tài khoản người bán.
- **Ví dụ cURL:**
  ```bash
  curl -X DELETE http://localhost:8080/api/v1/sellers/1
  ```

---

## 9. Đơn hàng của Người bán (`/seller/orders`)

Cho phép người bán quản lý đơn hàng của họ.

### 9.1. Lấy tất cả đơn hàng cho Người bán

- **Method:** `GET`
- **Path:** `/seller/orders`
- **Mô tả:** Truy xuất tất cả các đơn hàng được liên kết với người bán đã được xác thực.
- **Ví dụ cURL:**
  ```bash
  curl -X GET http://localhost:8080/api/v1/seller/orders \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
  ```

---

## 10. Sản phẩm của Người bán (`/sellers/products`)

Cho phép người bán quản lý sản phẩm của họ.

### 10.1. Lấy sản phẩm theo Người bán

- **Method:** `GET`
- **Path:** `/sellers/products`
- **Mô tả:** Truy xuất tất cả các sản phẩm được liệt kê bởi người bán đã được xác thực.
- **Ví dụ cURL:**
  ```bash
  curl -X GET http://localhost:8080/api/v1/sellers/products \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
  ```

### 10.2. Tạo sản phẩm

- **Method:** `POST`
- **Path:** `/sellers/products`
- **Mô tả:** Tạo một sản phẩm mới cho người bán đã được xác thực. Payload phải khớp với đối tượng `CreateProductRequest`.
- **Ví dụ cURL:**
  ```bash
  curl -X POST http://localhost:8080/api/v1/sellers/products \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Premium Cotton T-Shirt",
    "description": "A very high-quality t-shirt made from 100% premium cotton. Soft, breathable, and durable.",
    "mrpPrice": 35,
    "sellPrice": 28,
    "color": "Midnight Black",
    "sizes": "S,M,L,XL,XXL",
    "images": [
      "http://example.com/tshirt_front.jpg",
      "http://example.com/tshirt_back.jpg"
    ],
    "category": { "id": 1, "name": "Apparel", "categoryId": "CAT_APP" },
    "category2": { "id": 10, "name": "Mens", "categoryId": "CAT_MENS_APP" },
    "category3": { "id": 101, "name": "T-Shirts", "categoryId": "CAT_MENS_TSHIRT" }
  }'
  ```

### 10.3. Xóa sản phẩm

- **Method:** `DELETE`
- **Path:** `/sellers/products/{productId}`
- **Mô tả:** Xóa một sản phẩm thuộc sở hữu của người bán.
- **Ví dụ cURL:**
  ```bash
  curl -X DELETE http://localhost:8080/api/v1/sellers/products/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
  ```

### 10.4. Cập nhật sản phẩm

- **Method:** `PUT`
- **Path:** `/sellers/products/{productId}`
- **Mô tả:** Cập nhật một sản phẩm đã có. Payload phải khớp với cấu trúc mô hình `Product`.
- **Ví dụ cURL:**
  ```bash
  curl -X PUT http://localhost:8080/api/v1/sellers/products/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Premium Cotton T-Shirt (2025 Edition)",
    "description": "Updated description for the new 2025 edition.",
    "sellPrice": 29,
    "color": "Navy Blue",
    "stock": "AVAILABLE"
  }'
  ```

---

## 11. Người dùng (`/users`)

Quản lý thông tin người dùng.

### 11.1. Lấy hồ sơ Người dùng

- **Method:** `GET`
- **Path:** `/users/profile`
- **Mô tả:** Truy xuất hồ sơ của người dùng đang được xác thực.
- **Ví dụ cURL:**
  ```bash
  curl -X GET http://localhost:8080/api/v1/users/profile \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
  ```

### 11.2. Cập nhật hồ sơ Người dùng

- **Method:** `PATCH`
- **Path:** `/users/profile`
- **Mô tả:** Cập nhật hồ sơ của người dùng đang được xác thực.
- **Ví dụ cURL:**
  ```bash
  curl -X PATCH http://localhost:8080/api/v1/users/profile \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "Dinh Truong"
  }'
  ```

---

## 12. Danh sách yêu thích (`/wishlist`)

Quản lý danh sách yêu thích của người dùng.

### 12.1. Lấy danh sách yêu thích của người dùng

- **Method:** `GET`
- **Path:** `/wishlist`
- **Mô tả:** Truy xuất danh sách yêu thích cho người dùng đang được xác thực.
- **Ví dụ cURL:**
  ```bash
  curl -X GET http://localhost:8080/api/v1/wishlist \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
  ```

### 12.2. Thêm/Cập nhật sản phẩm trong danh sách yêu thích

- **Method:** `POST`
- **Path:** `/wishlist/update-product/{productId}`
- **Mô tả:** Thêm một sản phẩm vào danh sách yêu thích của người dùng.
- **Ví dụ cURL:**
  ```bash
  curl -X POST http://localhost:8080/api/v1/wishlist/update-product/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
  ```

### 12.3. Xóa sản phẩm khỏi danh sách yêu thích

- **Method:** `DELETE`
- **Path:** `/wishlist/remove-product/{productId}`
- **Mô tả:** Xóa một sản phẩm khỏi danh sách yêu thích của người dùng.
- **Ví dụ cURL:**
  ```bash
  curl -X DELETE http://localhost:8080/api/v1/wishlist/remove-product/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
  ```
