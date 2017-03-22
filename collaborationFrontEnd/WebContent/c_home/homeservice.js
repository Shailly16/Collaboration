(function () {
    'use strict';

    angular.module('fuelComparatorApp.homeServices', []).service('sayHelloService', sayHelloService);
    sayHelloService.$inject = ['$http', '$q'];

    function sayHelloService() {
        function sayHi() {
            console.log("hi from home service");
        }
    }
})();