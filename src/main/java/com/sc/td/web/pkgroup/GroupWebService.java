package com.sc.td.web.pkgroup;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sc.td.business.base.BaseService;
import com.sc.td.business.dao.sccurrency.ScCurrencyDao;
import com.sc.td.business.dao.scfutures.ScFuturesDao;
import com.sc.td.business.dao.scgroup.GroupDao;
import com.sc.td.business.dao.schisgrpstock.ScHisGrpStockDao;
import com.sc.td.business.dao.scmoney.ScMoneyDao;
import com.sc.td.business.dao.scnotify.ScNotifyRecordDao;
import com.sc.td.business.dao.scpkresult.ScHisPkResultDao;
import com.sc.td.business.dao.scstock.ScStockDao;
import com.sc.td.business.dao.scuser.ScUserDao;
import com.sc.td.business.entity.sccurrency.ScCurrency;
import com.sc.td.business.entity.scfutures.ScFutures;
import com.sc.td.business.entity.scgroup.Group;
import com.sc.td.business.entity.schisgrpstock.ScHisGrpStock;
import com.sc.td.business.entity.scmoney.ScMoney;
import com.sc.td.business.entity.scnotify.ScNotify;
import com.sc.td.business.entity.scnotify.ScNotifyRecord;
import com.sc.td.business.entity.scpkresult.ScHisPkResult;
import com.sc.td.business.entity.scstock.ScStock;
import com.sc.td.business.entity.scuser.ScUser;
import com.sc.td.common.utils.IdGen;
import com.sc.td.common.utils.StringUtils;
import com.sc.td.common.utils.calc.ArithDecimal;
import com.sc.td.common.utils.json.CreateJson;
import com.sc.td.frame.DictConfig;
import com.sc.td.frame.RespInfoConfig;

@Service
public class GroupWebService extends BaseService {

	@Autowired
	private ScFuturesDao scFuturesDao;

	@Autowired
	private ScStockDao scStockDao;

	@Autowired
	private ScHisGrpStockDao scHisGrpStockDao;

	@Autowired
	private ScCurrencyDao scCurrencyDao;

	@Autowired
	private DictConfig dictConfig;

	@Autowired
	private ScHisPkResultDao scHisPkResultDao;

	@Autowired
	private RespInfoConfig respInfoConfig;

	@Autowired
	private GroupDao groupDao;

	@Autowired
	private ScUserDao scUserDao;

	@Autowired
	private ScMoneyDao scMoneyDao;

	@Autowired
	private ScNotifyRecordDao scNotifyRecordDao;

	/**
	 * 计算PK收益，更新相关状态
	 * 
	 * @param request
	 * @param response
	 * @param arr
	 * @param userId
	 * @return
	 */
	@Transactional
	public String exec(String arr, String userId) {
		if (StringUtils.isNotBlank(arr)) {
			String params[] = arr.split(",");
			if (params.length == 3) {
				List<ScHisGrpStock> applyList = scHisGrpStockDao.findByGroupIdAndStartDate(params[0], params[2]);
				List<ScHisGrpStock> acceptList = scHisGrpStockDao.findByGroupIdAndStartDate(params[1], params[2]);
				Double applyGroupIdProfit = caclProfit(applyList);
				Double acceptGroupIdProfit = caclProfit(acceptList);
				String pkResult = null;
				if (applyGroupIdProfit > acceptGroupIdProfit) {
					pkResult = dictConfig.getPk_result_applywin();
				} else if (applyGroupIdProfit < acceptGroupIdProfit) {
					pkResult = dictConfig.getPk_result_acceptwin();
				} else {
					pkResult = dictConfig.getPk_result_planish();
				}
				ScHisPkResult scHisPkResult = scHisPkResultDao.findByApplyGroupIdAndAcceptGroupIdAndStartDate(params[0],
						params[1], params[2]);
				if (!scHisPkResult.getPkStatus().equals(dictConfig.getPk_status_end())) {
					return CreateJson.createTextJson(respInfoConfig.getOperateFail(), false);
				}
				scHisPkResult.setAcceptGroupIdProfit(acceptGroupIdProfit);
				scHisPkResult.setApplyGroupIdProfit(applyGroupIdProfit);
				scHisPkResult.setUpdateValue(scHisPkResult, userId);
				if (!scHisPkResult.getPkResult().equals(pkResult)) {
					// 比赛结果与之前不同时，更新相关状态
					scHisPkResult.setPkResult(pkResult);
					update(scHisPkResult, userId);
				}

				scHisPkResultDao.save(scHisPkResult);
			}
		}
		return CreateJson.createTextJson(respInfoConfig.getOperateSuccess(), true);
	}

