/**
 * 税务业务管理
 * Created by Neo on 2017/5/9.
 */


angular.module('ws.app').controller('taxThirdCtrl', ['$rootScope', '$scope', '$http', function ($rootScope, $scope, $http) {
    $scope.searchParams = {};
    $scope.categories = [];
    $scope.addObj = {};
    $scope.editObj = {};

    $scope.hasIssue = false;
    $scope.issues = [];

    getIssue();

    $scope.initDtp = function (e) {
        $scope.datetimepicker('#add_time').onChange(function (d) {
            $scope.addObj.busTime = d;
        });
        $scope.datetimepicker('#edit_time').onChange(function (d) {
            $scope.editObj.busTime = d;
        });
    };

    $http.post('app/tax/business/category/type/list', {}).success(function (data) {
        if (data.success) {
            $scope.categoryTypes = data.data;
        } else if (data.message) {
            $scope.alert(data.message, 'error');
        }
    }).error(function (data) {
        $scope.alert(data, 'error');
    });

    function getCategory(typeId, target) {
        console.info(typeId, target)
        $http.post('app/tax/business/category/list', {
            typeId: typeId
        }).success(function (data) {
            if (data.success) {
                $.extend(target, data.data);
            } else if (data.message) {
                $scope.alert(data.message, 'error');
            }
            console.info(target);
        }).error(function (data) {
            $scope.alert(data, 'error');
        });
    }

    function getIssue() {
        $http.post('app/tax/business/issue/list', {}).success(function (data) {
            if (data.success) {
                $.extend($scope.issues, data.data);
            } else if (data.message) {
                $scope.alert(data.message, 'error');
            }
        }).error(function (data) {
            $scope.alert(data, 'error');
        });
    }

    //初始化组列表
    $scope.datagrid = {
        url: 'app/tax/examine/paging/third',
        method: 'post',
        params: {},
        columns: [{
            field: 'taxpayerCode',
            title: '纳税人识别号'
        }, {
            field: 'taxpayerName',
            title: '纳税人名称'
        }, {
            field: 'categoryTypeName',
            title: '业务类型'
        }, {
            field: 'categoryName',
            title: '业务项目'
        }, {
            field: 'agencyName',
            title: '主管税务机关'
        }, {
            field: 'createName',
            title: '税收管理员'
        }, {
            field: 'firstExamine',
            title: '自查意见',
            formatter: function (row) {
                var str = JSON.stringify(row);
                str = str.replace(/"/g, "'");
                if(row.firstExamine){
                    return row.firstExamine.hasIssue ?
                        '<button type="button" class="btn btn-link btn-sm" title="否" onClick="angular.custom.taxBusinessFirstDetail(' + str + ')">否</button>' :
                        '<button type="button" class="btn btn-link btn-sm" title="是" disabled>是</button>';
                } else {
                    return '';
                }
            }
        }, {
            field: 'secondExamine',
            title: '审查意见',
            formatter: function (row) {
                var str = JSON.stringify(row);
                str = str.replace(/"/g, "'");
                if(row.secondExamine){
                    return row.secondExamine.hasIssue ?
                        '<button type="button" class="btn btn-link btn-sm" title="否" onClick="angular.custom.taxBusinessSecondDetail(' + str + ')">否</button>' :
                        '<button type="button" class="btn btn-link btn-sm" title="是" disabled>是</button>';
                } else {
                    return '';
                }
            }
        }, {
            field: 'thirdExamine',
            title: '核查意见',
            formatter: function (row) {
                var str = JSON.stringify(row);
                str = str.replace(/"/g, "'");
                if(row.thirdExamine){
                    return row.thirdExamine.hasIssue ?
                        '<button type="button" class="btn btn-link btn-sm" title="否" onClick="angular.custom.taxBusinessThirdDetail(' + str + ')">否</button>' :
                        '<button type="button" class="btn btn-link btn-sm" title="是" disabled>是</button>';
                } else {
                    return '';
                }
            }
        }, {
            field: 'createTime',
            title: '创建时间'
        }, {
            field: 'id',
            title: '操作',
            formatter: function (row) {
                var str = JSON.stringify(row);
                str = str.replace(/"/g, "'");
                return '<button type="button" class="btn btn-link btn-sm" title="核查" onClick="angular.custom.taxBusinessThird(' + str + ')">核查</button>';
            }
        }],
        checkbox: true,
        sizes: [10, 20, 50, 80],
        pageSize: 10
    };

    //查询
    $scope.search = function () {
        $scope.innerCtrl.load($.extend($scope.datagrid.params, $scope.searchParams));
    };

    //清空
    $scope.resetSearch = function () {
        var clearSearch = {
            taxpayerName: ''
        };
        $.extend($scope.datagrid.params, clearSearch);
        $.extend($scope.searchParams, clearSearch);
    };

    //========= 核查 =================
    angular.custom.taxBusinessThird = function (row) {
        $scope.$apply(function () {
            $scope.detailObj = row;
            $scope.isThird = row.thirdExamine !== null;
            $scope.hasIssue = '0';
            console.info($scope.hasIssue)
        });
        $("#thirdModal").modal('show');
    };

    angular.custom.taxBusinessFirstDetail = function (row) {
        alert(row.firstExamine.issues[0].name);
    };

    //=============== 提交核查 =====================

    $scope.commitThird = function (row) {
        console.info($scope.detailObj);
        var thirdData = {
            busId: $scope.detailObj.id,
            hasIssue: $scope.hasIssue
        };

        if ($scope.hasIssue) {
            var issues = $('#issues').find('input');
            var issuesStr = '';
            $.each(issues, function (i) {
                issuesStr = $(issues[i]).val() + ',';
            });
            issuesStr = issuesStr.substr(0, issuesStr.length - 1);

            thirdData = $.extend(thirdData, {
                issueIdStrs: issuesStr,
                description: $scope.issueDesc
            });
        }

        $http.post('app/tax/examine/third/commit', thirdData).success(function (data) {
            if (data.success) {
                $scope.innerCtrl.load($scope.datagrid.params);
                $scope.alert(data.message);
            } else
                $scope.alert(data.message, 'error');
        });
    };

}]);