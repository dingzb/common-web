/**
 * 统计县局内所有业务情况
 */

angular.module('ws.app').controller('taxXianjuCtrl', ['$rootScope', '$scope', '$http', function ($rootScope, $scope, $http) {
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

    $http.post('app/tax/agency/list', {}).success(function (data) {
        if (data.success) {
            $scope.agencies = data.data;
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
            busTimeStart: $scope.searchParams.busTimeStart,
            busTimeEnd: $scope.searchParams.busTimeEnd
        }).success(function (data) {
            if (data.success) {
                var rrrs = data.data;
                var recs = [];
                rrrs.forEach(function (rrs) {
                    var agencyName = rrs.agencyName;
                    var agencyId = rrs.agencyId;
                    var aSpan = {i: 0};
                    rrs.recs.forEach(function (rs) {
                        var categoryTypeName = rs.name;
                        var categoryTypeId = rs.id;
                        var ctSpan = {i: 0};
                        rs.recs.forEach(function (r) {
                            var rec = $.extend({
                                agencyName: agencyName,
                                agencyId: agencyId,
                                categoryTypeName: categoryTypeName,
                                categoryTypeId: categoryTypeId,
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


    $scope.issueDetail = function (agencyId, categoryTypeId, categoryId, issueStep) {

        var hasIssueDetail = {
            firstHasIssue: undefined,
            secondHasIssue: undefined,
            thirdHasIssue: undefined
        };

        var hasIssue = undefined;

        if (issueStep === 1) {
            hasIssue.firstHasIssue = true;
        } else if (issueStep === 2) {
            hasIssue.secondHasIssue = true;
        } else if (issueStep === 3) {
            hasIssue.thirdHasIssue = true;
        } else if (issueStep === 4) {
            hasIssue = true;
        }

        $scope.innerCtrl.load($.extend({
            agencyId: agencyId,
            categoryTypeId: categoryTypeId,
            categoryId: categoryId,
            hasIssue: hasIssue
        }, hasIssueDetail, $scope.searchParams));
        $('#issueDetailModal').modal('show');
    };

    //初始化组列表
    $scope.datagrid = {
        queryOnLoad: false,
        url: 'app/tax/business/paging/all',
        method: 'post',
        params: {},
        columns: [{
            field: 'taxpayerCode',
            title: '纳税人识别号'
        }, {
            field: 'taxpayerName',
            title: '纳税人名称'
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
            field: 'firstExamine',
            title: '自查意见',
            formatter: function (row) {
                var str = JSON.stringify(row);
                str = str.replace(/"/g, "'");
                if (row.firstExamine) {
                    return row.firstExamine.hasIssue ?
                        '<button type="button" class="btn btn-link btn-sm" title="有问题" onClick="angular.custom.taxBusinessIssueDetail(' + str + ', 1)">有问题</button>' :
                        '<button type="button" class="btn btn-link btn-sm" title="没问题" disabled>没问题</button>';
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
                if (row.secondExamine) {
                    return row.secondExamine.hasIssue ?
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
                if (row.thirdExamine) {
                    return row.thirdExamine.hasIssue ?
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

}]);
