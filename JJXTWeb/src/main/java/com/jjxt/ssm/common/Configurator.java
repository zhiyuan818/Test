package com.jjxt.ssm.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.jjxt.ssm.redis.JJMRedisConfig;
import com.jjxt.ssm.redis.JJMRedisPoolMap;


public class Configurator {

	protected static final Logger LOGGER = LoggerFactory.getLogger(Configurator.class);
	private static Configurator instance = null;
	private final static Object LOCK = new Object();


	private Configurator() {
	}

	public static Configurator getInstance() {
		synchronized (LOCK) {
			if (instance == null) {
				instance = new Configurator();
			}
			return instance;
		}
	}

	/**
	 * Load the configuration data from XML file.
	 * 
	 * @param configFile
	 * @return
	 */
	public boolean loadConfig(String configFile) {
		boolean result = false;

		if(configFile==null || configFile.length()==0){
			return false;
		}
		configFile = configFile.trim();

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(configFile);

			List<JJMRedisConfig> list = new ArrayList<JJMRedisConfig>();
			NodeList redises = doc.getElementsByTagName("redises");
			if(redises != null && redises.getLength() > 0) {
				for(int i = 0;i<redises.getLength(); i++) {
					Node item = redises.item(i);
					NodeList childNodes = item.getChildNodes();
					if(childNodes != null && childNodes.getLength()>0) {
						for(int j=0;j<childNodes.getLength();j++) {
							Node child = childNodes.item(j);
							if (child instanceof Element) {
								String nodeName = child.getNodeName();
								if("common".equals(nodeName)){
									NodeList thirdNodes = child.getChildNodes();
									if(thirdNodes!=null && thirdNodes.getLength()>0){
										for(int k=0;k<thirdNodes.getLength();k++){
											Node third = thirdNodes.item(k);
											if(!(third instanceof  Element)){
												continue;
											}
											JJMRedisConfig config = new JJMRedisConfig();
											String redisName = getNodeValue(third, "name");
											config.setRedisName(redisName);
											config.setMaxTotal(getNodeIntValue(third, "maxActive"));
											config.setMaxIdle(getNodeIntValue(third, "maxIdle"));
											config.setMaxWait(getNodeIntValue(third, "maxWait"));
											config.setReadTimeout(getNodeIntValue(third, "readTimeout"));
											config.setSentinel(Boolean.valueOf(getNodeValue(third, "isSentinel")));
											config.setUserName(getNodeValue(third, "user"));
											config.setPasswd(getNodeValue(third, "password"));
											config.setPwd(Boolean.valueOf(getNodeValue(third, "isPwd")));
											
											Node ip = third.getFirstChild();
											Set<String> set = new HashSet<String>();
											while (ip != null) {
												if (ip.getNodeType() == Node.ELEMENT_NODE) {
													Element entry = (Element) ip;
													set.add(getNodeValue(entry, "ip") + ":" + getNodeValue(entry, "port"));
												}
												ip = ip.getNextSibling();
											}
											config.setIps(set);
											list.add(config);
										}
									}
								}else if("acc".equals(nodeName) || "chan".equals(nodeName)){
									String flag = getNodeValue(child, "flag");
									NodeList thirdNodes = child.getChildNodes();
									Set<String> set = new HashSet<String>();
									if(thirdNodes!=null && thirdNodes.getLength()>0){
										for(int k=0;k<thirdNodes.getLength();k++){
											Node third = thirdNodes.item(k);
											if(!(third instanceof  Element)){
												continue;
											}
											String thirdNodeName = third.getNodeName();
//											String flag = getNodeValue(third, "flag");
											if(thirdNodeName.equals("address")){
												set.add(getNodeValue(third, "ip"));
											}else{
												JJMRedisConfig config = new JJMRedisConfig();
												String redisName = getNodeValue(third, "name");
												config.setRedisName(nodeName+flag+":"+redisName);
												config.setMaxTotal(getNodeIntValue(third, "maxActive"));
												config.setMaxIdle(getNodeIntValue(third, "maxIdle"));
												config.setMaxWait(getNodeIntValue(third, "maxWait"));
												config.setReadTimeout(getNodeIntValue(third, "readTimeout"));
												config.setSentinel(Boolean.valueOf(getNodeValue(third, "isSentinel")));
												config.setUserName(getNodeValue(third, "user"));
												config.setPasswd(getNodeValue(third, "password"));
												config.setPwd(Boolean.valueOf(getNodeValue(third, "isPwd")));
												String port = getNodeValue(third,"port");
												Set<String> tempSet = new HashSet<String>();
												for(String str : set){
													tempSet.add(str + ":" + port);
												}
												config.setIps(tempSet);
												list.add(config);
											}
										}
									}
								}
							}
						}
					}
				}
				JJMRedisPoolMap.getInstance().init(list);
			}
			
			result = true;
		} catch (Exception e) {
			result = false;
			LOGGER.error("[ERR:CONFIG] Exception occurred={}", e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	private String getNodeValue(Node n, String k) {
		if (n == null || n.getAttributes() == null) {
			LOGGER.error("[ERR:CONFIG] redis {} value is error.", k);
			return null;
		}
		Node node = n.getAttributes().getNamedItem(k);
		if (node == null) {
			LOGGER.error("[ERR:CONFIG] redis {} value is error.", k);
			return null;
		}
		return node.getNodeValue().trim();
	}

	private int getNodeIntValue(Node n, String k) {
		String str = getNodeValue(n, k);
		if (str == null || str.length() < 1) {
			return 0;
		}
		return Integer.parseInt(str);

	}

	public static void main(String[] args) {
		Configurator config = Configurator.getInstance();
		boolean loadConfig = config.loadConfig("config2.xml");
		System.out.println(loadConfig);
	}
}
