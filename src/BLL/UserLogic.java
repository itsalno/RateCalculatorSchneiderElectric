package BLL;


import BE.User;
import DAL.UserDAO;
import Exceptions.RateCalcException;

import java.util.LinkedList;
import java.util.List;

public class UserLogic {
    UserDAO userDAO = new UserDAO();

    public boolean checkUser(String username, String password) throws RateCalcException {
        List<User> users = new LinkedList<>(userDAO.getAllUsers());

        for (User user : users) {
            if(user.getName().equals(username) && user.getPass().equals(password)){
                return true;
            };
        }
        return false;

    }
}
