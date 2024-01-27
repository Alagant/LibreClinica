package org.akaza.openclinica.control.submit;
import com.fasterxml.jackson.annotation.JsonProperty;
public class SuccessResponse {
    @JsonProperty("pid")
    private String pid;
    private String success;

    public SuccessResponse() {
    }

    public SuccessResponse(String pid, String success) {
        this.pid = pid;
        this.success = success;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
