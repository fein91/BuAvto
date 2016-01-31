angular
    .module('buavto.brandsService', [])
    .service('brandsService', function($http) {
        this.getAllBrands = function() {
            var url = "/brands";
            console.log("get request produced: " + url);
            return $http.get(url);
        }
    });