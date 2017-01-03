package org.ishausa.transport.carpool.model;

import lombok.Data;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by tosri on 12/30/2016.
 */
@Data
@Entity("users")
public class User {
    @Id
    private String userId;
    private String name;
    private String firstName;
    private String lastName;
    private String email;
}
