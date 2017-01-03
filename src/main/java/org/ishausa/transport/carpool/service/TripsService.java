package org.ishausa.transport.carpool.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ishausa.transport.carpool.model.Trip;
import org.ishausa.transport.carpool.model.User;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by tosri on 12/30/2016.
 */
public class TripsService {
    private static final Logger log = Logger.getLogger(TripsService.class.getName());

    private static final Gson GSON = new GsonBuilder().create();

    private final Datastore datastore;

    public TripsService(final Datastore datastore) {
        this.datastore = datastore;
    }

    public List<Trip> listAll() {
        return datastore.find(Trip.class).asList();
    }

    public String createTrip(final User creator, final String jsonBody) {
        final Trip trip = GSON.fromJson(jsonBody, Trip.class);
        trip.setCreator(creator.getUserId());
        trip.setCreatedAt(new Date());

        final Key<Trip> key = datastore.save(trip);

        log.info("Created trip: " + trip + ", from input json: " + jsonBody);

        return (String) key.getId();
    }

    public Trip find(final String id) {
        final Trip trip = datastore.get(Trip.class, new ObjectId(id));

        log.info("trip for id: " + id + ", trip: " + trip);

        return trip;
    }
}
