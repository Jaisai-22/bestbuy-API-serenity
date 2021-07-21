package com.bestbuy.productinfo;

import com.bestbuy.constant.EndPoints;
import com.bestbuy.stepinfo.ProductSteps;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;


import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsMapContaining.hasValue;
import static org.junit.Assert.assertThat;

@RunWith(SerenityRunner.class)
public class ProductCURDTest extends TestBase {

    static String name = "Samsung Tablet" +TestUtils.getRandomValue();
    static String type = "Electronics" +TestUtils.getRandomValue();
    static double price = 12.99;
    static String upc = "1234545641";
    static double shipping = 11.99;
    static String description = "Universal";
    static String manufacturer = "Walmart";
    static String model = "String";
    static String url = "String";
    static String image = "String";
    static long productId;



    @Steps
    ProductSteps productSteps;


    @Title("This will create new product")
    @Test
    public void test001() {

       productId= productSteps.createNewProduct(name, type, price, upc, shipping, description, manufacturer, model, url, image).statusCode(201).log().all().extract().response()
                .body().jsonPath().getLong("id");
        System.out.println("product id is : "+productId);
    }
   @Title("This will read product- By product ID ")
   @Test
    public void test002() {

       productSteps.getProductByID(productId).statusCode(200).log().all();

    }

    @Title("This test will update the product information and verify the updated information")
    @Test

    public void test003() {
        name = name + "_Changed";
        price = 89.99;
        upc = upc + "_added";
        productSteps.updateProduct(productId, name, type, upc, price, description, model).log().all().statusCode(200);
        productSteps.getProductByID(productId).body("name", equalTo(name));

    }

    @Title("This will delete and verify if product is deleted")
    @Test
    public void test004() {

        productSteps.deleteProduct(productId).log().all().statusCode(200);
        productSteps.getProductByID(productId).log().all().statusCode(404);
    }
    @Title("This will get all products")
    @Test
    public void test005() {
        productSteps.getAllProducts().log().all().statusCode(200);

    }


    }











