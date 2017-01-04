package com.sc.td.business.service.scuser;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.sc.td.business.base.BaseService;
import com.sc.td.business.dao.SysDict.SysDictDao;
import com.sc.td.business.dao.scgroup.GroupDao;
import com.sc.td.business.dao.scmoney.ScMoneyDao;
import com.sc.td.business.dao.scpklevel.ScPklevelDao;
import com.sc.td.business.dao.scright.ScRightDao;
import com.sc.td.business.dao.scuser.ScUserDao;
import com.sc.td.business.entity.scgroup.Group;
import com.sc.td.business.entity.scmoney.ScMoney;
import com.sc.td.business.entity.scpklevel.ScPklevel;
import com.sc.td.business.entity.scright.ScRight;
import com.sc.td.business.entity.scuser.ScUser;
import com.sc.td.business.entity.sysdict.SysDict;
import com.sc.td.business.service.scgroup.ScGroupService;
import com.sc.td.common.config.Global;
import com.sc.td.common.config.SystemConfig;
import com.sc.td.common.utils.GetHttpMsg;
import com.sc.td.common.utils.IdGen;
import com.sc.td.common.utils.ParseXml;
import com.sc.td.common.utils.RandomCode;
import com.sc.td.common.utils.StringUtils;
import com.sc.td.common.utils.calc.ArithDecimal;
import com.sc.td.common.utils.datetime.TimeUtil;
import com.sc.td.common.utils.image.ImgBase64;
import com.sc.td.common.utils.image.ImgCreate;
import com.sc.td.common.utils.ip.IpUtils;
import com.sc.td.common.utils.json.CreateJson;
import com.sc.td.common.utils.json.JacksonUtil;
import com.sc.td.common.utils.jwt.Jwt;
import com.sc.td.common.utils.redis.RedisService;
import com.sc.td.frame.DictConfig;
import com.sc.td.frame.RespInfoConfig;

@Service
public class ScUserService extends BaseService {

	@Autowired
	private ScUserDao scUserDao;

	@Autowired
	private SysDictDao sysDictDao;

	@Autowired
	private ScMoneyDao scMoneyDao;

	@Autowired
	private RedisService redisService;

	@Autowired
	private RespInfoConfig respInfoConfig;

	@Autowired
	private DictConfig dictConfig;

	@Autowired
	private ScRightDao scRightDao;

	@Autowired
	private GroupDao groupDao;

	@Autowired
	private ScPklevelDao scPklevelDao;

	@Autowired
	private ScGroupService scGroupService;

	@Autowired
	private SystemConfig systemConfig;

	/**
	 * 验证登录
	 * 
	 * @param jsonText
	 * @return
	 * @throws UnknownHostException
	 */
	public String checklogin(String jsonText) throws UnknownHostException {
		ScUser scUser = null;
		// 解析json数据，看是否正确返回ScUser对象
		scUser = JacksonUtil.jsonToObj(jsonText, ScUser.class);
		if (scUser == null || StringUtils.isBlank(scUser.getMobile())) {
			return CreateJson.createTextJson(respInfoConfig.getParamsNull(), false);
		} else {
			String token_new = null;
			/**
			 * 验证手机号密码
			 */
			String mobile = scUser.getMobile();
			String password = scUser.getPassword();
			password = DigestUtils.md5Hex(password);

			List<ScUser> scUserlist = null;
			scUserlist = scUserDao.findByMobileAndPassword(mobile, password);

			if (scUserlist != null && scUserlist.size() > 0) {
				// 验证成功
				// 生成新的token
				token_new = createToken(scUserlist.get(0));
				// 将ScUser的基本信息以及token返回给客户端
				ScUser scUser_new = getScUser(scUserlist.get(0));
				String resp = getResp(token_new, scUser_new);
				return resp;
			} else {
				// 验证失败，需重新登录
				return CreateJson.createTextJson(respInfoConfig.getMobileOrPwdError(), false);
			}
		}
	}

