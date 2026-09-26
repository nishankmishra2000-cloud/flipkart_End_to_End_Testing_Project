# UI Automation Framework — Selenium + TestNG (Hybrid/POM)

Maven-based framework testing flipkart.com guest flows.

## Rubric Concept → Implementation Map

| Required concept | Where it lives |
|---|---|
| Page Object Model with PageFactory (`@FindBy`) | `src/main/java/pages/*.java` |
| Centralized Object Repository | All locators live as `@FindBy` fields (or documented dynamic `By`) inside their page class only — no locator is duplicated in a test |
| TestNG | `src/test/java/tests/*.java`, `testng.xml` |
| Extent Report | `utils/ExtentManager.java` + `listeners/ExtentTestListener.java` → generates `reports/Flipkart_Automation_Report.html` |
| Logging (Log4j) | `src/main/resources/log4j2.xml`, used via `LogManager.getLogger(...)` in `BaseTest` and the Extent listener → `logs/flipkart-automation.log` |
| Property file (`config.properties`) | `src/main/resources/config.properties`, read via `utils/ConfigReader.java` |
| Excel utility (Apache POI) | `utils/ExcelUtil.java`, backing `tests/DataDrivenSearchTests.java` + `src/test/resources/SearchTestData.xlsx` |
| Handling different browser windows | `ProductDetailTests.switchToNewTabIfOpened()` / same pattern in `CartTests`, `DropdownAndCollectionsTests` |
| Search option | `pages/HomePage.search()`, `tests/SearchTests.java` |
| Capturing warning/alert messages | `utils/AlertUtil.java`, `tests/AlertHandlingTests.java` (see class Javadoc for why it drives a controlled JS `confirm()` rather than an unpredictable live-site alert) |
| Retrieving data | `SearchResultsPage.getVisiblePrices()/getVisibleTitles()`, `CartPage.getGrandTotal()` |
| Validation checks (element presence, checkbox/button state) | `ProductDetailPage.isAddToCartEnabled()`, `SearchResultsPage.isRatingFilterVisible()`, assertions throughout `tests/*` |
| Selenium Actions / event handling | `utils/ActionsUtil.java` (hover), exercised in `tests/NavigationTests.hoverOverCategoryMenuRevealsSubmenu` |
| Dropdown handling + Select class | `ProductDetailPage.selectQuantityIfNativeDropdownPresent()`, exercised in `tests/DropdownAndCollectionsTests.java` |
| Collections of Java for storing results | `tests/DropdownAndCollectionsTests.collectionsStoreAndDeduplicateResultsAcrossPages` (`List`, `HashSet`, `Collections.sort`) |
| Navigating back to homepage | `tests/NavigationTests.navigateBackReturnsToHomepage` |
| Scrolling | `utils/ScrollUtil.java`, exercised in `tests/NavigationTests.scrollToFooterBringsItIntoView` |
| Screenshot on failure | `utils/ScreenshotUtil.java` (file) + `ExtentTestListener` (embedded in HTML report) |

## Running

```bash
mvn clean test                     # headless (default, config.properties)
mvn clean test -Dheadless=false    # watch it run locally
```

After a run:
- JUnit-style results: `target/surefire-reports/`
- Extent HTML report: `reports/Flipkart_Automation_Report.html`
- Log file: `logs/flipkart-automation.log`
- Failure screenshots: `screenshots/failures/`

## A note on live-site locators

Flipkart ships no stable `data-testid` attributes and its class names can change without notice on a live production site. Every locator lives inside its page class specifically so a drift only requires updating one file. If a test fails with a "no such element" style error, check the corresponding page class in `src/main/java/pages/` against the current DOM first.
