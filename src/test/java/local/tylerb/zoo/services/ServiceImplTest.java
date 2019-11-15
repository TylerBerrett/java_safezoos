package local.tylerb.zoo.services;

import local.tylerb.zoo.ZooApplication;
import local.tylerb.zoo.model.Animal;
import local.tylerb.zoo.model.Zoo;
import local.tylerb.zoo.model.ZooAnimals;
import local.tylerb.zoo.view.Count;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZooApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServiceImplTest {

    @Autowired Service service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void AgetAll() {
        assertEquals(5, service.getAll().size());
    }

    @Test
    public void BgetZooById() {
        assertEquals("San Diego Zoo", service.getZooById(3).getZooname());
    }

    @Test
    public void CgetZooByLikeName() {
        assertEquals("San Diego Zoo", service.getZooByLikeName("San Diego Zoo").get(0).getZooname());
    }

    @Test
    public void FaddZoo() {
        List<ZooAnimals> animals = new ArrayList<>();
        Zoo zoo = new Zoo("Cincinnati Zoo", animals);

        Zoo addZoo = service.addZoo(zoo);
        assertNotNull(addZoo);
        Zoo foundZoo = service.getZooById(10);


        assertEquals(addZoo.getZooname(), foundZoo.getZooname());


    }

    @Test
    public void EupdateZoo() {
        List<ZooAnimals> animals = new ArrayList<>();
        Zoo zoo = new Zoo("Harambe's grave", animals);

        Zoo updatedZoo = service.updateZoo(5, zoo);

        assertEquals("Harambe's grave", updatedZoo.getZooname());

    }

    @Test
    public void DdeleteZoo() {

        service.deleteZoo(3);
        assertEquals(4, service.getAll().size());
    }

    @Test
    public void GanimalCount() {
        // it is four because i combined the two bears into one bear on the table
        assertEquals(4, service.animalCount().size());
    }

    @Test
    public void HdeleteZooAnimalsItem() {
        service.deleteZooAnimalsItem(5, 7);
        assertEquals(3, service.animalCount().size());
    }

    @Test
    public void IaddZooAnimal() {
        long correctId = -1;
        service.addZooAnimal(2, 4);
        for (Count c : service.animalCount()){
            if (c.getAnimalname().equals("penguin")){
                correctId = 2;
            }
        }

        assertEquals(2, correctId);




    }
}