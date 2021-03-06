package com.tendyron.wifi.web.controller.business.tax;

import com.tendyron.wifi.web.controller.BaseController;
import com.tendyron.wifi.web.model.Json;
import com.tendyron.wifi.web.service.ServiceException;
import com.tendyron.wifi.web.service.business.tax.BusinessIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Neo on 2017/5/11.
 */

@Controller
@RequestMapping("tax/business/issue")
public class BusIssueController extends BaseController {

    @Autowired
    private BusinessIssueService businessIssueService;

    @RequestMapping("list")
    @ResponseBody
    public Json list() {
        try {
            return success(businessIssueService.list());
        } catch (ServiceException e) {
            return fail(e);
        }
    }
}
