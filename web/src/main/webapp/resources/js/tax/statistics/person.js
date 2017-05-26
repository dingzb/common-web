/**
 * 统计个人录入业务情况
 */

angular.module('ws.app').controller('taxPersonCtrl', ['$rootScope', '$scope', '$http', function ($rootScope, $scope, $http) {
    $scope.searchParams = {};
    //日期控件初始化
    $scope.initDtp = function (e) {
        console.info(e);
        $scope.datetimepicker('#createTimeStart').onChange(function (d) {
            $scope.searchParams.startCreate = d;
        });
        $scope.datetimepicker('#createTimeEnd').onChange(function (d) {
            $scope.searchParams.endCreate = d;
        });
    };

    $scope.person = function () {

        $http.post('app/tax/statistics/person', {
            // agencyIdsStr: agencyIdStr,
            startCreate: $scope.searchParams.startCreate,
            endCreate: $scope.searchParams.endCreate
        }).success(function (data) {
            if (data.success) {
                var rrrs = data.data;
                var recs = [];
                rrrs.forEach(function (rrs) {

                        var categoryTypeName = rrs.name;
                        var ctSpan = {i: 0};
                    rrs.recs.forEach(function (r) {
                            var rec = $.extend({
                                categoryTypeName: categoryTypeName,
                                ctSpan: ctSpan
                            }, r);
                            ctSpan.i = ctSpan.i + 1;
                            recs.push(rec);
                            categoryTypeName = false;
                        });
                    });
                $scope.recs = recs;
                console.info(recs);
            } else if (data.message) {
                $scope.alert(data.message, 'error');
            }
        }).error(function (data) {
            $scope.alert(data, 'error');
        });
    };

}]);
