package br.com.alura.produto.consumer.users;

import java.time.Instant;

public class AuthUserEvent {

    private String token;
    private String userName;
    private Boolean active;
    private String type;
    private Instant occurredAt;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Instant getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(Instant occurredAt) {
        this.occurredAt = occurredAt;
    }

    @Override
    public String toString() {
        return "AuthUserEvent{" +
                "token='" + token + '\'' +
                ", userName='" + userName + '\'' +
                ", active=" + active +
                ", type='" + type + '\'' +
                ", occurredAt=" + occurredAt +
                '}';
    }
}