package biz.melamart.www.cov19.models.countryStat;

import java.util.ArrayList;
import java.util.List;

public class countryStatResponse {
    List<countryStat> resource = new ArrayList<>();

    public List<countryStat> getResource() {
        return resource;
    }
}
