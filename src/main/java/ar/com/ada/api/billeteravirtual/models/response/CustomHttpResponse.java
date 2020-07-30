package ar.com.ada.api.billeteravirtual.models.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class CustomHttpResponse {

    private HttpStatus status;

    private String error;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime date;

    public CustomHttpResponse() {
        // Empty Constructor
    }

    public CustomHttpResponse(HttpStatus status, String errorMessage) {
        this.status = status;
        this.error = errorMessage;
        this.date = LocalDateTime.now();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CustomHttpResponse{" +
                "status=" + status +
                ", error='" + error + '\'' +
                ", date=" + date +
                '}';
    }
}
