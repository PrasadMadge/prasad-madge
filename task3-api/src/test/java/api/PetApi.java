package api;

import base.BaseApi;
import io.restassured.response.Response;
import models.Pet;

import static io.restassured.RestAssured.given;

public class PetApi extends BaseApi {

    private static final String BASE_PATH = "/pet";

    public Response postPet(Pet pet) {
        return given()
                .auth().preemptive().basic(USERNAME, PASSWORD)
                .contentType("application/json")
                .accept("application/json")
                .body(pet)
                .when()
                .post(BASE_PATH);
    }

    public Response getPetById(long id) {
        return given()
                .auth().preemptive().basic(USERNAME, PASSWORD)
                .contentType("application/json")
                .accept("application/json")
                .when()
                .get(BASE_PATH + "/" + id);
    }

    public Response putPet(Pet pet) {
        return given()
                .auth().preemptive().basic(USERNAME, PASSWORD)
                .contentType("application/json")
                .accept("application/json")
                .body(pet)
                .when()
                .put(BASE_PATH);
    }

    public Response deletePetById(long id) {
        return given()
                .auth().preemptive().basic(USERNAME, PASSWORD)
                .contentType("application/json")
                .accept("application/json")
                .when()
                .delete(BASE_PATH + "/" + id);
    }
}