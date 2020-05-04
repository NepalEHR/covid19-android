package biz.melamart.www.cov19.models.suspect;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class suspectContact {

    @SerializedName("personId")
    @Expose
    private String personId;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("personToContact")
    @Expose
    private String personToContact;
    @SerializedName("contactNumber")
    @Expose
    private String contactNumber;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("reporter")
    @Expose
    private String reporter;
    @SerializedName("reporterContact")
    @Expose
    private String reporterContact;
    @SerializedName("status")
    @Expose
    private Boolean status;

    public suspectContact(String name, String address, String personToContact, String contactNumber, String description, String reporter, String reporterContact, Boolean status) {
        this.name = name;
        this.address = address;
        this.personToContact = personToContact;
        this.contactNumber = contactNumber;
        this.description = description;
        this.reporter = reporter;
        this.reporterContact = reporterContact;
        this.status = status;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPersonToContact() {
        return personToContact;
    }

    public void setPersonToContact(String personToContact) {
        this.personToContact = personToContact;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getReporterContact() {
        return reporterContact;
    }

    public void setReporterContact(String reporterContact) {
        this.reporterContact = reporterContact;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
