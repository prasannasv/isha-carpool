'use strict';

angular.
  module('tripList').
  component('tripList', {
    templateUrl: 'trip-list/trip-list.template.html',
    controller: ['$http', function TripListController($http) {
      var self = this;

      self.orderByField = 'departureDateAndTime';
      self.reverseOrder = true;

      self.orderBy = function (field) {
        self.reverseOrder = (field === self.orderByField) ? !self.reverseOrder : false;
        self.orderByField = field;
      };

      $http.get('/api/v1/trips').then(function (response) {
        console.log("response", response);
        self.trips = response.data;

        // Convert the date in string format to JavaScript Date objects.
        for (var i = 0, len = self.trips.length; i < len; ++i) {
          var trip = self.trips[i];
          trip.departureDateAndTime = new Date(trip.departureDateAndTime);
          trip.createdOn = new Date(trip.createdOn);
        }
      });
    }
    ]
});
