package org.opennms.horizon.systemtests.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.Assert;
import org.opennms.horizon.systemtests.steps.DiscoverySteps;
import org.testcontainers.containers.GenericContainer;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class FlowsPage {
    private static final SelenideElement headerTxt = $("[data-test='flows-page-header']");
    private static final String topApplicationTotalQuery = "//div[@class='table-container']//td[1]";
    private static final SelenideElement topApplicationTotal = $x(topApplicationTotalQuery);
    private static final SelenideElement noDataTxt = $x("//div[@class='subtitle'][contains(text(), 'No applications data')]");
    private static final SelenideElement trafficChart = $("div.table-chart-container");
    private static final SelenideElement exporterInp = $("[data-ref-id='feather-autocomplete-input']");

    public static void verifyNoDataTitle() {
        noDataTxt.should(exist);
    }

    public static void verifyTopApplicationFlowTotalsChanges() {
        // We'll just check that the top application totals have changed
        String appTotal = getTopApplicationFlowTotal();
        if (appTotal.isEmpty()) {
            // No flow data exists yet, so just check for when it shows up
            RefreshMonitor.waitForElement(topApplicationTotal, exist, 120, true);
        } else {
            // There is already flow data, so check for when it changes (i.e. the old value is gone)
            SelenideElement topTotalElement = $x(topApplicationTotalQuery + "[./text()='" + getTopApplicationFlowTotal() + "']");
            RefreshMonitor.waitForElement(topTotalElement, exist, 120, false);
        }
    }

    private static String getTopApplicationFlowTotal() {
        try {
            topApplicationTotal.should(exist);
            return topApplicationTotal.getText();
        } catch (com.codeborne.selenide.ex.ElementNotFound e) {
            // Catch the exception from the timeout here if we can't get the flow total,
            // in which case return empty below
        }
        return "";
    }

    public static void clickOnExporterInput() {
        exporterInp.shouldBe(enabled).click();
    }

    public static void setValueInExporterFilter(String pattern) {
        exporterInp.setValue("").sendKeys(pattern);
    }

    public static void checkExporterDropdown(String exporterName) {
        String ip = DiscoverySteps.getIpaddress(exporterName);

        setValueInExporterFilter(ip);
        SelenideElement dropdown = $x("//div[@class='feather-menu-dropdown']//span[contains(text(), '" + ip + "')]");
        dropdown.should(exist);
    }

    public static void waitPageLoaded() {
        headerTxt.shouldBe(visible, Duration.ofSeconds(10));
    }

}
