package com.example.service;

import com.example.pojo.UserLogin;

/**
 * login service
 */
public interface LoginService {

    /**
     * user first login
     *
     * @param userLogin user entity for login
     * @return token for the user has already login
     */
    String login(UserLogin userLogin);

    /**
     * server login record.
     * record lasts 24h.
     *
     * @param userId user's ID
     * @param token  token
     * @return login success
     */
    boolean reLogin(String userId, String token);

    /**
     * check user's login state
     *
     * @param userId user's ID
     * @return true if user already login otherwise false.
     */
    boolean isLogin(String userId);

    /**
     * @param userId user ID
     * @param token  token for verify user's profile
     * @return logout operation success
     */
    boolean logout(String userId, String token);


    static String[] parseToken(String token) {
        if (token != null) {
            String[] split = token.split("\\.");
            if (split.length >= 2
                    && !split[0].isEmpty()
                    && !split[1].isEmpty()
            ) {
                return split;
            }
        }
        throw new IllegalArgumentException();
    }

}
