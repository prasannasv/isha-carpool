'use strict';

angular.
  module('tripList').
  component('tripList', {
    templateUrl: 'trip-list/trip-list.template.html',
    controller: function TripListController() {
        this.trips = [
            {
                from: 'BNA',
                to: 'iii',
                departureDateAndTime: new Date()
            },
            {
                from: 'iii',
                to: 'BNA',
                departureDateAndTime: new Date()
            }
        ];
    }
});
