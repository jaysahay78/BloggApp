<h1>BloggApp</h1>

A full-stack blogging platform with a Java/Spring Boot REST API and a modern Next.js (App Router) frontend. It supports authentication, roles/permissions, creating and managing posts, categories and comments, rich-text editing, user profiles, image handling, and a Stripe-powered checkout for paid features.

<h2> Features</h2>

* Auth & Roles: JWT authentication, role-based access (e.g., ROLE_USER, ROLE_ADMIN).

* Blogging: Create, read, update, delete posts; categories; comments; pagination. 

* Users: Profile edit, password update, dashboard.
  
* Rich text editor in the frontend with a clean UI kit (Tailwind + shadcn/ui).
* AI-generated content suggestions**: one-click ideas for titles, outlines, tags, and opening paragraphs to kickstart a post.

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
```

<h3>Install & Run</h3>

```
cd frontend
npm install
npm run dev
# Next.js dev server at http://localhost:3000
```

## AI Content Suggestions

The app can generate intelligent writing prompts to speed up blog creation:
- **Suggest Title & Outline**: Get multiple headline options and a section-wise outline.
- **Tag Ideas**: Auto-proposed tags/keywords based on your draft.
- **First Paragraph**: A short intro to break the blank-page problem.

**How to use**
1. Start a new post (or open an existing draft).
2. Type **“/write”** and enter a short topic/brief.
3. Pick the suggestions you like and insert them into the editor. Edit as needed.

**Config**
- Set `GEMINI_API_KEY` (and `MODEL`) in `frontend/.env.local`.
- For self-hosting or other providers, point your request code to that service and keep the same env names.


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


![1745701875789](https://github.com/user-attachments/assets/45e8bb64-673b-4237-8732-f43aa008e86f)

![1745701874476](https://github.com/user-attachments/assets/96d4a184-6da0-4418-9fb2-2d9daccc7588)

![1745701874601](https://github.com/user-attachments/assets/6d922aa3-28cd-47d1-b340-78a9c94f0cc2)

![1745701875012](https://github.com/user-attachments/assets/ed530e15-b24b-4444-b18b-7362e60c2b42)

![1745701874476 (1)](https://github.com/user-attachments/assets/af8d6f09-88f2-41a4-b99e-cccfb4ae54f9)

![1745701873108](https://github.com/user-attachments/assets/1f37969d-3860-43ce-80fb-c1bb2f20436e)
![1745701874018](https://github.com/user-attachments/assets/2cf258fd-116b-4106-8669-f0e928e25967)

![1745701872989](https://github.com/user-attachments/assets/6262fea0-4eee-4967-b295-d5bd3e8e2cec)


![1745701873583](https://github.com/user-attachments/assets/fb77207c-96f1-4837-926e-20eefec4f8ce)


![1745701876464](https://github.com/user-attachments/assets/534e9ff5-f5ab-4e63-ad32-c1ef8312d3da)

