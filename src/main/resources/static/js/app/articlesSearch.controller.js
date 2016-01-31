angular
    .module('buavto.ArticlesSearchController', [])
    .controller('ArticlesSearchController', function($scope, articlesService) {
        var self = this;

        self.AutoSite = {
            AutoRia: 1,
            Rst: 2,
            Olx: 3,
            AvtoBazar: 4
        }

        $scope.submitForm = function() {
            if ($scope.sitesCheckbox.autoria === 'YES') {
                articlesService.parse(self.AutoSite.AutoRia, $scope.yearFrom, $scope.priceFrom, $scope.priceTo);
            }
            if ($scope.sitesCheckbox.rst === 'YES') {
                articlesService.parse(self.AutoSite.Rst, $scope.yearFrom, $scope.priceFrom, $scope.priceTo);
            }
            if ($scope.sitesCheckbox.olx === 'YES') {
                articlesService.parse(self.AutoSite.Olx, $scope.yearFrom, $scope.priceFrom, $scope.priceTo);
            }
            if ($scope.sitesCheckbox.avtobazar === 'YES') {
                articlesService.parse(self.AutoSite.AvtoBazar, $scope.yearFrom, $scope.priceFrom, $scope.priceTo);
            }
            self.clear();
        };

        self.clear = function() {
            $scope.yearFrom = '';
            $scope.priceFrom = '';
            $scope.priceTo = '';
            $scope.sitesCheckbox = {
                    autoria: '',
                    avtobazar: '',
                    olx: '',
                    rst: ''
            };
        };

        self.clear();
    });
