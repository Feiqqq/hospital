package net.thumbtack.school.hospital.database;

import net.thumbtack.school.hospital.model.Doctor;
import net.thumbtack.school.hospital.model.User;
import net.thumbtack.school.hospital.server.exceptions.ServerErrorCode;
import net.thumbtack.school.hospital.server.exceptions.ServerException;

import java.util.HashMap;
import java.util.Map;

public final class Database {

    private static Database instance;// реализация ленивого сингелтона
    private Map<String, User> users = new HashMap<>();
    private Map<String, User> usersByToken = new HashMap<>();

    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }


    public User selectByLogin(String login) throws ServerException {
        if(users.get(login)==null) throw new ServerException(ServerErrorCode.NOT_FOUND_USER);
        return users.get(login);
    }

    public void insert(User user) throws ServerException {
        if (users.putIfAbsent(user.getLogin(), user) != null)
            throw new ServerException(ServerErrorCode.USER_ALREADY_EXISTS);
    }

    public void delete(String token)  {
        users.remove(usersByToken.get(token).getLogin());
        usersByToken.remove(token);
    }

    public void upgrade(String token, User user) {
        users.remove(user.getLogin());
        users.put(user.getLogin(), user);
        usersByToken.remove(token);
        usersByToken.put(token, user);
    }

    public void insert(String token, User user) {
        usersByToken.put(token, user);
    }

    public void logoutDoc(String token) throws ServerException {
        if (usersByToken.remove(token) == null) throw new ServerException(ServerErrorCode.NOT_FOUND_USER);

    }

    public void addPatient(String token, Doctor doctor) throws ServerException {
        if (usersByToken.remove(token) == null) throw new ServerException(ServerErrorCode.NOT_FOUND_USER);
        usersByToken.put(token, doctor);
    }

    public User selectByToken(String token) {
        return usersByToken.get(token);
    }

    public void deleteAll() {
        users.clear();
        usersByToken.clear();
    }
}

