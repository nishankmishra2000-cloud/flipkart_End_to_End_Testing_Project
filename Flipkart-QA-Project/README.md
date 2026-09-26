# Flipkart QA Portfolio Project

End-to-end QA project covering the full testing lifecycle for **[flipkart.com](https://www.flipkart.com)** — manual testing, UI automation, API testing, and CI/CD — built as a portfolio companion to the SauceDemo QA project.

## Why Flipkart

Flipkart is a large, real-world e-commerce site with rich, testable flows (search, filters, sorting, product details, cart, wishlist) and no test-friendly sandbox login (OTP-based auth). That constraint is used deliberately here to demonstrate how a QA engineer scopes automation sensibly around what's reliably testable **without** faking login success — all flows below are guest/no-login flows.

> **Note on the API module:** Flipkart does not expose a public test API. The API testing module therefore targets **[DummyJSON](https://dummyjson.com/products)**, a public REST e-commerce API, to demonstrate API testing methodology (GET/POST/PUT/DELETE, status codes, schema/response validation). This substitution is documented here and in `03-API-Testing/README.md` rather than hidden.

## Project Structure

```
Flipkart-QA-Project/
├── 01-Manual-Testing/          Requirements, PDF test plan, test scenarios/cases, execution & defect reports
├── 02-Automation-Selenium-TestNG/   Maven + Selenium + TestNG POM framework (Log4j, ExtentReports, config.properties)
├── 03-API-Testing/             REST Assured module + Postman collection (DummyJSON), full CRUD incl. PATCH
├── 04-CICD/                    Jenkinsfile + setup guide
├── 05-Supporting/              AI usage evidence, presentation deck, screenshot placeholders
├── Final_Project_Report.pdf    Consolidated report: overview, results, CI/CD flow, AI usage, challenges
├── .gitignore
└── README.md
```

## Scope Covered

| Phase | Deliverables |
|---|---|
| Manual Testing | Requirements doc, PDF test plan, 34 test scenarios, 27 test cases (with Actual Result/Status), execution report, defect report (with screenshot references) |
| UI Automation | Selenium WebDriver + TestNG, Page Object Model (PageFactory), Log4j logging, ExtentReports HTML reporting, config.properties, Actions/hover, JS scrolling, native alert handling, Select-class dropdown handling, Java Collections usage, data-driven tests |
| API Testing | REST Assured automated suite + Postman collection with environment, covering GET/POST/PUT/PATCH/DELETE |
| CI/CD | Jenkins declarative pipeline running automation + API suites on every push, archiving JUnit results, the Extent report, and logs |
| Final Submission | Consolidated Final_Project_Report.pdf + a 27-slide presentation deck (05-Supporting/) |

## Modules Under Test (Flipkart, guest flows)

- Homepage load & navigation
- Global search (valid, invalid, special characters, empty)
- Search results: sorting (price low-high/high-low, popularity, newest), filters (price range, brand, rating, discount)
- Product listing page pagination
- Product detail page (images, price, specifications, "Add to Cart" / "Buy Now")
- Cart (guest): add, update quantity, remove, price recalculation
- Login/Signup page: field validations only (no OTP automation, since OTP cannot be automated without a live phone)

## How to Run

### Selenium automation
```bash
cd 02-Automation-Selenium-TestNG
mvn clean test
```

### API automation
```bash
cd 03-API-Testing
mvn clean test
```

### Postman
Import `03-API-Testing/Flipkart_API_Postman_Collection.json` and `03-API-Testing/DummyJSON.postman_environment.json` into Postman, select the environment, and run the collection (or use the Collection Runner / Newman).

### Final report & presentation
- `Final_Project_Report.pdf` — consolidated write-up covering application overview, manual testing summary, automation framework design, API testing results, CI/CD flow, AI tool usage, and challenges/learnings.
- `05-Supporting/Flipkart_QA_Project_Presentation.pptx` — 27-slide presentation deck for the final walkthrough.

## Uploading to GitHub

```bash
cd Flipkart-QA-Project
git init
git add .
git commit -m "Initial commit: Flipkart QA portfolio project"
git branch -M main
git remote add origin <your-repo-url>
git push -u origin main
```

## Author's Note on AI Usage

AI tools were used to accelerate drafting of boilerplate (page object skeletons, TestNG configs, report structure) and are documented transparently in `05-Supporting/AI_Assisted_Test_Case_Evidence.md`. All test case logic, selectors, and Flipkart-specific flows were reviewed against the live site rather than fabricated. Screenshots of live tool runs (Jenkins console, Postman runner, browser test execution) are **not** included as fake images — placeholder folders in `05-Supporting/screenshots/` describe exactly what to capture and where to drop it before final submission.