	/**
	 * 验证token
	 * 
	 * @param token
	 * @return
	 * @throws UnknownHostException
	 */
	@SuppressWarnings("unchecked")
	public String checktoken(String token) throws UnknownHostException {

		// token为空直接返回null
		if (StringUtils.isBlank(token)) {
			return CreateJson.createTextJson(respInfoConfig.getParamsNull(), false);
		} else {
			// 如果token不为空，则验证token
			// 1、将token解析出来
			Map<String, Object> resultMap = Jwt.validToken(token);
			String tokenState = Jwt.getResult((String) resultMap.get("state"));
			// 2、验证token有效性
			String mobile = null;
			String userId = null;
			if ("valid".equals(tokenState)) {
				// 如果token有效，获取mobile信息
				Map<String, String> dataMap = new HashMap<String, String>();
				dataMap = (HashMap<String, String>) resultMap.get("data");
				mobile = dataMap.get("mobile");
				userId = dataMap.get("userId");
				// 判断token在redis中是否存在
				List<String> list = redisService.hmget("token", mobile);
				String token_old = null;
				if (list != null && list.get(0) != null) {
					token_old = list.get(0);
				}
				if (StringUtils.isNotBlank(token_old)) {
					// 将传入的token与redis中token进行对比
					if (!token.equals(token_old)) {
						// 如果不一致，则认为客户端传入非法token，需重新登录
						return CreateJson.createTextJson(respInfoConfig.getTokenIllegal(), false);
					} else {
						// 通过userId将SCUser的信息从数据库检索出来
						ScUser scUser = scUserDao.findOne(userId);
						// 判断该userID是否存在用户
						if (scUser != null) {
							// 将ScUser的基本信息以及token返回给客户端
							ScUser scUser_new = getScUser(scUser);
							String resp = getResp(token, scUser_new);
							return resp;
						}

					}
				} else {
					// 如果不存在，则认为token失效，返回登录页面重新登录
					return CreateJson.createTextJson(respInfoConfig.getTokenInvalid(), false);
				}
			} else {
				return CreateJson.createTextJson(respInfoConfig.getTokenIllegal(), false);
			}

		}

		return null;
	}

	/**
	 * 用户注册
	 * 
	 * @param jsonText
	 * @return
	 * @throws UnknownHostException
	 * @throws UnsupportedEncodingException
	 *
	 */
	@Transactional
	public String register(String jsonText, String path) throws UnknownHostException, UnsupportedEncodingException {
		ScUser scUser = null;
		// 解析json数据，看是否正确返回ScUser对象
		scUser = JacksonUtil.jsonToObj(jsonText, ScUser.class);
		if (!checkParams(scUser)) {
			return CreateJson.createTextJson(respInfoConfig.getParamsNull(), false);
		} else {
			// 判断手机号是否存在
			List<ScUser> listBymobile = scUserDao.findByMobile(scUser.getMobile());
			if (listBymobile != null && listBymobile.size() > 0) {
				return CreateJson.createTextJson(respInfoConfig.getMobileExist(), false);
			}

			// 判断验证码是否过期
			String validCode = redisService.get(scUser.getValidCodeType() + "-validCode-" + scUser.getMobile());
			if (StringUtils.isBlank(validCode)) {
				return CreateJson.createTextJson(Global.validcode_expire, false);
			} else {
				// 判断验证码是否正确
				if (!validCode.equals(scUser.getValidCode())) {
					return CreateJson.createTextJson(Global.validcode_error, false);
				}
			}

			// 判断用户名是否存在
			List<ScUser> listByUserName = scUserDao.findByUserName(scUser.getUserName());
			if (listByUserName != null && listByUserName.size() > 0) {
				return CreateJson.createTextJson(respInfoConfig.getUserNameExist(), false);
			}

			// 设置ScUser初始属性
			scUser = setInitScUser(scUser, path);

			// 保存用户信息，同时将出入金表sc_money中插入一条数据，最后返回token
			if (scUserDao.save(scUser) != null) {

				// 如果初始虚拟币不为0，就插入一条记录
				if (scUser.getMoney() > 0) {
					scMoneyDao.save(setScMoney(scUser));
				}
				// 返回用户基本信息以及token
				ScUser scUser_new = getScUser(scUser);
				// 注册成功之后，更新用户PK等级，重载ScUserMap
				updatePkLevel(scUser);
				reloadScUser();
				String token = createToken(scUser);
				String resp = getResp(token, scUser_new);
				return resp;
			} else {
				return CreateJson.createTextJson(respInfoConfig.getSysExeption(), false);
			}
		}

	}

