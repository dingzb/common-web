package com.tendyron.wifi.web.controller.business.tax;

import com.tendyron.wifi.web.controller.BaseController;
import com.tendyron.wifi.web.model.Json;
import com.tendyron.wifi.web.query.business.tax.AgencyQuery;
import com.tendyron.wifi.web.service.ServiceException;
import com.tendyron.wifi.web.service.business.tax.AgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public Json list(@RequestParam(value = "level", required = false) String level) {
        try {
            return success(agencyService.list(level));
        } catch (ServiceException e) {
            return fail(e);
        }
    }

    /**
     * 当前用户所在机构的所有基层用户
     *
     * @param query
     * @return
     */
    @RequestMapping("user/list")
    @ResponseBody
    public Json userlist(AgencyQuery query) {
        try {
            return success(agencyService.userList(query));
        } catch (ServiceException e) {
            return fail(e);
        }
    }

    /**
     * 当前用户所在机构的所有基层用户
     *
     * @param query
     * @return
     */
    @RequestMapping("current")
    @ResponseBody
    public Json current(AgencyQuery query) {
        try {
            return success(agencyService.current(query));
        } catch (ServiceException e) {
            return fail(e);
        }
    }
}
