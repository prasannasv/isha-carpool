'use strict';

angular.
  module('carpoolApp').
  config(['$locationProvider', '$routeProvider',
    function config($locationProvider, $routeProvider) {
      $locationProvider.hashPrefix('!');

      $routeProvider.
        when('/trips', {
          template: '<trip-list></trip-list>'
        }).
        when('/trips/:tripId', {
          template: '<trip-detail></trip-detail>'
        }).
        when('/trip_create', {
          template: '<trip-create></trip-create>'
        }).
        otherwise('/trips');
    }
  ]);
