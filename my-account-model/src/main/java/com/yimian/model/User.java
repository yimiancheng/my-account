package com.yimian.model;

import lombok.Data;
import java.util.Date;
import javax.validation.constraints.NotEmpty;

/**
 * User
 * chengshaohua
 *
 * @date 2020/6/11 11:26
 */
@Data
public class User {
    @NotEmpty(message = "姓名必填！")
    private String userName;
    @NotEmpty(message = "密码必填！")
    private String passWord;
    private Date createDate;
}
