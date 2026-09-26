# API Testing Module

Flipkart does not publish a public test/sandbox API, so this module targets **[DummyJSON](https://dummyjson.com/products)** — a free public REST API modeling an e-commerce product catalog — to demonstrate API test automation methodology (status codes, schema, and data validation across GET/POST/PUT/DELETE) in a way that mirrors what a real Flipkart product/catalog API would look like.

This substitution is stated plainly rather than hidden, for the same reason the SauceDemo project used JSONPlaceholder for its API module: the goal is to demonstrate API testing skill, not to pretend access to a private production API that doesn't exist publicly.

## Endpoints Covered

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/products` | List all products |
| GET | `/products/{id}` | Get a single product |
| GET | `/products/search?q=` | Search products |
| POST | `/products/add` | Add a product (simulated — API doesn't persist) |
| PUT | `/products/{id}` | Update a product (simulated) |
| PATCH | `/products/{id}` | Partially update a product (simulated) |
| DELETE | `/products/{id}` | Delete a product (simulated) |

## Contents
- `pom.xml` — Maven module (REST Assured + TestNG)
- `src/test/java/api/` — Automated API test classes
- `Flipkart_API_Postman_Collection.json` — Postman collection (importable)
- `DummyJSON.postman_environment.json` — Postman environment (base URL)

## Running
```bash
mvn clean test
```
