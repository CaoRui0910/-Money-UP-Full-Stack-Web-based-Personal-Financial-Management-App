package com.example.bookkeeping.controller.account;

import com.example.bookkeeping.helper.PasswordHelper;
import com.example.bookkeeping.helper.ResponseVo;
import com.example.bookkeeping.helper.ResultUtil;
import com.example.bookkeeping.model.Account;
import com.example.bookkeeping.service.AccountService;
import lombok.AllArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@AllArgsConstructor
public class AccountController {

    private final AccountService userService;

    @GetMapping("/login")
    public String login() {
        return "account/sign";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseVo login(String username, String password){
        Subject subject = SecurityUtils.getSubject();

        Account user = userService.selectByUsername(username);
        if (user == null){
            return ResultUtil.error("账户不存在");
        }
        String passwordMd5 = PasswordHelper.makePassword(password, user.getCredentialsSalt());

        UsernamePasswordToken token = new UsernamePasswordToken(username, passwordMd5);

        try {
            subject.login(token);
            Session session = subject.getSession();
            session.setAttribute("loginUser", user);
        }
        catch (IncorrectCredentialsException e) {
            return ResultUtil.error("用户名或密码错误");
        }
        return ResultUtil.success("登录成功！");
    }

    @GetMapping("/register")
    public String register() {
        return "account/register";
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseVo register(Account accountForm, String confirmPassword){
        String username = accountForm.getUsername();
        Account user = userService.selectByUsername(username);
        if (null != user) {
            return ResultUtil.error("用户名已存在");
        }
        String password = accountForm.getPassword();
        //判断两次输入密码是否相等
        if (confirmPassword != null && password != null) {
            if (!confirmPassword.equals(password)) {
                return ResultUtil.error("两次密码不一致");
            }
        }
        accountForm.setRole("customer");
        PasswordHelper.encryptPassword(accountForm);
        int num = userService.register(accountForm);
        if (num > 0) {
            return ResultUtil.success("注册成功");
        } else {
            return ResultUtil.error("注册失败");
        }
    }

    @GetMapping("/resetPassword")
    public String resetPassword(){
        return "account/reset-password";
    }

    @PostMapping("/resetPassword")
    @ResponseBody
    public ResponseVo resetPassword(String password, String password1, String password2){
        Account user = (Account) SecurityUtils.getSubject().getPrincipal();
        if (password1 != null && !password1.equals(password2)){
            return ResultUtil.error("两次密码输入不一致");
        }
        String passwordGet = PasswordHelper.makePassword(password, user.getCredentialsSalt());
        if (!passwordGet.equals(user.getPassword())){
            return ResultUtil.error("旧密码输入不正确");
        }
        user.setPassword(password1);
        PasswordHelper.encryptPassword(user);
        int num = userService.updateUser(user);
        if (num > 0) {
            return ResultUtil.success("修改成功");
        } else {
            return ResultUtil.error("修改失败");
        }
    }

    @GetMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "account/sign";
    }
    @PostMapping("/deleteAccount")
    @ResponseBody
    public ResponseVo deleteAccount(int userId){
        int num = userService.deleteById(userId);
        if (num > 0) {
            return ResultUtil.success("删除成功");
        } else {
            return ResultUtil.error("删除失败");
        }
    }

    @GetMapping("/profile")
    public String profile(Model model){
        Account user = (Account) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("user", user);
        return "account/profile";
    }

    @PostMapping("/profile")
    @ResponseBody
    public ResponseVo profile(String username, String mobile){
        Account user = (Account) SecurityUtils.getSubject().getPrincipal();
        user.setUsername(username);
        user.setMobile(mobile);
        int num = userService.updateUser(user);
        if (num > 0) {
            return ResultUtil.success("修改成功");
        } else {
            return ResultUtil.error("修改失败");
        }
    }

}











