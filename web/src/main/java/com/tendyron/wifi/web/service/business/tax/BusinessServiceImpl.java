package com.tendyron.wifi.web.service.business.tax;

import com.tendyron.wifi.web.config.UserType;
import com.tendyron.wifi.web.dao.business.tax.AgencyDao;
import com.tendyron.wifi.web.dao.business.tax.BusinessCategoryDao;
import com.tendyron.wifi.web.dao.business.tax.BusinessDao;
import com.tendyron.wifi.web.dao.business.tax.BusinessIssueDao;
import com.tendyron.wifi.web.dao.system.UserDao;
import com.tendyron.wifi.web.entity.business.tax.*;
import com.tendyron.wifi.web.entity.business.tax.BusinessEntity.BUS_STATUS;
import com.tendyron.wifi.web.entity.system.UserEntity;
import com.tendyron.wifi.web.model.PagingModel;
import com.tendyron.wifi.web.model.business.tax.*;
import com.tendyron.wifi.web.model.business.tax.statistics.FenjuModel;
import com.tendyron.wifi.web.model.business.tax.statistics.StatisticsCategoryModel;
import com.tendyron.wifi.web.model.business.tax.statistics.StatisticsCategoryTypeModel;
import com.tendyron.wifi.web.model.business.tax.statistics.XianjuModel;
import com.tendyron.wifi.web.query.business.tax.BusinessQuery;
import com.tendyron.wifi.web.query.business.tax.FenjuQuery;
import com.tendyron.wifi.web.query.business.tax.StatisticsQuery;
import com.tendyron.wifi.web.query.business.tax.XianjuQuery;
import com.tendyron.wifi.web.service.BaseServiceImpl;
import com.tendyron.wifi.web.service.ServiceException;
import com.tendyron.wifi.web.utils.StringTools;
import com.tendyron.wifi.web.utils.UploadTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by Neo on 2017/5/9.
 */
@Service("businessService")
public class BusinessServiceImpl extends BaseServiceImpl<BusinessEntity> implements BusinessService {

    private static final Logger logger = LoggerFactory.getLogger(BusinessServiceImpl.class);
    @Autowired
    private BusinessDao businessDao;

    @Autowired
    private BusinessCategoryDao businessCategoryDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BusinessIssueDao businessIssueDao;

    @Autowired
    private AgencyDao agencyDao;

    @Transactional
    @Override
    public PagingModel pagingCreated(BusinessQuery query) throws ServiceException {
        queryBasicAssert(query);
        query.setStatus(BUS_STATUS.CREATE);
        return pagingBaseUser(query);
    }

    @Transactional
    @Override
    public PagingModel pagingFirst(BusinessQuery query) throws ServiceException {
        queryBasicAssert(query);
        query.setIncludeStatus(new Integer[]{BUS_STATUS.FIRST, BUS_STATUS.SECOND, BUS_STATUS.THIRD, BUS_STATUS.HAS_ISSUE, BUS_STATUS.FINISH});
        return pagingBaseUser(query);
    }

    @Transactional
    @Override
    public PagingModel pagingSecond(BusinessQuery query) throws ServiceException {
        queryBasicAssert(query);
        query.setIncludeStatus(new Integer[]{BUS_STATUS.SECOND, BUS_STATUS.THIRD, BUS_STATUS.HAS_ISSUE, BUS_STATUS.FINISH});
        return pagingBaseUser(query);
    }

    @Transactional
    @Override
    public PagingModel pagingThird(BusinessQuery query) throws ServiceException {
        queryBasicAssert(query);
        query.setIncludeStatus(new Integer[]{BUS_STATUS.THIRD, BUS_STATUS.HAS_ISSUE, BUS_STATUS.FINISH});
        return pagingBaseUser(query);
    }

    @Transactional
    @Override
    public PagingModel pagingAmendment(BusinessQuery query) throws ServiceException {
        queryBasicAssert(query);
        query.setStatus(BUS_STATUS.HAS_ISSUE);
        return pagingBaseUser(query);
    }

