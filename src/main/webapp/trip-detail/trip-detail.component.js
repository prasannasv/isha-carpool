'use strict';

angular.
  module('tripDetail').
  component('tripDetail', {
    templateUrl: 'trip-detail/trip-detail.template.html',
    controller: ['$routeParams', '$http',
      function TripDetailController($routeParams, $http) {
        var self = this;
        $http.get('/api/v1/trips/' + $routeParams.tripId).then(function(response) {
          console.log("response", response);
          self.trip = response.data;
        });
      }
    ]
  });
