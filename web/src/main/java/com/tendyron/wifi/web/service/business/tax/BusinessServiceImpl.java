package com.tendyron.wifi.web.service.business.tax;

import com.tendyron.wifi.web.dao.business.tax.AgencyDao;
import com.tendyron.wifi.web.dao.business.tax.BusinessCategoryDao;
import com.tendyron.wifi.web.dao.business.tax.BusinessDao;
import com.tendyron.wifi.web.dao.business.tax.BusinessIssueDao;
import com.tendyron.wifi.web.dao.system.UserDao;
import com.tendyron.wifi.web.entity.business.tax.*;
import com.tendyron.wifi.web.entity.system.UserEntity;
import com.tendyron.wifi.web.model.PagingModel;
import com.tendyron.wifi.web.model.business.tax.BusinessModel;
import com.tendyron.wifi.web.query.business.tax.BusinessQuery;
import com.tendyron.wifi.web.service.BaseServiceImpl;
import com.tendyron.wifi.web.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neo on 2017/5/9.
 */
@Service("businessService")
public class BusinessServiceImpl extends BaseServiceImpl<BusinessEntity> implements BusinessService {

    private static final Logger logger = LoggerFactory.getLogger(BusinessServiceImpl.class);
    @Autowired
    private BusinessDao businessDao;

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
                businessModel.setAgencyId(agencyEntity.getId());
                businessModel.setAgencyName(agencyEntity.getName());

                BusCategoryEntity categoryEntity = businessEntity.getCategory();
                businessModel.setCategoryId(categoryEntity.getId());
                businessModel.setCategoryName(categoryEntity.getName());

                BusCategoryTypeEntity categoryTypeEntity = categoryEntity.getType();
                businessModel.setCategoryTypeId(categoryTypeEntity.getId());
                businessModel.setCategoryTypeName(categoryTypeEntity.getName());

                BusIssueEntity issueEntity = businessEntity.getIssue();
                businessModel.setIssueId(issueEntity.getId());
                businessModel.setIssueName(issueEntity.getName());

                UserEntity create = businessEntity.getCreate();
                businessModel.setCreateId(create.getId());
                businessModel.setCreateName(create.getName());

                UserEntity check = businessEntity.getCheck();
                businessModel.setCheckId(check.getId());
                businessModel.setCheckName(check.getName());

                UserEntity finalCheck = businessEntity.getFinalCheck();
                businessModel.setFinalCheckId(finalCheck.getId());
                businessModel.setFinalCheckName(finalCheck.getName());

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
}
