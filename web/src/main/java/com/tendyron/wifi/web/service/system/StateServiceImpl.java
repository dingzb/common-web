package com.tendyron.wifi.web.service.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tendyron.wifi.web.config.UserType;
import com.tendyron.wifi.web.model.PagingModel;
import com.tendyron.wifi.web.service.BaseServiceImpl;
import com.tendyron.wifi.web.service.ServiceException;
import com.tendyron.wifi.web.utils.StringTools;
import com.tendyron.wifi.web.dao.system.UserDao;
import com.tendyron.wifi.web.entity.system.UserEntity;
import com.tendyron.wifi.web.entity.system.RoleEntity;
import com.tendyron.wifi.web.logger.BusinessLog;
import com.tendyron.wifi.web.query.system.StateQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tendyron.wifi.web.config.BaseInfo;
import com.tendyron.wifi.web.dao.system.RoleDao;
import com.tendyron.wifi.web.dao.system.StateDao;
import com.tendyron.wifi.web.entity.system.StateEntity;
import com.tendyron.wifi.web.logger.LogOperationType;
import com.tendyron.wifi.web.model.system.StateModel;
import com.tendyron.wifi.web.model.system.UserModel;

/**
 * @author Dzb
 */
@Service("stateService")
public class StateServiceImpl extends BaseServiceImpl<StateEntity> implements StateService {

    private static final Logger logger = LogManager
            .getLogger(StateServiceImpl.class);

    @Autowired
    private StateDao stateDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    @BusinessLog(content = "添加除独立页或目录或目录组或子系统", operation = LogOperationType.ADD)
    public Serializable add(StateModel stateModel) throws ServiceException {

        StateEntity parent = null;
        StateEntity stateEntity = new StateEntity();

        if (StringTools.isEmpty(stateModel.getCode()))
            throw new ServiceException("没有设置代码");
        if (StringTools.isEmpty(stateModel.getName()))
            throw new ServiceException("没有设置名称");

        Boolean exist = false;
        try {
            exist = stateDao.existByCode(stateModel.getCode());
            if (StringTools.hasText(stateModel.getParentId())) {
                parent = stateDao.getById(stateModel.getParentId());
                if (parent == null)
                    throw new ServiceException("父State不存在");
            } else {
                parent = stateDao.getByCode(BaseInfo.STATE_MAIN.getCode());
            }
        } catch (Exception e) {
            logger.catching(e);
            throw new ServiceException();
        }
        if (exist)
            throw new ServiceException("Code名称为" + stateModel.getCode()
                    + "的state已经存在");
        try {
            BeanUtils.copyProperties(stateModel, stateEntity);

            if (parent != null) {
                stateEntity.setParent(parent);
            }
            if (StringTools.isEmpty(stateEntity.getId()))
                stateEntity.setId(StringTools.randomUUID());
            if (stateEntity.getCreateTime() == null)
                stateEntity.setCreateTime(new Date());
            if (stateEntity.getModifyTime() == null)
                stateEntity.setModifyTime(new Date());
            if (stateModel.getRoleIds() != null
                    && !stateModel.getRoleIds().isEmpty()) {
                Set<RoleEntity> roleEntity = null;
                roleEntity = roleDao.getByIds(stateModel.getRoleIds());
                stateEntity.setRoles(roleEntity);
            }
            return stateDao.save(stateEntity);
        } catch (Exception e) {
            logger.catching(e);
            throw new ServiceException();
        }
    }

    @Override
    @Transactional
    @BusinessLog(content = "编辑除独立页或目录或目录组或子系统", operation = LogOperationType.MODIFY)
    public void edit(StateModel stateModel) throws ServiceException {

        if (StringTools.isEmpty(stateModel.getId()))
            throw new ServiceException("编辑时Id不能为空");
        if (StringTools.isEmpty(stateModel.getCode()))
            throw new ServiceException("没有设置代码");
        if (StringTools.isEmpty(stateModel.getName()))
            throw new ServiceException("没有设置名称");

        StateEntity parent = null;
        StateEntity stateEntity = null;

        try {
            stateEntity = stateDao.getById(stateModel.getId());
            if (StringTools.hasText(stateModel.getParentId())) {
                parent = stateDao.getById(stateModel.getParentId());
                if (parent == null)
                    throw new ServiceException("父State不存在");
            }

            stateEntity.setName(stateModel.getName());
            stateEntity.setCode(stateModel.getCode());
            stateEntity.setSequence(stateModel.getSequence());
            stateEntity.setDescription(stateModel.getDescription());
            stateEntity.setModifyTime(new Date());
            stateEntity.setParent(parent);

            stateEntity.setModifyTime(new Date());

            if (stateModel.getRoleIds() != null
                    && !stateModel.getRoleIds().isEmpty()) {
                Set<RoleEntity> roleEntity = roleDao.getByIds(stateModel
                        .getRoleIds());
                stateEntity.setRoles(roleEntity);
            }
            stateDao.update(stateEntity);
        } catch (Exception e) {
            logger.catching(e);
            throw new ServiceException();
        }
    }

