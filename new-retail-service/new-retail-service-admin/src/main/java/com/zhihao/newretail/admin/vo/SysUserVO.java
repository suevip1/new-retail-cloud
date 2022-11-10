package com.zhihao.newretail.admin.vo;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
public class SysUserVO {

    private Integer id;

    private String username;

    private SysRoleVO sysRoleVO;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public SysRoleVO getSysRoleVO() {
        return sysRoleVO;
    }

    public void setSysRoleVO(SysRoleVO sysRoleVO) {
        this.sysRoleVO = sysRoleVO;
    }

    @Override
    public String toString() {
        return "SysUserVO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", sysRoleVO=" + sysRoleVO +
                '}';
    }

}
