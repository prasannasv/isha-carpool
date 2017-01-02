'use strict';

angular.
  module('tripDetail').
  component('tripDetail', {
    template: 'TBD: Detail view for <span>{{$ctrl.tripId}}</span>',
    controller: ['$routeParams',
      function TripDetailController($routeParams) {
        this.tripId = $routeParams.tripId;
      }
    ]
  });
