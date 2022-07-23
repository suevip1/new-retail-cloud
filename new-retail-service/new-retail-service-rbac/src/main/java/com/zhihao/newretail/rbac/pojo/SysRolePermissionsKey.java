package com.zhihao.newretail.rbac.pojo;

public class SysRolePermissionsKey {

    private Integer roleId;

    private Integer permissionsId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPermissionsId() {
        return permissionsId;
    }

    public void setPermissionsId(Integer permissionsId) {
        this.permissionsId = permissionsId;
    }

    @Override
    public String toString() {
        return "SysRolePermissionsKey{" +
                "roleId=" + roleId +
                ", permissionsId=" + permissionsId +
                '}';
    }

}
