package com.tendyron.wifi.web.service.business.tax;

import com.tendyron.wifi.web.dao.business.tax.BusinessCategoryDao;
import com.tendyron.wifi.web.dao.business.tax.BusinessCategoryTypeDao;
import com.tendyron.wifi.web.entity.business.tax.BusCategoryEntity;
import com.tendyron.wifi.web.entity.business.tax.BusCategoryTypeEntity;
import com.tendyron.wifi.web.model.business.tax.BusCategoryTypeModel;
import com.tendyron.wifi.web.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neo on 2017/5/10.
 */
@Service
public class BusinessCategoryTypeServiceImpl extends BaseServiceImpl<BusCategoryTypeEntity> implements BusinessCategoryTypeService {

    private static final Logger logger = LoggerFactory.getLogger(BusinessCategoryTypeServiceImpl.class);

    @Autowired
    private BusinessCategoryTypeDao businessCategoryTypeDao;

    @Override
    public List<BusCategoryTypeModel> list() {

        List<BusCategoryTypeModel> result = new ArrayList<>();

        try {
            List<BusCategoryTypeEntity> bces = businessCategoryTypeDao.getList();
        } catch (Exception e) {
            logger.error("",e);
        }
        return null;
    }
}
