angular
    .module('buavto.articlesService', [])
    .service('articlesService', function($http) {
        this.getAll = function() {
            var url = "/articles";
            console.log("get request produced: " + url);
            return $http.get(url);
        }

        this.parse = function(autoSiteId, yearFrom, priceFrom, priceTo) {
            var url = "/articles/parse?autoSiteId="+autoSiteId+"&yearFrom="+yearFrom+"&priceFrom="+priceFrom+"&priceTo="+priceTo;
            console.log("post request produced: " + url);
            return $http.post(url);
        }

    });