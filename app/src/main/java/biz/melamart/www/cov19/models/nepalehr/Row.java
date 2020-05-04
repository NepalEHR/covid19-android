package biz.melamart.www.cov19.models.nepalehr;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Row {
    @SerializedName("Tests")
    @Expose
    private String tests;
    @SerializedName("Numbers")
    @Expose
    private String numbers;

    public String getTests() {
        return tests;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }
}