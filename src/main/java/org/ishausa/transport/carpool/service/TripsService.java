package org.ishausa.transport.carpool.service;

import org.ishausa.transport.carpool.model.Trip;
import org.mongodb.morphia.Datastore;

import java.util.List;

/**
 * Created by tosri on 12/30/2016.
 */
public class TripsService {
    private final Datastore datastore;

    public TripsService(final Datastore datastore) {
        this.datastore = datastore;
    }

    public List<Trip> listAll() {
        return datastore.find(Trip.class).asList();
    }

    public String createTrip() {
        return (String) datastore.save(new Trip()).getId();
    }

    public Trip find(final String id) {
        return datastore.get(Trip.class, id);
    }
}
