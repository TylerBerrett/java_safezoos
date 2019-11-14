package local.tylerb.zoo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import local.tylerb.zoo.model.Zoo;
import local.tylerb.zoo.model.ZooAnimals;
import local.tylerb.zoo.services.Service;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ZooController.class)
public class ZooControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Service service;

    private List<Zoo> zooList;

    @Before
    public void setUp() throws Exception {
        zooList = new ArrayList<>();

        List<ZooAnimals> animals = new ArrayList<>();

        Zoo z1 = new Zoo("zoouno", animals);
        z1.setZooid(100);
        zooList.add(z1);
        Zoo z2 = new Zoo("Zoo2", animals);
        z2.setZooid(200);
        zooList.add(z2);
        Zoo z3 = new Zoo("Zoo3", animals);
        z3.setZooid(300);
        zooList.add(z3);


    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getAll() throws Exception {
        String apiUrl = "/zoos/zoos";

        Mockito.when(service.getAll()).thenReturn(zooList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(zooList);

        assertEquals(er, tr);
    }

    @Test
    public void getZooById() throws Exception {
        String apiUrl = "/zoos/zoo/200";

        Mockito.when(service.getZooById(200)).thenReturn(zooList.get(1));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(zooList.get(1));

        assertEquals(er, tr);
    }

    @Test
    public void getStringByName() throws Exception {
        String apiUrl = "/zoos/zoo/namelike/zoouno";

        List<Zoo> fake = new ArrayList<>();
        fake.add(zooList.get(0));

        Mockito.when(service.getZooByLikeName("zoouno")).thenReturn(fake);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(fake);

        assertEquals(er, tr);
    }

    @Test
    public void addZoo() throws Exception {

        String apiUrl = "/zoos/zoo";

        List<ZooAnimals> animals = new ArrayList<>();
        Zoo z4 = new Zoo("Zoo 4", animals);
        z4.setZooid(400);

        ObjectMapper mapper = new ObjectMapper();
        String zoo4 = mapper.writeValueAsString(z4);

        Mockito.when(service.addZoo(any(Zoo.class))).thenReturn(z4);

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(zoo4);

        mockMvc.perform(rb).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());


    }

    @Test
    public void updateZoo() throws Exception {

        String apiUrl = "/zoos/zoo/{id}";

        List<ZooAnimals> animals = new ArrayList<>();
        Zoo z1 = new Zoo("Zoo 300", animals);

        Mockito.when(service.updateZoo(300L, z1)).thenReturn(z1);
        ObjectMapper mapper = new ObjectMapper();
        String zoo4 = mapper.writeValueAsString(z1);

        RequestBuilder rb = MockMvcRequestBuilders.put(apiUrl, 300L)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(zoo4);

        mockMvc.perform(rb).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());


    }

    @Test
    public void deleteZoo() throws Exception {

        String apiUrl = "/zoos/zoo/{id}";

        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl, "200")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(rb).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void deleteZooAnimals() throws Exception {

        String apiUrl = "/zoos/zoo/{zooid}/animals/{animalid}";


        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl, "5","7")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(rb).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void addZooAnimals() throws Exception {

        String apiUrl = "/zoos/zoo/{zooid}/animals/{animalid}";

        RequestBuilder rb = MockMvcRequestBuilders.put(apiUrl, "100","7")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(rb).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());

    }
}