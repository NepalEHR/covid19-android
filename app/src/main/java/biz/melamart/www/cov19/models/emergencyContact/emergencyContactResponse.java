package biz.melamart.www.cov19.models.emergencyContact;

import java.util.ArrayList;
import java.util.List;

public class emergencyContactResponse {
    List<emergencyContacts> resource = new ArrayList<>();

    public List<emergencyContacts> getResource() {
        return resource;
    }
}
