package net.thumbtack.school.hospital.service;

import com.google.gson.Gson;
import net.thumbtack.school.hospital.dao.UserDao;
import net.thumbtack.school.hospital.daoimpl.UserDaoImpl;
import net.thumbtack.school.hospital.dto.request.LoginDtoRequest;
import net.thumbtack.school.hospital.dto.response.TokenDtoResponse;
import net.thumbtack.school.hospital.model.User;
import net.thumbtack.school.hospital.server.ServerResponse;
import net.thumbtack.school.hospital.server.exceptions.ServerException;

import java.util.UUID;

public class UserService {
    Gson json = new Gson();
    private static final int RESPONSE_CODE_OK = 200;
    private static final int RESPONSE_CODE_ERROR = 400;
    private final UserDao userDao = new UserDaoImpl();
    public ServerResponse loginUser(String jsonString) {
        try {
            LoginDtoRequest loginDtoRequest = ServiceUtils.getClassFromJson(jsonString, LoginDtoRequest.class);
            ServiceUtils.validateLogDtoRequest(loginDtoRequest);
            String token = UUID.randomUUID().toString();
            User user = userDao.selectByLogin(loginDtoRequest.getLogin());
            userDao.login(token, user);
            TokenDtoResponse tokenDtoResponse = new TokenDtoResponse(token);
            return new ServerResponse(RESPONSE_CODE_OK, json.toJson(tokenDtoResponse));
        } catch (ServerException e) {
            return new ServerResponse(RESPONSE_CODE_ERROR);
        }
    }

    public ServerResponse changePass(String token, String password) {
        try {
            User user = userDao.selectByToken(token);
            user.setPassword(password);
            userDao.changePass(token, user);
            return new ServerResponse(RESPONSE_CODE_OK);
        } catch (ServerException e) {
            return new ServerResponse(RESPONSE_CODE_ERROR, e);
        }

    }

    public ServerResponse logout(String token)  {
        try {
            userDao.logout(token);
            return new ServerResponse(RESPONSE_CODE_OK, "");
        } catch (ServerException e) {
            return new ServerResponse(RESPONSE_CODE_ERROR, e);
        }

    }
}
