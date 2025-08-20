<h1>BloggApp</h1>

A full-stack blogging platform with a Java/Spring Boot REST API and a modern Next.js (App Router) frontend. It supports authentication, roles/permissions, creating and managing posts, categories and comments, rich-text editing, user profiles, image handling, and a Stripe-powered checkout for paid features.

<h2> Features</h2>

* Auth & Roles: JWT authentication, role-based access (e.g., ROLE_USER, ROLE_ADMIN).

* Blogging: Create, read, update, delete posts; categories; comments; pagination. 

* Users: Profile edit, password update, dashboard.
  
* Rich text editor in the frontend with a clean UI kit (Tailwind + shadcn/ui). 

* Payments: Stripe Embedded Checkout workflow (/api/embedded-checkout, stripe.tsx). 

* Images: Static images folder and upload service on the backend. 

* DX: TypeScript on the frontend, ESLint, Tailwind; Maven + Dockerfile for backend.

<h2>Tech Stack</h2>

* Frontend: Next.js (App Router), React, TypeScript, Tailwind CSS, shadcn/ui, Axios.

* Backend: Spring Boot, Spring Security (JWT), JPA/Hibernate, Swagger/OpenAPI, Maven. 

* Payments: Stripe Embedded Checkout. 

* Build/Run: Dockerfile for backend, standard npm scripts for frontend.

<h2>File Structure</h2>

```
BloggApp/
├─ Backend/                              # Spring Boot API (JWT, Swagger, JPA)
│  ├─ src/main/java/com/spring/blogApp_api/
│  │  ├─ config/                         # SecurityConfig, SwaggerConfig, AppConstants
│  │  ├─ controllers/                    # Auth, User, Post, Category, Comment controllers
│  │  ├─ entities/                       # User, Role, Post, Category, Comment
│  │  ├─ payloads/                       # DTOs & responses
│  │  ├─ repositories/                   # Spring Data JPA repositories
│  │  ├─ security/                       # JWT filter, token helper, entrypoint
│  │  └─ services/ + impl/               # Business logic + file uploads
│  ├─ src/main/resources/                # application.properties (+ prod)
│  ├─ images/                            # Static image assets
│  ├─ pom.xml                            # Maven build
│  └─ Dockerfile                         # Containerize backend
└─ frontend/                             # Next.js app (App Router)
   ├─ src/app/                           # routes: (auth), (routes)/feed, singlepage/[postId], user/*
   │  ├─ api/                            # route handlers: generate, check-write-limit, embedded-checkout
   ├─ components/                        # UI, editor, navbar, etc.
   ├─ utils/                             # axiosInstance, *-service.tsx, stripe.tsx helpers
   ├─ context/                           # User context/provider
   ├─ sections/                          # Marketing sections (Hero, Pricing, etc.)
   ├─ tailwind.config.ts, components.json, next.config.mjs
   └─ package.json

```


<h2>Prerequisites</h2>

Java 17+ and Maven for the backend.

Node.js 18+ and npm or pnpm for the frontend.

Stripe account (test keys are fine) if you plan to test payments.

A SQL database (e.g., MySQL) configured in Spring Boot properties.

<h2>Backend </h2>

<h3>configuration</h3>

```
# Server
server.port=8080
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/bloggapp?useSSL=false&serverTimezone=UTC
spring.datasource.username=YOUR_DB_USER
spring.datasource.password=YOUR_DB_PASSWORD
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT
app.jwt.secret=CHANGE_ME_SUPER_SECRET
app.jwt.expiration-ms=86400000

# File Uploads (adjust a writable path)
file.upload-dir=./uploads

# CORS (allow frontend dev origin)
app.cors.allowed-origins=http://localhost:3000

<h3>Run (dev)</h3>
cd Backend
./mvnw spring-boot:run
# or
mvn spring-boot:run
```

The API will start on http://localhost:8080 by default.

<h3>Build a JAR</h3>

```
mvn clean package -DskipTests
```

<h3>Run with Docker</h3>

A Dockerfile is provided:

```
cd Backend
# Build image
docker build -t bloggapp-backend .
# Run container (map port; mount a volume if needed)
docker run --name bloggapp-api -p 8080:8080 bloggapp-backend
```

<h2>Frontend</h2>

<h3>Environment</h3>

Create frontend/.env.local with:
```
# Base URL to your backend API
NEXT_PUBLIC_API_BASE_URL=http://localhost:8080

# Stripe keys (test mode)
NEXT_PUBLIC_STRIPE_PUBLISHABLE_KEY=pk_test_...
STRIPE_SECRET_KEY=sk_test_...
STRIPE_WEBHOOK_SECRET=whsec_...   # if you add webhooks later

<h3>Install & Run</h3>
cd frontend
npm install
npm run dev
# Next.js dev server at http://localhost:3000
```

<h2>Authentication Flow</h2>

Sign up / login under src/app/(auth) pages; tokens are issued by the Spring Boot API. 


JWT is validated by a custom filter and entrypoint; roles are enforced at controller/service layers. 

<h2>Payments</h2>

Frontend exposes a “Payment” page (src/app/user/payment/page.tsx) and an EmbeddedCheckoutButton component; server actions/route handlers call Stripe to create sessions/intents. 

Make sure your Stripe keys are set in .env.local and that the backend/route handlers use the same currency you expect (see convertToSubcurrency.tsx).

<h2>APIs</h2>

The exact URLs are visible in the controllers:
```

POST /api/auth/login, POST /api/auth/register

GET /api/users/{id}, PUT /api/users/{id}, PUT /api/users/{id}/password

GET /api/posts?category=&page=&size=, POST /api/posts, PUT /api/posts/{id}, DELETE /api/posts/{id}

GET /api/categories, POST /api/categories, PUT /api/categories/{id}, DELETE /api/categories/{id}

POST /api/posts/{id}/comments, DELETE /api/comments/{id}
```

<h2>Images</h2>

Backend contains an images/ directory and a FileService/FileServiceImpl handling uploads. Confirm your file.upload-dir is writable and served (e.g., via a static resource handler). 


![1745701875789](https://.com/user-attachments/assets/fbbd682b-61a3-45cb-9936-7612acc658ca)
![1745701874601](https://.com/user-attachments/assets/64d81bf0-812c-40b1-8b4f-10cac62e3a0c)
![1745701875012](https://.com/user-attachments/assets/8833f4fd-b15e-4926-a5ab-57fbdeb37257)
![1745701874476](https://.com/user-attachments/assets/155e478b-2643-41ba-922b-b6f6c8f93ae4)
![1745701874476 (1)](https://.com/user-attachments/assets/787a8a67-d5b3-4df2-b154-75a685f82c6a)
![1745701873108](https://.com/user-attachments/assets/bf967d60-cf9d-4abb-91f7-c6cb5e83c0d9)
![1745701874018](https://.com/user-attachments/assets/d2394be6-5bb3-4300-80cb-f3d12f352d3f)
![1745701872989](https://.com/user-attachments/assets/94f97091-fcc3-4a4b-af65-b188e7840ece)
![1745701873583](https://.com/user-attachments/assets/b9877abf-3184-45af-9c39-319f22cf0327)
![1745701876464](https://.com/user-attachments/assets/090cf1c8-eccf-4a98-8b4b-2a7c681c254a)
