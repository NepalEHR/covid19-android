package biz.melamart.www.cov19.models.emergencyContact;

public class emergencyContacts {
    String contactId,name,address,telephone1,telephone2,telephone3,encharge,status;

    public emergencyContacts(String name, String address, String telephone1, String telephone2, String telephone3, String encharge, String status) {
        this.name = name;
        this.address = address;
        this.telephone1 = telephone1;
        this.telephone2 = telephone2;
        this.telephone3 = telephone3;
        this.encharge = encharge;
        this.status = status;
    }

    public emergencyContacts()
    {

    }

    public String getContactId() {
        return contactId;
    }



    public void setContactId(String contactId) {
        this.contactId = contactId;
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

    public String getTelephone1() {
        return telephone1;
    }

    public void setTelephone1(String telephone1) {
        this.telephone1 = telephone1;
    }

    public String getTelephone2() {
        return telephone2;
    }

    public void setTelephone2(String telephone2) {
        this.telephone2 = telephone2;
    }

    public String getTelephone3() {
        return telephone3;
    }

    public void setTelephone3(String telephone3) {
        this.telephone3 = telephone3;
    }

    public String getEncharge() {
        return encharge;
    }

    public void setEncharge(String encharge) {
        this.encharge = encharge;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