	/**
	 * 发送手机验证码
	 * 
	 * @param mobile
	 * @return
	 * 
	 */
	public String sendsms(String jsonText, HttpServletRequest request) {
		ScUser scUser = null;
		scUser = JacksonUtil.jsonToObj(jsonText, ScUser.class);
		String validCodeType = scUser.getValidCodeType();
		// 验证请求的IP在redis中是否存在，如果存在就拒绝发送短信
		// 该操作是防止恶意调用该接口
		String requestAddr = IpUtils.getIpAddr(request);
		String redis_smsIp = redisService.get("sendsms_" + requestAddr);
		if (StringUtils.isNotBlank(redis_smsIp)) {
			return CreateJson.createTextJson(respInfoConfig.getSendsmsLimit(), false);
		}
		if (StringUtils.isBlank(validCodeType)) { // 判断验证码的类型是否填写
			return CreateJson.createTextJson(respInfoConfig.getValidCodeTypeNull(), false);
		} else {
			// 生成随机验证码
			String randomCode = RandomCode.getCode(4);
			StringBuffer sbUrl = new StringBuffer();
			sbUrl.append("?username=");
			sbUrl.append(Global.sendmsg_username);
			sbUrl.append("&password=");
			sbUrl.append(Global.sendmsg_password);
			sbUrl.append("&mobile=");
			sbUrl.append(scUser.getMobile());
			sbUrl.append("&smsID=");
			sbUrl.append(TimeUtil.dateTime2Str(DateTime.now(), TimeUtil.DBdaytimeFormat));
			sbUrl.append("&smscontent=");
			sbUrl.append("欢迎您使用八爪鱼，验证码：" + randomCode + "，请在5分钟内完成验证。");
			sbUrl.append("&sendtime=&sa=");
			String resp = GetHttpMsg.getHttp(Global.sendmsg_url + sbUrl.toString());
			String result = new ParseXml().analList0(resp, "int");
			if ("0".equals(result)) {
				// 短信发送成功后，将验证码储存在redis中，供注册时验证
				redisService.set(validCodeType + "-validCode-" + scUser.getMobile(), randomCode,
						Integer.parseInt(Global.validcode_livetime));
				// 请求IP存入redis
				redisService.set("sendsms_" + requestAddr, requestAddr, Integer.parseInt(Global.sendsms_limitTime));
				return CreateJson.createTextJson(randomCode, true);
			} else {
				return CreateJson.createTextJson(respInfoConfig.getSmsException(), false);
			}
		}

	}

	/**
	 * 登录时忘记密码
	 * 
	 * @param jsonText
	 * @return
	 */
	@Transactional
	public String fgtpwd(String jsonText) {
		// 解析json数据
		ScUser scUser = JacksonUtil.jsonToObj(jsonText, ScUser.class);
		// 判断验证码是否过期
		String validCode = redisService.get(scUser.getValidCodeType() + "-validCode-" + scUser.getMobile());
		if (StringUtils.isBlank(validCode)) {
			return CreateJson.createTextJson(Global.validcode_expire, false);
		} else {
			// 判断验证码是否正确
			if (!validCode.equals(scUser.getValidCode())) {
				return CreateJson.createTextJson(Global.validcode_error, false);
			}
		}

		// 查询该手机号在数据库中是否存在
		List<ScUser> list = scUserDao.findByMobile(scUser.getMobile());
		if (list != null && list.size() > 0) {
			// 如果存在，修改密码
			ScUser scUser_new = list.get(0);
			scUser_new.setPassword(DigestUtils.md5Hex(scUser.getPassword()));
			// 保存ScUser
			scUserDao.save(scUser_new);
			return CreateJson.createTextJson(respInfoConfig.getResetpwdsuccess(), true);
		} else {
			return CreateJson.createTextJson(respInfoConfig.getResetpwdfail(), false);
		}

	}

