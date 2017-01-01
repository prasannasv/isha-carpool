package org.ishausa.transport.carpool.model;

import lombok.Data;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;

/**
 * Created by tosri on 12/30/2016.
 */
@Entity(Trip.COLLECTION_NAME)
@Data
public class Trip {
    public static final String COLLECTION_NAME = "trips";

    @Id
    private String id;
    private String from;
    private String to;
    private Date departureDateAndTime;
    /** userId of the User that created this trip. */
    private String creator;
    private Date createdOn;
}
