package biz.melamart.www.cov19.models.newsFeeds;

import java.util.ArrayList;
import java.util.List;

public class newsFeedResponse {
    List<newsFeed> resource = new ArrayList<>();

    public List<newsFeed> getResource() {
        return resource;
    }
}
