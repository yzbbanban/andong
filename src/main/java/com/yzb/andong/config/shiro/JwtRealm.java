package com.yzb.andong.config.shiro;

import com.yzb.andong.config.security.JwtConstant;
import com.yzb.andong.config.security.JwtHelper;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

/**
 * Created by ban on 2018/7/9.
 * 基于 jwt shiro 认证域
 *
 * @author wangban
 */
@Service
public class JwtRealm extends AuthorizingRealm {


    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String userId = "userid";
        String manageId = "manageid";
        String jwtRealm = "jwt_realm";
        String tokenInvalid = "token invalid";
        int len = 7;
        if (StringUtils.isNotBlank(token) && token.length() >= len) {
            Claims claims = JwtHelper.parseJWT(token, JwtConstant.JWT_SECRET);
            Claims claimsMa = JwtHelper.parseJWT(token, JwtConstant.MANAGE_JWT_SECRET);
            if (claims != null && claims.get(userId) != null) {
                return new SimpleAuthenticationInfo(token, token, jwtRealm);
            }

            if (claimsMa != null && claimsMa.get(manageId) != null) {
                return new SimpleAuthenticationInfo(token, token, jwtRealm);
            }
        }

        throw new AuthenticationException(tokenInvalid);

    }
}
