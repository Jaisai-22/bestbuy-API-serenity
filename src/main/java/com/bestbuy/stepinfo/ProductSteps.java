package com.bestbuy.stepinfo;

import com.bestbuy.constant.EndPoints;
import com.bestbuy.model.ProductsPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

public class ProductSteps {

    /*
     *This  is a  GET method which on request - brings all Product information
     */
    @Step("Getting all product information")
    public ValidatableResponse getAllProducts() {
        return SerenityRest.rest()
                .given()
                .when()
                .get(EndPoints.GET_ALL_PRODUCTS)
                .then();
    }


    @Step("Creating a new product with name: {0}, type:{1}, price:{2}, upc:{3}, shipping:{4}, description:{5}, manufacturer:{6}, model:{7}, url:{8} and image:{9}")
    public ValidatableResponse createNewProduct(String name, String type, double price, String upc, double shipping, String description, String manufacturer, String model,
                                                String url, String image) {

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

        return SerenityRest.rest().given()
                .header( "Content-Type", "application/json" )
                .log().all()
                .when()
                .body(productPojo)
                .post(EndPoints.CREATE_PRODUCT)
                .then().log().all();

    }
    @Step("Getting product with information with productId")
    public ValidatableResponse getProductByID(long productId) {
        return SerenityRest.rest()
                .given()
                .pathParam("id", productId)
                .when()
                .get(EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then();
    }

    @Step("Updating Product information with productId: name : {1} , type : {2} , upc : {3} , price : {4} , description : {5} , model : {6} ")

        public ValidatableResponse updateProduct(long productId,String name , String type , String upc , double price , String description , String model){

            ProductsPojo productsPojo = new ProductsPojo();
            productsPojo.setName(name);
            productsPojo.setType(type);
            productsPojo.setUpc(upc);
            productsPojo.setPrice(price);
            productsPojo.setDescription(description);
            productsPojo.setModel(model);

            return  SerenityRest.rest().given()
                    .contentType(ContentType.JSON)
                    .pathParam("id",productId)
                    .log().all()
                    .when()
                    .body(productsPojo)
                    .patch(EndPoints.UPDATE_PRODUCT_BY_ID )
                    .then();
        }

        @Step("Deleting product information with productId :{0}")
    public ValidatableResponse deleteProduct(long productId) {
        return SerenityRest.rest()
                .given()
                .pathParam("id", productId)
                .when()
                .delete(EndPoints.DELETE_PRODUCT_BY_ID)
                .then();

    }


    }




