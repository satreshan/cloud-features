package com.sap.cc.bulletinboard.ads;


import com.sap.cc.bulletinboard.NotFoundException;
import com.sap.cc.bulletinboard.rating.RatingServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/ads")
public class AdvertisementController {

    private Logger logger = LoggerFactory.getLogger(getClass());
/*
    @Autowired
    InMemoryAdvertisementStorage inMemoryAdvertisementStorage;*/

    @Autowired
    AdvertisementRepository repository;

    @Autowired
    RatingServiceClient ratingServiceClient;

    @GetMapping
    public List<Advertisement> getAllAds() {
        return repository.findAll();
        //return inMemoryAdvertisementStorage.retrieveAllAdvertisements();
    }


    @GetMapping("/{id}")
    public Advertisement getParticularAds(@PathVariable("id") String id) {
        MDC.put("endpoint", "/api/v1/ads/" + id);
        logger.info("{}", id);
        /*Optional<Advertisement> advertisement = inMemoryAdvertisementStorage.retrieveAdvertisementById(Long.valueOf(id));*/
        Optional<Advertisement> advertisement = repository.findById(Long.valueOf(id));
        logger.trace("{}", advertisement.orElse(null));
        if(advertisement.isPresent()){
            return advertisement.get();
        }
        throw new NotFoundException();
    }

    @PostMapping
    public ResponseEntity<Advertisement> addAdvertisement(@RequestBody Advertisement advertisement) throws URISyntaxException {
        logger.info("Creating ad {}", advertisement);
        //Advertisement createdAdvertisement = inMemoryAdvertisementStorage.saveAdvertisement(advertisement);
        advertisement.setAverageContactRating(getAverageRating(advertisement.getContact()));
        Advertisement createdAdvertisement = repository.save(advertisement);
        logger.info("Created ad {}", createdAdvertisement);
        return ResponseEntity.created(new URI("/api/v1/ads/" + createdAdvertisement.getId())).body(createdAdvertisement);
    }

    @DeleteMapping
    public ResponseEntity<Advertisement> deleteAllAdvertisement(){
        logger.info("Deleting all ads" );
        //inMemoryAdvertisementStorage.deleteAllAdvertisements();
        repository.deleteAll();
        logger.info("Deleted all ads" );
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Advertisement> deleteAdvertisement(@PathVariable("id") String id){
        logger.info("Deleting ad {}", id );
        //inMemoryAdvertisementStorage.deleteAdvertisement(Long.valueOf(id));
        repository.deleteById(Long.valueOf(id));
        logger.info("Deleted ad {}", id );
        return ResponseEntity.noContent().build();
    }

    private float getAverageRating(String contact){
        return ratingServiceClient.getAverageRating(contact);
    }

}
