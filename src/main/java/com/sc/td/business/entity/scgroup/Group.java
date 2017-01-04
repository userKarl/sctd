package com.sc.td.business.entity.scgroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sc.td.business.entity.schisgrpstock.ScHisGrpStock;
import com.sc.td.business.entity.scstockgroup.StockGroup;

@Table(name = "sc_group")
@Entity
public class Group extends BaseGroup implements Serializable {

	/**
	 * 唯一标识
	 */
	private static final long serialVersionUID = 1183267786623567237L;

	private List<StockGroup> stockGroup = new ArrayList<StockGroup>();
	private Double buysellRatio;// 多空比例
	private Double winRate; // 胜率
	private String userName;// 用户名
	private String image;// 头像
	private String pkStatus;// PK状态
	private List<ScHisGrpStock> scHisGrpStock;//历史战队信息
	private String pkLevelName;// PK等级
	
	@Transient
	public String getPkLevelName() {
		return pkLevelName;
	}

	public void setPkLevelName(String pkLevelName) {
		this.pkLevelName = pkLevelName;
	}

	@Transient
	public List<ScHisGrpStock> getScHisGrpStock() {
		return scHisGrpStock;
	}

	public void setScHisGrpStock(List<ScHisGrpStock> scHisGrpStock) {
		this.scHisGrpStock = scHisGrpStock;
	}

	@Transient
	public Double getWinRate() {
		return winRate;
	}
	
	public void setWinRate(Double winRate) {
		this.winRate = winRate;
	}

	@Transient
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Transient
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Transient
	public String getPkStatus() {
		return pkStatus;
	}

	public void setPkStatus(String pkStatus) {
		this.pkStatus = pkStatus;
	}

	@Transient
	public Double getBuysellRatio() {
		return buysellRatio;
	}

	public void setBuysellRatio(Double buysellRatio) {
		this.buysellRatio = buysellRatio;
	}

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "groupId")
	public List<StockGroup> getStockGroup() {
		return stockGroup;
	}

	public void setStockGroup(List<StockGroup> stockGroup) {
		this.stockGroup = stockGroup;
	}

	
}
