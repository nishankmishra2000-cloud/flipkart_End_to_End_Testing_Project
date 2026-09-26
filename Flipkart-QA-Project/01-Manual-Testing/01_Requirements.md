# Requirements Document — Flipkart (Guest Flows)

**Application Under Test:** https://www.flipkart.com
**Prepared for:** QA Portfolio Project
**Scope:** Guest (non-logged-in) shopping flows

## 1. Purpose
Define the functional requirements that the test suite (manual + automated) will validate for Flipkart's core shopping journey, scoped to flows that can be exercised without a live OTP-verified login.

## 2. In-Scope Modules

| ID | Module | Description |
|---|---|---|
| RQ-01 | Homepage | Page loads, header/nav, banner carousel, category menu render correctly |
| RQ-02 | Global Search | User can search products by keyword from any page |
| RQ-03 | Search Results / Sorting | Results can be sorted by Relevance, Popularity, Price (Low→High), Price (High→Low), Newest First |
| RQ-04 | Search Results / Filters | Results can be filtered by Price range, Brand, Customer Rating, Discount |
| RQ-05 | Pagination | Result set paginates correctly and preserves applied filters/sort |
| RQ-06 | Product Detail Page (PDP) | Product image, title, price, rating, specifications, and offers render; Add to Cart / Buy Now available |
| RQ-07 | Cart | Guest user can add/update/remove items; totals recalculate correctly |
| RQ-08 | Login/Signup Entry | Login page renders, accepts mobile number/email input, and validates format (OTP flow itself is out of scope for automation) |

## 3. Out of Scope

- OTP verification and authenticated checkout/payment (requires a live mobile number; cannot be automated reliably or ethically faked)
- Actual payment gateway integration
- Seller-side / Flipkart Seller Hub flows
- Native mobile app (Android/iOS) — this project targets the website only

## 4. Functional Requirements (sample, expandable)

1. **RQ-02.1** — The search bar shall accept alphanumeric input and return relevant results within an acceptable load time.
2. **RQ-02.2** — Searching with an empty string shall not trigger a broken navigation; the UI shall keep the user on a valid page.
3. **RQ-03.1** — Selecting "Price -- Low to High" shall reorder all visible results in non-decreasing price order.
4. **RQ-04.1** — Applying a price range filter shall remove from the result set any product priced outside the selected range.
5. **RQ-06.1** — The PDP shall display a price that matches the price shown on the search results card for the same product.
6. **RQ-07.1** — Increasing item quantity in the cart shall increase the line-item subtotal proportionally and update the cart's grand total.
7. **RQ-07.2** — Removing an item from the cart shall remove it from the grand total calculation.

## 5. Assumptions & Constraints

- Flipkart is a live third-party production site with content that changes frequently (prices, stock, promotions); test cases are written to assert on **behavior and structure**, not on specific product data that can go stale.
- Tests are designed to be resilient to minor UI changes by preferring stable locators (data-testid-like attributes, structural roles) where available, documented per page object.
- Network dependency: all automated suites require internet access to flipkart.com.
