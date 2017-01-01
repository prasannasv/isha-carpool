'use strict';

alert('tripList module in component', angular.module('tripList'));
console.log('tripList module in component', angular.module('tripList'));

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
console.log("tripList component registered", angular.module('tripList').component('tripList'));
