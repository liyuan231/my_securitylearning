package com.liyuan.enumation;

public enum  RoleType {
    /**
     * 可以理解为新注册的用户！
     */
    NORMAL_USER(0);
    private int roleType;

    RoleType(int roleType) {
        this.roleType = roleType;
    }

    public int getRoleType() {
        return roleType;
    }

    public void setRoleType(int roleType) {
        this.roleType = roleType;
    }
}
