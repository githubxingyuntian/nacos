package com.alibaba.nacos.config.server.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.nacos.config.server.constant.Constants;
import com.alibaba.nacos.config.server.model.ConfigHistoryInfo;
import com.alibaba.nacos.config.server.model.Page;
import com.alibaba.nacos.config.server.service.PersistService;

/**
 * 管理控制器。 
 *
 *@author Nacos
 */
@Controller
@RequestMapping(Constants.HISTORY_CONTROLLER_PATH)
public class HistoryController {

	@Autowired
	protected PersistService persistService;

	@RequestMapping(params = "search=accurate", method = RequestMethod.GET)
	@ResponseBody
	public Page<ConfigHistoryInfo> listConfigHistory(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("dataId") String dataId, //
			@RequestParam("group") String group, //
			@RequestParam(value = "tenant", required = false, defaultValue = StringUtils.EMPTY) String tenant,
			@RequestParam(value = "appName", required = false) String appName,
			@RequestParam(value = "pageNo", required = false) Integer pageNo, //
			@RequestParam(value = "pageSize", required = false) Integer pageSize, //
			ModelMap modelMap) {
		pageNo = null == pageNo ? Integer.valueOf(1) : pageNo;
		pageSize = null == pageSize ? Integer.valueOf(100) : pageSize;
		pageSize = pageSize > 500 ? Integer.valueOf(500) : pageSize;
		// configInfoBase没有appName字段
		Page<ConfigHistoryInfo> page = persistService.findConfigHistory(dataId, group, tenant, pageNo, pageSize);
		return page;
	}
	
	/**
	 * 查看配置历史信息详情
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ConfigHistoryInfo getConfigHistoryInfo(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("nid") Long nid, ModelMap modelMap) {
		ConfigHistoryInfo configInfo = persistService.detailConfigHistory(nid);
		return configInfo;
	}

}
