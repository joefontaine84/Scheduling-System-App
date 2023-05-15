package group.controller;

import group.model.Appointments;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;

import static group.model.Appointments.apptsList;

public class ApptsByTypeController implements Initializable {

    public TableColumn amount;
    public TableColumn type;
    public ArrayList<String> typeList = new ArrayList<>();
    public Map<String, Integer> typeMap = new HashMap<>();


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void populateTypeMap() {
        for (Appointments appts : apptsList) {
            typeList.add(appts.getType().toLowerCase());
        }

        for (int i = 0; i < typeList.size(); i++) {
            String key = "";
            int count = 0;
            for (int j = 0; j < typeList.size(); j++) {
                if (typeList.get(i).equals(typeList.get(j))) {
                    ++count;
                    if (count == 1) {
                        key = typeList.get(i);
                    }
                }
                if (j == (typeList.size() - 1)) {
                    typeMap.put(key, count);
                }
            }
        }
    }

    public Set<String> getTypeStrings() {
        return typeMap.keySet();
    }

    public Collection<Integer> getTypeInts () {
        return typeMap.values();
    }

}
