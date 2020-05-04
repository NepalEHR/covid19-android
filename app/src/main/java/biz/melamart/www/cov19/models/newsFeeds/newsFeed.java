package biz.melamart.www.cov19.models.newsFeeds;

public class newsFeed {
       String feed_id;
        String source;
        String feedTitle;
        String feedDescription;
        String iconImage;
        String newsImage;
        String fullLink;
    Boolean hasIconImage;
    Boolean hasNewsImage;
    Boolean hasLink;
        Boolean status;
        String dateAdded;

    public String getFeed_id() {
        return feed_id;
    }

    public void setFeed_id(String feed_id) {
        this.feed_id = feed_id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getFeedTitle() {
        return feedTitle;
    }

    public void setFeedTitle(String feedTitle) {
        this.feedTitle = feedTitle;
    }

    public String getFeedDescription() {
        return feedDescription;
    }

    public void setFeedDescription(String feedDescription) {
        this.feedDescription = feedDescription;
    }

    public String getIconImage() {
        return iconImage;
    }

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }

    public String getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(String newsImage) {
        this.newsImage = newsImage;
    }

    public String getFullLink() {
        return fullLink;
    }

    public void setFullLink(String fullLink) {
        this.fullLink = fullLink;
    }

    public Boolean getHasIconImage() {
        return hasIconImage;
    }

    public void setHasIconImage(Boolean hasIconImage) {
        this.hasIconImage = hasIconImage;
    }

    public Boolean getHasNewsImage() {
        return hasNewsImage;
    }

    public void setHasNewsImage(Boolean hasNewsImage) {
        this.hasNewsImage = hasNewsImage;
    }

    public Boolean getHasLink() {
        return hasLink;
    }

    public void setHasLink(Boolean hasLink) {
        this.hasLink = hasLink;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
}