	/**
	 * 比赛结果与之前不同，更新相关状态
	 * 
	 * @param scHisPkResult
	 */
	@Transactional
	public void update(ScHisPkResult sc, String userId) {
		if (sc != null) {
			String pkResult = sc.getPkResult();
			if (pkResult.equals(dictConfig.getPk_result_applywin())) {
				// 发起方胜利
				updateWinPkResult(sc.getApplyGroupId(), sc.getAcceptGroupId(), sc, dictConfig.getPk_result_applywin(), userId);
			} else if (pkResult.equals(dictConfig.getPk_result_acceptwin())) {
				// 接受方胜利
				updateWinPkResult(sc.getAcceptGroupId(), sc.getApplyGroupId(), sc, dictConfig.getPk_result_acceptwin(), userId);
			} else if (pkResult.equals(dictConfig.getPk_result_planish())) {
				// 平
				updateWinPkResult(sc.getAcceptGroupId(), sc.getApplyGroupId(), sc, dictConfig.getPk_result_planish(), userId);
			}
		}

	}

	@Transactional
	public void updateWinPkResult(String winGroupId, String loseGroupId, ScHisPkResult sc, String pkResultType,
			String userId) {
		String title = "战队PK公告";
		String type = dictConfig.getMsg_type_pk();
		String winContent = null;
		String loseContent = null;
		// 根据groupId获取发起方和接受方的userId
		Group winGroup = groupDao.findByGroupId(winGroupId);
		Group loseGroup = groupDao.findByGroupId(loseGroupId);
		ScUser winUser = null;
		ScUser loseUser = null;
		if (winGroup != null) {
			winUser = scUserDao.findByUserId(winGroup.getUserId());
		}
		if (loseGroup != null) {
			loseUser = scUserDao.findByUserId(loseGroup.getUserId());
		}
		if (winUser != null && loseUser != null) {
			if (pkResultType.equals(dictConfig.getPk_result_planish())) {
				// 打平
				winGroup.setPkRowWin(0);
				loseGroup.setPkRowWin(0);
				winUser.setMoney(winUser.getMoney() + sc.getMoney() / 2);
				loseUser.setMoney(loseUser.getMoney() + sc.getMoney() / 2);
				// 出入金表插入数据
				ScMoney scMoneyApply = setInitScMoney(winUser);
				scMoneyApply.setMoney(sc.getMoney() / 2);
				scMoneyApply.setRemark("和" + loseUser.getUserName() + "PK战平");
				ScMoney scMoneyAccept = setInitScMoney(loseUser);
				scMoneyAccept.setMoney(sc.getMoney() / 2);
				scMoneyAccept.setRemark("和" + winUser.getUserName() + "PK战平");
				scMoneyDao.save(scMoneyApply);
				scMoneyDao.save(scMoneyAccept);
				winContent=loseContent=getContent(winGroup.getGroupName(),sc.getStartDate(),sc.getMoney() / 2,"draw");
			} else {
				winGroup.setPkWin(winGroup.getPkWin() + 1);
				winGroup.setPkRowWin(winGroup.getPkRowWin() + 1);
				loseGroup.setPkRowWin(0);
				winUser.setMoney(winUser.getMoney() + sc.getMoney());
				// 出入金表插入数据
				ScMoney scMoneyApply = setInitScMoney(winUser);
				scMoneyApply.setMoney(sc.getMoney());
				scMoneyApply.setRemark("和" + loseUser.getUserName() + "PK获胜");
				scMoneyDao.save(scMoneyApply);
				winContent=getContent(loseGroup.getGroupName(),sc.getStartDate(),sc.getMoney() / 2,"win");
				loseContent=getContent(winGroup.getGroupName(),sc.getStartDate(),sc.getMoney(),"lose");
			}
			winGroup.setUpdateValue(winGroup, userId);
			loseGroup.setUpdateValue(loseGroup, userId);
			winUser.setUpdateValue(winUser, userId);
			loseUser.setUpdateValue(loseUser, userId);
			scHisPkResultDao.save(sc);
			groupDao.save(winGroup);
			groupDao.save(loseGroup);
			scUserDao.save(winUser);
			scUserDao.save(loseUser);
			// 更新PK等级
			updatePkLevel(winUser);
			updatePkLevel(loseUser);
			// 创建消息
			createNotify(winUser.getUserId(), type, title, winContent);
			createNotify(loseUser.getUserId(), type, title, loseContent);
		}
	}

