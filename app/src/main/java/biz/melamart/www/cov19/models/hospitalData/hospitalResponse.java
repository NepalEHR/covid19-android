package biz.melamart.www.cov19.models.hospitalData;

import java.util.ArrayList;
import java.util.List;

public class hospitalResponse {
    List<hospitalData> resource = new ArrayList<>();

    public List<hospitalData> getResource() {
        return resource;
    }
}
