package com.sc.td.business.controller.scuser;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sc.td.business.base.BaseController;
import com.sc.td.business.service.scuser.ScUserService;
import com.sc.td.common.config.SystemConfig;
import com.sc.td.common.utils.MacUtils;
import com.sc.td.common.utils.StringUtils;
import com.sc.td.common.utils.SystemPath;
import com.sc.td.common.utils.image.Pic;

@Controller
@RequestMapping("/operate/scuser")
public class ScUserController extends BaseController {

	@Autowired
	private ScUserService scUserService;

	@Autowired
	private SystemConfig systemConfig;
	
	/**
	 * 验证登录 调用此方法即重新生成token
	 * 
	 * @param jsonText
	 * @return
	 * @throws UnknownHostException
	 * @
	 */
	@RequestMapping(value = "/checklogin", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String checklogin(String jsonText) throws UnknownHostException {
		return scUserService.checklogin(jsonText);
	}

	/**
	 * 验证token
	 * 
	 * @param token
	 * @return
	 * @throws UnknownHostException
	 */

	@RequestMapping(value = "/checktoken", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String checktoken(HttpServletRequest request) throws UnknownHostException {
		String token = request.getHeader("token");
		return scUserService.checktoken(token);
	}

	/**
	 * 用户注册
	 * 
	 * @param jsonText
	 * @return
	 * @throws UnknownHostException
	 * @throws UnsupportedEncodingException
	 * @
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String register(HttpServletRequest request, String jsonText)
			throws UnknownHostException, UnsupportedEncodingException {
		String path = SystemPath.getClassPath().replace("//", File.separator)
				.replace("classes", "userfiles" + File.separator + "images");
		if(!MacUtils.getOSName().startsWith("windows")){
			path=File.separator+path;
		}
		return scUserService.register(jsonText, path);
	}

	
	/**
	 * 发送手机验证码
	 * 
	 * @param request
	 * @param jsonText
	 * @return @
	 */
	@RequestMapping(value = "/sendsms", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String sendsms(HttpServletRequest request, String jsonText) {
		return scUserService.sendsms(jsonText, request);
	}

	/**
	 * 获得头像
	 * 
	 * @param request
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/getImage", method = { RequestMethod.POST, RequestMethod.GET })
	public void getImageUrl(HttpServletRequest request, HttpServletResponse response, String fileName)
			throws IOException {
		String path = request.getServletContext().getRealPath("/") + systemConfig.getUserfilesImgUrl();
		Pic pic = new Pic();
		BufferedImage img = pic.loadImageLocal(path + fileName);
		String suffixImg = null;
		if (img != null && StringUtils.isNotBlank(fileName)) {
			suffixImg = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			response.setContentType("image/" + suffixImg);
			OutputStream os = response.getOutputStream();
			ImageIO.write(img, suffixImg, os);
		}
	}

	/**
	 * 用户登出
	 * 
	 * @param request
	 * @return
	 * @throws UnknownHostException
	 * @
	 */
	@RequestMapping(value = "/logout_ckt", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String logout(HttpServletRequest request, String jsonText) throws UnknownHostException {
		String token = request.getHeader("token");
		return scUserService.logout(token, jsonText);
	}

	/**
	 * 上传头像
	 * 
	 * @param request
	 * @param jsonText
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/uploadImg_ckt", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String uploadImg(HttpServletRequest request, String jsonText) throws UnknownHostException {
		String path = request.getServletContext().getRealPath("/") + systemConfig.getUserfilesImgUrl();
		String resp=scUserService.uploadImg(jsonText, path);
		return resp;
	}

	/**
	 * 登录时忘记密码
	 * 
	 * @param request
	 * @param jsonText
	 * @return
	 */
	@RequestMapping(value = "/fgtpwd", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String fgtpwd(HttpServletRequest request, String jsonText) {
		return scUserService.fgtpwd(jsonText);
	}

	/**
	 * 个人信息中重置密码
	 * 
	 * @param request
	 * @param jsonText
	 * @return
	 */
	@RequestMapping(value = "/resetpwd_ckt", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String resetpwd_ckt(HttpServletRequest request, String jsonText) {
		return scUserService.resetpwd(jsonText);
	}
	
	/**
	 * 获取用户信息
	 * 
	 * @param request
	 * @param jsonText
	 * @return
	 */
	@RequestMapping(value = "/getScUserInfo_ckt", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getScUserInfo_ckt(HttpServletRequest request, String jsonText) {
		return scUserService.getScUserInfo(jsonText);
	}
	
}
