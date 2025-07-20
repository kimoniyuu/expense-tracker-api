# ขั้นตอนที่ 1: Build Stage - ใช้ Maven เพื่อสร้างไฟล์ .jar
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# ขั้นตอนที่ 2: Run Stage - สร้าง Image สุดท้ายที่เล็กและพร้อมใช้งาน
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]