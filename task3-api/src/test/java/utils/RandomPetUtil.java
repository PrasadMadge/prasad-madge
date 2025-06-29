package utils;

import models.Pet;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class RandomPetUtil {

    public static Pet generateRandomPet() {
        long id = ThreadLocalRandom.current().nextLong(100000, 999999);
        String name = "Pet-" + UUID.randomUUID().toString().substring(0, 8);
        String[] photoUrls = new String[]{"http://example.com/" + name + ".jpg"};
        String status = "available";

        return new Pet(id, name, photoUrls, status);
    }
}
