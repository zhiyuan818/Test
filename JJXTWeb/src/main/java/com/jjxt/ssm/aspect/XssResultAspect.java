package com.jjxt.ssm.aspect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.jjxt.ssm.utils.EncodeFilter;
import com.jjxt.ssm.utils.XssChange;

@Aspect
@Component
@Order(1)
public class XssResultAspect {
	private static Logger logger = Logger.getLogger(XssResultAspect.class);
	
	@Pointcut("@annotation(com.jjxt.ssm.utils.XssChange)")
	public void controllerAspecta() {
	}

	// 环绕通知，日志表记录操作前后数据的变化
	@Around(value = "controllerAspecta() && @annotation(xssChange)")
	public Object interceptor(ProceedingJoinPoint joinPoint ,XssChange xssChange) {
		Object object=null;
		try {
			object = joinPoint.proceed();
			object=changeXss(object);
		} catch (Throwable e2) {
			logger.error("[ERROR]环绕通知执行错误", e2);
		}
		return object;
	}
	
	@SuppressWarnings("unchecked")
	public static Object changeXss(Object object) {
		if(object == null) {
			return null;
		}
		if(object instanceof Map) {
			Map<String, Object> map=(Map<String, Object>) object;
			Map<String, Object> map1=new HashMap<>();
			if(map != null && map.size()>0) {
				for(Entry<String, Object> entry:map.entrySet()) {
					String key = entry.getKey();
					Object value = entry.getValue();
					Object changeXss = changeXss(value);
					map1.put(key, changeXss);
				}
			}
			object=map1;
		}else if (object instanceof List){
			List<Object> list=(List<Object>) object;
			List<Object> list1=new ArrayList<Object>();
			if(list!=null && list.size()>0) {
				for(Object obj:list){
					Object changeXss = changeXss(obj);
					list1.add(changeXss);
				}
			}
			object=list1;
		}else if(object instanceof String){
			object=EncodeFilter.encode(String.valueOf(object));
		}else if(object instanceof Long) {
        	
        }else if(object instanceof Byte) {
        	
        }else if(object instanceof Short) {
        	
        }else if(object instanceof Integer) {
        	
        }else if(object instanceof Date) {
        	
        }else if(object instanceof Float) {
        	
        }else if(object instanceof Double) {
        	
        }else if(object instanceof Boolean) {
        	
        }else if(object instanceof Character) {
        	
        }else {
			try {
				object = changObjectValue(object);
			} catch (Exception e) {
				logger.error("[ERR:XSS] data change err.");
			}
		}
		return object;
	}
	
	@SuppressWarnings("unchecked")
	private static Object changObjectValue(Object _obj) throws Exception{
		if(_obj == null) {
			return _obj;
		}
        Class<?> resultClz = _obj.getClass();
        Field[] fieldInfo = resultClz.getDeclaredFields(); //获取class里的所有字段  父类字段获取不到    注：如果出现加密解密失败 请先查看idno是否在父类中
        for ( int i = 0 ; i < fieldInfo. length ; i++){
            Field f = fieldInfo[i];
            f.setAccessible( true ); // 设置些属性是可以访问的
            Object val = f.get(_obj); // 得到此属性的值    
            if(val == null) {
            	continue;
            }
            if(val instanceof Map) {
				Map<String, Object> map=(Map<String, Object>) val;
    			Map<String, Object> map1=new HashMap<>();
    			if(map != null && map.size()>0) {
    				for(Entry<String, Object> entry:map.entrySet()) {
    					String key = entry.getKey();
    					Object value = entry.getValue();
    					Object changeXss = changeXss(value);
    					map1.put(key, changeXss);
    				}
    			}
    			val=map1;
    		}else if (val instanceof List){
    			List<Object> list=(List<Object>) val;
    			List<Object> list1=new ArrayList<Object>();
    			if(list!=null && list.size()>0) {
    				for(Object obj:list){
    					Object changeXss = changeXss(obj);
    					list1.add(changeXss);
    				}
    			}
    			val=list1;
    		}else if(val instanceof String){
    			val=EncodeFilter.encode(String.valueOf(val));
    			f.set(_obj, val);
    		}else if(val instanceof Long) {
            	
            }else if(val instanceof Byte) {
            	
            }else if(val instanceof Short) {
            	
            }else if(val instanceof Integer) {
            	
            }else if(val instanceof Date) {
            	
            }else if(val instanceof Float) {
            	
            }else if(val instanceof Double) {
            	
            }else if(val instanceof Boolean) {
            	
            }else if(val instanceof Character) {
            	
            }else {
    			try {
    				val = changObjectValue(val);
    			} catch (Exception e) {
    				logger.error("[ERR:XSS] data change err.");
    			}
    		}
        }
        return _obj;
    }
}