    @Override
    @Transactional
    public List<StateModel> getByRoleIds(Set<String> roleIds)
            throws ServiceException {
        List<StateEntity> stateEntitys = null;
        List<StateModel> stateModels = new ArrayList<StateModel>();
        try {
            stateEntitys = stateDao.getByRoleIds(roleIds);
        } catch (Exception e) {
            logger.catching(e);
            throw new ServiceException();
        }
        for (StateEntity stateEntity : stateEntitys) {
            StateModel stateModel = new StateModel();
            BeanUtils.copyProperties(stateEntity, stateModel);
            stateModels.add(stateModel);
        }
        return stateModels;
    }

    @Override
    @Transactional
    public List<StateModel> getByUserId(String userId) throws ServiceException {
        Set<String> roleIds = new HashSet<String>();
        if (StringTools.isEmpty(userId)) {
            return getByRoleIds(roleIds);
        }
        try {
            UserEntity userEntity = userDao.getById(userId);
            if (userEntity.getRoles() != null) {
                for (RoleEntity role : userEntity.getRoles()) {
                    roleIds.add(role.getId());
                }
            }
        } catch (Exception e) {
            logger.catching(e);
            throw new ServiceException();
        }
        return getByRoleIds(roleIds);
    }

    @Override
    @Transactional
    public PagingModel getPaging(StateQuery query) throws ServiceException {
        PagingModel paging = new PagingModel();
        List<StateEntity> stateEntities = null;
        Long total = null;

        try {
            stateEntities = stateDao.getPaging(query);
            total = stateDao.getCount(query);
        } catch (Exception e) {
            logger.catching(e);
            throw new ServiceException();
        }

        List<StateModel> stateModels = new ArrayList<StateModel>();
        for (StateEntity stateEntity : stateEntities) {
            StateModel stateModel = new StateModel();
            BeanUtils.copyProperties(stateEntity, stateModel);
            if (!StringTools.isEmpty(stateEntity.getParent())) {
                stateModel.setParentId(stateEntity.getParent().getId());
            }
            stateModels.add(stateModel);
        }
        paging.setRows(stateModels);
        paging.setTotal(total);
        return paging;
    }

    @Override
    @Transactional
    public List<StateModel> getHierarchySubsystemById(String id)
            throws ServiceException {
        StateEntity stateEntity = null;
        try {
            stateEntity = stateDao.getById(id);
        } catch (Exception e) {
            logger.catching(e);
            throw new ServiceException();
        }
        if (stateEntity == null)
            return new ArrayList<StateModel>();
        return hierarchy2List(stateEntity, 2);
    }

    /**
     * 将entity转换成list
     *
     * @param stateEntity
     * @return
     */
    private List<StateModel> hierarchy2List(StateEntity stateEntity, Integer type) {

        List<StateModel> stateModelList = new ArrayList<StateModel>();

        StateModel stateModel = new StateModel();
        BeanUtils.copyProperties(stateEntity, stateModel);
        stateModelList.add(stateModel);

        if (stateEntity.getType() != null && stateEntity.getType() == type)
            return stateModelList;
        if (stateEntity.getParent() != null) {
            stateModelList.addAll(hierarchy2List(stateEntity.getParent(), type));
        }
        return stateModelList;
    }

    /**
     * 将type非null类型子节点全部转换
     *
     * @param stateEntity
     * @param stateIds
     * @return
     * @throws ServiceException
     */
    private List<StateModel> hierarchy2list(StateEntity stateEntity, Set<String> stateIds, String userName) throws ServiceException {
        List<StateModel> stateModels = new ArrayList<StateModel>();
        Set<StateEntity> children = null;

        try {
            children = stateEntity.getChildren();
        } catch (Exception e) {
            logger.catching(e);
            throw new ServiceException();
        }
        if (children != null) {
            for (StateEntity child : children) {
                //非内置用户不允许看到状态管理权限配置菜单
                if (!"supreme".equals(userName)
                        && child.getCode().equalsIgnoreCase("main.sysconfig.state")) {
                    continue;
                }

                StateModel childModel = new StateModel();
                BeanUtils.copyProperties(child, childModel);

                if (child.getParent().getId() != null
                        && !child.getParent().getCode()
                        .equals(BaseInfo.STATE_MAIN.getCode())) {
                    childModel.setParentId(child.getParent().getId());
                }
                if (stateIds.contains(child.getId())) {
                    childModel.setChecked(true);
                }
                childModel.setChildren(hierarchy2list(child, stateIds, userName));
                stateModels.add(childModel);
            }
        }
        Collections.sort(stateModels);
        return stateModels;
    }

