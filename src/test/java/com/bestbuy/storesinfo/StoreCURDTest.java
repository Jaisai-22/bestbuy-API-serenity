package com.bestbuy.storesinfo;

import com.bestbuy.stepinfo.StoreSteps;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SerenityRunner.class)
public class StoreCURDTest extends TestBase {

    static String name = "Decorama" + TestUtils.getRandomValue();
    static String type = "Dollar Saving" + TestUtils.getRandomValue();
    static String address = "123 Semley Road";
    static String address2 = "";
    static String city = "London" + TestUtils.getRandomValue();
    static String state = "MN";
    static String zip = "234531";
    static float lat = 45.087f;
    static float lng = 23.123f;
    static String hours = "String";
    static long storeId;


    @Steps
    StoreSteps storesSteps;

    @Title("This test will create a new stores and verify its generated")
    @Test
    public void test001() {
        storeId = storesSteps.createNewStore(name, type, address, address2, city, state, zip, lat, lng, hours).log().all().extract().response()
                .body().jsonPath().getLong("id");
        System.out.println("store id is : " + storeId);
    }

    @Title("This test will get the stores information by ID")
    @Test
    public void test002() {
        storesSteps.getStoreById(storeId).statusCode(200).log().all();

    }

    @Title("This test will update the store information and verify the updated information")
    @Test

    public void test003() {


        name = name + "_new";
        type = type + "_new";
        address = address + "_new";
        address2 = address2 + "_new";
        city = city + "_new";

        storesSteps.updateStore(storeId, name, type, address, address2, city).log().all().statusCode(200);
        storesSteps.getStoreById(storeId).body("name", equalTo(name));

    }

    @Title("This test will delete the store and verify the store is deleted ")
    @Test
    public void test004() {
        storesSteps.deleteStore(storeId).statusCode(200).log().all();
        storesSteps.getStoreById(storeId).statusCode(404).log().all();
    }

    @Title("This will get all stores")
    @Test
    public void test005() {
        storesSteps.getAllStores().log().all().statusCode(200);

    }


}
