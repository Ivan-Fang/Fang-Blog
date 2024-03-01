package com.ivanfang.fangtimes.service.impl;

import com.ivanfang.fangtimes.mapper.UserMapper;
import com.ivanfang.fangtimes.pojo.User;
import com.ivanfang.fangtimes.service.UserService;
import com.ivanfang.fangtimes.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getByUsername(String username) {
        return userMapper.getByUsername(username);
    }

    @Override
    public void register(String username, String password) {
        // encrypt password (with md5) before adding into database
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        userMapper.add(username, md5Password);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String, Object> userInfo = ThreadLocalUtil.get();
        Integer id = (Integer) userInfo.get("id");
        userMapper.updateAvatar(id, avatarUrl);
    }

    @Override
    public void updatePwd(String newPwd) {
        // get user id
        Map<String, Object> userInfo = ThreadLocalUtil.get();
        Integer id = (Integer) userInfo.get("id");

        // use md5 to encrypt new password
        String newPwdMd5 = DigestUtils.md5DigestAsHex(newPwd.getBytes());
        userMapper.updatePwd(id, newPwdMd5);
    }

    @Override
    public List<User> getAll() {
        return userMapper.getAll();
    }

    @Override
    public Map<Integer, String> getIdUsernameMap() {
        List<User> userList = getAll();
        Map<Integer, String> idUsernameMap = new HashMap<>();
        for (User user : userList) {
            idUsernameMap.put(user.getId(), user.getUsername());
        }
        return idUsernameMap;
    }

}
