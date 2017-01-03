'use strict';

angular.
  module('tripList').
  component('tripList', {
    templateUrl: 'trip-list/trip-list.template.html',
    controller: ['$http', function TripListController($http) {
      var self = this;

      self.orderByField = 'departureDateAndTime';
      self.reverseOrder = false;

      self.orderBy = function (field) {
        self.reverseOrder = (field === self.orderByField) ? !self.reverseOrder : false;
        self.orderByField = field;
      };

      $http.get('/api/v1/trips').then(function (response) {
        console.log("trips list response", response);
        var allTrips = response.data;
        self.upcomingTrips = [];
        self.pastTrips = [];

        // Group expired trips and upcoming trips separately.
        // Convert the date in string format to JavaScript Date objects.
        var now = new Date().getTime();
        for (var i = 0, len = allTrips.length; i < len; ++i) {
          var trip = allTrips[i];
          trip.departureDateAndTime = new Date(trip.departureDateAndTime);
          trip.createdAt = new Date(trip.createdAt);

          if (trip.departureDateAndTime.getTime() > now) {
            self.upcomingTrips.push(trip);
          } else {
            self.pastTrips.push(trip);
          }
        }
      });
    }
    ]
});
