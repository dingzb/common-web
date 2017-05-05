package com.tendyron.wifi.web.service.system;

import java.io.Serializable;
import java.util.List;

import com.tendyron.wifi.web.model.PagingModel;
import com.tendyron.wifi.web.model.system.UserModel;
import com.tendyron.wifi.web.service.BaseService;
import com.tendyron.wifi.web.service.ServiceException;
import com.tendyron.wifi.web.entity.system.GroupEntity;
import com.tendyron.wifi.web.model.system.GroupModel;
import com.tendyron.wifi.web.query.system.GroupQuery;

public interface GroupService extends BaseService<GroupEntity> {

    /**
     * 获取分页查询数据
     *
     * @param query
     * @return
     * @throws ServiceException
     */
    PagingModel getPaging(GroupQuery query) throws ServiceException;

    /**
     * 添加组
     *
     * @param groupModel
     * @throws ServiceException
     */
    Serializable addGroup(GroupModel groupModel) throws ServiceException;

    /**
     * 编辑组
     *
     * @param groupModel
     * @throws ServiceException
     */
    void editGroup(GroupModel groupModel) throws ServiceException;

    /**
     * 删除组
     *
     * @param groupIds
     * @return
     * @throws ServiceException
     */
    void deleteGroups(String[] groupIds) throws ServiceException;

    /**
     * 根据名称判断组是否存在
     *
     * @param name
     * @return
     * @throws ServiceException
     */
    boolean existByName(String name) throws ServiceException;

    /**
     * 获取组类型
     *
     * @param user
     * @return
     * @throws ServiceException
     */
    List<String> getTypes(UserModel user) throws ServiceException;
}
