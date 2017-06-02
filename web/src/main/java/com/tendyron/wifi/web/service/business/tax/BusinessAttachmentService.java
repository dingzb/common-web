package com.tendyron.wifi.web.service.business.tax;

import com.tendyron.wifi.web.entity.business.tax.BusAttachmentEntity;
import com.tendyron.wifi.web.model.business.tax.BusAttachmentModel;
import com.tendyron.wifi.web.service.BaseService;
import com.tendyron.wifi.web.service.ServiceException;

import java.io.InputStream;
import java.util.function.Function;

/**
 * Created by Neo on 2017/6/1.
 */
public interface BusinessAttachmentService extends BaseService<BusAttachmentEntity> {
    void del(String[] ids) throws ServiceException;

    void del(String id, Function<String, String> getAbsPath) throws ServiceException;

    /**
     * 添加附件
     * @param id
     * @param getAbsPath
     * @param fileName
     * @param is
     * @return
     * @throws ServiceException
     */
    String add(String id, Function<String, String> getAbsPath, String fileName, InputStream is) throws ServiceException;

    BusAttachmentModel getById(String id) throws ServiceException;
}
