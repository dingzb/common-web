package com.tendyron.wifi.web.controller.system;

import com.tendyron.wifi.web.config.AttrName;
import com.tendyron.wifi.web.controller.BaseController;
import com.tendyron.wifi.web.model.Json;
import com.tendyron.wifi.web.model.PagingModel;
import com.tendyron.wifi.web.model.system.UserModel;
import com.tendyron.wifi.web.service.ServiceException;
import com.tendyron.wifi.web.model.system.RoleModel;
import com.tendyron.wifi.web.query.system.RoleQuery;
import com.tendyron.wifi.web.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Dzb on 2017/2/28.
 */
@Controller("roleController")
@RequestMapping("system/role")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;

    @RequestMapping("paging")
    @ResponseBody
    public Json getPaging(RoleQuery query) {
        try {
            PagingModel paging = roleService.getPaging(query);
            return success(paging);
        } catch (ServiceException e) {
            return fail(e.getMessage());
        }
    }

    /**
     *
     * @param role
     * @return
     */
    @RequestMapping("add")
    @ResponseBody
    public Json addRole(RoleModel role) {
        try {
            roleService.add(role);
            return success("添加成功");
        } catch (ServiceException e) {
            return fail(e.getMessage());
        }
    }

    /**
     * 根据角色名称判断角色是否存在
     *
     * @param name
     * @return
     */
    @RequestMapping("exist")
    @ResponseBody
    public Json existRoleByName(String name) {
        try {
            return success(roleService.existByName(name));
        } catch (ServiceException e) {
            return fail(e);
        }
    }

    /**
     *
     * @param role
     * @return
     */
    @RequestMapping("edit")
    @ResponseBody
    public Json editRole(RoleModel role) {
        try {
            roleService.edit(role);
            return success("编辑成功");
        } catch (ServiceException e) {
            return fail(e.getMessage());
        }
    }

    /**
     * 删除角色
     *
     * @param ids
     * @return
     */
    @RequestMapping("del")
    @ResponseBody
    public Json deleteRoles(@RequestParam("ids[]") String[] ids) {
        Integer result = null;
        try {
            result = roleService.delete(ids);
        } catch (ServiceException e) {
            return fail(e.getMessage());
        }
        return success("成功删除" + result + "条数据");
    }

    /**
     * 保存权限配置
     *
     * @param roleId
     * @param stateIds
     * @return
     */
    @RequestMapping("state/save")
    @ResponseBody
    public Json saveState(
            @RequestParam("roleId") String roleId,
            @RequestParam(value = "stateIds[]", required = false) String[] stateIds) {

        try {
            roleService.saveState(roleId, stateIds);
        } catch (ServiceException e) {
            return fail(e.getMessage());
        }
        return success();
    }

    /**
     * 保存权限配置
     *
     * @param roleId
     * @param actionIds
     * @return
     */
    @RequestMapping("action/save")
    @ResponseBody
    public Json saveAction(
            @RequestParam("roleId") String roleId,
            @RequestParam(value = "actionIds[]", required = false) String[] actionIds,
            @RequestParam("stateId") String stateId) {

        try {
            roleService.saveAction(roleId, actionIds, stateId);
        } catch (ServiceException e) {
            return fail(e.getMessage());
        }
        return success();
    }

    @RequestMapping("types")
    @ResponseBody
    public Json getTypes(HttpServletRequest request) {
        try {
            return success(roleService.getTypes(((UserModel) WebUtils
                    .getSessionAttribute(request, AttrName.SESSION_USER))));
        } catch (ServiceException e) {
            return fail(e);
        }
    }
}
