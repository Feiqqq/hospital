package net.thumbtack.school.hospital.daoimpl;

import net.thumbtack.school.hospital.dao.UserDao;
import net.thumbtack.school.hospital.database.Database;
import net.thumbtack.school.hospital.model.User;
import net.thumbtack.school.hospital.server.exceptions.ServerException;

public class UserDaoImpl implements UserDao {

    @Override
    public void changePass(String token, User user) throws ServerException {
        Database.getInstance().upgrade(token, user);
    }


    @Override
    public void login(String token, User user) throws ServerException {
        Database.getInstance().login(token, user);
    }

    @Override
    public void logout(String token) throws ServerException {
        Database.getInstance().logoutDoc(token);
    }
    @Override
    public User selectByToken(String token) throws ServerException {
        return Database.getInstance().selectByToken(token);
    }
    @Override
    public User selectByLogin(String login) throws ServerException{
        return Database.getInstance().selectByLogin(login);
    }
    @Override
    public User selectById(Integer id) throws ServerException {
        return Database.getInstance().selectById(id);
    }
}