    @Transactional
    @Override
    public PagingModel pagingError(BusinessQuery query) throws ServiceException {
        PagingModel result = new PagingModel();
        List<BusinessModel> businessModels = new ArrayList<>();
        result.setRows(businessModels);
        try {
            List<BusinessEntity> businessEntities = businessDao.pagingError(query);
            businessEntities.forEach(businessEntity -> {
                BusinessModel businessModel = new BusinessModel();
                BeanUtils.copyProperties(businessEntity, businessModel);

                AgencyEntity agencyEntity = businessEntity.getAgency();
                if (agencyEntity != null) {
                    businessModel.setAgencyId(agencyEntity.getId());
                    businessModel.setAgencyName(agencyEntity.getName());
                }

                BusCategoryEntity categoryEntity = businessEntity.getCategory();
                if (categoryEntity != null) {
                    businessModel.setCategoryId(categoryEntity.getId());
                    businessModel.setCategoryName(categoryEntity.getName());

                    BusCategoryTypeEntity categoryTypeEntity = categoryEntity.getType();
                    if (categoryTypeEntity != null) {
                        businessModel.setCategoryTypeId(categoryTypeEntity.getId());
                        businessModel.setCategoryTypeName(categoryTypeEntity.getName());
                    }
                }

                ExamineEntity first = businessEntity.getFirstExamine();
                convertIssue(first, businessModel, businessModel::setFirstHasIssue, businessModel::setFirstExamine);

                ExamineEntity second = businessEntity.getSecondExamine();
                convertIssue(second, businessModel, businessModel::setSecondHasIssue, businessModel::setSecondExamine);

                ExamineEntity third = businessEntity.getThirdExamine();
                convertIssue(third, businessModel, businessModel::setThirdHasIssue, businessModel::setThirdExamine);

                UserEntity create = businessEntity.getCreate();
                if (create != null) {
                    businessModel.setCreateId(create.getId());
                    businessModel.setCreateName(create.getName());
                }

                UserEntity check = businessEntity.getCheck();
                if (check != null) {
                    businessModel.setCheckId(check.getId());
                    businessModel.setCheckName(check.getName());
                }

                UserEntity finalCheck = businessEntity.getFinalCheck();
                if (finalCheck != null) {
                    businessModel.setFinalCheckId(finalCheck.getId());
                    businessModel.setFinalCheckName(finalCheck.getName());
                }

                businessModels.add(businessModel);
            });
        } catch (Exception e) {
            logger.error("", e);
            throw new ServiceException();
        }

        result.setTotal(businessDao.getCount(query));
        return result;
    }

    @Transactional
    @Override
    public PagingModel pagingBaseUser(BusinessQuery query) throws ServiceException {
        queryBasicAssert(query);
        try {
            UserEntity curUser = userDao.getById(query.getUserId());
            if (UserType.NORMAL.equals(curUser.getType())) {                     //非管理员
                if (curUser.getAgencyBoss()) {
                    Set<UserEntity> agencyUsers = curUser.getAgency().getUsers();
                    query.setCreateUserIds(getIds(agencyUsers));
                } else if (curUser.getAgency().getChildren() != null) {          //县局
                    Set<String> childrenUserIds = new HashSet<>();
                    for (AgencyEntity a : curUser.getAgency().getChildren()) {
                        childrenUserIds.addAll(getIds(a.getUsers()));
                    }
                    childrenUserIds.add(curUser.getId());                       // 县局用户自己，o(￣▽￣)ｄ 似乎没什么用
                    query.setCreateUserIds(childrenUserIds);
                } else {
                    query.setCreateUserId(curUser.getId());
                }
            }
        } catch (Exception e) {
            logger.error("", e);
            throw new ServiceException();
        }
        return paging(query);
    }


