package com.jjxt.ssm.common;

import java.nio.charset.Charset;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

public class Constants {

	public static class Common {
		public static class Logger {
			public static final int LEVEL_INFO = 0;
			public static final int LEVEL_ERROR = 1;
			public static final int LEVEL_WRAN = 2;
		}

		public static class Carrier {
			public static final String CMCC = "cmcc";
			public static final String UNICOM = "unicom";
			public static final String TELECOM = "telecom";
			public static final String UNKNOWN = "unknown";
			public static final String INTL = "intl";
		}

		public static class Logic {
			// kill -15时设置
			public static boolean isStop = false;
		}

		public static class GW {
			// kill -15时设置
			public static boolean isStop = false;
		}

		public static class Redis {
			public static class Naming {
				public static final String LOGIC = "logic";
				public static final String LOG = "log";
				public static final String REPORT = "rep";
				public static final String SYNC = "sync";
				public static final String LOAD = "load";
				public static final String BLACK = "black";
				public static final String SHUNT_PHONE = "shuntphone";
				public static final String WHITE = "white";
				public static final String AUDIT = "audit";
				public static final String PRIORITY="priority";
				public static final String DYNAMIC="dynamic";
				public static final String GW="gwlog";
				public static final String LONGMSG="longMsg";
				public static final String REDIS60="monitor";
				public static final String MONTHLIMIT="monthlimit";
			}
			public static class Config {
				public static String REDIS_FLAG = "1";
				public static String REDIS_ACCOUNT = "";
				public static String REDIS_CHANNEL = "";
			}
		}

		public static class MessageFormat {
			public static final byte GBK = 15;
			public static final byte Unicode = 8;
			public static final byte ASCII = 0;
		}

		public static class MessageEncoding {
			public static final String ASCII = "US-ASCII";
			public static final String Unicode = "UnicodeBigUnmarked";
			public static final String GBK = "GBK";
			// American Standard Code for Information Interchange
			public static final String ENC_ASCII = "ASCII";
			// Windows Latin-1
			public static final String ENC_CP1252 = "Cp1252";
			// ISO 8859-1, Latin alphabet No. 1
			public static final String ENC_ISO8859_1 = "ISO8859_1";
			// Sixteen-bit Unicode Transformation Format, big-endian byte order
			// with byte-order mark
			public static final String ENC_UTF16_BEM = "UnicodeBig";
			// Sixteen-bit Unicode Transformation Format, big-endian byte order
			public static final String ENC_UTF16_BE = "UnicodeBigUnmarked";
			// Sixteen-bit Unicode Transformation Format, little-endian byte
			// order
			// with byte-order mark
			public static final String ENC_UTF16_LEM = "UnicodeLittle";
			// Sixteen-bit Unicode Transformation Format, little-endian byte
			// order
			public static final String ENC_UTF16_LE = "UnicodeLittleUnmarked";
			// Eight-bit Unicode Transformation Format
			public static final String ENC_UTF8 = "UTF8";
			// Sixteen-bit Unicode Transformation Format, byte order specified
			// by
			// a mandatory initial byte-order mark
			public static final String ENC_UTF16 = "UTF-16";
		}

		public static class Config {
			public static String CONF_FILE = "config.xml";
			public static final int DEF_FIRST_MSG_CHARGE_LEN = 70;
			public static final int DEF_SUBSEQ_MSG_CHARGE_LEN = 67;
			public static int INSTANCE_ID = 0;

			public static class System {
				public static final int SO_BACK_LOG_SIZE = 10 * 1024;

				public static final int getWorkerThreadsNum() {
					return Runtime.getRuntime().availableProcessors() + 1;
				}
			}

			public static class TestModel {// 是否是测试模式
				public static final String YES = "yes";
				public static final String NO = "no";
			}

			public static class NumSegment {
				public static final int MAJOR_SEG_CHARS = 3;
				public static final int SECONDARY_SEG_CHARS = 4;
				public static final int LOCATION_SEG_CHARS = 7;
			}

			public static class Content {
				public static final int MAX_CONTENT_LEN = 500;
//				public static final boolean FORCE_FULLWIDTH_SIGNATURE = false;
			}

			public static class Message {
				/* 短信优先级 */
				public static final long PRIORITY_HIGH = 0;
				public static final long PRIORITY_NORMAL = 1;
				public static final long PRIORITY_LOW = 2;
				public static final long PRIORITY_URGENT = 3;
			}

