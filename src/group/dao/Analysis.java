package group.dao;

import group.model.Appointments;
import group.model.Contacts;
import group.model.ReportData;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static group.model.Appointments.apptsList;
import static group.model.ReportData.reportDataOL;
import static group.model.Contacts.contactList;

public class Analysis {
    public ArrayList<String> typeList = new ArrayList<>();
    public static Map<String, Integer> typeMap = new HashMap<>();
    public static Map<String, Integer> months = new HashMap<>();
    public static Map<Contacts, ArrayList> schedule = new HashMap<>();

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
        reportDataOL.clear();
        for (Map.Entry<String, Integer> entry : typeMap.entrySet()) {
            ReportData obj = new ReportData();
            obj.setType_Month(entry.getKey());
            obj.setCount(entry.getValue());
            reportDataOL.add(obj);
        }
    }

    public void populateMonthsHashMap() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-uuuu");
        ArrayList<String> monthList = new ArrayList<>();
        for (Appointments appts : apptsList) {
            monthList.add(appts.getStartDateTime().toLocalDateTime().format(formatter));
        }

        for (int i = 0; i < monthList.size(); i++) {
            String key = "";
            int count = 0;
            for (int j = 0; j < monthList.size(); j++) {
                if (monthList.get(i).equals(monthList.get(j))) {
                    ++count;
                    if (count == 1) {
                        key = monthList.get(i);
                    }
                }
                if (j == (monthList.size() - 1)) {
                    months.put(key, count);
                }
            }
        }
    }

    public void getApptsByMonth() {
        populateMonthsHashMap();
        reportDataOL.clear();
        for (Map.Entry<String, Integer> entry : months.entrySet()) {
            ReportData obj = new ReportData();
            obj.setType_Month(entry.getKey());
            obj.setCount(entry.getValue());
            reportDataOL.add(obj);
        }
    }

    public void populateContactHashMap() {
        for (int i = 0; i < contactList.size(); i++) {
            ArrayList<Appointments> apptsByContact = new ArrayList<>();
            for (int j = 0; j < apptsList.size(); j++) {
                if (apptsList.get(j).getContactID() == contactList.get(i).getContactID()) {
                    apptsByContact.add(apptsList.get(j));
                }
            }
            schedule.put(contactList.get(i), apptsByContact);
        }
    }

    public void getContactSchedule() {
        populateContactHashMap();
        reportDataOL.clear();
        for (Map.Entry<Contacts, ArrayList> entry : schedule.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                ReportData obj = new ReportData();
                obj.setContactID(entry.getKey().getContactID());
                obj.setContactName(entry.getKey().getName());
                obj.setAppointmentID(((Appointments)entry.getValue().get(i)).getAppointmentID());
                obj.setTitle(((Appointments)entry.getValue().get(i)).getTitle());
                obj.setDescription(((Appointments)entry.getValue().get(i)).getDescription());
                obj.setCustomerID(((Appointments)entry.getValue().get(i)).getCustomerID());
                obj.setType_Month(((Appointments)entry.getValue().get(i)).getType());
                obj.setStartDateTime(((Appointments)entry.getValue().get(i)).getStartDateTime());
                obj.setEndDateTime(((Appointments)entry.getValue().get(i)).getEndDateTime());
                reportDataOL.add(obj);
            }
        }
    }

}