    @Transactional
    @Override
    public PagingModel paging(BusinessQuery query) throws ServiceException {

        queryBasicAssert(query);

        List<BusinessModel> businessModels = new ArrayList<>();
        PagingModel paging = new PagingModel();
        Long total = null;

        try {
            List<BusinessEntity> businessEntities = businessDao.paging(query);
            for (BusinessEntity businessEntity : businessEntities) {

                BusinessModel businessModel = new BusinessModel();
                BeanUtils.copyProperties(businessEntity, businessModel);

                AgencyEntity agencyEntity = businessEntity.getAgency();
                if (agencyEntity != null) {
                    businessModel.setAgencyId(agencyEntity.getId());
                    businessModel.setAgencyName(agencyEntity.getName());
                }

                BusCategoryEntity categoryEntity = businessEntity.getCategory();
                if (categoryEntity != null) {
                    businessModel.setCategoryId(categoryEntity.getId());
                    businessModel.setCategoryName(categoryEntity.getName());

                    BusCategoryTypeEntity categoryTypeEntity = categoryEntity.getType();
                    if (categoryTypeEntity != null) {
                        businessModel.setCategoryTypeId(categoryTypeEntity.getId());
                        businessModel.setCategoryTypeName(categoryTypeEntity.getName());
                    }
                }

                if (businessEntity.getFirstHasIssue() != null) {
                    businessModel.setFirstHasIssue(businessEntity.getFirstHasIssue());
                } else {
                    ExamineEntity first = businessEntity.getFirstExamine();
                    convertIssue(first, businessModel, businessModel::setFirstHasIssue, businessModel::setFirstExamine);
                }

                if (businessEntity.getSecondHasIssue() != null) {
                    businessModel.setSecondHasIssue(businessEntity.getSecondHasIssue());
                } else {
                    ExamineEntity second = businessEntity.getSecondExamine();
                    convertIssue(second, businessModel, businessModel::setSecondHasIssue, businessModel::setSecondExamine);
                }

                if (businessEntity.getThirdHasIssue() != null) {
                    businessModel.setThirdHasIssue(businessEntity.getThirdHasIssue());
                } else {
                    ExamineEntity third = businessEntity.getThirdExamine();
                    convertIssue(third, businessModel, businessModel::setThirdHasIssue, businessModel::setThirdExamine);
                }

                UserEntity create = businessEntity.getCreate();
                if (create != null) {
                    businessModel.setCreateId(create.getId());
                    businessModel.setCreateName(create.getName());
                }

                UserEntity check = businessEntity.getCheck();
                if (check != null) {
                    businessModel.setCheckId(check.getId());
                    businessModel.setCheckName(check.getName());
                }

                UserEntity finalCheck = businessEntity.getFinalCheck();
                if (finalCheck != null) {
                    businessModel.setFinalCheckId(finalCheck.getId());
                    businessModel.setFinalCheckName(finalCheck.getName());
                }

                businessModels.add(businessModel);
            }
            total = businessDao.getCount(query);
        } catch (Exception e) {
            logger.error("", e);
            throw new ServiceException();
        }

        paging.setRows(businessModels);
        paging.setTotal(total);
        return paging;
    }

    /**
     * 转换问题名称
     *
     * @param examine
     * @param bus
     */
    private void convertIssue(ExamineEntity examine, BusinessModel bus, Consumer<Boolean> hasIssue, Consumer<ExamineModel> setExa) {
        if (examine == null) {
            return;
        }
        hasIssue.accept(examine.getHasIssue());
        ExamineModel examineModel = new ExamineModel();
        BeanUtils.copyProperties(examine, examineModel);
        Set<BusIssueEntity> issues = examine.getIssues();
        if (issues != null) {
            Set<BusIssueModel> busIssueModels = new HashSet<>();
            for (BusIssueEntity issue : issues) {
                BusIssueModel busIssueModel = new BusIssueModel();
                BeanUtils.copyProperties(issue, busIssueModel);
                busIssueModels.add(busIssueModel);
            }
            examineModel.setIssues(busIssueModels);
        }
        setExa.accept(examineModel);

    }

    @Override
    @Transactional
    public void add(BusinessModel businessModel) throws ServiceException {

        if (businessModel == null) {
            throw new ServiceException("业务对象不能为空！");
        }

        BusinessEntity businessEntity = new BusinessEntity();

        BeanUtils.copyProperties(businessModel, businessEntity);

        try {
            if (!StringTools.isEmpty(businessModel.getCategoryId())) {
                BusCategoryEntity categoryEntity = businessCategoryDao.getById(businessModel.getCategoryId());
                businessEntity.setCategory(categoryEntity);
            }
            if (!StringTools.isEmpty(businessModel.getUserId())) {
                UserEntity create = userDao.getById(businessModel.getUserId());
                businessEntity.setCreate(create);
                businessEntity.setAgency(create.getAgency());
            }
        } catch (Exception e) {
            logger.error("", e);
        }

        businessEntity.setCreateTime(new Date());
        businessEntity.setId(StringTools.randomUUID());
        businessEntity.setStatus(BUS_STATUS.CREATE);
        businessDao.save(businessEntity);
    }


