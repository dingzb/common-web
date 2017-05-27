/**
 * 税务业务管理
 * Created by Neo on 2017/5/9.
 */


angular.module('ws.app').controller('taxAmendmentCtrl', ['$rootScope', '$scope', '$http', function ($rootScope, $scope, $http) {
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
        url: 'app/tax/business/paging/amendment',
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
            field: 'busTime',
            title: '业务时间'
        }, {
            field: 'firstExamine',
            title: '自查意见',
            formatter: function (row) {
                var str = JSON.stringify(row);
                str = str.replace(/"/g, "'");
                if(row.firstExamine){
                    return row.firstExamine.hasIssue ?
                        '<button type="button" class="btn btn-link btn-sm" title="否" onClick="angular.custom.taxBusinessIssueDetail(' + str + ', 1)">否</button>' :
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
                        '<button type="button" class="btn btn-link btn-sm" title="否" onClick="angular.custom.taxBusinessIssueDetail(' + str + ', 2)">否</button>' :
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
                        '<button type="button" class="btn btn-link btn-sm" title="否" onClick="angular.custom.taxBusinessIssueDetail(' + str + ', 3)">否</button>' :
                        '<button type="button" class="btn btn-link btn-sm" title="是" disabled>是</button>';
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
                return '<button type="button" class="btn btn-link btn-sm" title="详情" onClick="angular.custom.taxBusinessDetail(' + str + ')"><span class="glyphicon glyphicon-link"></span></button>';
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

    //================== 整改 =========================
    $scope.showEdit = function () {
        var checkeds = $scope.innerCtrl.getChecked();
        if (checkeds.length !== 1) {
            $scope.alert("只能选择一个进行编辑", 'error');
            return;
        }

        $scope.editObj2 = {categories: [], issues: []};

        $scope.editObj = {
            id: checkeds[0].id,
            taxpayerCode: checkeds[0].taxpayerCode,
            taxpayerName: checkeds[0].taxpayerName,
            categoryTypeId: checkeds[0].categoryTypeId,
            categoryId: checkeds[0].categoryId,
            hasIssue: checkeds[0].hasIssue,
            issueId: checkeds[0].issueId,
            description: checkeds[0].description,
            busTime: checkeds[0].busTime
        };

        getIssue($scope.editObj2.issues);

        $scope.editObj2.categories = [];
        getCategory($scope.editObj.categoryTypeId, $scope.editObj2.categories);
        $("#editModal").modal('show');
    };

    $scope.editHasIssue = function () {
        $scope.editObj.issueId = null;
    };

    $scope.editGetCategory = function (typeId) {
        $.extend($scope.editObj, {
            categoryId: null
        });
        $scope.editObj2.categories = [];
        getCategory(typeId, $scope.editObj2.categories);
    };

    $scope.edit = function () {
        if (!validateEditForm()) {
            return;
        }
        $scope.mask(true, 1);
        $http.post('app/tax/business/edit', $scope.editObj).success(function (data) {
            if (data.success) {
                $scope.innerCtrl.load($scope.datagrid.params);
                $scope.alert(data.message);
                $("#editModal").modal('hide');
            } else {
                $scope.alert(data.message, 'error');
            }
            $scope.mask(false);
        }).error(function () {
            $scope.mask(false);
        });
    };

    //编辑清空
    $scope.editReset = function () {
        $scope.editObj = {};
    };

    //编辑校验
    function validateEditForm() {
        if ($scope.editForm.$invalid) {
            console.info($scope.editForm);
            $scope.editForm.taxpayerCode.$setDirty();
            $scope.editForm.taxpayerName.$setDirty();
            $scope.editForm.categoryTypeId.$setDirty();
            $scope.editForm.categoryId.$setDirty();
            $scope.editForm.description.$setDirty();
            return false;
        }
        return true;
    }

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
            $scope.detailObj = row;
            switch (step) {
                case 1:
                    issues = row.firstExamine.issues;
                    $scope.issueDetail = row.firstExamine;
                    $scope.issueDetail.title = '自查';
                    break;
                case 2:
                    issues = row.secondExamine.issues;
                    $scope.issueDetail = row.secondExamine;
                    $scope.issueDetail.title = '审查';
                    break;
                case 3:
                    issues = row.thirdExamine.issues;
                    $scope.issueDetail = row.thirdExamine;
                    $scope.issueDetail.title = '核查';
            }
        });

        $('#issue_issues').find('input').prop('checked', false);

        if (issues){
            issues.forEach(function (issue) {
                $('#issue_issues').find('input[value=' + issue.id + ']').prop('checked', true);
            });
        }


        $('#issueDetailModal').modal('show');
    };


    //=============== 提交业务 =====================

    $scope.commit = function () {
        var checkeds = $scope.innerCtrl.getChecked();
        if (checkeds.length <= 0) {
            $scope.alert("至少选择一条记录！", 'error');
            return;
        }
        var ids = [];
        for (var i = 0; i < checkeds.length; i++) {
            ids.push(checkeds[i].id);
        }
        $scope.confirm("将要提交" + ids.length + "条记录", function (y) {
            if (!y) {
                return;
            }
            $http.post('app/tax/business/commit/amendment', {
                'ids': ids
            }).success(function (data) {
                if (data.success) {
                    $scope.innerCtrl.load($scope.datagrid.params);
                    $scope.alert(data.message);
                } else
                    $scope.alert(data.message, 'error');
            });
        });
    };
}]);