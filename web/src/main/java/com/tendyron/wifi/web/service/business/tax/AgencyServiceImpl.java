package com.tendyron.wifi.web.service.business.tax;

import com.tendyron.wifi.web.dao.business.tax.AgencyDao;
import com.tendyron.wifi.web.entity.business.tax.AgencyEntity;
import com.tendyron.wifi.web.model.business.tax.AgencyModel;
import com.tendyron.wifi.web.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neo on 2017/5/12.
 */

@Service
public class AgencyServiceImpl extends BaseServiceImpl<AgencyEntity> implements AgencyService {

    private final static Logger logger = LoggerFactory.getLogger(AgencyServiceImpl.class);

    @Autowired
    private AgencyDao agencyDao;

    @Override
    @Transactional
    public List<AgencyModel> list() throws SerialException {

        List<AgencyModel> ams = new ArrayList<>();

        try {
            List<AgencyEntity> aes = agencyDao.getList();

            for (AgencyEntity ae : aes){
                AgencyModel am = new AgencyModel();
                BeanUtils.copyProperties(ae, am);
                ams.add(am);
            }
        } catch (Exception e) {
            logger.error("", e);
            throw new SerialException();
        }

        return ams;
    }
}
