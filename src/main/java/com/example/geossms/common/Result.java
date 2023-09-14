package com.example.geossms.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接口统一返回包装类
 * @author 珞珈壹佰叁
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {


    private String code;
    private String msg;
    private Object data;
    private String jsonContent;
    private boolean image;
    private boolean report;
    private String time;


    public static Result success() {
        return new Result(Constants.CODE_200, "", null,"",false,false,"");
    }

    public static Result success(Object data) {
        return new Result(Constants.CODE_200, "", data,"",false,false,"");
    }

    public static Result error(String code, String msg) {
        return new Result(code, msg, null,"",false,false,"");
    }

    public static Result error() {
        return new Result(Constants.CODE_500, "系统错误", null,"",false,false,"");
    }

}
