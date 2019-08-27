package com.yzb.andong.domain.orm;

/**
 * Created by brander on 2019/1/12
 */
public class SysResourceRoleRelation {

    private Integer sysResourceId;
    private Integer sysRoleId;

    public Integer getSysResourceId() {
        return sysResourceId;
    }

    public void setSysResourceId(Integer sysResourceId) {
        this.sysResourceId = sysResourceId;
    }

    public Integer getSysRoleId() {
        return sysRoleId;
    }

    public void setSysRoleId(Integer sysRoleId) {
        this.sysRoleId = sysRoleId;
    }

    @Override
    public String toString() {
        return "SysResourceRoleRelation{" +
                "sysResourceId=" + sysResourceId +
                ", sysRoleId=" + sysRoleId +
                '}';
    }
}
