package org.ishausa.transport.carpool.service;

import org.ishausa.transport.carpool.model.User;
import org.mongodb.morphia.Datastore;

import java.util.logging.Logger;

/**
 * Created by tosri on 1/2/2017.
 */
public class UsersService {
    private static final Logger log = Logger.getLogger(UsersService.class.getName());

    private final Datastore datastore;

    public UsersService(final Datastore datastore) {
        this.datastore = datastore;
    }

    public User findById(final String userId) {
        return datastore.get(User.class, userId);
    }

    public void createOrUpdate(final User user) {
        final User existingUser = findById(user.getUserId());
        // Return if no fields have changed.
        if (user.equals(existingUser)) {
            return;
        }

        if (existingUser == null) {
            log.info("Creating a new user: " + user);
        } else {
            log.info("User info has changed. Old: " + existingUser + ", new: " + user);
        }

        datastore.save(user);
    }
}