	/**
	 * 个人信息中重置密码
	 * 
	 * @param jsonText
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String resetpwd(String jsonText) {
		// 解析json数据
		HashMap<String, String> map = JacksonUtil.jsonToObj(jsonText, HashMap.class);
		String userId = map.get("userId"); // 获取用户ID
		String password = map.get("password");// 获取用户旧密码
		String passwordNew = map.get("passwordNew");
		String repasswordNew = map.get("repasswordNew");
		// 验证用户是否存在
		List<ScUser> list = scUserDao.findByUserIdAndPassword(userId, DigestUtils.md5Hex(password));
		if (list != null && list.size() > 0) {
			// 判断两次密码输入是否一致
			if (passwordNew.equals(repasswordNew)) {
				ScUser scUser = list.get(0);
				// 修改密码
				scUser.setPassword(DigestUtils.md5Hex(passwordNew));
				scUserDao.save(scUser);
				// 重载ScUser
				reloadScUser();
				return CreateJson.createTextJson(respInfoConfig.getOperateSuccess(), true);
			} else {
				return CreateJson.createTextJson(respInfoConfig.getPasswordReError(), false);
			}
		} else {
			return CreateJson.createTextJson(respInfoConfig.getPasswordOldError(), false);
		}

	}

	/**
	 * 用户登出
	 * 
	 * @param token
	 * @return
	 * @throws UnknownHostException
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String logout(String token, String jsonText) throws UnknownHostException {
		HashMap<String, String> map = JacksonUtil.jsonToObj(jsonText, HashMap.class);
		if (map != null) {
			// 如果token验证成功，就把token删除
			String mobile = map.get("mobile");
			redisService.hdel("token", mobile);
			return CreateJson.createTextJson(respInfoConfig.getLogout(), true);
		}

		return CreateJson.createTextJson(respInfoConfig.getLogoutException(), true);
	}

	/**
	 * 上传头像
	 * 
	 * @param jsonText
	 * @return
	 * @throws UnknownHostException
	 */
	public String uploadImg(String jsonText, String path) throws UnknownHostException {
		// 解析token，得到用户手机号
		// 解析json数据
		@SuppressWarnings({ "unchecked", "rawtypes" })
		HashMap<String, String> dataMap = (HashMap) JacksonUtil.jsonToObj(jsonText, Object.class);
		// 得到img的字符串数据
		String imgStr = dataMap.get("img");
		// 得到手机号
		String mobile = dataMap.get("mobile");
		if (StringUtils.isBlank(imgStr) || StringUtils.isBlank(mobile)) {
			return CreateJson.createTextJson(respInfoConfig.getParamsNull(), false);
		}
		// 检查该手机号用户是否存在
		List<ScUser> scUserList = scUserDao.findByMobile(mobile);
		String dateTime = TimeUtil.dateTime2Str(DateTime.now(), TimeUtil.DBdaytimeFormat);
		String old_img = null;
		if (scUserList != null && scUserList.size() > 0) {
			old_img = scUserList.get(0).getImage().replace(systemConfig.getUserImgUrl() + "?fileName=", "");
			scUserList.get(0).setImage(systemConfig.getUserImgUrl() + "?fileName=" + mobile + "_" + dateTime + ".jpg");
		} else {
			return CreateJson.createTextJson(respInfoConfig.getUserNotExist(), false);
		}
		String fileName = mobile + "_" + dateTime + ".jpg";
		// 上传头像
		boolean b = ImgBase64.GenerateImage(imgStr, path, fileName);
		if (b) {
			// 头像上传成功后，更改数据库的字段值，并删除原有的文件
			if (scUserDao.save(scUserList.get(0)) != null) {
				// 重载ScUserMap
				reloadScUser();
			}
			// String localPath = SystemPath.getClassPath().replace("//",
			// File.separator)
			// .replace("classes", "userfiles" + File.separator + "images");
			// if(!MacUtils.getOSName().startsWith("windows")){
			// localPath=File.separator+localPath;
			// }
			File file = new File(path + old_img);
			if (file.exists()) {
				file.delete();
			}
			// 将ScUser的基本信息以及token返回给客户端
			ScUser scUser_new = getScUser(scUserList.get(0));
			Map<String, Object> map = Maps.newHashMap();
			map.put("result", true);
			map.put("data", scUser_new);
			return JacksonUtil.objToJson(map);
		} else {
			return CreateJson.createTextJson(respInfoConfig.getUploadImgFail(), false);
		}

	}