    @Override
    @Transactional
    public void edit(BusinessModel business) throws ServiceException {

        try {
            BusinessEntity businessEntity = businessDao.getById(business.getId());
            if (businessEntity == null) {
                throw new ServiceException("ID为" + business.getId() + " 的记录已经不存在。");
            }

            businessEntity.setTaxpayerCode(business.getTaxpayerCode());
            businessEntity.setTaxpayerName(business.getTaxpayerName());
            BusCategoryEntity categoryEntity = businessCategoryDao.getById(business.getCategoryId());
            if (categoryEntity != null) {
                businessEntity.setCategory(categoryEntity);
            }
//            if (business.getHasIssue() && !StringTools.isEmpty(business.getIssueId())) {
//                BusIssueEntity issueEntity = businessIssueDao.getById(business.getIssueId());
//                if (issueEntity != null) {
//                    businessEntity.setIssue(issueEntity);
//                }
//            } else {
//                businessEntity.setHasIssue(false);
//                businessEntity.setIssue(null);
//            }
            businessEntity.setBusTime(business.getBusTime());
            businessEntity.setDescription(business.getDescription());

            businessEntity.setModifyTime(new Date());
            businessDao.save(businessEntity);
        } catch (Exception e) {
            logger.error("", e);
            throw new ServiceException();
        }

    }

    @Override
    @Transactional
    public Integer del(String[] ids) throws ServiceException {
        Integer result = null;
        try {
            result = businessDao.delByIds(ids);
        } catch (Exception e) {
            logger.error("", e);
            throw new ServiceException();
        }
        return result;
    }

    @Override
    @Transactional
    public List<XianjuModel> xianju(XianjuQuery query) throws ServiceException {

        BusinessQuery bQuery = new BusinessQuery();

        bQuery.setBusTimeStart(query.getBusTimeStart());
        bQuery.setBusTimeEnd(query.getBusTimeEnd());

        List<XianjuModel> sms = new ArrayList<>();

        try {
            for (String agencyId : query.getAgencyIds()) {
                bQuery.setAgencyId(agencyId);
                AgencyEntity agency = agencyDao.getById(agencyId);
                XianjuModel sm = new XianjuModel();
                sm.setAgencyName(agency.getName());
                sm.setAgencyId(agency.getId());
                List<BusinessEntity> bes = businessDao.getList(bQuery);
                List<StatisticsCategoryTypeModel> sctms = new ArrayList<>();

                for (BusinessEntity be : bes) {
                    StatisticsCategoryTypeModel sctmTmp = null;
                    BusCategoryTypeEntity bcte = be.getCategory().getType();

                    for (StatisticsCategoryTypeModel sctm : sctms) {
                        if (sctm.getId().equals(bcte.getId())) {
                            sctmTmp = sctm;
                            break;
                        }
                    }
                    if (sctmTmp == null) {
                        sctmTmp = new StatisticsCategoryTypeModel();
                        sctmTmp.setName(bcte.getName());
                        sctmTmp.setId(bcte.getId());
                        sctms.add(sctmTmp);
                    }

                    BusCategoryEntity bce = be.getCategory();

                    List<StatisticsCategoryModel> scms = sctmTmp.getRecs();
                    StatisticsCategoryModel scmTmp = null;

                    if (scms != null) {
                        for (StatisticsCategoryModel scm : scms) {
                            if (scm.getId().equals(bce.getId())) {
                                scmTmp = scm;
                                break;
                            }
                        }
                    } else {
                        scms = new ArrayList<>();
                        sctmTmp.setRecs(scms);
                    }

                    if (scmTmp == null) {
                        scmTmp = new StatisticsCategoryModel();
                        scmTmp.setName(bce.getName());
                        scmTmp.setId(bce.getId());
                        scms.add(scmTmp);
                    }

                    scmTmp.setCount(scmTmp.getCount() + 1);     // 业务总数

                    boolean hasIssue = false;
                    if (be.getStatus() != BUS_STATUS.FINISH) { // 排除业务处在完成状态的业务，如三级审核后都没有问题或月经整改
                        if (be.getFirstExamine() != null && be.getFirstExamine().getHasIssue()) {
                            hasIssue = true;
                            Set<String> issueNames = new HashSet<>();
                            Set<BusIssueEntity> busIssueEntities = be.getFirstExamine().getIssues();
                            busIssueEntities.forEach(busIssueEntity -> issueNames.add(busIssueEntity.getName()));
                            Set<String> oldIssueNames = scmTmp.getIssueNames();
                            if (oldIssueNames == null) {
                                oldIssueNames = new HashSet<>();
                            }
                            oldIssueNames.addAll(issueNames);
                            scmTmp.setFirstIssueCount(scmTmp.getFirstIssueCount() + 1);
                        } else if (be.getSecondExamine() != null && be.getSecondExamine().getHasIssue()) {
                            hasIssue = true;
                            Set<String> issueNames = new HashSet<>();
                            Set<BusIssueEntity> busIssueEntities = be.getSecondExamine().getIssues();
                            busIssueEntities.forEach(busIssueEntity -> issueNames.add(busIssueEntity.getName()));
                            Set<String> oldIssueNames = scmTmp.getIssueNames();
                            if (oldIssueNames == null) {
                                oldIssueNames = new HashSet<>();
                            }
                            oldIssueNames.addAll(issueNames);
                            scmTmp.setSecondIssueCount(scmTmp.getSecondIssueCount() + 1);
                        } else if (be.getThirdExamine() != null && be.getThirdExamine().getHasIssue()) {
                            hasIssue = true;
                            Set<String> issueNames = new HashSet<>();
                            Set<BusIssueEntity> busIssueEntities = be.getThirdExamine().getIssues();
                            busIssueEntities.forEach(busIssueEntity -> issueNames.add(busIssueEntity.getName()));
                            Set<String> oldIssueNames = scmTmp.getIssueNames();
                            if (oldIssueNames == null) {
                                oldIssueNames = new HashSet<>();
                            }
                            oldIssueNames.addAll(issueNames);
                            scmTmp.setThirdIssueCount(scmTmp.getThirdIssueCount() + 1);
                        }
                    }
                    scmTmp.setIssueCount(scmTmp.getIssueCount() + (hasIssue ? 1 : 0));      //问题业务总数

                    sm.setDetailCount(sm.getDetailCount() + 1);

                }
                sm.setRecs(sctms);
                sms.add(sm);
            }
        } catch (Exception e) {
            logger.error("", e);
            throw new ServiceException();
        }
        return sms;
    }


