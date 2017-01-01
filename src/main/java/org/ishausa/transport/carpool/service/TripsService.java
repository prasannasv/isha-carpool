package org.ishausa.transport.carpool.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.ishausa.transport.carpool.model.Trip;
import org.mongodb.morphia.Datastore;

import java.util.List;

/**
 * Created by tosri on 12/30/2016.
 */
public class TripsService {

    private static final Gson GSON = new GsonBuilder().create();

    private final Datastore datastore;

    public TripsService(final Datastore datastore) {
        this.datastore = datastore;
    }

    public List<Trip> listAll() {
        return datastore.find(Trip.class).asList();
    }

    public void createTrip(final String jsonBody) {
        final Trip trip = GSON.fromJson(jsonBody, Trip.class);

        datastore.save(trip);
    }

    public Trip find(final String id) {
        return datastore.get(Trip.class, id);
    }
}
