package com.example.geossms.controller.dto;

import com.example.geossms.entity.Menu;
import lombok.Data;

import java.util.List;

/**
 * @author 珞珈壹佰叁
 * 接受前端登录请求的参数
 */
@Data
public class UserDTO {

    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String phone;
    private String address;
    private String role;
    private String token;
    private List<Menu> menus;

}
