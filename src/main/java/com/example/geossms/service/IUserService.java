package com.example.geossms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.geossms.controller.dto.UserDTO;
import com.example.geossms.controller.dto.UserPasswordDTO;
import com.example.geossms.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 珞珈壹佰叁
 * @since 2023-07-05
 */
public interface IUserService extends IService<User> {

    UserDTO login(UserDTO userDTO);

    User register(UserDTO userDTO);

    void updatePassword(UserPasswordDTO userPasswordDTO);

    Page<User> findPage(Page<User> objectPage, String username, String email, String address);

}
