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
                    new User("john_doe", "password123", "John", "Doe", getDate(1990, Calendar.JANUARY, 1)),
                    new User("jane_doe", "password456", "Jane", "Doe", getDate(1992, Calendar.FEBRUARY, 14)),
                    new User("sam_smith", "password789", "Sam", "Smith", getDate(1985, Calendar.MARCH, 10)),
                    new User("emma_jones", "password101", "Emma", "Jones", getDate(1993, Calendar.APRIL, 25)),
                    new User("mike_brown", "password202", "Mike", "Brown", getDate(1988, Calendar.MAY, 30)),
                    new User("lisa_white", "password303", "Lisa", "White", getDate(1991, Calendar.JUNE, 15)),
                    new User("chris_black", "password404", "Chris", "Black", getDate(1987, Calendar.JULY, 8)),
                    new User("sara_young", "password505", "Sara", "Young", getDate(1994, Calendar.AUGUST, 20)),
                    new User("david_green", "password606", "David", "Green", getDate(1986, Calendar.SEPTEMBER, 5)),
                    new User("nancy_clark", "password707", "Nancy", "Clark", getDate(1995, Calendar.OCTOBER, 18))
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
