package com.dsc.carsharing.config;

import com.dsc.carsharing.model.Administrator;
import com.dsc.carsharing.model.Parent;
import com.dsc.carsharing.repositories.AdministratorRepository;
import com.dsc.carsharing.repositories.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private AdministratorRepository administratorRepository;
    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        createAdmin();
        createUser();
        alreadySetup = true;
    }

    private void createAdmin() {
        String username = "app-admin";
        Administrator administrator = administratorRepository.findByUsername(username);
        if (administrator == null) {
            Administrator admin = new Administrator();
            admin.setFirstName("Admin");
            admin.setLastName("Admin");
            admin.setEmail("admin@admin.com");
            admin.setUsername(username);
            admin.setEnabled(true);
            admin.setPassword(passwordEncoder.encode("administrator12345"));
            administratorRepository.save(admin);
        }
    }

    private void createUser() {
        String username = "toto";
        Parent parent = parentRepository.findByUsername(username);
        if (parent == null) {
            parent = new Parent();
            parent.setFirstName("toto");
            parent.setLastName("toto");
            parent.setEmail("toto@carsharing.com");
            parent.setUsername(username);
            parent.setEnabled(true);
            parent.setPassword(passwordEncoder.encode("toto"));
            parentRepository.save(parent);
        }
    }

}