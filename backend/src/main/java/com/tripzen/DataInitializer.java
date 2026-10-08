package com.tripzen;

import com.tripzen.entity.User;
import com.tripzen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * DataInitializer runs once on startup.
 * It creates the default ADMIN and USER accounts
 * with properly BCrypt-encoded passwords.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // Create ADMIN user if not already exists
        if (!userRepository.existsByEmail("admin@tripzen.com")) {
            User admin = new User();
            admin.setName("Admin User");
            admin.setEmail("admin@tripzen.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            admin.setPhone("9000000000");
            userRepository.save(admin);
            System.out.println("✅ Admin user created: admin@tripzen.com / admin123");
        }

        // Create default USER if not already exists
        if (!userRepository.existsByEmail("user@tripzen.com")) {
            User user = new User();
            user.setName("Demo User");
            user.setEmail("user@tripzen.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRole("USER");
            user.setPhone("9876543210");
            userRepository.save(user);
            System.out.println("✅ Demo user created: user@tripzen.com / user123");
        }

        System.out.println("✅ TripZen startup complete — backend is ready!");
    }
}
