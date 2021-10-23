package net.thumbtack.school.hospital.dao;

import net.thumbtack.school.hospital.model.User;
import net.thumbtack.school.hospital.server.exceptions.ServerException;

public interface UserDao{
    void login(String token, User user) throws ServerException;
    void changePass(String token,User user) throws ServerException;
    void logout(String token) throws ServerException;
    User selectByToken(String token) throws ServerException;
    User selectByLogin(String login) throws ServerException;
}
