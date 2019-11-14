package local.tylerb.zoo.controllers;

import local.tylerb.zoo.logging.Loggable;
import local.tylerb.zoo.services.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Loggable
@RestController
@RequestMapping("/animals")
public class AnimalController {

    @Autowired
    Service service;
    private static final Logger logger = LoggerFactory.getLogger(AnimalController.class);

    @GetMapping(value = "/count")
    public ResponseEntity<?> getCount(HttpServletRequest request) {
        logger.info(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
        return new ResponseEntity<>(service.animalCount(), HttpStatus.OK);
    }

}
