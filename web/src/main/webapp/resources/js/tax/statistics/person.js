/**
 * 统计个人录入业务情况
 */

angular.module('ws.app').controller('taxPersonCtrl', ['$rootScope', '$scope', '$http', function ($rootScope, $scope, $http) {

    $scope.searchParams = {};
    $scope.issues = {};
    $scope.issueDetailSearchParams = {};

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

    $http.post('app/tax/business/issue/list', {}).success(function (data) {
        if (data.success) {
            $.extend($scope.issues, data.data);
        } else if (data.message) {
            $scope.alert(data.message, 'error');
        }
    }).error(function (data) {
        $scope.alert(data, 'error');
    });

    $scope.person = function () {

        $http.post('app/tax/statistics/person', {
            // agencyIdsStr: agencyIdStr,
            busTimeStart: $scope.searchParams.busTimeStart,
            busTimeEnd: $scope.searchParams.busTimeEnd
        }).success(function (data) {
            if (data.success) {
                var rrrs = data.data;
                var recs = [];
                rrrs.forEach(function (rrs) {
                        var categoryTypeName = rrs.name;
                        var categoryTypeId = rrs.id;
                        var ctSpan = {i: 0};
                    rrs.recs.forEach(function (r) {
                            var rec = $.extend({
                                categoryTypeName: categoryTypeName,
                                categoryTypeId: categoryTypeId,
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



    var issueDetailParams = {};
    $scope.issueDetail = function (categoryTypeId, categoryId, issueStep) {

        issueDetailParams = {};
        var hasIssueDetail = {
            firstHasIssue: undefined,
            secondHasIssue: undefined,
            thirdHasIssue: undefined,
            amendmentIssue: undefined
        };

        var hasIssue = undefined;

        if (issueStep === 1) {
            hasIssueDetail.firstHasIssue = true;
            $scope.issueDetail.title = '自控有问题';
        } else if (issueStep === 2) {
            hasIssueDetail.secondHasIssue = true;
            $scope.issueDetail.title = '防控有问题';
        } else if (issueStep === 3) {
            hasIssueDetail.thirdHasIssue = true;
            $scope.issueDetail.title = '监控有问题';
        } else if (issueStep === 4) {
            hasIssue = true;
            $scope.issueDetail.title = '有问题';
        } else if (issueStep === 5) {
            hasIssueDetail.amendmentIssue = true;
            $scope.issueDetail.title = '已经整改问题';
        }

        $scope.innerCtrl.load($.extend(issueDetailParams, {
            createUserId: $scope.user.id,
            categoryTypeId: categoryTypeId,
            categoryId: categoryId,
            hasIssue: hasIssue
        }, hasIssueDetail, $scope.searchParams));
        $('#issueDetailModal').modal('show');
    };



    //查询
    $scope.search = function () {
        console.info($scope.issueDetailSearchParams);
        $scope.innerCtrl.load($.extend({}, issueDetailParams, $scope.issueDetailSearchParams));
    };

    //清空
    $scope.resetSearch = function () {
        var clearSearch = {
            taxpayerName: ''
        };
        $.extend($scope.datagrid.params, clearSearch);
        $.extend($scope.issueDetailSearchParams, clearSearch);
    };

    //初始化组列表
    $scope.datagrid = {
        queryOnLoad: false,
        url: 'app/tax/business/paging/error',
        method: 'post',
        params: {},
        columns: [{
            field: 'taxpayerCode',
            title: '纳税人识别号'
        }, {
            field: 'taxpayerName',
            title: '纳税人名称'
        }, {
            field: 'createName',
            title: '税收管理员'
        }, {
            field: 'busTime',
            title: '业务时间'
        }, {
            field: 'status',
            title: '状态',
            translator: function (row) {
                switch (row.status) {
                    case 0:
                        return '待提交';
                    case 1:
                        return '待自查';
                    case 2:
                        return '待审查';
                    case 3:
                        return '待核查';
                    case 4:
                        return '待整改';
                    case 5:
                        return '完成';
                }
            }
        }, {
            field: 'firstHasIssue',
            title: '自查意见',
            formatter: function (row) {
                var str = JSON.stringify(row);
                str = str.replace(/"/g, "'");
                if (row.firstHasIssue !== null) {
                    return row.firstHasIssue ?
                        '<button type="button" class="btn btn-link btn-sm" title="有问题" onClick="angular.custom.taxBusinessIssueDetail(' + str + ', 1)">有问题</button>' :
                        '<button type="button" class="btn btn-link btn-sm" title="没问题" disabled>没问题</button>';
                } else {
                    return '';
                }
            }
        }, {
            field: 'secondHasIssue',
            title: '审查意见',
            formatter: function (row) {
                var str = JSON.stringify(row);
                str = str.replace(/"/g, "'");
                if (row.secondHasIssue !== null) {
                    return row.secondHasIssue ?
                        '<button type="button" class="btn btn-link btn-sm" title="有问题" onClick="angular.custom.taxBusinessIssueDetail(' + str + ', 2)">有问题</button>' :
                        '<button type="button" class="btn btn-link btn-sm" title="没问题" disabled>没问题</button>';
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
                if (row.thirdHasIssue !== null) {
                    return row.thirdHasIssue ?
                        '<button type="button" class="btn btn-link btn-sm" title="有问题" onClick="angular.custom.taxBusinessIssueDetail(' + str + ', 3)">有问题</button>' :
                        '<button type="button" class="btn btn-link btn-sm" title="没问题" disabled>没问题</button>';
                } else {
                    return '';
                }
            }
        }, {
            field: 'status',
            title: '整改状态',
            translator: function (row) {
                if (row['firstHasIssue'] || row['secondHasIssue'] || row['thirdHasIssue']) {
                    return row['status'] === 5 ? '已整改' : '未整改';
                } else {
                    return '';
                }
            }
        }, {
            field: 'id',
            title: '操作',
            formatter: function (row) {
                var str = JSON.stringify(row);
                str = str.replace(/"/g, "'");
                return "<button type=\"button\" class=\"btn btn-link btn-sm\" title='详情' onClick=\"angular.custom.taxBusinessDetail(" + str + ")\"><span class=\"glyphicon glyphicon-link\" > </span></button>";
            }
        }],
        checkbox: true,
        sizes: [10, 20, 50, 80],
        pageSize: 10
    };

    //=========详情===================
    angular.custom.taxBusinessDetail = function (row) {
        $scope.$apply(function () {
            $scope.detailObj = row;
        });
        $("#detailModal").modal('show');
    };

    angular.custom.taxBusinessIssueDetail = function (row, step) {
        var issues = null;

        $scope.$apply(function () {

            $http.post('app/tax/business/examine/detail', {
                busId: row.id,
                step: step
            }).success(function (data) {
                if (data.success) {
                    $scope.examineDetail = data.data;
                    switch (step) {
                        case 1:
                            $scope.examineDetail.title = '自查';
                            break;
                        case 2:
                            $scope.examineDetail.title = '审查';
                            break;
                        case 3:
                            $scope.examineDetail.title = '核查';
                    }
                } else {
                    $scope.alert(data.message, 'error');
                }

                $('#issue_issues').find('input').prop('checked', false);

                if ($scope.examineDetail.issues){
                    $scope.examineDetail.issues.forEach(function (issue) {
                        $('#issue_issues').find('input[value=' + issue.id + ']').prop('checked', true);
                    });
                }

                console.info($scope.examineDetail);
                console.info($scope.issues);
            });

            $('#examineDetailModal').modal('show');
        });

    };



}]);
