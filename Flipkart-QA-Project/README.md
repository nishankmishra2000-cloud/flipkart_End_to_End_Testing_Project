# Simple Books API - REST Assured Tests

Target: https://simple-books-api.click

## Stack
Java 11+, Maven, REST Assured 5.5.0, TestNG 7.10.2

## Run
```
mvn clean test
```
Reports: `target/surefire-reports/`

## Coverage
| Class          | Method | Endpoints                                              |
|----------------|--------|--------------------------------------------------------|
| GetTests       | GET    | /status, /books (+type, limit), /books/{id}, /orders, /orders/{id} |
| PostTests      | POST   | /api-clients, /orders                                  |
| PutPatchTests  | PATCH + PUT | PATCH /orders/{id}; PUT asserted as unsupported   |
| DeleteTests    | DELETE | /orders/{id}                                           |

Each test validates **status code**, **headers** (Content-Type, response time) and **body** fields.

## Notes
- The API has **no PUT endpoint**; orders are updated with **PATCH**.
  `putIsNotSupported` asserts PUT returns 404/405 and leaves the data unchanged.
- A unique API client is registered once per run (token cached in BaseTest).
- Orders created by tests are deleted in `@AfterClass`.
- Error bodies are asserted as "non-empty `error` field" rather than exact text,
  so the tests don't break if wording changes.