			public static class Database {
				public static final int DB_MIN_POOLED_CONNS = 5;
				public static final int DB_MAX_POOLED_CONNS = 10;
				public static final int DB_MAX_POOL_SIZE = 30;
				public static final int DB_CONN_IDLE_TIMEOUT = 3600 * 6;
				public static final int DB_CONN_FAIL_RETRY_INTERVAL = 10 * 1000;
				public static final String OPER_INSERT = "I";
				public static final String OPER_UPDATE = "U";
				public static final String OPER_DELETE = "D";
				public static int DB_SYNC_INTERVAL = 300;
				public static int DB_SYNC_BATCH_SIZE = 10000;
				public static int MAX_BATCH_LOG_SYNC_COUNT = 1000;
				public static int MAX_BATCH_LOG_CLEAN_COUNT = 1000;
				public static int MAX_BATCH_LOG_REPORT_COUNT = 1000;
			}

			public static class Informer {
				public static int MAX_BATCH_REPORTS_WAIT_SECONDS = 5;
				public static int MIN_BATCH_REPORTS_COUNT = 100;
			}

			// 入库,状态报告勾兑,统计 config by qklv 2017-09-08
			public static class Sync {
				public static int SYNC_REDIS_COUNT = 5000;
				// public static long SYNC_TABLE_COUNT = 2000000;

				public static int SYNC_INSERT_MTRPT_THREAD_NUM = 10;
				public static int SYNC_UPDATE_RPT_THREAD_NUM = 30;
				public static int SYNC_UPDATE_RPT_REDIS_COUNT = 1000;

				// shuntphone数据入库间隔时间 按秒计算
				public static long SYNC_SHUNT_PHONE_INTERVAL_TIME = 1200;

				// 入库积压报警
				public static int SYNC_ALARM_NUM = 10000;
			}
		}

		public static class Naming {
			public static final String CVN_SERVICE_PORT = "service-port";
			public static final String CVN_ENABLE_SSL = "enable-ssl";
			public static final String CVN_ENABLE_PULL = "enable-pull";
			public static final String CVN_SSL_SERVICE_PORT = "ssl-service-port";
			public static final String CVN_DATA_DIR = "data-dir";
			public static final String CVN_GW_ID = "gw-id";
			public static final String CVN_INSTANCE_ID = "instance-id";
			public static final String CVN_MAX_BATCH_REPORTS_WAIT = "maxBatchReportsWait";
			public static final String CVN_MIN_BATCH_REPORTS_COUNT = "minBatchReportsCount";
			public static final String CVN_DB_SYNC_INTERVAL = "db-sync-interval";
			public static final String CVN_GATEWAY_PROXY_ADDR = "gateway-server-addr";
			public static final String CVN_CMPP_SERVICE_PORT = "cmpp_service-port";
			public static final String CVN_CMPP_STATUS_SERVICE_PORT = "cmpp_status_service-port";
			public static final String CDN_DB_SMS_CORE = "db_sms_core";
			public static final String CDN_DB_SMS_CORE_2 = "db_sms_core_2";
			public static final String CDN_DB_SMS_LOG = "db_sms_log";
			public static final String CVN_REDIS_FLAG = "redis_flag";
			
			// 入库,状态报告勾兑,统计 by qklv 20170917
			public static final String CDN_DB_MANAGE = "db_manage";
			public static final String CDN_DB_SMS_INFORMATION_SCHEMA = "db_sms_information_schema";

			public static final String CVN_SYNC_REDIS_COUNT = "sync_redis_count";
			// public static final String CVN_SYNC_TABLE_COUNT =
			// "sync_table_count";
			public static final String CVN_PRE_RANEXT = "pre_ranext";
			public static final String CVN_TIMER_NUM = "timer_num";

			public static final String CVN_SYNC_INSERT_MTRPT_THREAD_NUM = "sync_insert_mtrpt_thread_num";
			public static final String CVN_SYNC_SYNC_UPDATE_RPT_THREAD_NUM = "sync_update_rpt_thread_num";
			public static final String CVN_SYNC_UPDATE_RPT_REDIS_COUNT = "sync_update_rpt_redis_count";

			// 清理MTMATCH一次性取出的数据
			public static final String CVN_DEL_MTMATCH_HSCAN_COUNT = "del_mtmatch_hscan_count";
			public static final String CVN_DEL_MTLOGIC_TASK_TIMER = "del_mtlogic_task_timer";

			// 模块报警
			public static final String CVN_ALARM_URL = "alarm_url";
			public static final String CVN_ALARM_NODE = "alarm_node";
			public static final String CVN_ALARM_NODE_PW = "alarm_node_pw";

			// shuntphone数据入库间隔时间 按秒计算
			public static final String CVN_SYNC_SHUNT_PHONE_INTERVAL_TIME = "shunt_phone_interval_time";