    @Transactional
    @Override
    public List<FenjuModel> fenju(FenjuQuery query) throws ServiceException {

        BusinessQuery bQuery = new BusinessQuery();

        bQuery.setBusTimeStart(query.getBusTimeStart());
        bQuery.setBusTimeEnd(query.getBusTimeEnd());

        List<FenjuModel> fms = new ArrayList<>();

        try {
            for (String userId : query.getUserIds()) {
                bQuery.setCreateUserId(userId);
                UserEntity userEntity = userDao.getById(userId);
                FenjuModel fm = new FenjuModel();
                fm.setUserName(userEntity.getName());
                fm.setUserId(userEntity.getId());
                List<BusinessEntity> bes = businessDao.getList(bQuery);
                List<StatisticsCategoryTypeModel> sctms = new ArrayList<>();

                for (BusinessEntity be : bes) {
                    StatisticsCategoryTypeModel sctmTmp = null;
                    BusCategoryTypeEntity bcte = be.getCategory().getType();

                    for (StatisticsCategoryTypeModel sctm : sctms) {
                        if (sctm.getId().equals(bcte.getId())) {
                            sctmTmp = sctm;
                            break;
                        }
                    }
                    if (sctmTmp == null) {
                        sctmTmp = new StatisticsCategoryTypeModel();
                        sctmTmp.setName(bcte.getName());
                        sctmTmp.setId(bcte.getId());
                        sctms.add(sctmTmp);
                    }

                    BusCategoryEntity bce = be.getCategory();

                    List<StatisticsCategoryModel> scms = sctmTmp.getRecs();
                    StatisticsCategoryModel scmTmp = null;

                    if (scms != null) {
                        for (StatisticsCategoryModel scm : scms) {
                            if (scm.getId().equals(bce.getId())) {
                                scmTmp = scm;
                                break;
                            }
                        }
                    } else {
                        scms = new ArrayList<>();
                        sctmTmp.setRecs(scms);
                    }

                    if (scmTmp == null) {
                        scmTmp = new StatisticsCategoryModel();
                        scmTmp.setName(bce.getName());
                        scmTmp.setId(bce.getId());
                        scms.add(scmTmp);
                    }

                    scmTmp.setCount(scmTmp.getCount() + 1);     // 业务总数

                    boolean hasIssue = false;
                    if (be.getStatus() != BUS_STATUS.FINISH) { // 排除业务处在完成状态的业务，如三级审核后都没有问题或月经整改
                        if (be.getFirstExamine() != null && be.getFirstExamine().getHasIssue()) {
                            hasIssue = true;
                            Set<String> issueNames = new HashSet<>();
                            Set<BusIssueEntity> busIssueEntities = be.getFirstExamine().getIssues();
                            busIssueEntities.forEach(busIssueEntity -> issueNames.add(busIssueEntity.getName()));
                            Set<String> oldIssueNames = scmTmp.getIssueNames();
                            if (oldIssueNames == null) {
                                oldIssueNames = new HashSet<>();
                            }
                            oldIssueNames.addAll(issueNames);
                            scmTmp.setFirstIssueCount(scmTmp.getFirstIssueCount() + 1);
                        } else if (be.getSecondExamine() != null && be.getSecondExamine().getHasIssue()) {
                            hasIssue = true;
                            Set<String> issueNames = new HashSet<>();
                            Set<BusIssueEntity> busIssueEntities = be.getSecondExamine().getIssues();
                            busIssueEntities.forEach(busIssueEntity -> issueNames.add(busIssueEntity.getName()));
                            Set<String> oldIssueNames = scmTmp.getIssueNames();
                            if (oldIssueNames == null) {
                                oldIssueNames = new HashSet<>();
                            }
                            oldIssueNames.addAll(issueNames);
                            scmTmp.setSecondIssueCount(scmTmp.getSecondIssueCount() + 1);
                        } else if (be.getThirdExamine() != null && be.getThirdExamine().getHasIssue()) {
                            hasIssue = true;
                            Set<String> issueNames = new HashSet<>();
                            Set<BusIssueEntity> busIssueEntities = be.getThirdExamine().getIssues();
                            busIssueEntities.forEach(busIssueEntity -> issueNames.add(busIssueEntity.getName()));
                            Set<String> oldIssueNames = scmTmp.getIssueNames();
                            if (oldIssueNames == null) {
                                oldIssueNames = new HashSet<>();
                            }
                            oldIssueNames.addAll(issueNames);
                            scmTmp.setThirdIssueCount(scmTmp.getThirdIssueCount() + 1);
                        }
                    }
                    scmTmp.setIssueCount(scmTmp.getIssueCount() + (hasIssue ? 1 : 0));      //问题业务总数

                    fm.setDetailCount(fm.getDetailCount() + 1);

                }
                fm.setRecs(sctms);
                fms.add(fm);
            }
        } catch (Exception e) {
            logger.error("", e);
            throw new ServiceException();
        }
        return fms;
    }


