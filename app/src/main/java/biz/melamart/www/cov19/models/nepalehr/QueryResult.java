package biz.melamart.www.cov19.models.nepalehr;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QueryResult {
    @SerializedName("retrieved_at")
    @Expose
    private String retrievedAt;
    @SerializedName("query_hash")
    @Expose
    private String queryHash;
    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("runtime")
    @Expose
    private Double runtime;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("data_source_id")
    @Expose
    private Integer dataSourceId;

    public String getRetrievedAt() {
        return retrievedAt;
    }

    public void setRetrievedAt(String retrievedAt) {
        this.retrievedAt = retrievedAt;
    }

    public String getQueryHash() {
        return queryHash;
    }

    public void setQueryHash(String queryHash) {
        this.queryHash = queryHash;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Double getRuntime() {
        return runtime;
    }

    public void setRuntime(Double runtime) {
        this.runtime = runtime;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(Integer dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

}