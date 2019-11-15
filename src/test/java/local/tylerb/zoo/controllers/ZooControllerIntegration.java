package local.tylerb.zoo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import local.tylerb.zoo.model.Zoo;
import local.tylerb.zoo.model.ZooAnimals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.number.OrderingComparison.lessThan;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ZooControllerIntegration {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void initialiseRestAssuredMockMvcWebApplicationContext()
    {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @Test
    public void whenMeasuredReponseTime()
    {
        given().when().get("/zoos/zoos").then().time(lessThan(5000L));
    }

    @Test
    public void givenGetAll() throws Exception {

        given().when().get("/zoos/zoos").then().statusCode(200).and().body(containsString("bear"));

    }

    @Test
    public void givenFindZooId() throws Exception {

        given().when().get("/zoos/zoo/2").then().statusCode(200).and().body(containsString("bear"));

    }

    @Test
    public void givenFindZooName() throws Exception {

        given().when().get("/zoos/zoo/namelike/san").then().statusCode(200).and().body(containsString("San Diego Zoo"));

    }

    @Test
    public void givenAddZoo() throws Exception {

        List<ZooAnimals> animals = new ArrayList<>();
        Zoo z4 = new Zoo("Zoo 4", animals);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(z4);

        given().contentType("application/json").body(json).when().post("/zoos/zoo").then().statusCode(201);

    }

    @Test
    public void givenUpdateZoo() throws Exception {

        List<ZooAnimals> animals = new ArrayList<>();
        Zoo z4 = new Zoo("Zoo 4", animals);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(z4);

        given().contentType("application/json").body(json).when().put("/zoos/zoo/2").then().statusCode(200);

    }

    @Test
    public void givenDeleteZoo() throws Exception {

        given().when().delete("/zoos/zoo/2").then().statusCode(200);

    }

    @Test
    public void givenDeleteAnimal() throws Exception {

        given().when().delete("/zoos/zoo/5/animals/7").then().statusCode(200);

    }

    @Test
    public void givenAddAnimal() throws Exception {

        given().when().put("/zoos/zoo/1/animals/5").then().statusCode(200);

    }





}