	/**
	 * 远程调用接口，获取用户信息
	 * 
	 * @param jsonText
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getScUserInfo(String jsonText) {
		HashMap<String, String> jsonMap = JacksonUtil.jsonToObj(jsonText, HashMap.class);
		String userId = null;
		if (jsonMap != null && jsonMap.size() > 0) {
			userId = jsonMap.get("userId");
		}
//		ConcurrentHashMap<String, ConcurrentHashMap<String, ScUser>> scUserMap = BusinessTask.scUserMap;
//		ConcurrentHashMap<String, ScUser> userMap = new ConcurrentHashMap<String, ScUser>();
//		if (scUserMap != null && scUserMap.size() > 0) {
//			userMap = scUserMap.get("scUser");
//			ScUser scUser = userMap.get(userId);
//			if (scUser != null) {
//				scUser.setPassword(null);
//			}
//			Map<String, Object> map = Maps.newHashMap();
//			map.put("result", true);
//			map.put("data", scUser);
//			return JacksonUtil.objToJson(map);
//		}
		if(StringUtils.isNoneBlank(userId)){
			ScUser sc=scUserDao.findByUserId(userId);
			if (sc != null) {
				sc.setPassword(null);
				sc.setImage(getServerAddress() + (sc.getImage() == null ? "" : sc.getImage()));
				Map<String, Object> map = Maps.newHashMap();
				map.put("result", true);
				map.put("data", sc);
				return JacksonUtil.objToJson(map);
			}else{
				return CreateJson.createTextJson(respInfoConfig.getNonData(), false);
			}
		}else{
			return CreateJson.createTextJson(respInfoConfig.getNonData(), false);
		}
	}

	/**
	 * 设置出入金scMoney属性
	 * 
	 * @param scUser
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public ScMoney setScMoney(ScUser scUser) throws UnsupportedEncodingException {
		ScMoney scMoney = new ScMoney();
		scMoney.setSeqNo(IdGen.uuid());
		scMoney.setUserId(scUser.getUserId());
		scMoney.setMoney(scUser.getMoney());
		scMoney.setApplyTime(TimeUtil.dateTime2Str(DateTime.now(), TimeUtil.DSPdaytimeFormat));
		scMoney.setAcceptTime(TimeUtil.dateTime2Str(DateTime.now(), TimeUtil.DSPdaytimeFormat));
		scMoney.setApplyUserName(scUser.getUserName());
		scMoney.setAcceptUserName(scUser.getUserName());
		scMoney.setAcceptStatus(dictConfig.getAudit_accept_status());
		scMoney.setRemark(dictConfig.getMoney_scuser_new_remark());
		scMoney.setInitValue(scMoney, scUser.getUserId());
		return scMoney;
	}

	/**
	 * 设置scUser初始属性
	 * 
	 * @param scUser
	 * @return
	 */
	public ScUser setInitScUser(ScUser scUser, String path) {
		scUser.setUserId(IdGen.uuid()); // 生成ID
		scUser.setPassword(DigestUtils.md5Hex(scUser.getPassword()));// 密码以MD5方式保存数据库
		// 生成默认头像，并设置头像路径，头像的文件名为用户手机号+img.jpg(路径为项目的实际部署路径)
		// 头像保存的url为scUserController的getImageUrl方法
		String fileName = scUser.getMobile() + "_" + TimeUtil.dateTime2Str(DateTime.now(), TimeUtil.DBdaytimeFormat);
		ImgCreate imgCreate = new ImgCreate();
		imgCreate.createImage(path, scUser.getUserName(), fileName);
		scUser.setImage(systemConfig.getUserImgUrl() + "?fileName=" + fileName + ".jpg");
		// 查询数据字典，设置初始虚拟币
		List<SysDict> list = sysDictDao.findByType(dictConfig.getReg_ini_money());
		if (list != null && list.size() > 0) {
			scUser.setMoney(Double.parseDouble(list.get(0).getValue()));
		} else {
			scUser.setMoney(0.0);
		}
		// 冻结虚拟币0
		scUser.setFrozenMoney(0.0);
		// 设置用户初始等级
		scUser.setLevelId(getMinLevelId());
		// 设置创建者、创建日期、更新者、更新日期
		scUser.setInitValue(scUser);
		return scUser;
	}

