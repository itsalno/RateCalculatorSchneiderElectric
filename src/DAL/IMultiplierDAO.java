package DAL;

import BE.Group;
import BE.Multiplier;
import Exceptions.RateCalcException;

import java.util.ArrayList;

public interface IMultiplierDAO {
    void createMulti(Multiplier multiplier) throws RateCalcException;

    ArrayList<Multiplier> getAllMultis() throws RateCalcException;

    void deleteMulti(int id) throws RateCalcException;

    void editMulti(Multiplier multiplier) throws RateCalcException;
}
