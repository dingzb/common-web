package com.tendyron.wifi.web.service.business.tax;

import com.tendyron.wifi.web.entity.business.tax.BusinessEntity;
import com.tendyron.wifi.web.model.PagingModel;
import com.tendyron.wifi.web.model.business.tax.BusAttachmentModel;
import com.tendyron.wifi.web.model.business.tax.BusinessModel;
import com.tendyron.wifi.web.model.business.tax.ExamineModel;
import com.tendyron.wifi.web.model.business.tax.statistics.FenjuModel;
import com.tendyron.wifi.web.model.business.tax.statistics.StatisticsCategoryTypeModel;
import com.tendyron.wifi.web.model.business.tax.statistics.XianjuModel;
import com.tendyron.wifi.web.query.business.tax.BusinessQuery;
import com.tendyron.wifi.web.query.business.tax.FenjuQuery;
import com.tendyron.wifi.web.query.business.tax.StatisticsQuery;
import com.tendyron.wifi.web.query.business.tax.XianjuQuery;
import com.tendyron.wifi.web.service.BaseService;
import com.tendyron.wifi.web.service.ServiceException;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.List;
import java.util.function.Function;

/**
 * Created by Neo on 2017/5/9.
 */
public interface BusinessService extends BaseService<BusinessEntity> {

    /**
     * 基于登陆用户的分页获取
     *
     * @param query
     * @return
     * @throws ServiceException
     */
    PagingModel pagingBaseUser(BusinessQuery query) throws ServiceException;

//    /**
//     * 获取所有业务分页数据
//     *
//     * @param query
//     * @return
//     * @throws ServiceException
//     */
//    PagingModel paging(BusinessQuery query) throws ServiceException;

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
     * @param query
     * @return
     * @throws ServiceException
     */
    PagingModel pagingSecond(BusinessQuery query) throws ServiceException;

    /**
     * @param query
     * @return
     * @throws ServiceException
     */
    PagingModel pagingThird(BusinessQuery query) throws ServiceException;

    /**
     * 查询需要整改的业务
     *
     * @param query
     * @return
     * @throws ServiceException
     */
    PagingModel pagingAmendment(BusinessQuery query) throws ServiceException;

    /**
     * 统计页面 业务详情专用
     *
     * @param query
     * @return
     * @throws ServiceException
     */
    PagingModel pagingError(BusinessQuery query) throws ServiceException;

    @Transactional
    PagingModel paging(BusinessQuery query) throws ServiceException;

    void add(BusinessModel businessModel) throws ServiceException;

    void edit(BusinessModel business) throws ServiceException;

    Integer del(String[] ids) throws ServiceException;

    /**
     * 县局报表统计
     *
     * @param query
     * @return
     * @throws ServiceException
     */
    List<XianjuModel> xianju(XianjuQuery query) throws ServiceException;

    /**
     * 分局报表统计
     *
     * @param query
     * @return
     * @throws ServiceException
     */
    List<FenjuModel> fenju(FenjuQuery query) throws ServiceException;

    /**
     * 个人报表统计
     *
     * @param query
     * @return
     * @throws ServiceException
     */
    List<StatisticsCategoryTypeModel> person(StatisticsQuery query) throws ServiceException;

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

    /**
     * 获取业务中问题详情
     *
     * @param busId
     * @param step
     * @return
     * @throws ServiceException
     */
    ExamineModel examineDetail(String busId, String step) throws ServiceException;


    List<BusAttachmentModel> listAttachment(String busId) throws ServiceException;
}
