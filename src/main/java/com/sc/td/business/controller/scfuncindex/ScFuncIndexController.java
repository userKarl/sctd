package com.sc.td.business.controller.scfuncindex;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sc.td.business.service.scfuncindex.ScFuncIndexService;
import com.sc.td.common.config.SystemConfig;
import com.sc.td.common.utils.StringUtils;
import com.sc.td.common.utils.image.Pic;

@Controller
@RequestMapping("/operate/scfuncindex")
public class ScFuncIndexController {

	@Autowired
	private ScFuncIndexService scFuncIndexService;
	
	@Autowired
	private SystemConfig systemConfig;

	
	/**
	 * 获取指标信息
	 * 
	 * @param propertyName
	 * @return
	 */
	
	@RequestMapping(value = "/getIdxInfo", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getIdxInfo() {
		return scFuncIndexService.getIdxInfo();
	}
	
	/**
	 * 获得图标
	 * 
	 * @param request
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/getIcon", method = { RequestMethod.POST, RequestMethod.GET })
	public void getIcon(HttpServletRequest request, HttpServletResponse response, String fileName)
			throws IOException {
		String path = request.getServletContext().getRealPath("/") + systemConfig.getUserfilesIconUrl();
		Pic pic = new Pic();
		BufferedImage img = pic.loadImageLocal(path + fileName);
		String suffixImg=null;
		// 将文件上传至服务器
		if (img != null && StringUtils.isNotBlank(fileName)) {
			suffixImg=fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
			response.setContentType("image/"+suffixImg);
			OutputStream os = response.getOutputStream();
			ImageIO.write(img, suffixImg, os);
		}
	}
}
