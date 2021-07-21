package com.bestbuy.productinfo;

import com.bestbuy.constant.EndPoints;
import com.bestbuy.model.ProductsPojo;
import com.bestbuy.stepinfo.ProductSteps;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.collection.IsMapContaining.hasValue;
import static org.junit.Assert.assertThat;

public class ProductCURDTest1 extends TestBase {

    static String name = "Samsung Tablet" + TestUtils.getRandomValue();
    static String type = "Electronics" + TestUtils.getRandomValue();
    static double price = 12.99;
    static String upc = "1234545641";
    static double shipping = 11.99;
    static String description = "Universal";
    static String manufacturer = "Walmart";
    static String model = "String";
    static String url = "String";
    static String image = "String";
    static long productId;


    @Title("This will create a new product")
    @Test
    public void test001() {

        ProductsPojo productPojo = new ProductsPojo();

        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setUpc(upc);
        productPojo.setShipping(shipping);
        productPojo.setDescription(description);
        productPojo.setManufacturer(manufacturer);
        productPojo.setModel(model);
        productPojo.setUrl(url);
        productPojo.setImage(image);

        SerenityRest.rest().given().log().all()
                .header("Content-Type", "application/json")
                .body(productPojo)
                .when()
                .post(EndPoints.CREATE_PRODUCT)
                .then().log().all().statusCode(201);


    }

    @Title("Verify if the product was added to the application")
    @Test
    public void test002() {
        String p1 = "findAll{it.name=='";
        String p2 = "'}.get(0)";

        HashMap<String, Object> value =
                SerenityRest.rest()
                        .given().log().all()
                        .pathParam("name", name)
                        .when()
                        .get(EndPoints.GET_ALL_PRODUCTS)
                        .then()
                        .statusCode(200)
                        .extract()
                        .path(p1 + name + p2);
        assertThat(value, Matchers.hasValue(name));
        System.out.println(value);
        productId = (long) value.get("id");
    }

        @Title("Update the user information and verify the updated information")
        @Test
        public void test003() {
            String p1 = "findAll{it.name=='";
            String p2 = "'}.get(0)";

            name = name + "_Changed";
            price = 89.99;
            upc = upc + "_added";
           ProductsPojo productsPojo= new ProductsPojo();

            SerenityRest.rest().given().log().all()
                    .header("Content-Type", "application/json")
                    .pathParam("productId",productId)
                    .body(productsPojo)
                    .when()
                    .put(EndPoints.UPDATE_PRODUCT_BY_ID)
                    .then().log().all().statusCode(200);

            HashMap<String, Object> value =

                    SerenityRest.rest()
                            .given()
                            .when()
                            .get(EndPoints.GET_ALL_PRODUCTS)
                            .then()
                            .statusCode(200)
                            .extract().path(p1 + name + p2);
            assertThat(value, hasValue(name));
            System.out.println(value);
            productId = (int) value.get("id");


        }

        @Title("Delete the student and verify if the student is deleted!")
        @Test
        public void test004() {
            SerenityRest.rest()
                    .given()
                    .pathParam("productId", productId)
                    .when()
                    .delete(EndPoints.DELETE_PRODUCT_BY_ID)
                    .then()
                    .statusCode(204);
            SerenityRest.rest()
                    .given()
                    .pathParam("productId", productId)
                    .when()
                    .get(EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                    .then()
                    .statusCode(404);

        }

    }
