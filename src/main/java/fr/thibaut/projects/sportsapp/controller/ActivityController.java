package fr.thibaut.projects.sportsapp.controller;

import fr.thibaut.projects.sportsapp.model.Activity;
import fr.thibaut.projects.sportsapp.repository.ActivityRepository;
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
public class ActivityController {

    @Autowired
    ActivityRepository activityRepository;

    @GetMapping("/activities")
    public ResponseEntity<List<Activity>> getAllActivities(@RequestParam(required = false) String title) {
        try {
            List<Activity> activities = new ArrayList<Activity>();

            if (title == null)
                activityRepository.findAll().forEach(activities::add);
            else
                activityRepository.findByTitleContaining(title).forEach(activities::add);

            if (activities.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(activities, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/activities/{id}")
    public ResponseEntity<Activity> getActivityById(@PathVariable("id") long id) {
        Optional<Activity> activityData = activityRepository.findById(id);

        if (activityData.isPresent()) {
            return new ResponseEntity<>(activityData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/activities")
    public ResponseEntity<Activity> createActivity(@RequestBody Activity activity) {
        try {
            Activity _activity = activityRepository
                    .save(new Activity(activity.getTitle(),
                            activity.getSport(),
                            activity.getDuration(),
                            activity.getDistance(),
                            activity.getDescription(),
                            activity.getActivityDate(),
                            activity.getRelatedPic(),
                            activity.isPrivate(),
                            activity.getPostedBy()));
            return new ResponseEntity<>(_activity, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/activities/{id}")
    public ResponseEntity<Activity> updateActivity(@PathVariable("id") long id, @RequestBody Activity activity) {
        Optional<Activity> activityData = activityRepository.findById(id);

        if (activityData.isPresent()) {
            Activity _activity = activityData.get();
            _activity.setTitle(activity.getTitle());
            _activity.setSport(activity.getSport());
            _activity.setDuration(activity.getDuration());
            _activity.setDistance(activity.getDistance());
            _activity.setDescription(activity.getDescription());
            _activity.setActivityDate(activity.getActivityDate());
            _activity.setRelatedPic(activity.getRelatedPic());
            _activity.setPrivate(activity.isPrivate());
            return new ResponseEntity<>(activityRepository.save(_activity), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/activities/{id}")
    public ResponseEntity<HttpStatus> deleteActivity(@PathVariable("id") long id) {
        try {
            activityRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/activities")
    public ResponseEntity<HttpStatus> deleteAllActivities() {
        try {
            activityRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/activities/isPrivate")
    public ResponseEntity<List<Activity>> findByisPrivate() {
        try {
            List<Activity> activities = activityRepository.findByisPrivate(true);

            if (activities.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(activities, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
