/**
 * 税务业务管理
 * Created by Neo on 2017/5/9.
 */


angular.module('ws.app').controller('taxManageCtrl', ['$rootScope', '$scope', '$http', function ($rootScope, $scope, $http) {
    $scope.searchParams = {};
    $scope.categories = [];
    $scope.addObj = {};
    $scope.editObj = {};

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

    function getIssue(target) {
        $http.post('app/tax/business/issue/list', {}).success(function (data) {
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

    //初始化组列表
    $scope.datagrid = {
        url: 'app/tax/business/paging',
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
        }
            // , {
            //     field: 'hasIssue',
            //     title: '是否存在问题',
            //     formatter: function (val) {
            //         return val ? '是' : '否';
            //     }
            // }, {
            //     field: 'issueName',
            //     title: '问题种类'
            // }
            , {
                field: 'agencyName',
                title: '主管税务机关'
            }, {
                field: 'createName',
                title: '税收管理员'
            }, {
                field: 'createTime',
                title: '创建时间'
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

    //=======================添加================================
    $scope.showAdd = function () {

        $scope.addObj = {
            issueId: null
        };
        $scope.addObj2 = {
            issues: [],
            categories: []
        };

        getIssue($scope.addObj2.issues);
        $scope.addForm.$setPristine();
        $("#addModal").modal('show');
    };

    $scope.addHasIssue = function () {
        $scope.addObj.issueId = null;
    };

    $scope.addGetCategory = function (typeId) {
        $.extend($scope.addObj, {
            categoryId: null
        });
        $scope.addObj2.categories = [];
        getCategory(typeId, $scope.addObj2.categories);
    };

    $scope.add = function () {
        if (!validateAddForm()) {
            return;
        }


        $scope.mask(true, 1);
        $http.post('app/tax/business/add', $scope.addObj).success(function (data) {
            if (data.success) {
                $scope.innerCtrl.load($scope.datagrid.params);
                $scope.alert(data.message);
                $("#addModal").modal('hide');
            } else {
                $scope.alert(data.message, 'error');
            }
            $scope.mask(false);
        }).error(function () {
            $scope.mask(false);
        });
    };

    //添加清空
    $scope.addReset = function () {
        $scope.addObj = {};
    };
    //添加校验
    function validateAddForm() {
        if ($scope.addForm.$invalid) {
            console.info($scope.addForm);
            $scope.addForm.taxpayerCode.$setDirty();
            $scope.addForm.taxpayerName.$setDirty();
            $scope.addForm.categoryTypeId.$setDirty();
            $scope.addForm.categoryId.$setDirty();
            $scope.addForm.description.$setDirty();
            return false;
        }
        return true;
    }

    //==================编辑=========================
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

    //=================================删除====================================
    $scope.del = function () {
        var checkeds = $scope.innerCtrl.getChecked();
        if (checkeds.length <= 0) {
            $scope.alert("至少选择一条记录！", 'error');
            return;
        }
        var ids = [];
        for (var i = 0; i < checkeds.length; i++) {
            ids.push(checkeds[i].id);
        }
        $scope.confirm("将要删除" + ids.length + "条记录", function (y) {
            if (!y) {
                return;
            }
            $http.post('app/tax/business/del', {
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

    //=========详情===================
    angular.custom.taxBusinessDetail = function (row) {
        $scope.$apply(function () {
            $scope.detailObj = row;
        });
        $("#detailModal").modal('show');
    }
}]);