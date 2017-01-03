'use strict';

angular.
  module('rideCreate').
  component('rideCreate', {
    templateUrl: 'ride-create/ride-create.template.html',
    controller: ['$routeParams', '$http',
      function RideCreateController($routeParams, $http) {
        var self = this;
        var tripId = $routeParams.tripId;

        self.hasAlreadyOfferedOrRequestedRide = false;
        $http.get('/api/v1/trips/' + tripId + '/ride_offers').then(function (response) {
          if (response.data && response.data !== "null") {
            self.hasAlreadyOfferedOrRequestedRide = true;
          }
        });
        // TODO: Do the same check for /ride_requests

        self.ride = {
          seats: "1"
        };

        self.createRideOfferOrRequest = function () {
          $http.post('/api/v1/trips/' + tripId + '/ride_' + self.ride.option, self.ride).then(function (response) {
            alert('Your request was recorded successfully');
            window.location.href = '/';
          });
        };

        console.log("tripId:", tripId, "ride:", self.ride);
      }
    ]
  });