			// 入库积压报警
			public static final String CVN_SYNC_ALARM_NUM = "sync_alarm_num";
			// 日限清理key极限
			public static final String CVN_DEL_LOGICDAYLIMIT_MAXNUM = "del_logicdaylimit_maxnum";
			// 当待发队列无数据时的休眠时间
			public static final String CVN_NODATA_SLEEP_TIME = "nodata_sleep_time";
			// 每次从队列取出数量
			public static final String CVN_EACH_COUNT = "each_count";
			// 当cmpp推送给客户无数据时的休眠时间
			public static final String CVN_CMPP_SEND_ACC_DELIVER_SLEEP_TIME = "cmpp_send_acc_deliver_sleep_time";

			// audit服务器相关
			public static final String CVN_EXT_PORT = "ext_port";
			public static final String CVN_EXT_ENABLE_SSL = "ext_enable_ssl";
			public static final String CVN_EXT_CHECK_IP_ADDR = "ext_check_ip_addr";

			// http推送给客户无数据时的休眠时间
			public static final String CVN_HTTP_SEND_ACC_DELIVER_SLEEP_TIME = "http_send_acc_deliver_sleep_time";
			
			public static final String CVN_ALL_ACC_REDIS_FLAG ="all_acc_redis_flag";
			public static final String CVN_ALL_CHAN_REDIS_FLAG ="all_chan_redis_flag";
			public static final String CVN_REDIS_ACCOUNT ="redis_account";
			public static final String CVN_REDIS_CHANNEL ="redis_channel";

		}

		public static class DataObjects {
			public static final String KEY_VERIF_CODE_KEYWORDS = "verif_code_keywords";
			public static final String MO_BLACK_KEYWORDS = "mo_black_keywords";
			public static final String MO_BLACK_KEYWORDS_EQUALS = "mo_black_keywords_equals";
			public static final String CN_SEND_LIMITS = "send_limits";
			public static final String CN_AUTO_CHANNEL_SWITCH = "auto_channel_switch";
			public static final String CN_UPDATE_STATUS_REALTIME = "enable_realtime_status";
			public static final String CN_ENABLE_SHUNT = "enable_shunt";
			public static final String CN_SHUNT_EMPTY_CODE = "shunt_empty_code";
			public static final String CN_SHUNT_DELIVRD_CODE = "shunt_delivrd_code";
			public static final String CN_IS_SYNC_SHUNT = "is_sync_shunt";
			public static final String CN_MAX_READY_JOBS = "max_ready_jobs";
			public static final String CN_WEB_AUTH_IP = "web_auth_ip";
			public static final String CN_BATCH_MT_SYNC_COUNT = "batch_mt_sync_count";
			public static final String CN_BATCH_REPORT_SYNC_COUNT = "batch_report_sync_count";
			public static final String CN_BATCH_MT_CLEAN_COUNT = "batch_mt_clean_count";
			public static final String KEY_OVERRIDE_MO_DEST_ACCOUNTS = "override_mo_dest_accounts";
			public static final String KEY_STATS_TO_CONVERT = "stats_to_convert";
			public static final String KEY_OVERRIDE_PUSH_FORMAT_XML_ACCOUNTS = "override_push_format_xml_accounts";
			public static final String KEY_OVERRIDE_PUSH_FORMAT_YL_ACCOUNTS = "override_push_format_yl_accounts";
			public static final String KEY_CHANNEL_PROVINCE_FORWARDS = "channel_province_forwards";
			public static final String KEY_IGNORE_DAYSENT_ACCOUNTS = "ignore_daysent_accounts";
			public static final String KEY_ENABLE_31_PROVS_TEST = "enable_31_provs_test";
			// mt表数量
			public static final String KEY_MT_TABLE_NUM = "mt_table_num";
			// 通道自定义format和编码
			public static final String KEY_CHAN_FORMAT_UNICODE = "chan_format_unicode";
			// 帐号,状态变为delivrd
			public static final String KEY_ACC_STATS_TO_DELIVRD = "acc_stats_to_delivrd";
			// 相似度值
			public static final String KEY_SIMILARITY_SCORE = "similarity_score";
			//签名全角半角true  false
			public static final String KEY_FORCE_FULLWIDTH_SIGNATURE = "force_fullwidth_signature";
			public static final String KEY_HTTP_CHANNEL_FOR_URI = "http_channel_for_uri";
			//上行追加msgid和ref
			public static final String KEY_MO_MSGID_REF = "mo_msgid_ref";
		}
	}

	public static class HttpApi {
		public static class Config {
			public static int PORT = 8080;
			public static int SSL_PORT = 8443;
			public static boolean ENABLE_SSL = false;
			public static int MIN_PULL_INTERVAL = 1000;
			public static boolean ENABLE_PULL = false;
			// 当http推送给客户无数据时的休眠时间
			public static int HTTP_SEND_ACC_DELIVER_SLEEP_TIME = 10000;

			public static class Http {
				public static enum KeepAliveStrategy {
					DETECT_CLIENT_HEADER, OVERRIDE_TO_ON, OVERRIDE_TO_OFF,
				};

