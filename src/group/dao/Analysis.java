package group.dao;

import group.model.Appointments;
import group.model.ApptsByType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static group.model.Appointments.apptsList;
import static group.model.ApptsByType.apptsByTypeOL;

public class Analysis {
    public ArrayList<String> typeList = new ArrayList<>();
    public static Map<String, Integer> typeMap = new HashMap<>();

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

    public void getUniqueTypes() {
        populateTypeMap();
        for (Map.Entry<String, Integer> entry : typeMap.entrySet()) {
            ApptsByType obj = new ApptsByType();
            obj.setType(entry.getKey());
            obj.setCount(entry.getValue());
            apptsByTypeOL.add(obj);
        }
    }


}
