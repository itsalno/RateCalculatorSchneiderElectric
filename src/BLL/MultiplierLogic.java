package BLL;

import BE.Multiplier;
import DAL.MultiplierDAO;
import Exceptions.RateCalcException;

import java.util.ArrayList;
import java.util.List;

public class MultiplierLogic {
    MultiplierDAO multiplierDAO = new MultiplierDAO();

    public void createMulti(Multiplier multiplier) throws RateCalcException {
        multiplierDAO.createMulti(multiplier);
    }

    public ArrayList<Multiplier> getAllMultis() throws RateCalcException {
        return multiplierDAO.getAllMultis();
    }

    public void deleteMulti(int id) throws RateCalcException {
        multiplierDAO.deleteMulti(id);
    }

    public void editMultiplier(Multiplier multiplier) throws RateCalcException {
        multiplierDAO.editMulti(multiplier);
    }

}
