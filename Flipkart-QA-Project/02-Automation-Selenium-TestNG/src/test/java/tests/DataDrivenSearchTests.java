package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchResultsPage;
import utils.ExcelUtil;

import java.util.List;

public class DataDrivenSearchTests extends BaseTest {

    private static final String DATA_FILE = "src/test/resources/SearchTestData.xlsx";

    @DataProvider(name = "searchKeywords")
    public Object[][] searchKeywords() {
        List<String[]> rows = ExcelUtil.readSheet(DATA_FILE, "SearchData");
        Object[][] data = new Object[rows.size()][3];
        for (int i = 0; i < rows.size(); i++) {
            data[i][0] = rows.get(i)[0]; // keyword
            data[i][1] = rows.get(i)[1]; // expectResults ("Yes"/"No")
            data[i][2] = rows.get(i)[2]; // description
        }
        return data;
    }

    @Test(dataProvider = "searchKeywords",
          description = "Data-driven: search behavior across multiple keyword types")
    public void searchBehaviorAcrossKeywords(String keyword, String expectResults, String description) {
        HomePage home = new HomePage(driver);
        home.search(keyword);

        SearchResultsPage results = new SearchResultsPage(driver);
        boolean hasResults = !results.getVisibleTitles().isEmpty();

        if ("Yes".equalsIgnoreCase(expectResults)) {
            Assert.assertTrue(hasResults, description + " — expected results for keyword: " + keyword);
        } else {
            Assert.assertTrue(!hasResults || results.isNoResultsShown(),
                    description + " — expected no/empty results for keyword: " + keyword);
        }
    }
}