    @Transactional
    @Override
    public List<StatisticsCategoryTypeModel> person(StatisticsQuery query) throws ServiceException {

        BusinessQuery bQuery = new BusinessQuery();

        bQuery.setBusTimeStart(query.getBusTimeStart());
        bQuery.setBusTimeEnd(query.getBusTimeEnd());

        bQuery.setCreateUserId(query.getUserId());

        List<StatisticsCategoryTypeModel> sctms = new ArrayList<>();
        try {
            List<BusinessEntity> bes = businessDao.getList(bQuery);

            for (BusinessEntity be : bes) {
                StatisticsCategoryTypeModel sctmTmp = null;
                BusCategoryTypeEntity bcte = be.getCategory().getType();

                for (StatisticsCategoryTypeModel sctm : sctms) {
                    if (sctm.getId().equals(bcte.getId())) {
                        sctmTmp = sctm;
                        break;
                    }
                }
                if (sctmTmp == null) {
                    sctmTmp = new StatisticsCategoryTypeModel();
                    sctmTmp.setName(bcte.getName());
                    sctmTmp.setId(bcte.getId());
                    sctms.add(sctmTmp);
                }

                BusCategoryEntity bce = be.getCategory();

                List<StatisticsCategoryModel> scms = sctmTmp.getRecs();
                StatisticsCategoryModel scmTmp = null;

                if (scms != null) {
                    for (StatisticsCategoryModel scm : scms) {
                        if (scm.getId().equals(bce.getId())) {
                            scmTmp = scm;
                            break;
                        }
                    }
                } else {
                    scms = new ArrayList<>();
                    sctmTmp.setRecs(scms);
                }

                if (scmTmp == null) {
                    scmTmp = new StatisticsCategoryModel();
                    scmTmp.setName(bce.getName());
                    scmTmp.setId(bce.getId());
                    scms.add(scmTmp);
                }

                scmTmp.setCount(scmTmp.getCount() + 1);     // 业务总数

                boolean hasIssue = false;
                if (be.getStatus() != BUS_STATUS.FINISH) { // 排除业务处在完成状态的业务，如三级审核后都没有问题或月经整改
                    if (be.getFirstExamine() != null && be.getFirstExamine().getHasIssue()) {
                        hasIssue = true;
                        Set<String> issueNames = new HashSet<>();
                        Set<BusIssueEntity> busIssueEntities = be.getFirstExamine().getIssues();
                        busIssueEntities.forEach(busIssueEntity -> issueNames.add(busIssueEntity.getName()));
                        Set<String> oldIssueNames = scmTmp.getIssueNames();
                        if (oldIssueNames == null) {
                            oldIssueNames = new HashSet<>();
                        }
                        oldIssueNames.addAll(issueNames);
                        scmTmp.setFirstIssueCount(scmTmp.getFirstIssueCount() + 1);
                    } else if (be.getSecondExamine() != null && be.getSecondExamine().getHasIssue()) {
                        hasIssue = true;
                        Set<String> issueNames = new HashSet<>();
                        Set<BusIssueEntity> busIssueEntities = be.getSecondExamine().getIssues();
                        busIssueEntities.forEach(busIssueEntity -> issueNames.add(busIssueEntity.getName()));
                        Set<String> oldIssueNames = scmTmp.getIssueNames();
                        if (oldIssueNames == null) {
                            oldIssueNames = new HashSet<>();
                        }
                        oldIssueNames.addAll(issueNames);
                        scmTmp.setSecondIssueCount(scmTmp.getSecondIssueCount() + 1);
                    } else if (be.getThirdExamine() != null && be.getThirdExamine().getHasIssue()) {
                        hasIssue = true;
                        Set<String> issueNames = new HashSet<>();
                        Set<BusIssueEntity> busIssueEntities = be.getThirdExamine().getIssues();
                        busIssueEntities.forEach(busIssueEntity -> issueNames.add(busIssueEntity.getName()));
                        Set<String> oldIssueNames = scmTmp.getIssueNames();
                        if (oldIssueNames == null) {
                            oldIssueNames = new HashSet<>();
                        }
                        oldIssueNames.addAll(issueNames);
                        scmTmp.setThirdIssueCount(scmTmp.getThirdIssueCount() + 1);
                    }
                }
                scmTmp.setIssueCount(scmTmp.getIssueCount() + (hasIssue ? 1 : 0));      //问题业务总数
            }
        } catch (Exception e) {
            logger.error("", e);
            throw new ServiceException();
        }
        return sctms;
    }

