package com.quanqinle.myworld.entity.po;

import javax.persistence.*;

/**
 * @author quanql
 */
@Entity
@Table(name = "estate_secondhand_listing")
public class EstateSecondHandListing {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/**
	 * 房源核验统一编码
	 */
	@Column(name = "fwtybh", nullable = false, length = 20)
	private String fwtybh;
	/**
	 *  建筑面积
	 */
	private String jzmj;
	/**
	 *  委托出售价格
	 */
	private String wtcsjg;
	/**
	 *  账号id
	 */
	private String accountid;
	/**
	 *  账号名称
	 */
	private String accountname;
	/**
	 *  创建时间
	 */
	private String cjsj;
	/**
	 *  城区名称
	 */
	private String cqmc;
	/**
	 *  城区速记？
	 */
	private String cqsj;
	/**
	 *  从业人员编号
	 */
	private String cyrybh;
	/**
	 *
	 */
	private String czfs;
	/**
	 *  地区？
	 */
	private String dqlc;
	/**
	 *  发布状态
	 */
	private String fbzt;
	/**
	 *  房产证书
	 */
	private String fczsh;
	/**
	 *  房屋用途id
	 */
	private String fwyt;
	/**
	 *  房屋用途
	 */
	@Column(name = "fwytvalue")
	private String fwytValue;
	/**
	 *
	 */
	private String gisx;
	/**
	 *
	 */
	private String gisy;
	/**
	 *  挂牌房屋id
	 */
	private String gpfyid;
	/**
	 *  挂牌行业提供数据？
	 */
	private String gphytgsj;
	/**
	 *  挂牌id
	 */
	private String gpid;
	/**
	 *  挂牌联系人代码
	 */
	private String gplxrcode;
	/**
	 *  挂牌联系人电话
	 */
	private String gplxrdh;
	/**
	 *  挂牌联系人姓名
	 */
	private String gplxrxm;
	/**
	 *  挂牌来源
	 */
	private String gply;
	/**
	 *  挂牌状态id
	 */
	private String gpzt;
	/**
	 *  挂牌状态
	 */
	@Column(name = "gpztvalue")
	private String gpztValue;
	/**
	 *  户型？
	 */
	private String hxs;
	/**
	 *
	 */
	private String hxt;
	/**
	 *
	 */
	private String hxw;
	/**
	 *
	 */
	private String hyid;
	/**
	 *
	 */
	private String hyjzsj;
	/**
	 *  是否新房？
	 */
	private String isnew;
	/**
	 *  门店名称
	 */
	private String mdmc;
	/**
	 *  签约id
	 */
	private String qyid;
	/**
	 *  签约状态
	 */
	private String qyzt;
	/**
	 *  首次挂牌上市时间
	 */
	private String scgpshsj;
	/**
	 *
	 */
	private String sellnum;
	/**
	 *
	 */
	private String sqhysj;
	/**
	 *  所在楼层
	 */
	private String szlc;
	/**
	 *  所在楼层名称
	 */
	private String szlcname;
	/**
	 *  统一挂牌编号
	 */
	private String tygpbh;
	/**
	 *  委托地区？
	 */
	private String wtdqts;
	/**
	 *  委托协议编号
	 */
	private String wtxybh;
	/**
	 *  委托协议代码
	 */
	private String wtxycode;
	/**
	 *  委托协议id
	 */
	private String wtxyid;
	/**
	 *  小区id
	 */
	private String xqid;
	/**
	 *  小区名称
	 */
	private String xqmc;
	/**
	 *  行政区划id
	 */
	private String xzqh;
	/**
	 *  行政区划名称
	 */
	private String xzqhname;
	/**
	 *
	 */
	private String zzcs;

	protected EstateSecondHandListing() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFwtybh() {
		return fwtybh;
	}

	public void setFwtybh(String fwtybh) {
		this.fwtybh = fwtybh;
	}

	public String getJzmj() {
		return jzmj;
	}

	public void setJzmj(String jzmj) {
		this.jzmj = jzmj;
	}

	public String getWtcsjg() {
		return wtcsjg;
	}

	public void setWtcsjg(String wtcsjg) {
		this.wtcsjg = wtcsjg;
	}

