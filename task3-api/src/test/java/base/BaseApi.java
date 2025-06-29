package base;

import api.PetApi;
import io.restassured.RestAssured;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseApi {

    protected String BASE_URL;
    protected String USERNAME;
    protected String PASSWORD;

    public BaseApi() {
        loadConfig();
        RestAssured.baseURI = BASE_URL;
    }

    private void loadConfig() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("config/config.properties")) {
            props.load(fis);
            BASE_URL = props.getProperty("base.url");
            USERNAME = props.getProperty("username");
            PASSWORD = props.getProperty("password");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    public PetApi petApi() {
        return new PetApi();
    }

    // Can add more chained APIs here
    // public UserApi userApi() { return new UserApi(); }
}
