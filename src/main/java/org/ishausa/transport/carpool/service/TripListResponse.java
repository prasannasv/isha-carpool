package org.ishausa.transport.carpool.service;

import org.ishausa.transport.carpool.model.Role;
import org.ishausa.transport.carpool.model.Trip;
import org.ishausa.transport.carpool.model.User;

import java.util.List;

/**
 * Created by Prasanna Venkat on 1/7/2017.
 */
public class TripListResponse {
    private Role userRole;
    private List<Trip> trips;

    public TripListResponse(final User user, final List<Trip> trips) {
        this.userRole = user.getRole();
        this.trips = trips;
    }
}
