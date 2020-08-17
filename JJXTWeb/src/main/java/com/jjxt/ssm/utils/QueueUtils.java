package com.jjxt.ssm.utils;

import java.text.NumberFormat;
import java.util.Calendar;

public class QueueUtils {
	
	//lg_provinces_send队列
	public static final String LOGIC_PROVINCE = "logic:province";
	//logic模块常用的全局参数key
	public static final String GL_LOGIC = "gl:logic";
	//更新lg_provinces_send的最后更新时间field
	public static final String PROVINCE_UPDATETIME = "fprotime";
	//存放account信息hash key
	public static final String LOGIC_ACCOUNT = "logic:account";
	//存放channel信息hash key
	public static final String LOGIC_CHANNEL = "logic:channel";
	//用于帐户id和name匹配
	public static final String ID_MATCH_NAME = "id:match:name";
	//数据同步的版本号
	public static final String U_VERSION = "u:version";
	//存放t_num_segment信息hash key
	public static final String LOGIC_NUMSEGMENT = "logic:numsegment";
	//存放pre_segment信息hash key
	public static final String LOGIC_PRESEGMENT = "logic:presegment";
	//存放pre_black_keyword信息hash key
	public static final String LOGIC_BLACKKEYWORD = "logic:blackkeyword";
	//存放t_global_setting信息hash key
	public static final String LOGIC_GL_SETTING = "logic:glsetting";
	//存放t_account_shunt_policies信息 hash key
	public static final String LOGIC_SHUNT_POLICIES = "logic:policies";
	//存放t_channel_forward信息hash key
	public static final String LOGIC_CHANNEL_FORWARD = "logic:chanforward";
	public static final String LOGIC_ACCVERSION = "logic:accversion";
	//存入pre_white信息hash key
	public static final String  LOGIC_WHITE = "logic:white";
	//存入pre_black_level_1信息hash key
	public static final String LOGIC_BLACK_LEVEL1 = "logic:black1";
	//存入pre_black_level_2信息hash key
	public static final String LOGIC_BLACK_LEVEL2 = "logic:black2";
	
	public static final String SYNC_BLACK_LEVEL2 = "sync:black2";
	//存入pre_black_level_3信息hash key
	public static final String LOGIC_BLACK_LEVEL3 = "logic:black3";
	//存放时限信息hash key
	public static final String LOGIC_MIN_LIMIT = "logic:minlimit";
	//存放日限信息hash key
	public static final String LOGIC_DAY_LIMIT = "logic:daylimit";
	//存放日限(手机号,帐号,内容) hash key
	public static final String LOGIC_DAY_CON_LIMIT = "logic:daylimit:content";
	//存放帐户对应发送的内容(帐号,内容) hash key
	public static final String LOGIC_ACC_CONT = "logic:acc:cont";
	//帐户余额
	public static final String LOGIC_ACC_BALANCE = "acc:balance";
	//cmpp长短信messageid匹配队列
	public static final String LOGIC_MSGID_MATCH = "msgkey:msgid";
	//存入pre_ext_src信息hash key(按扩展找签名)
	public static final String LOGIC_EXT_SIGN = "logic:extsign";
	//存入pre_black_sign信息hash key
	public static final String LOGIC_BLACK_SIGN = "logic:blacksign";
	//存入pre_shunt_phone信息hash key
	public static final String LOGIC_SHUNT_PHONE = "logic:shuntphone";
	public static final String LOGIC_CHECK_MUST = "logic:checkmust";
	public static final String LOGIC_MODEL_AUDIT = "logic:auditmodel";
	public static final String LOGIC_AUDIT_CON = "logic:auditcontent";
	public static final String AUDIT_KEY_REPEAT = "audit:key:repeat";
	//存入lg_model_send队列
	public static final String LOGIC_MODEL = "logic:model";
	
	//入库,状态报告勾兑  by qklv 2017-09-15
	private static String SYNC_INSERT_MO_KEY = "SYNC:MO";
	private static String SYNC_INSERT_MT_KEY = "SYNC:MT";
	private static String SYNC_UPDATE_RPT_KEY = "SYNC:RPT";
	
//	public static final String SYNC_INSERT_MT_TABLE_COUNTER = "insert_mt_table_counter";
	public static final String SYNC_INSERT_MT_DAY_COUNTER = "insert_mt_day_counter";
	public static final String SYNC_INSERT_RPT_DAY_COUNTER = "insert_rpt_day_counter";
	public static final String SYNC_UPDATE_RPT_DAY_COUNTER = "update_rpt_day_counter";
	public static final String SYNC_UPDATE_RPT_SUCC_COUNTER = "update_rpt_succ_counter";
//	public static final String SYNC_UPDATE_RPT_EXEC_COUNTER = "update_rpt_exec_counter";
	public static final String SYNC_INSERT_MO_MONTH_COUNTER = "insert_mo_month_counter";
	
	
	//随机扩展对比队列
	public static final String LOGIC_RANEXT_MSGEXT = "ranext:msgext";
	public static final String LOGIC_MSGEXT_RANEXT = "msgext:ranext";
	public static final String LOGIC_RANEXT = "ranext";
	//同步随机扩展入库队列
	public static final String SYNC_EXT = "sync:ext";
	
