angular
    .module('buavto.HomeController', [])
    .controller('HomeController', function($scope, articlesService, NgTableParams) {
        var self = this;

        self.init = function() {
            return articlesService.getAll()
                .then(function successCallback(response){
                    self.tableParams = new NgTableParams({}, {
                        //filterOptions: { filterFn: priceRangeFilter },
                        dataset: response.data
                    });
                    console.log('init brands from db');
                }, function errorCallback(response) {
                    console.log('got ' + response.status + ' error');
                });
        };

        self.priceFilterDef = {
            start: {
                id: 'number',
                placeholder: 'Start'
            },
            end: {
                id: 'number',
                placeholder: 'End'
            }
        };

        function priceRangeFilter(data, filterValues/*, comparator*/){
            return data.filter(function(article){
                var start = filterValues.start == null ? Number.MIN_VALUE : filterValues.start;
                var end = filterValues.end == null ? Number.MAX_VALUE : filterValues.end;
                return start <= article.price && end >= article.price;
            });
        }

        self.init();
    });