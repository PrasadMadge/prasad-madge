package tests;

import base.BaseSetup;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.testng.AllureTestNg;
import io.restassured.response.Response;
import models.Pet;
import org.apache.http.HttpStatus;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.RandomPetUtil;

import static org.assertj.core.api.Assertions.assertThat;

@Listeners({AllureTestNg.class})
public class PetsTests extends BaseSetup {
    Pet pet = null;

    @Epic("Pet API")
    @Feature("Pet Management")
    @Test(description = "Verify that a pet can be created successfully", groups = {"pet", "regression"})
    public void testCreatePet() {
        try {
            // test-data
            pet = RandomPetUtil.generateRandomPet();

            // POST pet call
            Response postResponse = baseApi.petApi().postPet(pet);

            // assertion
            assertThat(postResponse.statusCode()).isEqualTo(HttpStatus.SC_OK);
            softAssert.assertThat(postResponse.getTime()).isLessThan(1000L);
            softAssert.assertThat(postResponse.getHeader("Content-Type")).contains("application/json");

            Pet createdPet = postResponse.as(Pet.class);
            softAssert.assertThat(createdPet.getName()).isEqualTo(pet.getName());
            softAssert.assertThat(createdPet.getId()).isEqualTo(pet.getId());
            softAssert.assertThat(createdPet.getPhotoUrls()).contains(pet.getPhotoUrls());

            // Extra verification: Get pet by ID and verify it exists and matches
            Response getResponse = baseApi.petApi().getPetById(createdPet.getId());
            softAssert.assertThat(getResponse.statusCode()).isEqualTo(HttpStatus.SC_OK);

            Pet retrievedPet = getResponse.as(Pet.class);
            softAssert.assertThat(retrievedPet.getId()).isEqualTo(pet.getId());
            softAssert.assertThat(retrievedPet.getName()).isEqualTo(pet.getName());
            softAssert.assertThat(retrievedPet.getPhotoUrls()).contains(pet.getPhotoUrls());

            softAssert.assertAll();
        } finally {
            // Clean up
            baseApi.petApi().deletePetById(pet.getId());
        }
    }

    @Test(description = "Verify that a pet can be retrieved successfully by its ID.", groups = {"pet", "regression"})
    public void testGetPet() {
        try {
            // test-data
            pet = RandomPetUtil.generateRandomPet();

            // Pre-condition
            baseApi.petApi().postPet(pet);

            // Get Pet call
            Response getResponse = baseApi.petApi().getPetById(pet.getId());

            // Assertion
            assertThat(getResponse.statusCode()).isEqualTo(HttpStatus.SC_OK);
            softAssert.assertThat(getResponse.getTime()).isLessThan(1000L);
            softAssert.assertThat(getResponse.getHeader("Content-Type")).contains("application/json");
            Pet retrievedPet = getResponse.as(Pet.class);
            softAssert.assertThat(retrievedPet.getName()).isEqualTo(pet.getName());
            softAssert.assertThat(retrievedPet.getId()).isEqualTo(pet.getId());
            softAssert.assertAll();
        } finally {
            // Clean up
            baseApi.petApi().deletePetById(pet.getId());
        }
    }

    @Test(description = "Verify that an existing pet's details can be updated successfully.", groups = {"pet", "regression"})
    public void testUpdatePet() {
        try {
            // Pre-condition
            pet = RandomPetUtil.generateRandomPet();
            baseApi.petApi().postPet(pet);

            // test-data
            String updatedName = "Max";
            String updatedStatus = "sold";
            String[] updatedPhotoUrls = new String[]{"http://example.com/photo2.jpg"};
            pet.setName(updatedName);
            pet.setStatus(updatedStatus);
            pet.setPhotoUrls(updatedPhotoUrls);

            // PUT pet call
            Response updateResponse = baseApi.petApi().putPet(pet);

            // Assertion
            assertThat(updateResponse.statusCode()).isEqualTo(HttpStatus.SC_OK);
            softAssert.assertThat(updateResponse.getTime()).isLessThan(1000L);
            softAssert.assertThat(updateResponse.getHeader("Content-Type")).contains("application/json");

            Response getResponse = baseApi.petApi().getPetById(pet.getId());
            assertThat(getResponse.statusCode()).isEqualTo(HttpStatus.SC_OK);

            // Extra verification: Get pet by ID and verify its updated
            Pet retrievedPet = getResponse.as(Pet.class);
            softAssert.assertThat(retrievedPet.getName()).isEqualTo(updatedName);
            softAssert.assertThat(retrievedPet.getStatus()).isEqualTo(updatedStatus);
            softAssert.assertThat(retrievedPet.getPhotoUrls()).containsExactly(updatedPhotoUrls); // was string before
            softAssert.assertAll();
        } finally {
            // Clean up
            baseApi.petApi().deletePetById(pet.getId());
        }
    }

    @Test(description = "Verify that an existing pet can be deleted successfully.", groups = {"pet", "regression"})
    public void testDeletePet() {
        // test-data
        pet = RandomPetUtil.generateRandomPet();

        // Pre-condition
        baseApi.petApi().postPet(pet);

        // Delete pet call
        Response deleteResponse = baseApi.petApi().deletePetById(pet.getId());

        //Assertion
        assertThat(deleteResponse.statusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(deleteResponse.getTime()).isLessThan(1000L);
        assertThat(deleteResponse.getHeader("Content-Type")).contains("application/json");

        // Extra verification: Get pet by ID and verify it does not exist
        Response getAfterDeleteResponse = baseApi.petApi().getPetById(pet.getId());
        assertThat(getAfterDeleteResponse.statusCode()).isEqualTo(HttpStatus.SC_NOT_FOUND);
        assertThat(getAfterDeleteResponse.getBody().asString()).contains("Pet not found");
    }

}