    @Transactional
    @Override
    public void commit(String[] ids) throws ServiceException {
        changeStatus(ids, BUS_STATUS.FIRST);
    }

    private void changeStatus(String[] ids, int status) throws ServiceException {
        try {
            for (String id : ids) {
                BusinessEntity business = businessDao.getById(id);
                if (business == null) {
                    throw new ServiceException("业务不存在");
                }
                business.setStatus(status);
                businessDao.update(business);
            }
        } catch (Exception e) {
            logger.error("", e);
            throw new ServiceException();
        }
    }

    @Transactional
    @Override
    public void commitExamine(ExamineModel examine) throws ServiceException {
        if (examine.getStep() == 0) {
            throw new ServiceException("审查阶段错误");
        }

        String busId = examine.getBusId();
        BusinessEntity business;
        try {
            business = businessDao.getById(busId);
        } catch (Exception e) {
            logger.error("", e);
            throw new ServiceException();
        }
        if (business == null) {
            throw new ServiceException("业务不存在");
        }

        if (BUS_STATUS.HAS_ISSUE == business.getStatus()) {
            throw new ServiceException("业务已经被标记为有问题");
        }

        if (examine.getStep() + 1 == business.getStatus()) {
            throw new ServiceException("业务已经检查过");
        }

        ExamineEntity examineEntity = new ExamineEntity();
        examineEntity.setId(StringTools.randomUUID());
        examineEntity.setHasIssue(examine.getHasIssue());

        String issueIdStr = examine.getIssueIdStrs();
        if (!StringTools.isEmpty(issueIdStr)) {
            String[] issueIds = issueIdStr.split(",");
            Set<BusIssueEntity> issues = businessIssueDao.getByIds(Arrays.asList(issueIds));
            examineEntity.setIssues(issues);
            examineEntity.setDescription(examine.getDescription());
        }

        switch (examine.getStep()) {
            case 1:
                business.setFirstExamine(examineEntity);
                break;
            case 2:
                business.setSecondExamine(examineEntity);
                break;
            case 3:
                business.setThirdExamine(examineEntity);
        }

        if (examine.getHasIssue()) {
            business.setStatus(BUS_STATUS.HAS_ISSUE);
            business.setAmendment(false);
        } else {
            business.setStatus((business.getStatus() + 1) > BUS_STATUS.THIRD ? BUS_STATUS.FINISH : business.getStatus() + 1);
        }

        try {

            businessDao.update(business);
        } catch (Exception e) {
            logger.error("", e);
            throw new ServiceException();
        }


    }

