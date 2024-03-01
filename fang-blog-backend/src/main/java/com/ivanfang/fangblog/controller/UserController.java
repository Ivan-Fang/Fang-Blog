package com.ivanfang.fangblog.controller;

import com.ivanfang.fangblog.pojo.Result;
import com.ivanfang.fangblog.pojo.User;
import com.ivanfang.fangblog.service.UserService;
import com.ivanfang.fangblog.utils.JwtUtil;
import com.ivanfang.fangblog.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Validated
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username,    // fixed form : "^...$"
                           @Pattern(regexp = "^\\S{5,16}$") String password) {
        // check if the username is used
        if (userService.getByUsername(username) != null) {
            return Result.failed("Username '" + username + "' is used. Please use another name.");
        }

        // register
        userService.register(username, password);
        return Result.success();
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username,
                                @Pattern(regexp = "^\\S{5,16}$") String password) {
        User userFound = userService.getByUsername(username);

        // check username
        if (userFound == null) {
            return Result.failed("Username Not Found");
        }

        // check password
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!md5Password.equals(userFound.getPassword())) {
            return Result.failed("Password Incorrect");
        }

        // return jwt token
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", userFound.getId());
        userInfo.put("username", userFound.getUsername());
        String jwtToken = JwtUtil.genToken(userInfo);

        // save jwt token in redis
        stringRedisTemplate.opsForValue().set(jwtToken, jwtToken, 1, TimeUnit.DAYS);

        return Result.success(jwtToken);
    }

    @GetMapping("/userInfo")
    public Result<User> getUserInfo() {
        Map<String, Object> userInfo = ThreadLocalUtil.get();
        String username = (String) userInfo.get("username");
        User user = userService.getByUsername(username);
        return Result.success(user);
    }

    @PutMapping("/update")
    // use "@RequestBody" when request body is sent in "raw JSON" form, rather than "x-www-form-urlencoded"
    // only when we add "@Validated" will the validation annotations in "User" class take effect
    public Result update(@RequestBody @Validated User user) {
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")    // update password
    public Result updatePwd(@RequestBody Map<String, String> params,
                            @RequestHeader("Authorization") String jwtToken) {
        // 1. parameter validation
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        // 1.1. check if any parameter is empty
        if (oldPwd.isEmpty() || newPwd.isEmpty() || rePwd.isEmpty()) {
            return Result.failed("passwords should not be empty");
        }

        // 1.2. check if old_pwd (original password) is correct
        Map<String, Object> userInfo = ThreadLocalUtil.get();
        String username = (String) userInfo.get("username");
        User user = userService.getByUsername(username);

        if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(oldPwd.getBytes()))) {
            return Result.failed("original password incorrect");
        }

        // 1.3. check if new_pwd (new password) and re_pwd (re-enter password) are the same
        if (!newPwd.equals(rePwd)) {
            return Result.failed("new password & re-enter password should be same");
        }

        // 2. update password
        userService.updatePwd(newPwd);

        // 3. delete the original jwt token
        stringRedisTemplate.delete(jwtToken);

        return Result.success();
    }

    // get id-username map
    @GetMapping("/id/username/map")
    public Result<Map<Integer, String>> getIdUsernameMap() {
        Map<Integer, String> idUsernameMap = userService.getIdUsernameMap();
        return Result.success(idUsernameMap);
    }

}
