package biz.melamart.www.cov19.models.hospitalData;

public class hospitalData {

String hospitalName,hospitalId,hospitalAddress;
    String screened,positiveCase,negativeCase,qurantined,isolated,totalppe,usedppe,resultWaiting;

    String unIsoBed,samCol,ref,suspect;

String dashUrl ="";
String    dateUpdated;
      Boolean      status;

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public String getScreened() {
        return screened;
    }

    public void setScreened(String screened) {
        this.screened = screened;
    }

    public String getPositiveCase() {
        return positiveCase;
    }

    public void setPositiveCase(String positiveCase) {
        this.positiveCase = positiveCase;
    }

    public String getNegativeCase() {
        return negativeCase;
    }

    public void setNegativeCase(String negativeCase) {
        this.negativeCase = negativeCase;
    }

    public String getQurantined() {
        return qurantined;
    }

    public void setQurantined(String qurantined) {
        this.qurantined = qurantined;
    }

    public String getIsolated() {
        return isolated;
    }

    public void setIsolated(String isolated) {
        this.isolated = isolated;
    }

    public String getTotalppe() {
        return totalppe;
    }

    public void setTotalppe(String totalppe) {
        this.totalppe = totalppe;
    }

    public String getUsedppe() {
        return usedppe;
    }

    public void setUsedppe(String usedppe) {
        this.usedppe = usedppe;
    }

    public String getUnIsoBed() {
        return unIsoBed;
    }

    public void setUnIsoBed(String unIsoBed) {
        this.unIsoBed = unIsoBed;
    }

    public String getSamCol() {
        return samCol;
    }

    public void setSamCol(String samCol) {
        this.samCol = samCol;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getSuspect() {
        return suspect;
    }

    public void setSuspect(String suspect) {
        this.suspect = suspect;
    }

    public String getDashUrl() {
        return dashUrl;
    }

    public void setDashUrl(String dashUrl) {
        this.dashUrl = dashUrl;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getResultWaiting() {
        return resultWaiting;
    }

    public void setResultWaiting(String resultWaiting) {
        this.resultWaiting = resultWaiting;
    }
}
