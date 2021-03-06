package com.ginkgomall.controller.backend;

import com.ginkgomall.common.Const;
import com.ginkgomall.common.ServerResponse;
import com.ginkgomall.pojo.User;
import com.ginkgomall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 管理员用户模块相关功能
 *
 * @author dill
 * @date 2018/1/27
 */
@Controller
@RequestMapping("/manage/user/")
public class UserMangeController {

    @Autowired
    private IUserService iUserService;

    /**
     * 管理员登录
     *
     * @param username username
     * @param password password
     * @param session HttpSession
     * @return ServerResponse
     */
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse login(String username, String password, HttpSession session) {
        ServerResponse<User> response = iUserService.login(username, password);
        if (response.isSuccess()) {
            User user = response.getData();
            if (user.getRole() == Const.Role.ROLE_ADMIN) {
                session.setAttribute(Const.CURRENT_USER, user);
                return response;
            } else {
                return ServerResponse.createByErrorMessage("还是管理员，无法登录");
            }
        }
        return response;
    }
}
