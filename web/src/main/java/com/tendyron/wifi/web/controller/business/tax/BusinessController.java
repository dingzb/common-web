package com.tendyron.wifi.web.controller.business.tax;

import com.tendyron.wifi.web.controller.BaseController;
import com.tendyron.wifi.web.model.Json;
import com.tendyron.wifi.web.model.business.tax.BusinessModel;
import com.tendyron.wifi.web.query.business.tax.BusinessQuery;
import com.tendyron.wifi.web.service.ServiceException;
import com.tendyron.wifi.web.service.business.tax.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Neo on 2017/5/10.
 */

@Controller("businessController")
@RequestMapping("tax/business")
public class BusinessController extends BaseController {

    @Autowired
    private BusinessService businessService;

    @RequestMapping("paging")
    @ResponseBody
    public Json paging(BusinessQuery query) {
        try {
            return success(businessService.paging(query));
        } catch (ServiceException e) {
            return fail(e);
        }
    }

    @RequestMapping("add")
    @ResponseBody
    public Json add(BusinessModel business) {
        try {
            businessService.add(business);
        } catch (ServiceException e) {
            return fail(e);
        }
        return success("保存成功");
    }

    @RequestMapping("edit")
    @ResponseBody
    public Json edit(BusinessModel business) {
        try {
            businessService.edit(business);
        } catch (ServiceException e) {
            return fail(e);
        }
        return success("编辑成功");
    }

    @RequestMapping("del")
    @ResponseBody
    public Json del(@RequestParam("ids[]") String[] ids){
        Integer result = null;
        try {
            result = businessService.del(ids);
        } catch (ServiceException e) {
            return fail(e.getMessage());
        }
        return success("成功删除" + result + "条数据");
    }
}
