package com.exchangeservice.config;

import com.exchangeservice.model.User;
import com.exchangeservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private UserRepository userRepository;

    @Autowired
    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user_1 = new User();
        user_1.setUsername("test_1");
        user_1.setPassword("$2a$10$ONImibOVK2oXlCqoio9Z2OujXPsC.1uMjJeKFNzcYNy50rEp//yey"); // original password is "test_1"
        userRepository.save(user_1);

        User user_2 = new User();
        user_2.setUsername("test_2");
        user_2.setPassword("$2a$10$kH4i5wSdskWsscXkF7sjSelnq3cr3filmXnOkzjO.QVjpGSoel.WS"); // original password is "test_2"
        userRepository.save(user_2);
    }
}
