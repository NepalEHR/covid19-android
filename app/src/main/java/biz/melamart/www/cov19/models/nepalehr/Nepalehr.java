package biz.melamart.www.cov19.models.nepalehr;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Nepalehr {

    @SerializedName("query_result")
    @Expose
    private QueryResult queryResult;

    public QueryResult getQueryResult() {
        return queryResult;
    }

    public void setQueryResult(QueryResult queryResult) {
        this.queryResult = queryResult;
    }

}