    @Transactional
    @Override
    public void commitAmendment(String[] ids) throws ServiceException {

        try {
            for (String id : ids) {
                BusinessEntity business = businessDao.getById(id);
                if (business == null) {
                    throw new ServiceException("业务不存在");
                }
                business.setStatus(BUS_STATUS.FINISH);
                business.setAmendment(true);
                businessDao.update(business);
            }
        } catch (Exception e) {
            logger.error("", e);
            throw new ServiceException();
        }
    }

    @Transactional
    @Override
    public ExamineModel examineDetail(String busId, String step) throws ServiceException {
        ExamineModel examineModel = new ExamineModel();
        try {
            BusinessEntity businessEntity = businessDao.getById(busId);
            ExamineEntity examineEntity = null;
            if ("1".equals(step)) {
                examineEntity = businessEntity.getFirstExamine();
            } else if ("2".equals(step)) {
                examineEntity = businessEntity.getSecondExamine();
            } else if ("3".equals(step)) {
                examineEntity = businessEntity.getThirdExamine();
            }
            if (examineEntity != null) {
                BeanUtils.copyProperties(examineEntity, examineModel);
                Set<BusIssueEntity> issues = examineEntity.getIssues();
                if (issues != null) {
                    Set<BusIssueModel> busIssueModels = new HashSet<>();
                    for (BusIssueEntity issue : issues) {
                        BusIssueModel busIssueModel = new BusIssueModel();
                        BeanUtils.copyProperties(issue, busIssueModel);
                        busIssueModels.add(busIssueModel);
                    }
                    examineModel.setIssues(busIssueModels);
                }
            }
        } catch (Exception e) {
            logger.error("", e);
            throw new ServiceException();
        }
        return examineModel;
    }

    @Transactional
    @Override
    public void addAttachment(String id, Function<String, String> getAbsPath, String fileName, InputStream is) throws ServiceException {
        String finalName = null;
        BusinessEntity businessEntity = null;
        try {
            businessEntity = businessDao.getById(id);
        } catch (Exception e){
            logger.error("", e);
            throw new ServiceException();
        }
        if (businessEntity == null){
            throw new ServiceException("业务记录不存在");
        }

        final StringBuilder url = new StringBuilder();

        try {
            UploadTools.save(is, UploadTools.UPLOAD_TYPE.TAX, fileName, path -> {
                url.append(path).append("/").append(id).append("/").append(fileName);
                return getAbsPath.apply(path) + File.separator + id;
            });
        } catch (IOException e) {
            throw new ServiceException( fileName + "文件保存错误");
        }

        try{

            Set<BusAttachmentEntity> attachmentEntities = businessEntity.getAttachments();
            if (attachmentEntities == null){
                attachmentEntities = new HashSet<>();
            }
            BusAttachmentEntity attachmentEntity = new BusAttachmentEntity();
            attachmentEntity.setId(StringTools.randomUUID());
            attachmentEntity.setSort(attachmentEntities.size() + 1);
            attachmentEntity.setUri(url.toString().replace("\\", "/"));
            attachmentEntity.setBusiness(businessEntity);
            attachmentEntities.add(attachmentEntity);
            businessEntity.setAttachments(attachmentEntities);
            businessDao.update(businessEntity);
        } catch (Exception e) {
            logger.error("", e);
            throw new ServiceException();
        }

    }

    @Transactional
    @Override
    public List<BusAttachmentModel> listAttachment(String busId) throws ServiceException {
        List<BusAttachmentModel> result = new ArrayList<>();
        try{
            BusinessEntity businessEntity = businessDao.getById(busId);
            Set<BusAttachmentEntity> attachmentEntities = businessEntity.getAttachments();
            if (attachmentEntities!=null){
                attachmentEntities.forEach(busAttachmentEntity -> {
                    BusAttachmentModel busAttachmentModel = new BusAttachmentModel();
                    BeanUtils.copyProperties(busAttachmentEntity, busAttachmentModel);
                    result.add(busAttachmentModel);
                });
            }
            return result;
        } catch (Exception e){
            logger.error("", e);
            throw new ServiceException();
        }
    }
}
