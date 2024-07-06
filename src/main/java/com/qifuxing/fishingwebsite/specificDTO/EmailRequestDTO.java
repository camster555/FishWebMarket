package com.qifuxing.fishingwebsite.specificDTO;

import java.util.Objects;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This entity represents email request.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-07-01
 * @version 1.0.0
 */

public class EmailRequestDTO {

    private String to;
    private String subject;
    private String body;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailRequestDTO that = (EmailRequestDTO) o;
        return Objects.equals(to, that.to) && Objects.equals(subject, that.subject) && Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(to, subject, body);
    }

}
