package com.bestbuy.stepinfo;

import com.bestbuy.constant.EndPoints;
import com.bestbuy.model.CategoriesPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

public class CategoriesSteps {

    @Step("Creating Categories with name : {0} , id : {1}")

    public ValidatableResponse createNewCategories(String name, String id){

        CategoriesPojo categoriesPojo = new CategoriesPojo();
        categoriesPojo.setName(name);
        categoriesPojo.setId(id);

        return SerenityRest.rest().given()
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .body(categoriesPojo)
                .post(EndPoints.CREATE_CATEGORIES)
                .then();
    }
    @Step("Getting the categories information with categoriesId : {0}")

    public ValidatableResponse getCategoriesById(String categoriesId){
        return SerenityRest.rest()
                .given()
                .pathParam("id",categoriesId)
                .contentType(ContentType.JSON)
                .when()
                .get(EndPoints.GET_CATEGORIES_BY_ID )
                .then();
    }
    @Step("Updating Categories information with categoriesId : {0} name : {1} ")

    public ValidatableResponse updateCategories(String categoriesId,String name){

        CategoriesPojo categoriesPojo = new CategoriesPojo();
        categoriesPojo.setName(name);
        categoriesPojo.setId(categoriesId);

        return  SerenityRest.rest().given()
                .contentType(ContentType.JSON)
                .pathParam("id",categoriesId)
                .log().all()
                .when()
                .body(categoriesPojo)
                .patch(EndPoints.UPDATE_CATEGORIES_BY_ID )
                .then();
    }

    @Step("Deleting the Categories information with CategoriesId : {0} ")

    public ValidatableResponse deleteCategoriesById(String categoriesId){
        return SerenityRest.rest()
                .given()
                .pathParam("id",categoriesId)
                .when()
                .delete(EndPoints.GET_CATEGORIES_BY_ID)
                .then();



    }
    @Step("Getting all categories Information ")

    public ValidatableResponse getAllCategories() {
        return SerenityRest.rest()
                .given()
                .when()
                .get(EndPoints.GET_ALL_CATEGORIES)
                .then();

    }


}
