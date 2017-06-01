package com.tendyron.wifi.web.controller.business.tax;

import com.tendyron.wifi.web.controller.BaseController;
import com.tendyron.wifi.web.entity.business.tax.BusinessEntity.BUS_STATUS;
import com.tendyron.wifi.web.model.Json;
import com.tendyron.wifi.web.model.business.tax.BusinessModel;
import com.tendyron.wifi.web.query.business.tax.BusinessQuery;
import com.tendyron.wifi.web.service.ServiceException;
import com.tendyron.wifi.web.service.business.tax.BusinessAttachmentService;
import com.tendyron.wifi.web.service.business.tax.BusinessService;
import com.tendyron.wifi.web.utils.StringTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Neo on 2017/5/10.
 */

@Controller("businessController")
@RequestMapping("tax/business")
public class BusinessController extends BaseController {

    @Autowired
    private BusinessService businessService;
    @Autowired
    private BusinessAttachmentService businessAttachmentService;

    @RequestMapping("paging/created")
    @ResponseBody
    public Json pagingCreated(BusinessQuery query) {
        try {
            return success(businessService.pagingCreated(query));
        } catch (ServiceException e) {
            return fail(e);
        }
    }

    @RequestMapping("paging/all")
    @ResponseBody
    public Json pagingAll(BusinessQuery query) {
        try {
            return success(businessService.pagingBaseUser(query));
        } catch (ServiceException e) {
            return fail(e);
        }
    }

    @RequestMapping("paging/amendment")
    @ResponseBody
    public Json pagingAmendment(BusinessQuery query) {
        try {
            return success(businessService.pagingAmendment(query));
        } catch (ServiceException e) {
            return fail(e);
        }
    }

    @RequestMapping("paging/error")
    @ResponseBody
    public Json pagingError(BusinessQuery query) {
        query.setStatus(BUS_STATUS.HAS_ISSUE);
        try {
            if (query.getHasIssue() == null) {
                return success(businessService.paging(query));
            } else {
                return success(businessService.pagingError(query));
            }
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

    @RequestMapping("attachment/upload")
    @ResponseBody
    public Map<String, String> attachmentUpload(@RequestParam(value = "attachment", required = false) MultipartFile file, @RequestParam("busId") String busId, HttpServletRequest request) {
        Map<String, String> json = new HashMap<>();
        if (file == null) {
            return json;
        }
        try {
            businessService.addAttachment(busId, path -> request.getServletContext().getRealPath(path), file.getOriginalFilename(), file.getInputStream());
        } catch (ServiceException | IOException e) {
            json.put("error", e.getMessage());
            return json;
        }

        return json;
    }

    @RequestMapping("attachment/list")
    @ResponseBody
    public Json attachmentList(@RequestParam("busId") String busId) {
        try {
            return success(businessService.listAttachment(busId));
        } catch (ServiceException e) {
            return fail(e);
        }
    }

    @RequestMapping("attachment/del")
    @ResponseBody
    public Map<String, String> attachmentDel(@RequestParam(value = "id[]", required = false) String[] ids,
                                             @RequestParam(value = "key", required = false) String id, HttpServletRequest request) {
        Map<String, String> json = new HashMap<>();
        try {
            if (!StringTools.isEmpty(ids)) {
                businessAttachmentService.del(ids);
            } else if (!StringTools.isEmpty(id)) {
                businessAttachmentService.del(id, path -> request.getServletContext().getRealPath(path));
            } else {
                json.put("error", "没有指定任何删除内容");
                return json;
            }
        } catch (ServiceException e) {
            json.put("error", e.getMessage());
            return json;
        }
        return json;
    }

    @RequestMapping("del")
    @ResponseBody
    public Json del(@RequestParam("ids[]") String[] ids) {
        Integer result = null;
        try {
            result = businessService.del(ids);
        } catch (ServiceException e) {
            return fail(e.getMessage());
        }
        return success("成功删除" + result + "条数据");
    }

    @RequestMapping("commit")
    @ResponseBody
    public Json commit(@RequestParam("ids[]") String[] ids) {
        try {
            businessService.commit(ids);
            return success("提交成功");
        } catch (ServiceException e) {
            return fail(e);
        }
    }

    @RequestMapping("commit/amendment")
    @ResponseBody
    public Json commitAmendment(@RequestParam("ids[]") String[] ids) {
        try {
            businessService.commitAmendment(ids);
            return success("提交成功");
        } catch (ServiceException e) {
            return fail(e);
        }
    }


    @RequestMapping("examine/detail")
    @ResponseBody
    public Json examineDetail(String busId, String step) {
        try {
            return success(businessService.examineDetail(busId, step));
        } catch (ServiceException e) {
            return fail(e);
        }
    }

}
