package com.tendyron.wifi.web.service;

import com.tendyron.wifi.web.service.InitServiceImpl.InitProcess;

public interface InitService {

    /**
     * 初始化
     *
     * @throws ServiceException
     */
    void init(InitProcess process) throws ServiceException;

}
