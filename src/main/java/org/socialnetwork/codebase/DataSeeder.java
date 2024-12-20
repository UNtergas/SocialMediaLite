package org.socialnetwork.codebase;

import org.socialnetwork.codebase.models.RelationType;
import org.socialnetwork.codebase.models.User;
import org.socialnetwork.codebase.repository.RelationRepository;
import org.socialnetwork.codebase.repository.UserRepository;
import org.socialnetwork.codebase.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RelationService relationService;

    @Autowired
    public DataSeeder(UserRepository userRepository, RelationService relationService) {
        this.userRepository = userRepository;
        this.relationService = relationService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (this.userRepository.count() == 0) {
            // Seed 10 users with different details
            List<User> users = Arrays.asList(
                    new User("john_doe", "qwertyuiop", "I am John Doe, a software developer with a passion for technology."),
                    new User("jane_doe", "qwertyuiop", "Hello! I'm Jane, a graphic designer who loves creativity."),
                    new User("sam_smith", "qwertyuiop", "Hi, I'm Sam, a project manager with a knack for organization."),
                    new User("emma_jones", "qwertyuiop", "I'm Emma, a data analyst who finds patterns in numbers."),
                    new User("mike_brown", "qwertyuiop", "Hey, I’m Mike, an entrepreneur building innovative solutions."),
                    new User("lisa_white", "qwertyuiop", "I am Lisa White, a writer and storyteller."),
                    new User("chris_black", "qwertyuiop", "Chris here, a fitness enthusiast and wellness coach."),
                    new User("sara_young", "qwertyuiop", "Hi, I'm Sara, a digital marketer exploring the online world."),
                    new User("david_green", "qwertyuiop", "David Green, a photographer capturing life's moments."),
                    new User("nancy_clark", "qwertyuiop", "Hello, I'm Nancy, a teacher who loves inspiring young minds.")
            );
            userRepository.saveAll(users);

            relationService.createRelationByUsernames(RelationType.FRIEND, "john_doe", "jane_doe");
            relationService.createRelationByUsernames(RelationType.CLOSE, "emma_jones", "mike_brown");
            relationService.createRelationByUsernames(RelationType.FRIEND, "lisa_white", "chris_black");
            relationService.createRelationByUsernames(RelationType.CLOSE, "david_green", "nancy_clark");
            relationService.createRelationByUsernames(RelationType.COLLEGE, "john_doe", "david_green");
            relationService.createRelationByUsernames(RelationType.COLLEGE, "john_doe", "chris_black");

            System.out.println("Database seeded with sample users and relations.");
        }
    }

    private Date getDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }
}
