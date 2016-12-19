package org.github.dtsopensource.server.share;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.github.dtsopensource.server.share.exception.DTSRuntimeException;
import org.github.dtsopensource.server.share.rule.entity.ActivityRuleEntity;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ligaofeng 2016年12月11日 上午11:42:49
 */
@Getter
@Setter
public class DTSContext implements Serializable {

    private static final long                    serialVersionUID = -6560080158776388882L;

    private static final ThreadLocal<DTSContext> LOCAL            = new ThreadLocal<DTSContext>() {

                                                                      @Override
                                                                      protected DTSContext initialValue() {
                                                                          return new DTSContext();
                                                                      }
                                                                  };

    private String                               activityId;

    private ActivityRuleEntity                   activityRuleEntity;

    private final Map<Object, Object>            properties       = new HashMap<Object, Object>();

    /**
     * 默认构造函数 不推荐使用，用于json转换
     */
    public DTSContext() {
        //do nothing
    }

    public static DTSContext getInstance() {
        return LOCAL.get();
    }

    /**
     * 增加属性
     * 
     * @param k 实现了eqauls和hashcode
     * @param v
     */
    public void addArgs(Object k, Object v) {
        LOCAL.get().getProperties().put(k, v);
    }

    /**
     * 查询属性<br>
     * 
     * @param k 实现了eqauls和hashcode
     * @param requiredType
     * @return
     * @throws DTSRuntimeException
     */
    public <T> T getArgs(Object k, Class<T> requiredType) {
        Object bean = null;
        if (LOCAL.get() != null && LOCAL.get().getProperties() != null) {
            bean = LOCAL.get().getProperties().get(k);
        }
        if (bean == null) {
            //事务恢复json反解析时
            if (this.getProperties() != null) {
                bean = this.getProperties().get(k);
            }
        }
        if (bean == null) {
            return null;
        }
        //DTSContext从数据库的json反解析的时候由于properties的类型为Map<Object, Object> 所以fastjson不知道对应的Object类型，
        //使用了com.alibaba.fastjson.JSONObject 来代替,此处获取数据时为了不报ClassCastException
        //要求传入指定类型，先将数据解析成json在转成对应类型
        String beanJson = JSON.toJSONString(bean);
        return JSON.parseObject(beanJson, requiredType);
    }

    /**
     * 删除属性
     * 
     * @param k 实现了eqauls和hashcode not null
     */
    public void removeArgsIfPossible(Object k) {
        if (k == null) {
            return;
        }
        if (LOCAL.get().getProperties().containsKey(k)) {
            LOCAL.get().getProperties().remove(k);
        }
    }

    /**
     * 转换成jason
     * 
     * @return
     */
    public String toJson() {
        return JSON.toJSONString(this);
    }

    /**
     * 清理缓存
     */
    public static void clear() {
        LOCAL.get().getProperties().clear();
        LOCAL.remove();
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
