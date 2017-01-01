var carpoolApp = angular.module('carpoolApp', [
    'tripList'
]);

carpoolApp.component('greetUser', {
    template: 'Hello, {{$ctrl.user}}!',
    controller: function GreetUserController() {
        this.user = 'world';
    }
});
