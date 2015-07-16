(function(){
    var app = angular.module('CheckoutApp', []);

    var ASSETS_FOLDER = "assets";

    app.controller('CheckoutController', ['$http', '$log', '$scope', function($http, $log, $scope) {

        var self = this

        this.countries = [
            {'name': 'United States', 'id':1},
            {'name':  'Netherlands', 'id':2}
        ]

        this.products = [
            {'name': 'eleot', 'id':1},
            {'name': 'survey', 'id':2}
        ]

        var SUCCESS_MESSAGE = "Transaction has been successfully created at Braintree"

        this.checkout = function(form_data){
            $log.debug("form_data", form_data);

            var validateResult = this.validateFormData(form_data)

            if(validateResult){
                this.setErrorMessage(validateResult)
            }
            else {
                var url = '/checkout/token';

                this.disablePay()

                $http.get(url).
                    success(function(data, status, headers, config) {
                        $log.debug("success!", data);
                        self.chargeCard(data, form_data);
                    }).
                    error(function(data, status, headers, config) {
                        $log.error("error", data);
                    });
            }


        }


        this.validateFormData = function(form_data){
            var errorMessage = "Please complete required fields"
            var result = null;

            this.setErrorMessage("")

            if(!form_data.customer || !form_data.product || !form_data.card) {
                result = errorMessage
            }
            else {
                if(!form_data.customer.firstName || !form_data.customer.lastName || !form_data.customer.number
                || !form_data.card.number || !form_data.card.expirationDate  || !form_data.card.country
                || !form_data.card.amount ) {
                    result = errorMessage
                }

            }

            return result
        }

        this.chargeCard =function(ctoken, form_data){

            $log.debug("Charging card ... ", form_data.card);

            var braintreeClient = new braintree.api.Client({clientToken: ctoken});

            braintreeClient.tokenizeCard({
                number: form_data.card.number,
                cardholderName: form_data.card.cardholderName,
                expirationDate: form_data.card.expirationDate
            }, function (err, nonce) {
                self.pay(nonce, form_data);
            });
        }


        this.pay = function(nonce, form_data){
            $log.debug("nonce", nonce);
            var url = '/checkout/pay';

            var data = {
                'nonce': nonce,
                'amount': form_data.card.amount,
                'country':form_data.card.country,
                'customer':form_data.customer,
                'product':form_data.product
            }

            $http({
                method: 'POST',
                url: url,
                data: data,
                headers: {'Content-Type': 'application/json; charset=UTF-8'}
            }).
                success(function(data, status, headers, config) {
                    self.handlePayResponse(data)
                }).
                error(function(data, status, headers, config) {
                    self.enablePay()
                    $log.error("error", data);
                });
        }



        this.disablePay = function(){
            angular.element(".btn").attr('disabled', true);
        }

        this.enablePay = function(){
            angular.element(".btn").attr('disabled', false);
        }



        this.setSuccessMessage =  function(){
            $scope.successMessage = SUCCESS_MESSAGE
            $scope.errorMessage = ""
            this.showSuccess()
        }

        this.setErrorMessage =  function(error){
            $scope.successMessage = ""
            $scope.errorMessage = error
            $scope.screen = 'checkout'
        }

        this.handlePayResponse = function(data){
            this.enablePay()
            $log.debug("response", data);
            if (data.success == true){
                this.setSuccessMessage()
            }
            else {
                this.setErrorMessage(data.message)
            }
        }

        this.showCheckout = function(){
            this.initialize()
        }

        this.showSuccess = function(){
            $scope.screen = 'success'
        }



        this.initialize = function(){
            if($scope.form_data){
                if($scope.form_data.customer) {
                    $scope.form_data.customer = ""
                }
                if($scope.form_data.card) {
                    if($scope.form_data.card.amount) {
                        $scope.form_data.card.amount = ""
                    }
                }
            }
            $scope.screen = 'checkout'
        }

        this.initialize();
    }]);




    app.directive('checkoutForm', function () {
        return {
            restrict: 'E',
            templateUrl: ASSETS_FOLDER + '/angular/partials/checkout_form.html'
        };
    })

    app.directive('checkoutSuccess', function () {
        return {
            restrict: 'E',
            templateUrl: ASSETS_FOLDER + '/angular/partials/checkout_success.html'
        };
    })

    app.directive('testCreditCards', function () {
        return {
            restrict: 'E',
            templateUrl: ASSETS_FOLDER + '/angular/partials/test_credit_cards.html'
        };
    })

})();