package com.example.geossms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.geossms.entity.Role;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 珞珈壹佰叁
 * @since 2023-07-05
 */
public interface IRoleService extends IService<Role> {

    void setRoleMenu(Integer roleId, List<Integer> menuIds);

    List<Integer> getRoleMenu(Integer roleId);
}
