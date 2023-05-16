package group.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReportData {

    private String type;
    private int count;
    public static ObservableList<ReportData> reportDataOL = FXCollections.observableArrayList();

    public String getType() {
        return type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setType(String type) {
        this.type = type;
    }
}
