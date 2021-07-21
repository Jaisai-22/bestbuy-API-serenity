package com.bestbuy.stepinfo;

import com.bestbuy.constant.EndPoints;
import com.bestbuy.model.StoresPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

public class StoreSteps {

    @Step("Creating a new store with name :{0}, type :{1}. address :{2}, address2 :{3}, city :{4}, state :{5}, zip:{6}, lat:{7}, lng:{8}, hours:{9}")

    public ValidatableResponse createNewStore(String name, String type, String address, String address2,
                                              String city, String state, String zip, float lat, float lng,
                                              String hours) {

        StoresPojo storesPojo = new StoresPojo();
        storesPojo.setName(name);
        storesPojo.setType(type);
        storesPojo.setAddress(address);
        storesPojo.setAddress2(address2);
        storesPojo.setCity(city);
        storesPojo.setState(state);
        storesPojo.setZip(zip);
        storesPojo.setLat(lat);
        storesPojo.setLng(lng);
        storesPojo.setHours(hours);

        return SerenityRest.rest().given()
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .body(storesPojo)
                .post(EndPoints.CREATE_STORE)
                .then();
    }

    @Step("Getting the information of the store created by id:{0}")
    public ValidatableResponse getStoreById(long storeId) {
        return SerenityRest.rest()
                .given()
                .pathParam("id",storeId)
                .when()
                .get(EndPoints.GET_STORE_BY_ID)
                .then();
    }

    @Step("Updating Store information with  name :{0}, type :{1}. address :{2}, address2 :{3}, city :{4}")

    public ValidatableResponse updateStore(long storeId,String name, String type, String address, String address2,
                                           String city) {

        StoresPojo storesPojo = new StoresPojo();
        storesPojo.setName(name);
        storesPojo.setType(type);
        storesPojo.setAddress(address);
        storesPojo.setAddress2(address2);
        storesPojo.setCity(city);

        return SerenityRest.rest().given()
                .contentType(ContentType.JSON)
                .pathParam("id",storeId)
                .log().all()
                .when()
                .body(storesPojo)
                .patch(EndPoints.UPDATE_STORE_BY_ID )
                .then();
    }

    @Step("Deleting the store with store Id : {0} ")

    public ValidatableResponse deleteStore(long storeId) {
        return SerenityRest.rest()
                .given()
                .pathParam("id",storeId)
                .when()
                .delete(EndPoints.DELETE_STORE_BY_ID)
                .then();


    }

    @Step("Getting all stores Information ")

    public ValidatableResponse getAllStores() {
        return SerenityRest.rest()
                .given()
                .when()
                .get(EndPoints.GET_ALL_STORES)
                .then();
    }


}
