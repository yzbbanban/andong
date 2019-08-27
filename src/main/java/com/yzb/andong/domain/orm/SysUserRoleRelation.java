package com.yzb.andong.domain.orm;

/**
 * Created by brander on 2019/1/12
 */
public class SysUserRoleRelation {
    private Integer sysUserId;
    private Integer sysRoleId;

    public Integer getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Integer sysUserId) {
        this.sysUserId = sysUserId;
    }

    public Integer getSysRoleId() {
        return sysRoleId;
    }

    public void setSysRoleId(Integer sysRoleId) {
        this.sysRoleId = sysRoleId;
    }

    @Override
    public String toString() {
        return "SysUserRoleRelation{" +
                "sysUserId=" + sysUserId +
                ", sysRoleId=" + sysRoleId +
                '}';
    }
}