				public static final Charset DEFAULT_CHAR_ENCODING = CharsetUtil.UTF_8;
				public static final KeepAliveStrategy KEEP_ALIVE_STRATEGY = KeepAliveStrategy.OVERRIDE_TO_OFF;
				public static final HttpVersion DEFAULT_HTTP_VERSION = HttpVersion.HTTP_1_1;

				public static final boolean shouldKeepAlive(HttpRequest request) {
					boolean result = false;
					switch (KEEP_ALIVE_STRATEGY) {
					case DETECT_CLIENT_HEADER:
						result = HttpHeaders.isKeepAlive(request);
						break;
					case OVERRIDE_TO_ON:
						result = true;
						break;
					case OVERRIDE_TO_OFF:
						result = false;
						break;
					}
					return result;
				}
			}

			public static class Auth {
				public static final long REQ_TTL = 5 * 60 * 1000;
				public static final String TIMESTAMP_FORMAT = "yyyyMMddHHmmss";
				public static final String WHITELISTED_IP_DELIMITER = ",";
			}

			public static class RequestParse {
				public static final String DEST_NUMBER_DELIMITER = ",";
				public static final String PERSONALIZED_CONTENT_DELIMITER = "|||";
				public static final String PERSONALIZED_CONTENT_DELIMITER_REG = "\\|\\|\\|";
				public static final String PERSONALIZED_CONTENT_DELIMITER_V1 = "|";
				public static final String PERSONALIZED_CONTENT_DELIMITER_REG_V1 = "\\|";
			}
		}

		public static class Protocol {
			public static final String HTTP_HDR_KEY_SERVER = "Server";
			public static final String HTTP_HDR_KEY_CONNECTION = "Connection";
			public static final String HTTP_HDR_KEY_CONTENT_TYPE = "Content-Type";

			public static final String HTTP_HDR_VAL_SERVER = "JJM API 1.0.0";
			public static final String HTTP_HDR_VAL_CONNECTION = "close";
			public static final String HTTP_HDR_VAL_CONTENT_TYPE = "application/json";
		}

		public static class RequestHandling {
			public static final int RESULT_OK = 0;
			public static final int RESULT_FAILED = -9999;
			public static final int RESULT_EXCEPTION = -9998; // pross方法未处理的异常
			public static final int WRITE_EXCEPTION = -9997; // 写入流异常，网络异常
			public static final int VALID_EMPTY = -9996; // valider 为空
			public static final int SESSION_EMPTY = -9995; // session 为空
		}

		public static class RequestParams {
			public static final String RP_ACCOUNT = "account";
			public static final String RP_TOKEN = "token";
			public static final String RP_TIMESTAMP = "ts";
			public static final String RP_DEST_NUMBERS = "dest";
			public static final String RP_CONTENT = "content";
			public static final String RP_EXT = "ext";
			public static final String RP_REF = "ref";
			public static final String RP_FORCED_SINGLE_CONTENT = "singlecontent";
			public static final String RP_FORCED_CHANNEL_ID = "forcedchannelid";
			public static final String RP_FORCED_EXT = "forcedext";

			public static final String RP_ACCOUNT_V1 = "name";
			public static final String RP_TOKEN_V1 = "key";
			public static final String RP_TIMESTAMP_V1 = "seed";
			public static final String RP_DEST_NUMBERS_V1 = "dest";
			public static final String RP_CONTENT_V1 = "content";
			public static final String RP_EXT_V1 = "ext";
			public static final String RP_REF_V1 = "reference";
			public static final String RP_FORCED_SINGLE_CONTENT_V1 = "singlecontent";
			public static final String RP_FORCED_CHANNEL_ID_V1 = "forcedchannelid";
			public static final String RP_FORCED_EXT_V1 = "forcedext";
			public static final String RP_PWD = "pwd";
		}

		public static class AuthStatus {
			public static final int SUCCESS = 0;
			// Missing account.
			public static final int ERR_MISSING_ACCOUNT = -1001;
			// Missing time-stamp.
			public static final int ERR_MISSING_TIMESTAMP = -1002;
			// Missing key.
			public static final int ERR_MISSING_TOKEN = -1003;
			// Invalid time-stamp.
			public static final int ERR_INVALID_TIMESTAMP = -1004;
			// Time-stamp has expired.
			public static final int ERR_TIMESTAMP_EXPIRED = -1005;
			// Invalid key.
			public static final int ERR_INVALID_TOKEN = -1006;
			// Authentication failed.
			public static final int ERR_UNAUTHORIZED_REQUEST = -1007;
			// Source IP validation failed.
			public static final int ERR_UNAUTHORIZED_SRC_IP = -1008;
			// parameter have empty
			public static final int ERR_PARAMETER_EMPTY = -1009;
			// Unknown error.
			public static final int ERR_UNKNOWN = -1999;
		}

