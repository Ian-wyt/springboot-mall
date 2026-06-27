## 📖 Project Overview

This project is a simple e-commerce system based on RESTful APIs. It provides management features for **Users**, **Products**, and **Orders**, with unit tests for each feature to verify correctness. Test coverage reaches 100%.

## 🛠 Technologies

- **Backend framework**: Spring Boot (Java)
- **Database**: MySQL (connected through Spring JDBC)
- **Unit testing**: MockMvc
- **API format**: RESTful API

## API

### 1️⃣ Users

#### Register User
```http
POST /api/users/register
```
#### User Login
```http
POST /api/users/login
```

### 2️⃣ Products

#### Create Product
```http
POST /api/products
```

#### Get Product List
```http
GET /api/products
```

#### Update Product
```http
PUT /api/products/{productId}
```

#### Delete Product
```http
DELETE /api/products/{productId}
```

### 3️⃣ Orders

#### Create Order

```http
POST /api/users/{userId}/orders
```

#### Query Orders

```http
GET /api//users/{userId}/orders
```

## 📜 Future Enhancements

- Introduce JWT authorization JSON files
- Introduce a microservices architecture