    /**
     * 根据范围内的state筛选出tree
     *
     * @param stateEntity
     * @param stateIds
     * @param poolStateIds
     * @return
     * @throws ServiceException
     */
    private List<StateModel> hierarchy2list(StateEntity stateEntity, Set<String> stateIds, Set<String> poolStateIds) throws ServiceException {
        List<StateModel> stateModels = new ArrayList<StateModel>();
        Set<StateEntity> children = null;

        try {
            children = stateEntity.getChildren();
        } catch (Exception e) {
            logger.catching(e);
            throw new ServiceException();
        }
        if (children != null) {
            for (StateEntity child : children) {

                if (poolStateIds != null && !poolStateIds.contains(child.getId())) {
                    continue;
                }

                StateModel childModel = new StateModel();
                BeanUtils.copyProperties(child, childModel);

                if (child.getParent().getId() != null && !child.getParent().getCode().equals(BaseInfo.STATE_MAIN.getCode())) {
                    childModel.setParentId(child.getParent().getId());
                }
                if (stateIds.contains(child.getId())) {
                    childModel.setChecked(true);
                }
                childModel.setChildren(hierarchy2list(child, stateIds, poolStateIds));
                stateModels.add(childModel);
            }
        }
        Collections.sort(stateModels);
        return stateModels;
    }

    @Override
    @Transactional
    public List<StateModel> filterByRoleId(String code, String roleId, UserModel user)
            throws ServiceException {
        Set<String> ids = new HashSet<String>();
        ids.add(roleId);
        List<StateModel> stateModels = getByRoleIds(ids);
        ids = getIds(stateModels);
        StateEntity stateEntity = stateDao.getByCode(code);
        if (stateEntity == null) {
            return new ArrayList<StateModel>();
        }
        return hierarchy2list(stateEntity, ids, user.getName());
    }

    @Override
    @Transactional
    public List<StateModel> filterByRoleId(String roleId, UserModel user)
            throws ServiceException {
        return filterByRoleId(BaseInfo.STATE_MAIN.getCode(), roleId, user);
    }

    @Override
    @Transactional
    @BusinessLog(content = "删除独立页或目录或目录组或子系统", operation = LogOperationType.DELETE)
    public Integer delete(String[] ids) throws ServiceException {
        Integer result = 0;
        try {
            for (int i = 0; i < ids.length; i++) {
                String id = ids[i];
                StateEntity st = stateDao.getById(id);
                if (st.getChildren().isEmpty() && st.getActions().isEmpty()) {
                    stateDao.delete(st);
                } else {
                    throw new ServiceException("存在子节点不能删除");
                }
                result++;
            }
        } catch (Exception e) {
            logger.catching(e);
            throw new ServiceException();
        }
        return result;
    }

    @Override
    @Transactional
    @BusinessLog(content = "删除独立页或目录或目录组或子系统", operation = LogOperationType.DELETE)
    public void delete(String id) throws ServiceException {
        StateEntity e = stateDao.getById(id);
        if (e == null)
            throw new ServiceException("该记录不存在！");
        try {
            if (e.getChildren().isEmpty() && e.getActions().isEmpty()) {
                stateDao.delete(e);
            } else {
                throw new ServiceException("存在子节点不能删除");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException();
        }
    }

    @Override
    @Transactional
    public List<StateModel> getList() throws ServiceException {
        List<StateModel> stateModels = new ArrayList<StateModel>();
        List<StateEntity> stateEntitys = null;
        try {
            stateEntitys = stateDao.getList();
            if (stateEntitys != null && !stateEntitys.isEmpty()) {
                for (StateEntity stateEntity : stateEntitys) {
                    if (stateEntity.getType() != null) {
                        StateModel stateModel = new StateModel();
                        BeanUtils.copyProperties(stateEntity, stateModel);
                        stateModels.add(stateModel);
                    }
                }
            }
        } catch (Exception e) {
            logger.catching(e);
            throw new ServiceException();
        }
        return stateModels;
    }

    @Override
    @Transactional
    public List<StateModel> treeWithRoleUser(String roleId, String userId) throws ServiceException {
        StateEntity stateEntity = stateDao.getByCode(BaseInfo.STATE_MAIN.getCode());
        List<StateEntity> checkedStates = stateDao.getByRoleId(roleId);

        Set<String> poolStateIds = null;

        UserEntity userEntity = null;
        try {
            userEntity = userDao.getById(userId);
        } catch (Exception e) {
            logger.catching(e);
            throw new ServiceException();
        }
        if (userEntity == null) {
            throw new ServiceException("用户不存在");
        }

        if (!UserType.BUILD_IN.equals(userEntity.getType())) {
            poolStateIds = getIds(stateDao.getByRoleIds(getIds(userEntity.getRoles())));
        }

        return hierarchy2list(stateEntity, getIds(checkedStates), poolStateIds);
    }
}