		public static class ValidationStatus {
			public static final int SUCCESS = 0;

			// ---------------------------- Account specific errors
			// ----------------------------//
			// Insufficient account balance.
			public static final int ERR_ACC_INSUFFICIENT_BALANCE = -2001;
			// Account is disabled.
			public static final int ERR_ACC_DISABLED = -2002;
			// Account is not in service time.
			public static final int ERR_ACC_NOT_IN_SERVICE = -2003;
			// Account query action is too frequent.
			public static final int ERR_ACC_QUERY_TOO_FREQUENT = -2010;

			// ---------------------------- Product specific errors
			// ----------------------------//
			// Product not configured.
			public static final int ERR_PRODUCT_NOT_CONFIGURED = -2101;
			// Product is paused.
			public static final int ERR_PRODUCT_PAUSED = -2102;
			// Not in product service time.
			public static final int ERR_PRODUCT_NOT_IN_SERVICE = -2103;
			// Product is not a kind of SMS or MMS.
			public static final int ERR_INVALID_PRODUCT = -2104;
			// ---------------------------- Channel specific errors
			// ----------------------------//
			// Channel not found.
			public static final int ERR_CHANNEL_NOT_CONFIGURED = -2201;
			public static final int ERR_CHANNEL_NOT_IN_SERVICE = -2202;
			// ---------------------------- Destination specific errors
			// ----------------------------//
			// Missing destination number.
			public static final int ERR_MISSING_DEST_NUMS = -2301;
			// Invalid destination numbers.
			public static final int ERR_INVALID_DEST_NUMS = -2302;
			//http包超长
			public static final int ERR_OVERSIZE_DEST_NUMS = -2303;
			// ---------------------------- Extension specific errors
			// ----------------------------//
			// Invalid extension number.
			public static final int ERR_INVALID_EXT = -2401;
			public static final int ERR_TIME_FORMAT_INVALID = -2402;
			// ---------------------------- Content specific errors
			// ----------------------------//
			// Missing content.
			public static final int ERR_MISSING_CONTENT = -2501;
			// Content exceeds limit.
			public static final int ERR_CONTENT_OVERSIZE = -2502;
			// Missing signature in content.
			public static final int ERR_CONTENT_MISSING_SIGN = -2503;
			// Rejected due to the keywords.
			public static final int ERR_REJECTED_BY_KEYWORDS = -2504;
			// Number of destination doesn't match the number of content for a
			// personalized message.
			public static final int ERR_CONTENT_DEST_NOT_MATCH = -2505;
			// Template is not filed.
			// public static final int ERR_TPL_NOT_FILED = -2505;
			// Missing size for a multiple-part message.
			// public static final int ERR_MISSING_PK_TOTAL = -2506;
			public static final int ERR_UNKNOWN = -2999;
		}

		public static class ProcessStatus {
			public static final int SUCCESS = 0;
			public static final int ERR_UNKNOWN = -3999;
		}
	}

	public static class Gateway {
		public static class Config {
			public static int GW_ID = 0;
			public static final int CHANNEL_CFG_START_RETRY_INTERVAL = 60 * 1000;
			public static final int CHANNEL_CFG_LOAD_RETRY_INTERVAL = 10 * 1000;
			public static final int CHANNEL_CFG_RELOAD_INTERVAL = 10 * 1000;
			public static final int CHANNEL_CLS_RELOAD_INTERVAL = 10 * 1000;
			public static final int CHANNEL_SYNC_FAILED_MT_LOG_INTERVAL = 60 * 1000;
			public static final int INFORMER_CFG_LOAD_RETRY_INTERVAL = 10 * 1000;
			public static final int INFORMER_CFG_RELOAD_INTERVAL = 10 * 1000;

			public static final int RECONNECT_WAIT_FOR_PERSISTENT_CONN = 60 * 1000;
			public static final int CHECK_CONN_ALIVE_INTERVAL = 10 * 1000;
			public static final int SO_CMPP_CONNECT_TIMEOUT = 60 * 1000;
			public static final int SO_SGIP_CONNECT_TIMEOUT = 60 * 1000;
			public static final int SO_SMGP_CONNECT_TIMEOUT = 60 * 1000;
			public static final int SO_HTTP_CONNECT_TIMEOUT = 60 * 1000;
			public static final int SO_HTTP_SOCKET_TIMEOUT = 60 * 1000;
			public static final int DEFAULT_SLIPPING_WINDOW_SIZE = 16;
			public static final int DETECT_NEW_CHANNELS_INTERVAL = 50 * 1000;
			public static final int DEFAULT_MAX_BATCHED_ITEMS = 500;
			public static final int MAX_MSG_FROM_LEN = 21;
			public static final String PERSISTENT_RT_DATA_DIR = "/data/rt";

