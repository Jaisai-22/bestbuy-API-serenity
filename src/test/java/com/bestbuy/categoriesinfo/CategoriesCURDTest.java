package com.bestbuy.categoriesinfo;

import com.bestbuy.stepinfo.CategoriesSteps;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.equalTo;
@RunWith(SerenityRunner.class)
public class CategoriesCURDTest extends TestBase {


    static String name = "APPLE Iphone" + TestUtils.getRandomValue();
    static String id = "abc123" + TestUtils.getRandomValue();
    static String categoriesId;

    @Steps
    CategoriesSteps categoriesSteps;

    @Title("This test will create a new categories and verify its generated")
    @Test

    public void test001() {

        categoriesId = categoriesSteps.createNewCategories(name, id).log().all().statusCode(201).extract().response()
                .body().jsonPath().getString("id");
        System.out.println("categories ID is" + categoriesId);
    }

    @Title("This test will get the categories information by ID")
    @Test

    public void test002() {
        categoriesSteps.getCategoriesById(categoriesId).log().all().statusCode(200);

    }

    @Title("This test will update the categories information and verify the updated information")
    @Test

    public void test003() {

        name = name + "_Changed";

        categoriesSteps.updateCategories(categoriesId, name).log().all().statusCode(200);
        categoriesSteps.getCategoriesById(categoriesId).body("name", equalTo(name)).log().all();

    }

    @Title("This test will delete the categories information and verify the categories is deleted ")
    @Test

    public void test004() {
        categoriesSteps.deleteCategoriesById(categoriesId).statusCode(200).log().all();
        categoriesSteps.deleteCategoriesById(categoriesId).statusCode(404).log().all();
    }

    @Title("This will get all products")
    @Test
    public void test005() {
        categoriesSteps.getAllCategories().log().all().statusCode(200);

    }
}