package com.bestbuy.servicesinfo;

import com.bestbuy.stepinfo.ServiceSteps;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.equalTo;

@RunWith(SerenityRunner.class)
public class ServiceCURDTest extends TestBase {

    static String name = "Electronics Experience Shop" + TestUtils.getRandomValue();
    static long servicesId;

    @Steps
    ServiceSteps serviceSteps;

    @Title("This test will create a new services and verify its generated")
    @Test

    public void test001() {

        servicesId = serviceSteps.createNewServices(name).log().all().statusCode(201).extract().response()
                .body().jsonPath().getLong("id");
        System.out.println("services ID is" + servicesId);
    }

    @Title("This test will get the services information by ID")
    @Test

    public void test002() {
        serviceSteps.getServicesById(servicesId).log().all().statusCode(200);

    }

    @Title("This test will update the services information and verify the updated information")
    @Test

    public void test003() {

        name = name + "_Changed";

        serviceSteps.updateServices(servicesId, name).statusCode(200).log().all();
        serviceSteps.getServicesById(servicesId).body("name", equalTo(name));

    }

    @Title("This test will delete the categories information and verify the categories is deleted ")
    @Test

    public void test004() {
        serviceSteps.deleteServicesById(servicesId).statusCode(200).log().all();
        serviceSteps.getServicesById(servicesId).statusCode(404).log().all();
    }

    @Title("This will get all services")
    @Test
    public void test005() {
        serviceSteps.getAllServices().log().all().statusCode(200);

    }

}
