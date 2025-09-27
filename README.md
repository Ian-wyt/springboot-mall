## 📖 專案簡介

本專案是一個基於RESTful API的簡易電商系統，提供 **使用者 (Users)**、**商品 (Products)** 及 **訂單 (Orders)** 的管理功能，並針對各個功能進行單元測試以驗證程式碼的正確性，測試覆蓋率達100%。

## 🛠 技術

- **後端框架**：Spring Boot (Java)
- **資料庫**：MySQL (使用Spring JDBC連接)
- **單元測試**: MockMvc
- **API 格式**：RESTful API

## API

### 1️⃣ 使用者 (Users)

#### 註冊使用者
```http
POST /api/users/register
```
#### 使用者登入
```http
POST /api/users/login
```

### 2️⃣ 商品 (Products)

#### 新增商品
```http
POST /api/products
```

#### 取得商品列表
```http
GET /api/products
```

#### 修改商品
```http
PUT /api/products/{productId}
```

#### 刪除商品
```http
DELETE /api/products/{productId}
```

### 3️⃣ 訂單 (Orders)

#### 創建訂單 

```http
POST /api/users/{userId}/orders
```

#### 查詢訂單 

```http
GET /api//users/{userId}/orders
```

## 📜 未來延伸

- 引入JWT授權 JSON 檔案
- 引入Microservices架構



