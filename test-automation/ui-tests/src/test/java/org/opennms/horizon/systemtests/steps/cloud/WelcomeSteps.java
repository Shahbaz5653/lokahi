/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2023 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2023 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/
package org.opennms.horizon.systemtests.steps.cloud;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.opennms.horizon.systemtests.pages.cloud.LocationsPage;
import org.opennms.horizon.systemtests.pages.cloud.WelcomePage;



public class WelcomeSteps {

    @Given("check 'Start Setup' button is accessible and visible")
    public static void checkStartSetupButtonIsAccessibleAndVisible() {
        WelcomePage.checkIsStartSetupButtonVisible();
    }

    @Then("click on 'Start Setup' button to start welcome wizard")
    public static void startWelcomeWizardSetup() {
        WelcomePage.startWelcomeWizardSetup();
    }

    @Then("click on 'Download' button to get certificate and password for minion {string}")
    public static void downloadCertificate(String minionID) {
        WelcomePage.downloadCertificateAndStartMinion(minionID);
    }

    @Then("wizard shows that minion connected successfully")
    public static void minionStarted() {
        WelcomePage.checkMinionConnection();
    }

    @Then("click on 'Continue' button")
    public static void continueToDiscovery() {
        WelcomePage.continueToDiscovery();
    }

    @Then("enter IP {string} for discovery")
    public static void continueToDiscovery(String ip) {
        WelcomePage.setIPForDiscovery(ip);
    }

    @Then("click on 'Start Discovery' button")
    public static void clickStartDiscovery() {
        WelcomePage.clickStartDiscovery();
    }

    @Then("first node with IP {string} discovered successfully")
    public static void nodeDiscovered(String ip) {
        WelcomePage.nodeDiscovered(ip);
    }

    @Then("click on 'Continue' button to end the wizard")
    public static void clickContinueToEndWizard() {
        WelcomePage.clickContinueToEndWizard();
    }

    @Then("check {string} location exists")
    public void checkLocationExists(String locationName) {
        LocationsPage.checkLocationExists(locationName);
    }

    @Then("click on location {string}")
    public void clickOnLocation(String locationName) {
        LocationsPage.clickOnLocation(locationName);
    }

    @Then("check minion {string} exists")
    public void checkMinionExists(String minionId) {
        LocationsPage.checkMinionExists(minionId);
    }

    @Then("delete minion {string}")
    public void deleteMinion(String minionId) {
        LocationsPage.clickOnMinionDeleteButton(minionId);
    }

    @Then("verify minion {string} deleted")
    public void verifyMinionDeleted(String minionId) {
        LocationsPage.checkMinionDoesntExist(minionId);
    }
}
