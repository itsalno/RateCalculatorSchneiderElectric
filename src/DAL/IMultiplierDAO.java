package DAL;

import BE.Group;
import BE.Multiplier;

import java.util.ArrayList;

public interface IMultiplierDAO {
    void createMulti(Multiplier multiplier);

    ArrayList<Multiplier> getAllMultis();

    void deleteMulti(int id);

    void editMulti(Multiplier multiplier);
}