	//shuntphone入库
	public static final String SYNC_SHUNT_PHONE = "sync:shuntphone";
	
	
	
	/**
	 * 获取待发送数据的队列名
	 * @param channelId
	 * @return
	 */
	public static String getChannelTube(String channelId,String priority) {
		return "LOG:" + channelId+":" + priority;
	}
	
	public static String getGWChannelTube(String channelId){
		return "GWLOG:" + channelId;
	}
	
	/**
	 * //队列优先级ZSet队列
	 * @param channelId
	 * @return
	 */
	public static String getPriorityTube(String channelId){
		return "LOMT:PRIORITY:" + channelId;
	}

	/**
	 * 获取获得已勾兑状态报告的队列名
	 * @param accountId
	 * @return
	 */
	public static String getAccountDeliverTube(long accountId) {
		String tube = "AD:" + String.valueOf(accountId);
		return tube;
	}
	
	/**
	 * 用户上行信息队列名,用于状态报告拉取和推送
	 * @param accountId
	 * @return
	 */
	public static String getAccountMOTube(long accountId){
		String tube = "MOAD:" + String.valueOf(accountId);
		return tube;
	}
	
	/**
	 * 上行信息队列，用于入库
	 * 
	 * @return
	 */
	public static String getSyncMOTube(){
		return SYNC_INSERT_MO_KEY;
	}

	public static String getSyncMtTube() {
		return SYNC_INSERT_MT_KEY;
	}
	
	public static String getSyncRptTube() {
		return SYNC_INSERT_MT_KEY;
	}
	
	public static String getLog2RptTube() {
		return SYNC_UPDATE_RPT_KEY;
	}

	/**
	 * 上行匹配
	 * @param channelId
	 * @return
	 */
	public static String getMoMatchDataTube(long channelId) {
		String tube = "MOMATCH:" + String.valueOf(channelId);
		return tube;
	}
	
	public static String getMtLogDataTube() {
		String tube = "logic:modispatch";
		return tube;
	}

	/**
	 * 获取待勾兑状态报告的队列名
	 * @param channelId
	 * @return
	 */
	public static String getMtMatchDataTube(long channelId) {
		String tube = "MTMATCH:" + String.valueOf(channelId);
		return tube;
	}

	public static String getDailySentDataTube(long channelId) {
		String tube = "DAYSENT:" + String.valueOf(channelId);
		return tube;
	}
	
	
	/**
	 * 获取日限队列名,队列最后一位为当前日期
	 * @return
	 */
	public static String getDaylimitTube(){
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DATE);
		return QueueUtils.LOGIC_DAY_LIMIT+":"+day;
	}
	
	/**
	 * 获取内容日限队列名,队列最后一位为当前日期
	 * @return
	 */
	public static String getDaylimitConTube(){
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DATE);
		return QueueUtils.LOGIC_DAY_CON_LIMIT+":"+day;
	}
	
	/**
	 * 获取日限队列名,队列最后一位为昨天日期,用作删除队列
	 * @return
	 */
	public static String getDaylimitYesterdayTube(){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1);  //前一天
		int day  = c.get(Calendar.DATE);
		return QueueUtils.LOGIC_DAY_LIMIT+":"+day;
	}
	
	/**
	 * 获取内容日限队列名,队列最后一位为昨天日期,用作删除队列
	 * @return
	 */
	public static String getDaylimitConYesterdayTube(){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1);  //前一天
		int day  = c.get(Calendar.DATE);
		return QueueUtils.LOGIC_DAY_CON_LIMIT+":"+day;
	}
	
	
	public static String getAuditConTube(String accId,String channelId,String conHash){
		return "audit:" + accId + ":" + channelId + ":" + conHash;
	}
	
	public static String percentage(double numerator,double denominator) {
		if(denominator == 0) {
			return "0";
		}
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);
		String result = numberFormat.format(numerator/denominator*100);
		return result;
	}
}
