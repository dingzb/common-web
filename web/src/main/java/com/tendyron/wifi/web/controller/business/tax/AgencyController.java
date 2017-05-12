package com.tendyron.wifi.web.controller.business.tax;

import com.tendyron.wifi.web.controller.BaseController;
import com.tendyron.wifi.web.model.Json;
import com.tendyron.wifi.web.service.business.tax.AgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.rowset.serial.SerialException;

/**
 * Created by Neo on 2017/5/12.
 */
@Controller
@RequestMapping("tax/agency")
public class AgencyController extends BaseController {

    @Autowired
    private AgencyService agencyService;

    @RequestMapping("list")
    @ResponseBody
    public Json list(){
        try {
            return success(agencyService.list());
        } catch (SerialException e) {
            return fail(e);
        }
    }
}
