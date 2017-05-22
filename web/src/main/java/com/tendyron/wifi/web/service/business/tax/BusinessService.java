package com.tendyron.wifi.web.service.business.tax;

import com.tendyron.wifi.web.entity.business.tax.BusinessEntity;
import com.tendyron.wifi.web.model.PagingModel;
import com.tendyron.wifi.web.model.business.tax.BusinessModel;
import com.tendyron.wifi.web.model.business.tax.ExamineModel;
import com.tendyron.wifi.web.model.business.tax.StatementModel;
import com.tendyron.wifi.web.query.business.tax.BusinessQuery;
import com.tendyron.wifi.web.query.business.tax.StatementQuery;
import com.tendyron.wifi.web.service.BaseService;
import com.tendyron.wifi.web.service.ServiceException;

import java.util.List;

/**
 * Created by Neo on 2017/5/9.
 */
public interface BusinessService extends BaseService<BusinessEntity> {

    /**
     * 获取所有业务分页数据
     *
     * @param query
     * @return
     * @throws ServiceException
     */
    PagingModel paging(BusinessQuery query) throws ServiceException;

    /**
     * 获取 新建业务
     *
     * @param query
     * @return
     * @throws ServiceException
     */
    PagingModel pagingCreated(BusinessQuery query) throws ServiceException;

    /**
     * 获取完成自查的业务
     *
     * @param query
     * @return
     * @throws ServiceException
     */
    PagingModel pagingFirst(BusinessQuery query) throws ServiceException;

    /**
     * 查询需要整改的业务
     *
     * @param query
     * @return
     * @throws ServiceException
     */
    PagingModel pagingAmendment(BusinessQuery query) throws ServiceException;

    void add(BusinessModel businessModel) throws ServiceException;

    void edit(BusinessModel business) throws ServiceException;

    Integer del(String[] ids) throws ServiceException;

    List<StatementModel> statement(StatementQuery query) throws ServiceException;

    /**
     * 提交业务
     *
     * @param ids
     * @throws ServiceException
     */
    void commit(String[] ids) throws ServiceException;

    /**
     * 提交检查结果
     *
     * @param examine
     * @throws ServiceException
     */
    void commitExamine(ExamineModel examine) throws ServiceException;

    /**
     * 提交整改
     *
     * @param ids
     * @throws ServiceException
     */
    void commitAmendment(String[] ids) throws ServiceException;
}
