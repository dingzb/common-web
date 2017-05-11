/**
 * Created by Neo on 2017/5/11.
 * 报表形式统计
 */

angular.module('ws.app').controller('taxStatementCtrl', ['$rootScope', '$scope', '$http', function ($rootScope, $scope, $http) {
    $scope.searchParams = {};
    //日期控件初始化
    $scope.initDtp = function (e) {
        console.info(e);
        $scope.datetimepicker('#createTimeStart').onChange(function (d) {
            $scope.searchParams.createTimeStart = d;
        });
        $scope.datetimepicker('#createTimeEnd').onChange(function (d) {
            $scope.searchParams.createTimeEnd = d;
        });
    };

    $scope.statement = function (agencyIds) {

        agencyIds = ['BE3F79B002C2428994E63926C384A791'];

        var agencyIdStr = '';

        agencyIds.forEach(function (aId) {
            agencyIdStr += (aId + ',');
        });

        agencyIdStr = agencyIdStr.substring(0, agencyIdStr.length - 1);

        $http.post('http://localhost/app/tax/statistics/statement', {
            agencyIdsStr: agencyIdStr
        }).success(function (data) {
            if (data.success) {
                var rrrs = data.data;
                var recs = [];
                rrrs.forEach(function (rrs) {
                    var agencyName = rrs.agencyName;
                    var aSpan = {i: 0};
                    rrs.recs.forEach(function (rs) {
                        var categoryTypeName = rs.name;
                        var ctSpan = {i: 0};
                        rs.recs.forEach(function (r) {
                            var rec = $.extend({
                                agencyName: agencyName,
                                categoryTypeName: categoryTypeName,
                                aSpan: aSpan,
                                ctSpan: ctSpan
                            }, r);
                            aSpan.i = aSpan.i +1;
                            ctSpan.i = ctSpan.i + 1;
                            recs.push(rec);
                            agencyName = false;
                            categoryTypeName = false;
                        });
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
