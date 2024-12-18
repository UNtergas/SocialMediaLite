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
                    new User("john_doe", "qwertyuiop", "I am John Doe"),
                    new User("jane_doe", "qwertyuiop"),
                    new User("sam_smith", "qwertyuiop"),
                    new User("emma_jones", "qwertyuiop"),
                    new User("mike_brown", "qwertyuiop"),
                    new User("lisa_white", "qwertyuiop", "I am Lisa White"),
                    new User("chris_black", "qwertyuiop"),
                    new User("sara_young", "qwertyuiop"),
                    new User("david_green", "qwertyuiop"),
                    new User("nancy_clark", "qwertyuiop")
            );
            userRepository.saveAll(users);

            relationService.createRelationByUsernames(RelationType.FRIEND, "john_doe", "jane_doe");
            relationService.createRelationByUsernames(RelationType.CLOSE, "emma_jones", "mike_brown");
            relationService.createRelationByUsernames(RelationType.FRIEND, "lisa_white", "chris_black");
            relationService.createRelationByUsernames(RelationType.COLLEGE, "chris_black", "lisa_white");
            relationService.createRelationByUsernames(RelationType.CLOSE, "david_green", "nancy_clark");
            relationService.createRelationByUsernames(RelationType.FRIEND, "john_doe", "david_green");

            System.out.println("Database seeded with sample users and relations.");
            System.out.println("================== Query testing ==================");
            System.out.println("Users with keyword 'I am' in description: " + userRepository.findByDescriptionContaining("I am"));
            System.out.println("Users with no relation: " + userRepository.findUsersWithNoRelations());
            System.out.println("Users with number of relations more than 2: " + userRepository.findUsersWithMoreThanOrEqualsToNRelations(2));
            System.out.println("Users with relations to john_doe: " + userRepository.findUsersWithRelationToSpecificUser("john_doe"));
            System.out.println("Users with multiple relation types: " + userRepository.findUsersWithMultipleRelationTypes());
        }
    }

    private Date getDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }
}
