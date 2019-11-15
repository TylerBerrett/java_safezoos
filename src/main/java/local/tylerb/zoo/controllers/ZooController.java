package local.tylerb.zoo.controllers;

import local.tylerb.zoo.logging.Loggable;
import local.tylerb.zoo.model.Zoo;
import local.tylerb.zoo.services.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@Loggable
@RestController
@RequestMapping("/zoos")
public class ZooController {

    @Autowired
    Service service;

    private static final Logger logger = LoggerFactory.getLogger(ZooController.class);

    @GetMapping(value = "/zoos")
    public ResponseEntity<?> getAll(HttpServletRequest request){
        logger.info(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
        List<Zoo> list = service.getAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/zoo/{id}")
    public ResponseEntity<?> getZooById(HttpServletRequest request, @PathVariable long id) {
        logger.info(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
        Zoo zoo = service.getZooById(id);
        return new ResponseEntity<>(zoo, HttpStatus.OK);
    }

    @GetMapping(value = "/zoo/namelike/{name}")
    public ResponseEntity<?> getStringByName(@PathVariable String name, HttpServletRequest request) {
        logger.info(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
        List<Zoo> zoo = service.getZooByLikeName(name);
        return new ResponseEntity<>(zoo, HttpStatus.OK);
    }

    @PostMapping(value = "/zoo")
    public ResponseEntity<?> addZoo(@RequestBody Zoo zoo, HttpServletRequest request) {
        logger.info(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
        zoo = service.addZoo(zoo);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI zooURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(zoo.getZooid())
                .toUri();
        responseHeaders.setLocation(zooURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/zoo/{id}")
    public ResponseEntity<?> updateZoo(@PathVariable long id, @RequestBody Zoo zoo, HttpServletRequest request) {
        logger.info(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
        service.updateZoo(id, zoo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/zoo/{id}")
    public ResponseEntity<?> deleteZoo(@PathVariable long id, HttpServletRequest request) {
        logger.info(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
        service.deleteZoo(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/zoo/{zooid}/animals/{animalid}")
    public ResponseEntity<?> deleteZooAnimals(@PathVariable long zooid, @PathVariable long animalid, HttpServletRequest request) {
        logger.info(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
        service.deleteZooAnimalsItem(zooid, animalid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/zoo/{zooid}/animals/{animalid}")
    public ResponseEntity<?> addZooAnimals(@PathVariable long zooid, @PathVariable long animalid, HttpServletRequest request) {
        logger.info(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
        service.addZooAnimal(zooid, animalid);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
