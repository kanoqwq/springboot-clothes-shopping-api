package com.kano.springbootmongoclothesapi.dao;

import java.util.List;

/**
 * Registration APIs to register and update user
 *
 * @param <ID>                  - String/long
 * @param <USER>                - User
 * @author anshulbansal
 */
public interface UserDao<ID, USER> {
    /**
     * Fetch user record using userName
     *
     * @param userName
     * @return user
     * @throws Exception
     */
    USER getUserByUserName(ID userName) throws Exception;
    List<USER> getAllUser();
}