			public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";

			public static final String DAY_KEY_FORMAT = "yyyyMMdd";

			public static final String RPT_TIMESTAMP_FORMAT = "yyyyMMddHHmmss";

			public static final String DATE_FORMAT_FOR_MO_PARTITION = "yyyyMM";

			public static final String CHANNEL_VARIANT_PACKAGE = "com.jjm.gateway.channel.variant";

			public static final String SYS_PROP_CHANNEL = "CHANNEL";

			public static final String SYS_PROP_LOGIC = "LOGIC";

			public static final String CHANNEL_ALL = "ALL";

			public static final String INFORM_RESP_SUCCESS = "success";

			public static final int DEFAULT_MAX_READY_JOBS = 10000;

			public static final int MAX_MSG_SUBMIT_RETRIES = 3;

			public static final int CHANNEL_SUBMIT_EXPIRATION = 1 * 60 * 1000;

			public static String GW_FORWARD_SVR = "lvsvr";

			public static String PRE_RANEXT = "9";

			public static String TASK_TIME = "23:30";

			// 清理MTMATCH一次性取出的数据
			public static int DEL_MTMATCH_HSCAN_COUNT = 1000;
			// 清理定时任务时间
			public static String DEL_MTLOGIC_TASK_TIME = "00:00";
			// public static int DEL_HOUR_OF_DAY_TASK = 0;
			// public static int DEL_MINUTE_TASK = 0;
			// public static int DEL_SECOND_TASK = 0;
			// public static int DEL_MILLISECOND_TASK = 0;
			// public static int DEL_DAY_OF_MONTH_TASK = 1;

			// 报警模块配置
			public static String ALARM_URL = "http://101.200.189.243:9999/";
			public static String ALARM_NODE = "api";
			public static String ALARM_NODE_PW = "api1";
			// 日限清理key极限
			public static int DEL_LOGICDAYLIMIT_MAXNUM = 6000000;
			// mt默认表数
			public static int DEFAULT_MT_TABLE_NUM = 5;
			// 当待发队列无数据时的休眠时间
			public static int NODATA_SLEEP_TIME = 1000;
			// 队列中每次取出的数量
			public static int EACH_COUNT = 50;
		}

		public static class SendFlag {
			public static final String NORMAL = "NORMAL";
			public static final String SHUNT = "SHUNT";
			public static final String BLACK_TO_1 = "BLACK_TO_1";
			public static final String BLACK_TO_2 = "BLACK_TO_2";
			public static final String BLACK_TO_3 = "BLACK_TO_3";
			public static final String BLACK_TO_4 = "BLACK_TO_4";
			public static final String WHITE_TO = "WHITE_TO";
			public static final String BLACK_SIGN = "BLACK_SIGN";
			public static final String KEYWORD = "KEYWORD";
			public static final String SIGN_EXT = "SIGN_EXT";
			public static final String REPEATED_MIN = "REPEATED_MIN";
			public static final String REPEATED_CON = "REPEATED_CON";
			public static final String REPEATED_DAY = "REPEATED_DAY";
			public static final String RETRIES_FAILED = "RETRIES_FAILED";
			public static final String TEST_MODE = "TEST_MODE";
			// 必审词
			public static final String CHECK_MUST = "WA:REJECT";
			// 跳过必审词
			public static final String SKIP_WORD = "WA:AUDIT";
			//黑模板
			public static final String BLACK_MODEL = "BLACK_MODEL";
			//白模板
			public static final String WHITE_MODEL = "WHITE_MODEL";
			//命中审核词进审核队列
			public static final String CHECK_MUST_AUDIT = "CHECK_MUST_AUDIT";
			//相同内容进审核队列
			public static final String COUNTENT_AUDIT="CONTENT_AUDIT";
			//策略模板进审核队列
			public static final String RULE_AUDIT="RULE_AUDIT";
			//日限进入审核队列
			public static final String DAY_LIMIT_AUDIT="DAY_LIMIT_AUDIT";
		}

		public static class CustomMtReport {
			public static final String MT_RPT_CONTENT_REJECTED = "REJECTED";
			public static final String MT_RPT_TO_BLACKLISTED = "E:BLACK";
			public static final String MT_RPT_SIGN_BLACKLISTED = "E:SIGN";
			public static final String MT_RPT_EXCEEDS_60_SECS_LIMIT = "E:ODSL:60";
			public static final String MT_RPT_EXCEEDS_1_DAY_LIMIT = "E:ODDL";
			public static final String MT_RPT_UNDELIVERED = "E:UNDELIV";
			public static final String MT_RPT_ACCOUNT_INVALID = "E:ACC:FAR";
			public static final String MT_RPT_LOCATION_ERR = "E:LOCATION:FAR";
			public static final String MT_RPT_MUST_SIGNLISTED = "E:WA:REJECT";
		}

