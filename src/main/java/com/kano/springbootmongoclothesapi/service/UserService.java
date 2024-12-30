package com.kano.springbootmongoclothesapi.service;

import com.kano.springbootmongoclothesapi.config.RolesConfig;
import com.kano.springbootmongoclothesapi.model.User;
import com.kano.springbootmongoclothesapi.repository.UserRepository;
import com.kano.springbootmongoclothesapi.utils.JwtToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;




@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }


    /**
     * 创建用户/注册用户
     */
    public User createUser(User user) {
        user.setCreatedAt(Instant.now());
        return userRepository.save(user);
    }

    /**
     * 更新用户
     */
    public User updateUser(String id, User user) {
    if (userRepository.existsById(id)) {
            user.setId(id);
            return userRepository.save(user);
    }
        return null;
    }

    /**
     * 更新当前用户
     */
    public User updateSelfUser(String id, User user) {
        Optional<User> res = getUserById(id);
        if(res.isPresent()) {
            if(!Objects.equals(user.getRole(), res.get().getRole())) {
                return null;
            }
            if (res.get().getId().equals(id)) {
                user.setId(id);
                return userRepository.save(user);
            }
        }
        return null;
    }

    /**
     * 删除用户
     */
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    /**
     * 登录
     */
    public Object login(String username, String password) throws Exception  {
        // 查找用户
        User res = userRepository.findByUsername(username);


        if (res == null) {
            throw new Exception("用户名不存在");
        }

        if (Objects.equals(res.getRole(), RolesConfig.SB)) {
            throw new Exception("账号未激活，请联系管理员激活！");
        }


        HashMap<String,String> userInfo = new HashMap<>();
        userInfo.put("username", res.getUsername());
        userInfo.put("role", res.getRole());
        userInfo.put("email", res.getEmail());


        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 校验密码
        boolean isValid =  passwordEncoder.matches(password, res.getPassword());


        if(!isValid){return false;}


        // 登录成功，生成 JWT token
        JwtToken jwt = new JwtToken();

        String token = jwt.generateToken(res.getId(),res.getRole());
        System.out.println(token);

        HashMap<String,Object> map = new HashMap<>();
        map.put("token", token);
        map.put("userInfo", userInfo);

        return map;
    }

    /**
     * 注册
     */
    // 用户注册方法
    public HashMap<String,String> register(User user) throws Exception {
        // 检查用户名是否已经存在
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new Exception("用户名已被注册");
        }
        // 检查邮箱是否已经存在
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new Exception("邮箱已被注册");
        }

        // 密码加密处理
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // 可使用 BCrypt 加密

        // 保存用户
        userRepository.save(user);

        HashMap<String,String> returnMap = new HashMap<String,String>();
        returnMap.put("username",user.getUsername());
        returnMap.put("email",user.getEmail());
        returnMap.put("role",user.getEmail());
        return returnMap;
    }
}