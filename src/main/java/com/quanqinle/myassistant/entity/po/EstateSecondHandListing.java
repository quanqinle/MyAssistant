package com.quanqinle.myassistant.entity.po;

import lombok.Data;

import javax.persistence.*;

/**
 * @author quanql
 */
@Entity
@Table(name = "estate_secondhand_listing")
@Data
public class EstateSecondHandListing {
	/**
	 * Constructor for jpa
	 */
	protected EstateSecondHandListing() {
	}

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
	/**
	 * Todo 新字段还不知其作用
	 */
	private String sjhm;

}
