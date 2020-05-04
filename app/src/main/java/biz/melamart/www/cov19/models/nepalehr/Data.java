package biz.melamart.www.cov19.models.nepalehr;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("rows")
    @Expose
    private List<Row> rows = null;
    @SerializedName("columns")
    @Expose
    private List<Column> columns = null;

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

}