		public static class Naming {
			public static final String EN_SUBMIT_ERR = "SUBMIT_ERR";
			public static final String VN_PENDING_MESSAGES = "pendingMessages";
			public static final String VN_SUBMITTED_MESSAGES = "submittedMessages";
			public static final String VN_MESSAGE_IDS = "messageIds";
			// 滑动窗
			public static final String CN_SLIPPING_WINDOW_SIZE = "slippingWindowSize";
			public static final String CN_DEST_DELIMITER = "destDelimiter";
			public static final String CN_MAX_BATCH_ITEMS = "maxBatchItems";
			public static final String CN_ENCODING = "encoding";

			// 联通接收状态报告端口,在channel表的extras字段配
			public static final String CN_LOCAL_PORT = "localPort";
			public static final String CN_NODE_ID = "nodeId";
			public static final String CN_REMOVE_SIGN = "removeSign";
			public static final String CN_IGNORE_EXT = "ignoreExt";
			public static final String CN_NEED_TIMING = "needTiming";
			public static final String CN_REMOVE_DEFAULT_SIGN = "removeDefaultSign";
			public static final String CN_SECONDARY_PORT = "secondaryPort";
			public static final String CN_FEE_NUMBER = "feeNumber";
			public static final String CN_GEN_REPORT_RATE = "genReportRate";
			public static final String CN_DIST_CHANNEL_MSG_ID = "distChannelMsgId";
		}

		public static class ChannelStatus {
			public static final String CHANNEL_STAT_NORMAL = "NORMAL";
		}

		public static class ReportStatus {
			public static final String DELIVERED = "DELIVRD";
			public static final String GEN = "GEN";
		}

		public static class RemoteDatabase {
			public static final String TN_MT_LOG_PREFIX = "mt_";
			public static final String TN_MO_LOG_PREFIX = "mo_";
			public static final String TN_BLACK_LIST_LEVEL_2 = "pre_black_level_2";
		}

		public static class MoMatchModel {
			public static final String DEFAULT_CHANNEL_MOMATCH_MODEL = "ext";
			public static final String CHANNEL_MOMATCH_PHONE = "phone";
			public static final String CHANNEL_MOMATCH_EXTPHONE = "extphone";
		}
	}

	public static class Cmpp {
		public static class Error {
			public static final int ERR_ACC_INSUFFICIENT_BALANCE = 10;
			public static final int ERR_ACC_NO_PROD_BINDED = 11;
			public static final int ERR_INVALID_MSG_FORMAT = 21;
			public static final int ERR_INVALID_TP_UDHI = 22;
			public static final int ERR_MSG_NO_SIGNATURE = 23;
			public static final int ERR_MSG_LEN_EXCEED_LIMIT = 25;
			// 非法的scrid
			public static final int ERR_INVALID_SRCID = 28;
			public static final int ERR_INVALID_ACCOUNT = -101;
			public static final int ERR_INVALID_SUBMISSION = -102;
			public static final int ERR_INVALID_DESTNUMBERS = -103;
			public static final int ERR_INVALID_MSG_CONTENT = -104;
			public static final int ERR_INVALID_DEST_CHANNEL = -105;
			public static final int ERR_UNKNOWN = -127;
		};

		public static class Config {
			public static int CMPP_LISTEN_PORT = 7890;
			public static int CMPP_STATUS_LISTEN_PORT = 27890;
			public static final int CMPP_MAX_CLIENT_CONNS = 10;
			public static final int MSG_ID_SHIFT = 10;
			public static final int CMPP_DEDUCT_SAFE_MARGIN = 10;
			public static final byte CMPP_VERSION = (byte) 0x30;
			public static long HEART_BEAT_INTERVAL = 50000;
			public static long HEART_BEAT_RETRIES = 3;
			public static long RECONNECT_INTERVAL = 10000;
			public static final boolean REPORT_IN_SAME_LINK = false;
			public static final int CMPP_SEND_ACC_DELIVER_THREADS = 2;
			/* CMPP长短信键过期时间 */
			public static int EXPIRE_TIME = 3600;
			// 当cmpp推送给客户无数据时的休眠时间
			public static int CMPP_SEND_ACC_DELIVER_SLEEP_TIME = 3000;

		};

		public static enum AuthenticationStatus {
			SUCCESS, INVALID_MSG_CONTENT, INVALID_ACCOUNT, AUTH_ERROR, VERSION_ERROR, UNKNOWN_ERROR, OUT_OF_SERVICE_TIME_ERROR, NO_PRODUCTS_BINDED, INVALID_PRODUCT_TYPE, EXCEED_MAX_CONNS_LIMIT, INVALID_PRODUCT_STATUS
		};

	}

