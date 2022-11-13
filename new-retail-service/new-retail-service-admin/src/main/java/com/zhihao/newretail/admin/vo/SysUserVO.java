package com.zhihao.newretail.admin.vo;

import lombok.Data;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@Data
public class SysUserVO {

    private Integer id;

    private String username;

    private SysRoleVO sysRoleVO;

}
