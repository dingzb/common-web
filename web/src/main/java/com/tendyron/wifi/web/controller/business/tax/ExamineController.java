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
 * Created by Neo on 2017/5/17.
 */

@Controller
@RequestMapping("tax/examine")
public class ExamineController extends BaseController {

    @Autowired
    private BusinessService businessService;

    @RequestMapping("paging/committed")
    @ResponseBody
    public Json pagingCommitted(BusinessQuery query){
        try {
            return success(businessService.pagingCommitted(query));
        } catch (ServiceException e) {
            return fail(e);
        }
    }
}