	public String getContent(String groupName, String startDate,Double money, String pkResultType) {
		String content = null;
		if(pkResultType.equals("win")){
			//胜利
			content = "经过重新计算，恭喜您在和战队" + groupName + "在" + formatDate(startDate)
			+ "开始的PK中获胜！由于之前系统计算错误，将赠送您" + money + "墨币！";
		}else if(pkResultType.equals("lose")){
			//失败
			content = "经过重新计算，很遗憾您在和战队" + groupName + "在" + formatDate(startDate)
			+ "开始的PK中失败了！由于之前系统计算错误，将不会扣除您的墨币！";
		}else if(pkResultType.equals("draw")){
			//打平
			content = "经过重新计算，您和战队" + groupName + "在" + formatDate(startDate)
			+ "开始的PK中打平！由于之前系统计算错误，将赠送您" + money + "墨币！";
		}
		return content;
	}

	/**
	 * 创建消息
	 * 
	 * @param userId
	 * @param title
	 * @param content
	 */
	public void createNotify(String userId, String type, String title, String content) {
		ScNotify sc = new ScNotify();
		sc.setId(IdGen.uuid());
		sc.setType(type);
		sc.setTitle(title);
		sc.setContent(content);
		sc.setInitValue(sc, userId);
		sc.setStatus(dictConfig.getMsg_publish());
		ScNotifyRecord scNotifyRecord = new ScNotifyRecord();
		scNotifyRecord.setId(IdGen.uuid());
		scNotifyRecord.setScNotify(sc);
		scNotifyRecord.setUserId(userId);
		scNotifyRecord.setReadFlag(dictConfig.getNot_read_flag());
		scNotifyRecordDao.save(scNotifyRecord);
	}

	/**
	 * 计算总收益
	 * 
	 * @param list
	 * @return
	 */

	public Double caclProfit(List<ScHisGrpStock> list) {
		if (list.size() > 0) {
			Double totalProfit = 0.0;// 进行过汇率转换的数据
			for (ScHisGrpStock sc : list) {
				// 当产品是期货时，将开平仓价格进行十进制转换
				Double openPrice = 0.0;
				Double closePrice = 0.0;
				Double oneProfit = 0.0;// 未进行汇率转换
				if ("futures".equals(getStockType(sc.getCommodityType()))) {
					ScFutures scf = scFuturesDao.findByExchangeNoAndCodeAndCommodityType(sc.getExchangeNo(),
							sc.getStockNo(), sc.getCommodityType());
					if (scf != null) {
						openPrice = getRealPrice(sc.getOpenPrice(), scf.getLowerTick(), scf.getDotNum());
						closePrice = getRealPrice(sc.getClosePrice(), scf.getLowerTick(), scf.getDotNum());
						if (scf.getUpperTick() * scf.getProductDot() * sc.getTradeVol() != 0) {
							double unit = ArithDecimal.div(scf.getLowerTick(),
									Math.pow(10.0, (double) scf.getDotNum()));
							if (sc.getDirect().equals(dictConfig.getGroup_direct_buy())) {
								oneProfit = ArithDecimal.div(closePrice - openPrice, scf.getUpperTick(), 6) * unit
										* scf.getProductDot() * sc.getTradeVol();
							} else if (sc.getDirect().equals(dictConfig.getGroup_direct_sell())) {
								oneProfit = ArithDecimal.div(openPrice - closePrice, scf.getUpperTick(), 6) * unit
										* scf.getProductDot() * sc.getTradeVol();
							}
							// 进行汇率计算
							ScCurrency scCurrency = scCurrencyDao.findOne(scf.getCurrencyNo());
							if (scCurrency != null) {
								oneProfit = oneProfit * scCurrency.getExchange();
							}
							totalProfit += oneProfit;
						}
					} else {
						scHisGrpStockDao.delete(sc);
					}
				} else if ("stock".equals(getStockType(sc.getCommodityType()))) {
					ScStock scs = scStockDao.findByExchangeNoAndStockNoAndCommodityType(sc.getExchangeNo(),
							sc.getStockNo(), sc.getCommodityType());
					if (scs != null) {
						openPrice = sc.getOpenPrice();
						closePrice = sc.getClosePrice();
						if (sc.getTradeVol() != 0 && sc.getTradeVol() != null) {
							oneProfit = (closePrice - openPrice) * sc.getTradeVol();
							// 进行汇率计算
							ScCurrency scCurrency = scCurrencyDao.findOne(scs.getCurrencyNo());
							if (scCurrency != null) {
								oneProfit = oneProfit * scCurrency.getExchange();
							}
							totalProfit += oneProfit;
						}
					} else {
						scHisGrpStockDao.delete(sc);
					}
				}
			}
			return totalProfit;
		}
		return 0.0;
	}
}
