package local.tylerb.zoo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import local.tylerb.zoo.model.Zoo;
import local.tylerb.zoo.services.Service;
import local.tylerb.zoo.view.Count;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@WebMvcTest(value = AnimalController.class)
public class AnimalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Service service;

    private List<Count> countList;

    @Before
    public void setUp() throws Exception {
        countList = new ArrayList<>();

        Count c1 = new Count() {
            @Override
            public String getAnimalname() {
                return "Tigger";
            }

            @Override
            public int getLocations() {
                return 1;
            }
        };
        countList.add(c1);
        Count c2 = new Count() {
            @Override
            public String getAnimalname() {
                return "Not Tigger";
            }

            @Override
            public int getLocations() {
                return 2;
            }
        };
        countList.add(c2);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getCount() throws Exception{
        String apiUrl = "/animals/count";

        Mockito.when(service.animalCount()).thenReturn(countList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(countList);

        assertEquals(er, tr);


    }
}