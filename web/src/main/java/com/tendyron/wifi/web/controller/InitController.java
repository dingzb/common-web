package com.tendyron.wifi.web.controller;

import com.tendyron.wifi.web.controller.BaseController;
import com.tendyron.wifi.web.model.Json;
import com.tendyron.wifi.web.model.system.RoleModel;
import com.tendyron.wifi.web.model.system.StateModel;
import com.tendyron.wifi.web.service.InitService;
import com.tendyron.wifi.web.service.InitServiceImpl;
import com.tendyron.wifi.web.service.InitServiceImpl.InitProcess;
import com.tendyron.wifi.web.service.ServiceException;
import com.tendyron.wifi.web.service.system.RoleService;
import com.tendyron.wifi.web.service.system.StateService;
import com.tendyron.wifi.web.utils.StringTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller("initController")
@RequestMapping("init")
public class InitController extends BaseController {

	@Autowired
	private InitService initService;

	@RequestMapping("")
	public String index() {
		// TODO 判断数据库中是否有数据
		return "init/index";
	}

	@RequestMapping("init")
	@ResponseBody
	public Json init(HttpServletRequest req) {
		InitProcess process = new InitProcess();
		req.getServletContext().setAttribute("INIT_PROCESS", process);
		try {
			initService.init(process);
			return success("Init success!");
		} catch (ServiceException e) {
			return fail(e);
		}
	}

	@RequestMapping("process")
	@ResponseBody
	public Json initProcess (HttpServletRequest req) {
		InitProcess process = (InitProcess) req.getServletContext().getAttribute("INIT_PROCESS");
		return success(process);
	}

}
