# Secure Web Application with AWS, Apache and Spring Boot
- Jeimy Alejandra Yaya Martínez

## Overview

This project implements a secure, scalable web application deployed on AWS using a two-tier architecture:

* **Frontend Server (Apache)**: Serves an asynchronous HTML + JavaScript client over HTTPS using Let’s Encrypt.
* **Backend Server (Spring Boot)**: Provides REST API endpoints secured with TLS and implements user authentication with hashed passwords.

---

## Architecture

The system is composed of two independent servers:

* **EC2 #1 (Apache Server)**

  * Hosts the frontend (HTML + JavaScript)
  * Uses Let’s Encrypt certificates for HTTPS
  * Handles client requests

* **EC2 #2 (Spring Boot Server)**

  * Exposes REST API endpoints (`/login`, `/hello`)
  * Uses TLS with a PKCS12 keystore
  * Implements authentication with BCrypt

### 🔁 Communication Flow

Browser → Apache (HTTPS) → Spring Boot (HTTPS)

---

## Security Features

* **TLS Encryption**

  * Apache uses Let’s Encrypt certificates
  * Spring Boot uses a self-signed certificate (PKCS12 keystore)

* **Secure Authentication**

  * Passwords are hashed using BCrypt
  * Login endpoint validates credentials securely

* **Separation of Concerns**

  * Frontend and backend are deployed on different servers

* **AWS Security Groups**

  * Controlled access to ports (80, 443, 5000)

---

## Technologies Used

* Java 17 / 21
* Spring Boot
* Apache HTTP Server
* AWS EC2
* Let’s Encrypt (Certbot)
* HTML + JavaScript (Fetch API)

---

## Deployment Steps

### 1. Backend (Spring Boot)

```bash
mvn clean package
java -jar SecureSpring-1.0-SNAPSHOT.jar
```

### 2. Frontend (Apache)

* Install Apache
* Place `index.html` in:

```bash
/var/www/html/
```
- `index.html`
```
<html>
<head>
    <title>Secure App</title>
</head>
<body>

<h2>Login Seguro</h2>

<input id="user" placeholder="Usuario">
<input id="pass" type="password" placeholder="Contraseña">

<button onclick="login()">Login</button>

<p id="res"></p>

<script>
async function login() {
    const response = await fetch("https://ec2-54-173-38-174.compute-1.amazonaws.com:5000/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: document.getElementById("user").value,
            password: document.getElementById("pass").value
        })
    });

    const text = await response.text();
    document.getElementById("res").innerText = text;
}
</script>

</body>
</html>

```

* Enable HTTPS with Certbot:

```bash
sudo certbot --apache
```

---

## API Endpoints

### GET /

Returns a basic message to verify server status.

### GET /hello

Returns:

```
Servidor seguro funcionando
```

### POST /login

Request:

```json
{
  "username": "admin",
  "password": "1234"
}
```

Response:

```
Login exitoso
```

---

## ⚠️ Notes on HTTPS

* The frontend uses a trusted certificate (Let’s Encrypt).
* The backend uses a self-signed certificate, which may trigger browser warnings.
* Despite the warning, communication is encrypted and secure.

---

## Evidence

* HTTPS working on Apache
<img width="604" height="196" alt="image" src="https://github.com/user-attachments/assets/3dbc8c83-39fb-403d-b1c5-b9cdf67f29b2" />

* Login successful
<img width="565" height="271" alt="image" src="https://github.com/user-attachments/assets/f3736ed1-900d-4618-bd2b-f867a9676e15" />

* AWS EC2 instances running
<img width="1552" height="94" alt="image" src="https://github.com/user-attachments/assets/6d713260-3e51-4459-9e8b-fb9a11322336" />


* Backend execution
<img width="746" height="446" alt="image" src="https://github.com/user-attachments/assets/0028058f-ae4e-47fb-bde8-6e17f7dbfb34" />
* Full system integration


---

## Repository Structure

```
SecureSpring/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── edu/
│   │   │       └── eci/
│   │   │           └── securespring/
│   │   │               ├── Secureweb.java
│   │   │               ├── HelloController.java
│   │   │               ├── LoginController.java
│   │   │               └── SecurityConfig.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties
│   │       └── keystore/
│   │           ├── ecikeystore.p12
│   │           ├── ecicert.cer
│   │           └── myTrustStore
│   │
│   └── test/
│
├── pom.xml
└── README.md
```
