package org.socialnetwork.codebase;

import org.socialnetwork.codebase.models.User;
import org.socialnetwork.codebase.repository.UserRepository;
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

    @Autowired
    public DataSeeder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (this.userRepository.count() == 0) {
            // Seed 10 users with different details
            List<User> users = Arrays.asList(
                    new User("john_doe", "password123"),
                    new User("jane_doe", "password456"),
                    new User("sam_smith", "password789"),
                    new User("emma_jones", "password101"),
                    new User("mike_brown", "password202"),
                    new User("lisa_white", "password303"),
                    new User("chris_black", "password404"),
                    new User("sara_young", "password505"),
                    new User("david_green", "password606"),
                    new User("nancy_clark", "password707")
            );

            userRepository.saveAll(users);
            System.out.println("Database seeded with sample users.");
        }
    }

    private Date getDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }
}