	public String getAccountid() {
		return accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	public String getCjsj() {
		return cjsj;
	}

	public void setCjsj(String cjsj) {
		this.cjsj = cjsj;
	}

	public String getCqmc() {
		return cqmc;
	}

	public void setCqmc(String cqmc) {
		this.cqmc = cqmc;
	}

	public String getCqsj() {
		return cqsj;
	}

	public void setCqsj(String cqsj) {
		this.cqsj = cqsj;
	}

	public String getCyrybh() {
		return cyrybh;
	}

	public void setCyrybh(String cyrybh) {
		this.cyrybh = cyrybh;
	}

	public String getCzfs() {
		return czfs;
	}

	public void setCzfs(String czfs) {
		this.czfs = czfs;
	}

	public String getDqlc() {
		return dqlc;
	}

	public void setDqlc(String dqlc) {
		this.dqlc = dqlc;
	}

	public String getFbzt() {
		return fbzt;
	}

	public void setFbzt(String fbzt) {
		this.fbzt = fbzt;
	}

	public String getFczsh() {
		return fczsh;
	}

	public void setFczsh(String fczsh) {
		this.fczsh = fczsh;
	}

	public String getFwyt() {
		return fwyt;
	}

	public void setFwyt(String fwyt) {
		this.fwyt = fwyt;
	}

	public String getFwytValue() {
		return fwytValue;
	}

	public void setFwytValue(String fwytValue) {
		this.fwytValue = fwytValue;
	}

	public String getGisx() {
		return gisx;
	}

	public void setGisx(String gisx) {
		this.gisx = gisx;
	}

	public String getGisy() {
		return gisy;
	}

	public void setGisy(String gisy) {
		this.gisy = gisy;
	}

	public String getGpfyid() {
		return gpfyid;
	}

	public void setGpfyid(String gpfyid) {
		this.gpfyid = gpfyid;
	}

	public String getGphytgsj() {
		return gphytgsj;
	}

	public void setGphytgsj(String gphytgsj) {
		this.gphytgsj = gphytgsj;
	}

	public String getGpid() {
		return gpid;
	}

	public void setGpid(String gpid) {
		this.gpid = gpid;
	}

	public String getGplxrcode() {
		return gplxrcode;
	}

	public void setGplxrcode(String gplxrcode) {
		this.gplxrcode = gplxrcode;
	}

	public String getGplxrdh() {
		return gplxrdh;
	}

	public void setGplxrdh(String gplxrdh) {
		this.gplxrdh = gplxrdh;
	}

	public String getGplxrxm() {
		return gplxrxm;
	}

	public void setGplxrxm(String gplxrxm) {
		this.gplxrxm = gplxrxm;
	}

	public String getGply() {
		return gply;
	}

	public void setGply(String gply) {
		this.gply = gply;
	}

	public String getGpzt() {
		return gpzt;
	}

	public void setGpzt(String gpzt) {
		this.gpzt = gpzt;
	}

	public String getGpztValue() {
		return gpztValue;
	}

	public void setGpztValue(String gpztValue) {
		this.gpztValue = gpztValue;
	}

	public String getHxs() {
		return hxs;
	}

	public void setHxs(String hxs) {
		this.hxs = hxs;
	}

	public String getHxt() {
		return hxt;
	}

	public void setHxt(String hxt) {
		this.hxt = hxt;
	}

	public String getHxw() {
		return hxw;
	}

	public void setHxw(String hxw) {
		this.hxw = hxw;
	}

	public String getHyid() {
		return hyid;
	}

	public void setHyid(String hyid) {
		this.hyid = hyid;
	}

	public String getHyjzsj() {
		return hyjzsj;
	}

	public void setHyjzsj(String hyjzsj) {
		this.hyjzsj = hyjzsj;
	}

	public String getIsnew() {
		return isnew;
	}

	public void setIsnew(String isnew) {
		this.isnew = isnew;
	}

	public String getMdmc() {
		return mdmc;
	}

	public void setMdmc(String mdmc) {
		this.mdmc = mdmc;
	}

	public String getQyid() {
		return qyid;
	}

	public void setQyid(String qyid) {
		this.qyid = qyid;
	}

	public String getQyzt() {
		return qyzt;
	}

	public void setQyzt(String qyzt) {
		this.qyzt = qyzt;
	}

	public String getScgpshsj() {
		return scgpshsj;
	}

	public void setScgpshsj(String scgpshsj) {
		this.scgpshsj = scgpshsj;
	}

	public String getSellnum() {
		return sellnum;
	}

	public void setSellnum(String sellnum) {
		this.sellnum = sellnum;
	}

	public String getSqhysj() {
		return sqhysj;
	}

	public void setSqhysj(String sqhysj) {
		this.sqhysj = sqhysj;
	}

	public String getSzlc() {
		return szlc;
	}

	public void setSzlc(String szlc) {
		this.szlc = szlc;
	}

	public String getSzlcname() {
		return szlcname;
	}

	public void setSzlcname(String szlcname) {
		this.szlcname = szlcname;
	}

	public String getTygpbh() {
		return tygpbh;
	}

	public void setTygpbh(String tygpbh) {
		this.tygpbh = tygpbh;
	}

	public String getWtdqts() {
		return wtdqts;
	}

	public void setWtdqts(String wtdqts) {
		this.wtdqts = wtdqts;
	}

	public String getWtxybh() {
		return wtxybh;
	}

	public void setWtxybh(String wtxybh) {
		this.wtxybh = wtxybh;
	}

	public String getWtxycode() {
		return wtxycode;
	}

	public void setWtxycode(String wtxycode) {
		this.wtxycode = wtxycode;
	}

	public String getWtxyid() {
		return wtxyid;
	}

	public void setWtxyid(String wtxyid) {
		this.wtxyid = wtxyid;
	}

	public String getXqid() {
		return xqid;
	}

	public void setXqid(String xqid) {
		this.xqid = xqid;
	}

	public String getXqmc() {
		return xqmc;
	}

	public void setXqmc(String xqmc) {
		this.xqmc = xqmc;
	}

	public String getXzqh() {
		return xzqh;
	}

	public void setXzqh(String xzqh) {
		this.xzqh = xzqh;
	}

	public String getXzqhname() {
		return xzqhname;
	}

	public void setXzqhname(String xzqhname) {
		this.xzqhname = xzqhname;
	}

	public String getZzcs() {
		return zzcs;
	}

	public void setZzcs(String zzcs) {
		this.zzcs = zzcs;
	}
}
