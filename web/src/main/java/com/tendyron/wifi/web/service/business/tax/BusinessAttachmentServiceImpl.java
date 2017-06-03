package com.tendyron.wifi.web.service.business.tax;

import com.tendyron.wifi.web.dao.business.tax.BusinessAttachmentDao;
import com.tendyron.wifi.web.dao.business.tax.BusinessDao;
import com.tendyron.wifi.web.entity.business.tax.BusAttachmentEntity;
import com.tendyron.wifi.web.entity.business.tax.BusinessEntity;
import com.tendyron.wifi.web.model.business.tax.BusAttachmentModel;
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
import java.util.function.Function;

/**
 * Created by Neo on 2017/6/1.
 */
@Service
public class BusinessAttachmentServiceImpl extends BaseServiceImpl<BusAttachmentEntity> implements BusinessAttachmentService {

    private static final Logger logger = LoggerFactory.getLogger(BusinessAttachmentServiceImpl.class);

    @Autowired
    private BusinessDao businessDao;
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

    @Transactional
    @Override
    public String add(String id, Function<String, String> getAbsPath, String fileName, InputStream is) throws ServiceException {
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
        final long size;
        String randomFileName = StringTools.randomUUID();
        try {
            File finalFile = UploadTools.save(is, UploadTools.UPLOAD_TYPE.TAX, randomFileName, path -> {
                url.append(path).append("/").append(id).append("/").append(randomFileName);
                return getAbsPath.apply(path) + File.separator + id;
            });
            size = finalFile.length();
        } catch (IOException e) {
            throw new ServiceException( fileName + "文件保存错误");
        }

        try{

            BusAttachmentEntity attachmentEntity = new BusAttachmentEntity();
            attachmentEntity.setId(StringTools.randomUUID());
            attachmentEntity.setUrl(url.toString().replace("\\", "/"));
            attachmentEntity.setSize(size);
            attachmentEntity.setFileName(fileName);
            attachmentEntity.setBusiness(businessEntity);

            businessAttachmentDao.save(attachmentEntity);
            return attachmentEntity.getId();
        } catch (Exception e) {
            logger.error("", e);
            throw new ServiceException();
        }

    }

    @Transactional
    @Override
    public BusAttachmentModel getById(String id) throws ServiceException {
        BusAttachmentModel busAttachmentModel = new BusAttachmentModel();
        try {
            BusAttachmentEntity busAttachmentEntity = businessAttachmentDao.getById(id);
            BeanUtils.copyProperties(busAttachmentEntity, busAttachmentModel);
        } catch (Exception e){
            logger.error("", e);
            throw new ServiceException();
        }
        return busAttachmentModel;
    }
}
