'use strict';

angular.
  module('tripList').
  component('tripList', {
    templateUrl: 'trip-list/trip-list.template.html',
    controller: ['$http', function TripListController($http) {
      var self = this;

      $http.get('/api/v1/trips').then(function (response) {
        console.log("response", response);
        self.trips = response.data;
      });
    }
    ]
});
