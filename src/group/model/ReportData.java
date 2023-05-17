package group.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReportData {

    private String type_Month;
    private int count;
    public static ObservableList<ReportData> reportDataOL = FXCollections.observableArrayList();

    public String getType_Month() {
        return type_Month;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setType_Month(String type_Month) {
        this.type_Month = type_Month;
    }
}
