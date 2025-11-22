package com.project.initializer;

import com.project.entity.Post;
import com.project.entity.Role;
import com.project.entity.User;
import com.project.enums.PostStatus;
import com.project.enums.PostType;
import com.project.enums.RoleType;
import com.project.repository.PostRepository;
import com.project.repository.RoleRepository;
import com.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        log.info("Initializing sample data...");

        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setName(RoleType.ADMIN);
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setName(RoleType.USER);
            roleRepository.save(userRole);

            log.info("Roles created: ADMIN, USER");
        }

        if (userRepository.count() == 0) {
            Role adminRole = roleRepository.findByName(RoleType.ADMIN).orElseThrow();
            Role userRole = roleRepository.findByName(RoleType.USER).orElseThrow();

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@example.com");
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);
            admin.setRoles(adminRoles);
            userRepository.save(admin);

            User user1 = new User();
            user1.setUsername("john");
            user1.setPassword(passwordEncoder.encode("john123"));
            user1.setEmail("john@example.com");
            Set<Role> user1Roles = new HashSet<>();
            user1Roles.add(userRole);
            user1.setRoles(user1Roles);
            userRepository.save(user1);

            User user2 = new User();
            user2.setUsername("jane");
            user2.setPassword(passwordEncoder.encode("jane123"));
            user2.setEmail("jane@example.com");
            Set<Role> user2Roles = new HashSet<>();
            user2Roles.add(userRole);
            user2.setRoles(user2Roles);
            userRepository.save(user2);

            log.info("Users created: admin, john, jane");

            Post post1 = new Post();
            post1.setTitle("Internet connectivity issue");
            post1.setDescription("The WiFi in building A is not working properly");
            post1.setType(PostType.ISSUE);
            post1.setStatus(PostStatus.DRAFT);
            post1.setCreatedBy(user1);
            postRepository.save(post1);

            Post post2 = new Post();
            post2.setTitle("Noise complaint");
            post2.setDescription("Loud music from apartment 302");
            post2.setType(PostType.COMPLAINT);
            post2.setStatus(PostStatus.PENDING_APPROVAL);
            post2.setCreatedBy(user1);
            postRepository.save(post2);

            Post post3 = new Post();
            post3.setTitle("Community meeting");
            post3.setDescription("Monthly community meeting scheduled for next week");
            post3.setType(PostType.ANNOUNCEMENT);
            post3.setStatus(PostStatus.APPROVED);
            post3.setCreatedBy(admin);
            postRepository.save(post3);

            Post post4 = new Post();
            post4.setTitle("Lost keys");
            post4.setDescription("Found a set of keys near the parking lot");
            post4.setType(PostType.LOST);
            post4.setStatus(PostStatus.APPROVED);
            post4.setCreatedBy(user2);
            postRepository.save(post4);

            Post post5 = new Post();
            post5.setTitle("Need help with moving");
            post5.setDescription("Looking for help to move furniture this weekend");
            post5.setType(PostType.HELP);
            post5.setStatus(PostStatus.REJECTED);
            post5.setCreatedBy(user2);
            postRepository.save(post5);

            Post post6 = new Post();
            post6.setTitle("Maintenance request");
            post6.setDescription("Elevator maintenance completed");
            post6.setType(PostType.ISSUE);
            post6.setStatus(PostStatus.CLOSED);
            post6.setCreatedBy(admin);
            post6.setAssignedUpdate("Issue has been resolved and elevator is now working properly");
            postRepository.save(post6);

            log.info("Sample posts created in various states");
        }

        log.info("Data initialization completed successfully!");
    }
}
