'use strict';

angular.
  module('tripDetail').
  component('tripDetail', {
    templateUrl: 'trip-detail/trip-detail.template.html',
    controller: ['$routeParams', '$http',
      /**
       * Exports the following model information for the given tripId.
       *
       * trip - the Trip object corresponding to the given tripId.
       * offers - all the RideOffer objects corresponding to this trip.
       * requests - all the RideRequest objects corresponding to this trip.
       * matchedOffers - all the OfferRequestMatch objects corresponding to this trip, grouped by RideOffer.
       */
      function TripDetailController($routeParams, $http) {
        var self = this;

        $http.get('/api/v1/trips/' + $routeParams.tripId).then(function(response) {
          console.log("response of trip lookup by id:", $routeParams.tripId, response);
          self.trip = response.data;

          self.trip.departureDateAndTime = new Date(self.trip.departureDateAndTime);
          self.trip.createdOn = new Date(self.trip.createdOn);
        });

        self.offers = [
        ];

        self.requests = [
        ];

        self.matchedOffers = [
        ];
      }
    ]
  });
