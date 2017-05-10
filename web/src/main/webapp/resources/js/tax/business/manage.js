/**
 * 税务业务管理
 * Created by Neo on 2017/5/9.
 */


angular.module('ws.app').controller('taxManageCtrl', ['$rootScope', '$scope', '$http', function ($rootScope, $scope, $http) {
    $scope.searchParams = {};

    $http.post('app/system/group/types', {}).success(function (data) {
        if (data.success) {
            $scope.types = [];
            data.data.forEach(function (val) {
                switch (val) {
                    case 'ADMIN':
                        $scope.types.push({
                            id: val,
                            name: '管理员'
                        });
                        break;
                    case 'NORMAL':
                        $scope.types.push({
                            id: val,
                            name: '普通'
                        });
                        break;
                    default :
                        $scope.types.push({
                            id: 'unknown',
                            name: '未知'
                        });
                }
            });
        } else if (data.message) {
            $scope.alert(data.message, 'error');
        }
    }).error(function (data) {
        $scope.alert(data, 'error');
    });


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
                return "<button type=\"button\" class=\"btn btn-link btn-sm\" title='详情' onClick=\"angular.custom.onView(" + str + ")\"><span class=\"glyphicon glyphicon-link\" > </span></button>";
            }
        }],
        checkbox: true,
        sizes: [10, 20, 50, 80],
        pageSize: 10
    };

    //查询
    $scope.searchGroup = function () {
        $scope.innerCtrl.load($.extend($scope.datagrid.params, $scope.searchParams));
    };

    //清空
    $scope.resetSearch = function () {
        var clearSearch = {
            name: '',
            inclUserType: ''
        };
        $.extend($scope.datagrid.params, clearSearch);
        $.extend($scope.searchParams, clearSearch);
    };

    //添加
    $scope.showAdd = function () {
        $scope.add = {};
        $scope.addForm.$setPristine();
        $("#addModal").modal('show');
    };

    //添加保存
    $scope.addSave = function () {
        if (!validateAddForm()) {
            return;
        }
        $scope.mask(true, 1);
        $http.post('app/system/group/add', {
            'name': $scope.info.name,
            'type': 'NORMAL',
            'description': $scope.info.description
        }).success(function (data) {
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
        $('#add-name').val("");
        $scope.info = {};
    };
    //添加校验
    function validateAddForm() {
        if ($scope.addForm.$invalid) {
            $scope.addForm.name.$setDirty();
            $scope.addForm.type.$setDirty();
            $scope.addForm.description.$setDirty();
            return false;
        }
        return true;
    }

    //编辑
    $scope.editGroup = function () {
        var checkeds = $scope.innerCtrl.getChecked();
        if (checkeds.length == 1) {
            $scope.edit = {
                'id': checkeds[0].id,
                'name': checkeds[0].name,
                'type': checkeds[0].type,
                'description': checkeds[0].description
            };
            $("#editModal").modal('show');
        } else {
            $scope.alert("只能选择一个进行编辑", 'error');
        }
    };

    //编辑清空
    $scope.editReset = function () {
        $scope.edit.description = "";
    };

    //编辑保存
    $scope.editSave = function () {
        if (!validateEditForm()) {
            return;
        }
        $scope.mask(true, 2);
        $http.post('app/system/group/edit', {
            'id': $scope.edit.id,
            'name': $scope.edit.name,
            'type': $scope.edit.type,
            'description': $scope.edit.description
        }).success(function (data) {
            if (data.success) {
                $scope.innerCtrl.load();
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

    //编辑校验
    function validateEditForm() {
        if ($scope.editForm.$invalid) {
            $scope.editForm.name.$setDirty();
            $scope.editForm.description.$setDirty();
            return false;
        }
        return true;
    }

    //删除组
    $scope.delGroup = function () {
        var checkeds = $scope.innerCtrl.getChecked();
        if (checkeds.length <= 0) {
            $scope.alert("必须勾选一条记录才能删除！", 'error');
            return;
        }
        var groupIds = [];
        for (var i = 0; i < checkeds.length; i++) {
            groupIds.push(checkeds[i].id);
        }
        $scope.confirm("将要删除" + checkeds.length + "条记录", function (y) {
            if (!y) {
                return;
            }
            $http.post('app/system/group/del', {
                'ids': groupIds
            }).success(function (data) {
                if (data.success) {
                    $scope.innerCtrl.load($scope.datagrid.params);
                    $scope.alert(data.message);
                } else
                    $scope.alert(data.message, 'error');
            });
        });
    };


    angular.custom.taxBusinessDetail= function(row) {
        $scope.$apply(function(){
            $scope.view = row;
        });
        $("#viewModal").modal('show');
    }
}]);