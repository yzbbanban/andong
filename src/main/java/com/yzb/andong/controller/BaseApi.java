package com.yzb.andong.controller;

import com.yzb.andong.config.MessageKey;
import com.yzb.andong.config.ResultJson;
import com.yzb.andong.config.ResultStatus;
import com.yzb.andong.config.cache.LocalCache;
import com.yzb.andong.config.security.JwtConstant;
import com.yzb.andong.config.sms.ISmsUtils;
import com.yzb.andong.config.sms.SmsYpUtils;
import com.yzb.andong.config.util.RandomUtils;
import com.yzb.andong.domain.dto.SmsMessageDTO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.yzb.andong.domain.orm.MessageConstant.SMS_REPEAT;

/**
 * api中通用的类
 *
 * @author wangban
 * @date 17:19 2018/7/24
 */
@Component
public class BaseApi {
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    public static final String JWT_SECRET = JwtConstant.JWT_SECRET;
    /**
     * 正确码
     */
    protected static final int OK = 200;

    @Autowired
    protected LocalCache localCache;

    /**
     * 检查对象是否为空
     *
     * @param resultJson 包装的返回结果处理
     * @param obj        需要验证的对象
     * @return true:为空 false: 不为空
     */
    protected boolean isInfoNull(ResultJson resultJson, Object obj) {
        if (obj == null) {
            resultJson.setCode(ResultStatus.ERROR.getCode());
            resultJson.setMessage(MessageKey.CORRECT_PARAMS);
            return true;
        }
        return false;
    }

    /**
     * 检查参数是否为空
     *
     * @param resultJson 包装的返回结果处理
     * @param param      需要验证的参数
     * @param message    具体那个参数为空的信息
     * @return true:为空 false: 不为空
     */
    protected boolean isInfoEmpty(ResultJson resultJson, String param, String message) {
        if (StringUtils.isBlank(param)) {
            resultJson.setCode(ResultStatus.ERROR.getCode());
            resultJson.setMessage(message);
            return true;
        }
        return false;
    }

    /**
     * 检查参数是否为空
     *
     * @param resultJson 包装的返回结果处理
     * @param id         id
     * @param message    具体那个参数为空的信息
     * @return true:为空 false: 不为空
     */
    protected boolean isIdEmpty(ResultJson resultJson, Integer id, String message) {
        if (id == null || id <= 0) {
            resultJson.setCode(ResultStatus.ERROR.getCode());
            resultJson.setMessage(message);
            return true;
        }
        return false;
    }


    /**
     * 任意地点获取request
     *
     * @return servlet
     */
    protected HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getRequest();
    }

    /**
     * 获取当前用户id
     *
     * @return 用户id`
     */
    protected int getCurrentUserId() {
        try {
            HttpServletRequest httpServletRequest = getHttpServletRequest();
            Object userId = httpServletRequest.getAttribute("userid");
            return Integer.parseInt(String.valueOf(userId));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return -1;
    }

    /**
     * 获取当前用户id
     *
     * @return 用户id
     */
    protected int getCurrentManageUserId() {
        try {
            HttpServletRequest httpServletRequest = getHttpServletRequest();
            Object userId = httpServletRequest.getAttribute("manageid");
            return Integer.parseInt(String.valueOf(userId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 通过request获取用户请求来源
     *
     * @param request 请求
     * @return 来源
     */
    protected String getCurrentLoginSource(HttpServletRequest request) {
        return request.getHeader("from") == null ? "UNKNOW" : request.getHeader("from").toLowerCase();
    }


    /**
     * 通过request获取用户IP地址
     *
     * @param request 请求
     * @return 来源
     */
    protected String getIpAddr(HttpServletRequest request) {
        //如果request为空
        if (request == null) {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            request = requestAttributes.getRequest();
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取验证码是否正确
     *
     * @param resultJson              接口返回值
     * @param systemSmsLoginCodePhone 标示
     * @param code                    验证码
     * @param mobile                  手机号
     * @return 是否正确
     */
    protected boolean getCode(ResultJson resultJson, String systemSmsLoginCodePhone, String code, String mobile) {
        String smsCode = localCache.getCache(systemSmsLoginCodePhone + "86" + mobile);
        if (code.equals(smsCode)) {
            resultJson.setStatus(ResultStatus.OK);
            return false;
        }
        resultJson.setStatus(ResultStatus.ERROR);
        resultJson.setMessage(MessageKey.CODE_ERROR);
        return true;
    }

    /**
     * 删除验证码
     *
     * @param key         短信 key
     * @param countryCode 国家代码
     * @param mobile      手机号
     */
    protected void deleteCode(String key, String countryCode, String mobile) {
        localCache.removeCache(key + countryCode + mobile);
    }

    /**
     * 是否重复发送
     *
     * @param resultJson 接口返回值
     * @param mobile     手机号
     * @return 是否正确
     */
    protected boolean isCodeRepeat(ResultJson resultJson, String mobile) {
        String smsCode = localCache.getCache(SMS_REPEAT + mobile);
        if (StringUtils.isBlank(smsCode)) {
            resultJson.setStatus(ResultStatus.OK);
            return false;
        }
        resultJson.setStatus(ResultStatus.ERROR);
        resultJson.setMessage(MessageKey.CODE_REPEAT);
        return true;
    }

    /**
     * 发送短信
     *
     * @param messageDTO 短信内容
     * @param key        短信 key
     * @return 发送结果
     */
    protected ResultJson<String> getStringResultJson(SmsMessageDTO messageDTO, String key) {
        ISmsUtils sms = new SmsYpUtils();
        int codeLength = 4;
        ResultJson<String> resultJson = new ResultJson<>();
        String code = RandomUtils.generateMixNum(codeLength);

        if (isCodeRepeat(resultJson, messageDTO.getCountryCode() + messageDTO.getPhoneNumber())) {
            return resultJson;
        }
        boolean result = sms.sendSms(messageDTO.getPhoneNumber(), messageDTO.getCountryCode(), code, SmsYpUtils.SMS_YP);

        if (result) {
            localCache.setCache(key, code);
            localCache.setCache(SMS_REPEAT + messageDTO.getCountryCode() + messageDTO.getPhoneNumber(), code);
            return ResultJson.createBySuccess();
        }
        return ResultJson.createByError();
    }

}
