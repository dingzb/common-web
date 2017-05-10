package com.tendyron.wifi.web.controller.business.tax;

import com.tendyron.wifi.web.controller.BaseController;
import com.tendyron.wifi.web.model.Json;
import com.tendyron.wifi.web.query.business.tax.BusinessQuery;
import com.tendyron.wifi.web.service.ServiceException;
import com.tendyron.wifi.web.service.business.tax.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
