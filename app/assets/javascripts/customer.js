
(function(){
    var app = angular.module('CustomerApp', ['CustomersApp', 'CheckoutApp']);

    var ASSETS_FOLDER = "assets";

    app.controller('CustomerController', ['$http', '$log', '$scope', function($http, $log, $scope) {

        var self = this;
        var WS_URL = "http://localhost:9000/";

        var customer;

        this.initialize = function(){
        }



        this.getCustomer = function(id){
            $log.debug("Getting customer ..");

            this.customers = [];

            var url = '/customer/'+ id;


            $http.get(url).
                success(function(data, status, headers, config) {
                    $log.debug("success!");
                    self.customer = data;
                }).
                error(function(data, status, headers, config) {
                    $log.error("error", data);
                });
        };


        this.initialize();

    }]);


})();