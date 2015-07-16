
(function(){
    var app = angular.module('CustomersApp', []);

    var ASSETS_FOLDER = "assets";

    app.controller('CustomersController', ['$http', '$log', '$scope', function($http, $log, $scope) {

        var self = this;
        var WS_URL = "http://localhost:9000/";

        var customers = [];

        this.initialize = function(){
            this.getCustomers();
        }

        this.addCustomer = function(customer){

            $log.debug("Scope - ", $scope);
            $log.debug("customer - ", customer);

            var url = '/customer';

            $http({
                method: 'POST',
                url: url,
                data: customer,
                headers: {'Content-Type': 'application/json; charset=UTF-8'}
            }).
                success(function(data, status, headers, config) {
                    $log.debug("success!");
                    self.customers = data;
                }).
                error(function(data, status, headers, config) {
                    $log.error("error", data);
                });
        }


        this.getCustomers = function(){
            $log.debug("Getting customers ..");

            this.customers = [];

            var url = '/customers/company';


            $http.get(url).
                success(function(data, status, headers, config) {
                    $log.debug("success!");
                    self.customers = data;
                }).
                error(function(data, status, headers, config) {
                    $log.error("error", data);
                });
        };


        this.initialize();

    }]);


    app.directive('customerPartial', function () {
        return {
            restrict: 'E',
            templateUrl: ASSETS_FOLDER + '/angular/partials/customer_partial.html'
        };
    })

    app.directive('customers', function () {
        return {
            restrict: 'E',
            templateUrl: ASSETS_FOLDER + '/angular/partials/customers.html'
        };
    })

})();