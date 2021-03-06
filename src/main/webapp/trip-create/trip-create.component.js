'use strict';

angular.
  module('tripCreate').
  component('tripCreate', {
    templateUrl: 'trip-create/trip-create.template.html',
    controller: ['$http',
      function TripCreateController($http) {
        var self = this;

        self.createTrip = function (trip) {
          $http.post('/api/v1/trips', trip).then(function (response) {
            console.log("new trip's id:", response);
            window.location.href = '/';
          });
        };
      }
    ]
  });
