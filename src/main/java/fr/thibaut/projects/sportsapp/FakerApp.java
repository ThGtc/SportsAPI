package fr.thibaut.projects.sportsapp;

import com.github.javafaker.Faker;
import fr.thibaut.projects.sportsapp.controller.UserController;
import fr.thibaut.projects.sportsapp.model.Sport;
import fr.thibaut.projects.sportsapp.model.User;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;
import java.util.Random;

public class FakerApp {
    public static void populateUsersDatabase() {

        //List<Sport> sports = new SportController().getAllSports(null).getBody();
        RestTemplate restTemplate = new RestTemplate();
        Sport[] sports = restTemplate.getForObject("http://localhost:8080/api/sports", Sport[].class);
        Random random = new Random();

        for (int i = 0; i < 50; i++) {
            Faker faker = new Faker(new Locale("fr_FR"));
            User fakeUser = new User();

            assert sports != null;
            Sport faveSport = sports[random.nextInt(4)];

            fakeUser.setPseudo(faker.funnyName().name());
            fakeUser.setFirstName(faker.name().firstName());
            fakeUser.setLastName(faker.name().lastName());
            fakeUser.setMail(faker.internet().emailAddress());
            fakeUser.setPassword("toto");
            fakeUser.setBirthDate(faker.date().birthday().toString());
            fakeUser.setFavoriteSport(faveSport);

            System.out.println(fakeUser);

            User newUsr = restTemplate.postForObject("http://localhost:8080/api/users", fakeUser, User.class);

            UserController userController = new UserController();
            userController.createUser(fakeUser);
        }
    }

}
