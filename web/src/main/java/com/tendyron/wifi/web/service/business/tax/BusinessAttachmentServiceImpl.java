package com.tendyron.wifi.web.service.business.tax;

import com.tendyron.wifi.web.dao.business.tax.BusinessAttachmentDao;
import com.tendyron.wifi.web.entity.business.tax.BusAttachmentEntity;
import com.tendyron.wifi.web.service.BaseServiceImpl;
import com.tendyron.wifi.web.service.ServiceException;
import com.tendyron.wifi.web.utils.UploadTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.function.Function;

/**
 * Created by Neo on 2017/6/1.
 */
@Service
public class BusinessAttachmentServiceImpl extends BaseServiceImpl<BusAttachmentEntity> implements BusinessAttachmentService {

    private static final Logger logger = LoggerFactory.getLogger(BusinessAttachmentServiceImpl.class);

    @Autowired
    private BusinessAttachmentDao businessAttachmentDao;

    @Override
    public void del(String[] ids) throws ServiceException {
        try {
            businessAttachmentDao.delByIds(ids);
        } catch (Exception e) {
            logger.error("", e);
            throw new ServiceException();
        }
    }

    @Transactional
    @Override
    public void del(String id, Function<String, String> getAbsPath) throws ServiceException {
        BusAttachmentEntity busAttachmentEntity;
        try {
            busAttachmentEntity = businessAttachmentDao.getById(id);
        } catch (Exception e) {
            logger.error("", e);
            throw new ServiceException();
        }

        try {
            UploadTools.del(getAbsPath.apply(busAttachmentEntity.getUrl()));
        } catch (IOException e) {
            logger.error("", e);
            throw new ServiceException();
        }
        try {
            businessAttachmentDao.delete(businessAttachmentDao.getById(id));
        } catch (Exception e) {
            logger.error("", e);
            throw new ServiceException();
        }
    }
}
