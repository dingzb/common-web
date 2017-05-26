/**
 * 统计县局内所有业务情况
 */

angular.module('ws.app').controller('taxXianjuCtrl', ['$rootScope', '$scope', '$http', function ($rootScope, $scope, $http) {
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

    $http.post('app/tax/agency/list', {}).success(function (data) {
        if (data.success) {
            $scope.searchParams.agencies = data.data;
        } else if (data.message) {
            $scope.alert(data.message, 'error');
        }
    }).error(function (data) {
        $scope.alert(data, 'error');
    });

    $scope.statement = function () {

        userIds = [];
        var ags = $('#agencies').find('input');
        $.each(ags, function (i) {
            var ag = $(ags[i]);
            if (ag.is(':checked')) {
                userIds.push(ag.attr('id'));
            }
        });

        var agencyIdStr = '';

        userIds.forEach(function (aId) {
            agencyIdStr += (aId + ',');
        });

        agencyIdStr = agencyIdStr.substring(0, agencyIdStr.length - 1);

        $http.post('app/tax/statistics/xianju', {
            agencyIdsStr: agencyIdStr,
            startCreate: $scope.searchParams.startCreate,
            endCreate: $scope.searchParams.endCreate
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
                            aSpan.i = aSpan.i + 1;
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
