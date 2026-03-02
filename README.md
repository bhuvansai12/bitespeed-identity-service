# bitespeed-identity-service
# 🚀 Bitespeed Identity Resolution Service

This service identifies and links customer contacts across multiple purchases, ensuring a single customer identity even when different emails or phone numbers are used.

---

## 🌐 Live API

**Base URL**

https://bitespeed-identity-service-2-sq9a.onrender.com

**Endpoint**

POST /identify

Full URL:

https://bitespeed-identity-service-2-sq9a.onrender.com/identify

---

## 📥 Request Format

Send JSON:

{
"email": "string",
"phoneNumber": "string"
}

### Rules

- At least one field must be provided
- Both fields are optional
- Either value may be null

---

## 📤 Response Format

{
"contact": {
"primaryContactId": number,
"emails": [],
"phoneNumbers": [],
"secondaryContactIds": []
}
}

---

## 🧠 Identity Resolution Logic

### Primary Contact
- The oldest contact record
- Represents the main identity

### Secondary Contacts
Created when new information is linked to an existing contact.

### Contacts are linked when they share:
- Email
- Phone number

### Primary Merge Rule
If multiple primary contacts become linked:

- The oldest remains **primary**
- Others become **secondary**

---

## 🗄 Database Schema

Table: **Contact**

- id — unique contact identifier
- email — customer email
- phoneNumber — customer phone number
- linkedId — references primary contact
- linkPrecedence — primary / secondary
- createdAt — record creation time
- updatedAt — last update time
- deletedAt — soft delete timestamp

---

## 🛠 Tech Stack

- Java Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
- Render (cloud hosting)

---

## 🎯 Features

- Identity linking across purchases
- Automatic duplicate resolution
- Primary & secondary contact merging
- Persistent PostgreSQL storage
- RESTful API design
- Cloud deployment

---

## 👨‍💻 Author

Sai Bhuvan  
https://github.com/bhuvansai12

---

## ⭐ Project Purpose

Built as part of the **Bitespeed Backend Task** to demonstrate backend design, identity resolution logic, database relationships, and production deployment.

---