	/**
	 * 检查传入的参数是否为空
	 * 
	 * @param scUser
	 * @return
	 */
	public boolean checkParams(ScUser scUser) {
		String userName = scUser.getUserName();
		String mobile = scUser.getMobile();
		String password = scUser.getPassword();
		if (scUser == null || StringUtils.isBlank(userName) || StringUtils.isBlank(mobile)
				|| StringUtils.isBlank(password)) {
			return false;
		}
		return true;
	}

	/**
	 * 获取ScUser(只用于返回给客户端)
	 * 
	 * @param scUser
	 * @return
	 * @throws UnknownHostException
	 */
	public ScUser getScUser(ScUser scUser) throws UnknownHostException {
		ScUser scUser_new = new ScUser();
		scUser_new.setUserId(scUser.getUserId() == null ? "" : scUser.getUserId());
		scUser_new.setUserName(scUser.getUserName() == null ? "" : scUser.getUserName());
		scUser_new.setMobile(scUser.getMobile() == null ? "" : scUser.getMobile());
		scUser_new.setMoney(scUser.getMoney());
		scUser_new.setFrozenMoney(scUser.getFrozenMoney());
		scUser_new.setLevelId(scUser.getLevelId());
		String pkLevelName = "";
		if (StringUtils.isNotBlank(scUser.getLevelId())) {
			// 获取用户等级
			ScPklevel scPklevel = scPklevelDao.findByLevelId(scUser.getLevelId());
			if (scPklevel != null) {
				pkLevelName = scPklevel.getLevelName();
			}
		}
		scUser_new.setImage(getServerAddress() + (scUser.getImage() == null ? "" : scUser.getImage()));
		scUser_new.setLevelName(pkLevelName);
		return scUser_new;
	}

	/**
	 * 获取返回值
	 * 
	 * @param token
	 * @param scUser
	 * @return
	 * @throws UnknownHostException
	 */
	public String getResp(String token, ScUser scUser) throws UnknownHostException {
		String resp = null;
		// 获取用户的权限
		List<ScRight> rightList = scRightDao.findByUserId(scUser.getUserId());
		// 获取用户的战队
		List<Group> groupList = groupDao.findByUserId(scUser.getUserId());
		if (groupList != null && groupList.size() > 0) {
			for (Group group : groupList) {
				// 计算多空比例
				double buysellRatio = scGroupService.getbuysellRatio(group);// 多空比例
				group.setBuysellRatio(buysellRatio);
				// 计算胜率
				double winRate = 0.0;
				if (group.getPkTotal() > 0) {
					winRate = ArithDecimal.div(group.getPkWin(), group.getPkTotal(), 2);
				}
				group.setWinRate(winRate);
			}
		}
		ConcurrentHashMap<String, Object> dataMap = new ConcurrentHashMap<String, Object>();
		dataMap.put("scUser", scUser);
		dataMap.put("token", token);
		dataMap.put("result", true);
		dataMap.put("right", rightList);
		dataMap.put("group", groupList);
		resp = JacksonUtil.objToJson(dataMap);
		return resp;
	}

	/**
	 * 创建token
	 * 
	 * @param scUser
	 * @return
	 */
	public String createToken(ScUser scUser) {
		String token = "";
		ConcurrentHashMap<String, Object> payload = new ConcurrentHashMap<String, Object>();
		Date date = new Date();
		payload.put("userId", scUser.getUserId());// 用户id
		payload.put("mobile", scUser.getMobile());// 用户手机号
		payload.put("iat", date.getTime());// 生成时间:当前
		if ("1".equals(Global.token_expire)) {
			payload.put("ext", date.getTime() + Integer.parseInt(Global.token_expire_long) * 1000 * 60 * 60);// 过期时间2小时
		}
		token = Jwt.createToken(payload);
		// 将新的token保存至redis中。key为手机号，value为token
		ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();
		map.put(scUser.getMobile(), token);
		redisService.hmset("token", map);
		return token;
	}

}
