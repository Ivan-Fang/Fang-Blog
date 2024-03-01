package com.ivanfang.fangtimes.service;

import com.ivanfang.fangtimes.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User getByUsername(String username);

    void register(String username, String password);

    void update(User user);

    void updateAvatar(String avatarUrl);

    void updatePwd(String newPwd);

    List<User> getAll();

    Map<Integer, String> getIdUsernameMap();
}
