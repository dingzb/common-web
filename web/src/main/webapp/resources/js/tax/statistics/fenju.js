/**
 * 统计分局内业务录入情况
 */

angular.module('ws.app').controller('taxFenjuCtrl', ['$rootScope', '$scope', '$http', function ($rootScope, $scope, $http) {
    $scope.searchParams = {};
    //日期控件初始化
    $scope.initDtp = function (e) {
        console.info(e);
        $scope.datetimepicker('#busTimeStart').onChange(function (d) {
            $scope.searchParams.busTimeStart = d;
        });
        $scope.datetimepicker('#busTimeEnd').onChange(function (d) {
            $scope.searchParams.busTimeEnd = d;
        });
    };

    $http.post('app/tax/agency/user/list', {}).success(function (data) {
        if (data.success) {
            $scope.searchParams.users = data.data;
        } else if (data.message) {
            $scope.alert(data.message, 'error');
        }
    }).error(function (data) {
        $scope.alert(data, 'error');
    });

    $http.post('app/tax/agency/current', {}).success(function (data) {
        if (data.success) {
            $scope.agency = data.data;
        } else if (data.message) {
            $scope.alert(data.message, 'error');
        }
    }).error(function (data) {
        $scope.alert(data, 'error');
    });

    $scope.fenjuStatistics = function () {

        var userIds = [];
        var ags = $('#users').find('input');
        $.each(ags, function (i) {
            var ag = $(ags[i]);
            if (ag.is(':checked')) {
                userIds.push(ag.attr('id'));
            }
        });

        var userIdsStr = '';

        userIds.forEach(function (aId) {
            userIdsStr += (aId + ',');
        });

        userIdsStr = userIdsStr.substring(0, userIdsStr.length - 1);

        $http.post('app/tax/statistics/fenju', {
            userIdsStr: userIdsStr,
            busTimeStart: $scope.searchParams.busTimeStart,
            busTimeEnd: $scope.searchParams.busTimeEnd
        }).success(function (data) {
            if (data.success) {
                var rrrs = data.data;
                var recs = [];
                rrrs.forEach(function (rrs) {
                    var userName = rrs.userName;
                    var aSpan = {i: 0};
                    rrs.recs.forEach(function (rs) {
                        var categoryTypeName = rs.name;
                        var ctSpan = {i: 0};
                        rs.recs.forEach(function (r) {
                            var rec = $.extend({
                                userName: userName,
                                categoryTypeName: categoryTypeName,
                                aSpan: aSpan,
                                ctSpan: ctSpan
                            }, r);
                            aSpan.i = aSpan.i + 1;
                            ctSpan.i = ctSpan.i + 1;
                            recs.push(rec);
                            userName = false;
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
