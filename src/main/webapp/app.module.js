'use strict';

var carpoolApp = angular.module('carpoolApp', [
    'ngRoute',
    'tripDetail',
    'tripList'
]);

carpoolApp.component('greetUser', {
    template: 'Hello, {{$ctrl.user}}!',
    controller: function GreetUserController() {
        this.user = 'world';
    }
});
