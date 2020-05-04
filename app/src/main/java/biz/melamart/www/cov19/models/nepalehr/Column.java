package biz.melamart.www.cov19.models.nepalehr;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Column {




        @SerializedName("friendly_name")
        @Expose
        private String friendlyName;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("name")
        @Expose
        private String name;

        public String getFriendlyName() {
            return friendlyName;
        }

        public void setFriendlyName(String friendlyName) {
            this.friendlyName = friendlyName;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

}