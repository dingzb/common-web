package com.tendyron.wifi.web.controller.business.tax;

import com.tendyron.wifi.web.controller.BaseController;
import com.tendyron.wifi.web.model.Json;
import com.tendyron.wifi.web.query.business.tax.FenjuQuery;
import com.tendyron.wifi.web.query.business.tax.StatisticsQuery;
import com.tendyron.wifi.web.query.business.tax.XianjuQuery;
import com.tendyron.wifi.web.service.ServiceException;
import com.tendyron.wifi.web.service.business.tax.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Neo on 2017/5/11.
 * 统计
 */

@Controller
@RequestMapping("tax/statistics")
public class StatisticsController extends BaseController{

    @Autowired
    private BusinessService businessService;

    @ResponseBody
    @RequestMapping("xianju")
    public Json xianju(XianjuQuery query){
        try {
            return success(businessService.xianju(query));
        } catch (ServiceException e) {
            return fail();
        }
    }

    @ResponseBody
    @RequestMapping("fenju")
    public Json fenju(FenjuQuery query){
        try {
            return success(businessService.fenju(query));
        } catch (ServiceException e) {
            return fail();
        }
    }

    @ResponseBody
    @RequestMapping("person")
    public Json person(StatisticsQuery query){
        try {
            return success(businessService.person(query));
        } catch (ServiceException e) {
            return fail();
        }
    }
}
