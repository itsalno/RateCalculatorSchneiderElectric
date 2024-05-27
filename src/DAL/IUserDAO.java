package DAL;

import BE.User;
import Exceptions.RateCalcException;

import java.util.List;

public interface IUserDAO {
    List<User> getAllUsers() throws RateCalcException;
}