	// audit服务器相关
		public static class Ext {
			public static class Config {
				public static int PORT = 8100;
				// public static int SSL_PORT = 8443;
				public static boolean ENABLE_SSL = false;
				// public static int MIN_PULL_INTERVAL = 1000;
				// public static boolean ENABLE_PULL = false;
				public static String CHECK_IP_ADDR = "";

			}

			public static class RequestHandling {
				public static final int RESULT_OK = 0;
				public static final int RESULT_FAILED = -9999;
				public static final int RESULT_EXCEPTION = -9998; // pross方法未处理的异常
				public static final int WRITE_EXCEPTION = -9997; // 写入流异常，网络异常
			}

			public static class Audit {
				public static class AuthStatus {
					public static final int SUCCESS = 0;
					// Missing flag.
					public static final int ERR_MISSING_FLAG = -1001;
					// Missing amount.
					public static final int ERR_MISSING_AMOUNT = -1002;
					// Missing param.
					public static final int ERR_MISSING_PARAM = -1003;
					// Source IP validation failed.
					public static final int ERR_UNAUTHORIZED_SRC_IP = -1004;
					// AMOUNT长度小于等于0
					public static final int ERR_AMOUNT_ZERO = -1005;
					// param长度错误
					public static final int ERR_PARRAM_LENGTH = -1006;
					// flag错误 T,F大小写都可以
					public static final int ERR_FLAG_FAULT = -1007;
					// 对某个key执行太过频繁
					public static final int ERR_KEY_QUERY_TOO_FREQUENT = -1008;
					// 线程数超过5个
					public static final int ERR_EXCEED_THREAD_MAX = -1009;
					public static final int ERR_UNKNOWN = -1111;
				}

				public static class ReportStatus {
					public static final String AUDIT_FAILURE = "audit:failure";
				}
			}

			public static class CutChannel {
				public static class AuthStatus {
					public static final int SUCCESS = 0;
					// 切换gwLog通道(src_channel_id,dest_channel_id,num)
					// 切换log通道(src_channel_id,dest_channel_id,num,app_id,priority)
					// 切换log紧急通道(channel_id,app_id,num)
					// 清理rep的redis(key)

					// Missing src_channel_id.
					public static final int ERR_MISSING_SRC_CHANNEL_ID = -2001;
					// Missing dest_channel_id.
					public static final int ERR_MISSING_DEST_CHANNEL_ID = -2002;
					// Missing num.
					public static final int ERR_MISSING_NUM = -2003;
					// Missing app_id.
					public static final int ERR_MISSING_APP_ID = -2004;
					// Missing priority.
					public static final int ERR_MISSING_PRIORITY = -2005;
					// Source IP validation failed.
					public static final int ERR_UNAUTHORIZED_SRC_IP = -2006;
					// num长度小于等于0
					public static final int ERR_NUM_ZERO = -2007;
					// priority不是0,1,2
					public static final int ERR_PRIORITY = -2008;

					// 切换log紧急通道(channel_id,app_id,num)
					public static final int ERR_MISSING_CHANNEL_ID = -2009;
					// 清理rep的redis(key)
					public static final int ERR_MISSING_KEY = -2010;

					public static final int ERR_CUTGWLOG_UNKNOWN = -2222;
					public static final int ERR_CUTLOG_UNKNOWN = -3333;
					public static final int ERR_DELREP_UNKNOWN = -4444;
				}
			}

			public static class PushAgain {
				public static class AuthStatus {
					public static final int SUCCESS = 0;
					public static final int ERR_MISSING_LOG_DATE = -3001;
					//Source IP validation failed.
					public static final int ERR_UNAUTHORIZED_SRC_IP = -3002;
					
					public static final int ERR_PUTREPORT_UNKNOWN = -5555;
					public static final int ERR_PUTMO_UNKNOWN = -6666;
				}
			}
			
			public static class Priority {
				public static class AuthStatus {
					public static final int SUCCESS = 0;
					public static final int ERR_MISSING_CHANNEL_ID= -4001;
					//Source IP validation failed.
					public static final int ERR_UNAUTHORIZED_SRC_IP = -4002;
					
					public static final int ERR_PRIORITY_UNKNOWN = -7777;
				}
			}

	}
		
	public static class Map {
		public static class Province{
			public static final String[] province= {"北京",
					"天津","河北","山西","内蒙古","辽宁","吉林","黑龙江","上海","江苏","浙江","安徽","福建","江西","山东","河南","湖北",
					"湖南","重庆","四川","贵州","云南","西藏","陕西","甘肃","青海","宁夏","新疆","广东","广西","海南","unkown"};
		}
	}	
}
