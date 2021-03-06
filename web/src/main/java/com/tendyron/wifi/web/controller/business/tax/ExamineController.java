package com.tendyron.wifi.web.controller.business.tax;

import com.tendyron.wifi.web.controller.BaseController;
import com.tendyron.wifi.web.entity.business.tax.BusinessEntity.BUS_STATUS;
import com.tendyron.wifi.web.model.Json;
import com.tendyron.wifi.web.model.business.tax.ExamineModel;
import com.tendyron.wifi.web.query.business.tax.BusinessQuery;
import com.tendyron.wifi.web.service.ServiceException;
import com.tendyron.wifi.web.service.business.tax.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Neo on 2017/5/17.
 */

@Controller
@RequestMapping("tax/examine")
public class ExamineController extends BaseController {

    @Autowired
    private BusinessService businessService;

    @RequestMapping("paging/first")
    @ResponseBody
    public Json pagingFirst(BusinessQuery query){
        try {
            return success(businessService.pagingFirst(query));
        } catch (ServiceException e) {
            return fail(e);
        }
    }

    @RequestMapping("paging/second")
    @ResponseBody
    public Json pagingSecond(BusinessQuery query){
        try {
            return success(businessService.pagingSecond(query));
        } catch (ServiceException e) {
            return fail(e);
        }
    }

    @RequestMapping("paging/third")
    @ResponseBody
    public Json pagingThird(BusinessQuery query){
        try {
            return success(businessService.pagingThird(query));
        } catch (ServiceException e) {
            return fail(e);
        }
    }

    @RequestMapping("first/commit")
    @ResponseBody
    public Json commitFirst(ExamineModel examine){
        examine.setStep(BUS_STATUS.FIRST);
        try {
            businessService.commitExamine(examine);
            return success("提交成功");
        } catch (ServiceException e) {
            return fail(e);
        }
    }

    @RequestMapping("second/commit")
    @ResponseBody
    public Json commitSecond(ExamineModel examine){
        examine.setStep(BUS_STATUS.SECOND);
        try {
            businessService.commitExamine(examine);
            return success("提交成功");
        } catch (ServiceException e) {
            return fail(e);
        }
    }

    @RequestMapping("third/commit")
    @ResponseBody
    public Json commitThird(ExamineModel examine){
        examine.setStep(BUS_STATUS.THIRD);
        try {
            businessService.commitExamine(examine);
            return success("提交成功");
        } catch (ServiceException e) {
            return fail(e);
        }
    }
}
