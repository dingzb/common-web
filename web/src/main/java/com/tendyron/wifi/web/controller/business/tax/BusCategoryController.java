package com.tendyron.wifi.web.controller.business.tax;

import com.tendyron.wifi.web.controller.BaseController;
import com.tendyron.wifi.web.model.Json;
import com.tendyron.wifi.web.service.ServiceException;
import com.tendyron.wifi.web.service.business.tax.BusinessCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Neo on 2017/5/10.
 */
@Controller
@RequestMapping("tax/business/category")
public class BusCategoryController extends BaseController {

    @Autowired
    private BusinessCategoryService businessCategoryService;

    @RequestMapping("list")
    @ResponseBody
    public Json list(@RequestParam(value = "typeId", required = false) String typeId) {
        try {
            return success(businessCategoryService.list(typeId));
        } catch (ServiceException e) {
            return fail(e);
        }
    }
}
