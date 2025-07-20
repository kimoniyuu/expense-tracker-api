# Personal Expense Tracker API

นี่คือ Backend API สำหรับแอปพลิเคชันติดตามรายรับ-รายจ่ายส่วนตัว สร้างขึ้นด้วย Java และ Spring Boot เพื่อฝึกฝนทักษะการพัฒนาซอฟต์แวร์ในระดับ Job-Ready

## ✨ ฟีเจอร์หลัก (Features)

* **User Authentication:** ระบบลงทะเบียนและล็อกอินที่ปลอดภัยด้วย Spring Security และ JWT
* **Category Management:** ผู้ใช้สามารถสร้าง, อ่าน, และลบหมวดหมู่ค่าใช้จ่ายของตัวเองได้
* **Transaction Management:** ผู้ใช้สามารถบันทึกรายการรายรับ-รายจ่าย โดยแต่ละรายการจะผูกกับหมวดหมู่และผู้ใช้ที่เป็นเจ้าของ
* **Data Security:** ออกแบบโดยคำนึงถึงความปลอดภัย ผู้ใช้สามารถเข้าถึงได้เฉพาะข้อมูลของตัวเองเท่านั้น

## 🛠️ เทคโนโลยีที่ใช้ (Tech Stack)

* **Backend:** Java 17, Spring Boot 3
* **Database:** PostgreSQL
* **Security:** Spring Security, JSON Web Tokens (JWT)
* **Testing:** JUnit 5, Mockito, Spring Boot Test
* **Containerization:** Docker
* **Build Tool:** Maven

## 🚀 วิธีการรันโปรเจกต์ (Getting Started)

### สิ่งที่ต้องมี (Prerequisites)
* Java 17
* Maven
* PostgreSQL Server
* Docker Desktop

### การรันผ่าน Docker (วิธีที่แนะนำ)
1.  สร้างฐานข้อมูล PostgreSQL ชื่อ `expense_tracker_db`
2.  แก้ไขข้อมูลการเชื่อมต่อฐานข้อมูลในไฟล์ `src/main/resources/application-prod.properties`
3.  Build Docker image:
    ```bash
    docker build -t expense-tracker-api .
    ```
4.  Run Docker container:
    ```bash
    docker run -p 8080:8080 expense-tracker-api
    ```

## Endpoints ของ API

* `POST /api/auth/register` - ลงทะเบียนผู้ใช้ใหม่
* `POST /api/auth/login` - ล็อกอินเพื่อรับ JWT Token
* `POST /api/users/{userId}/categories` - สร้างหมวดหมู่ใหม่ (ต้องใช้ Token)
* `GET /api/users/{userId}/categories` - ดูหมวดหมู่ทั้งหมด (ต้องใช้ Token)
* `DELETE /api/categories/{categoryId}` - ลบหมวดหมู่ (ต้องใช้ Token)
* `POST /api/users/{userId}/categories/{categoryId}/transactions` - สร้างรายการ Transaction ใหม่ (ต้องใช้ Token)
* `GET /api/users/{userId}/transactions` - ดูรายการ Transaction ทั้งหมด (ต้องใช้ Token)
