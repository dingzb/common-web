package com.tendyron.wifi.web.service.business.tax;

import com.tendyron.wifi.web.dao.business.tax.BusinessIssueDao;
import com.tendyron.wifi.web.entity.business.tax.BusIssueEntity;
import com.tendyron.wifi.web.model.business.tax.BusIssueModel;
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
 * Created by Neo on 2017/5/11.
 */

@Service
public class BusinessIssueServiceImpl extends BaseServiceImpl<BusIssueEntity> implements BusinessIssueService {

    private static final Logger logger = LoggerFactory.getLogger(BusinessIssueServiceImpl.class);

    @Autowired
    private BusinessIssueDao businessIssueDao;

    @Override
    @Transactional
    public List<BusIssueModel> list() throws ServiceException {

        List<BusIssueModel> bims = new ArrayList<>();

        try{
            List<BusIssueEntity> bies = businessIssueDao.getList();
            for (BusIssueEntity bie : bies) {
                BusIssueModel bim = new BusIssueModel();
                BeanUtils.copyProperties(bie, bim);
                bims.add(bim);
            }
        }catch (Exception e){
            logger.error("", e);
            throw new ServiceException();
        }

        return bims;
    }
}
