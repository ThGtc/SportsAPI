package fr.thibaut.projects.sportsapp.controller;

import fr.thibaut.projects.sportsapp.model.Sport;
import fr.thibaut.projects.sportsapp.repository.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class SportController {

    @Autowired
    SportRepository sportRepository;

    @GetMapping("/sports")
    public ResponseEntity<List<Sport>> getAllSports(@RequestParam(required = false) String title) {
        try {
            List<Sport> sports = new ArrayList<Sport>();

            if (title == null)
                sports.addAll(sportRepository.findAll());
            else
                sportRepository.findByTitleContaining(title).forEach(sports::add);

            if (sports.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(sports, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sports/{id}")
    public ResponseEntity<Sport> getSportById(@PathVariable("id") long id) {
        Optional<Sport> sportData = sportRepository.findById(id);

        if (sportData.isPresent()) {
            return new ResponseEntity<>(sportData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/sports")
    public ResponseEntity<Sport> createSport(@RequestBody Sport sport) {
        try {
            Sport _sport = sportRepository
                    .save(new Sport(sport.getTitle(),
                            sport.getIncludesDistance(),
                            sport.getRelatedLogo()));
            return new ResponseEntity<>(_sport, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/sports/{id}")
    public ResponseEntity<Sport> updateSport(@PathVariable("id") long id, @RequestBody Sport sport) {
        Optional<Sport> sportData = sportRepository.findById(id);

        if (sportData.isPresent()) {
            Sport _sport = sportData.get();
            _sport.setTitle(sport.getTitle());
            _sport.setIncludesDistance(sport.getIncludesDistance());
            _sport.setRelatedLogo(sport.getRelatedLogo());
            return new ResponseEntity<>(sportRepository.save(_sport), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/sports/{id}")
    public ResponseEntity<HttpStatus> deleteSport(@PathVariable("id") long id) {
        try {
            sportRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/sports")
    public ResponseEntity<HttpStatus> deleteAllSports() {
        try {
            sportRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
