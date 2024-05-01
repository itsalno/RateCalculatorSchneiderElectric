package BLL;

import BE.Multiplier;
import DAL.MultiplierDAO;

import java.util.ArrayList;
import java.util.List;

public class MultiplierLogic {
    MultiplierDAO multiplierDAO = new MultiplierDAO();

    public void createMulti(Multiplier multiplier){
        multiplierDAO.createMulti(multiplier);
    }

    public ArrayList<Multiplier> getAllMultis(){
        return multiplierDAO.getAllMultis();
    }

    public void deleteMulti(int id){
        multiplierDAO.deleteMulti(id);
    }

    public void editMultiplier(Multiplier multiplier){
        multiplierDAO.editMulti(multiplier);
    }

}
