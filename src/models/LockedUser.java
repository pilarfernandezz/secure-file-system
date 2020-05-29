package models;

import java.time.LocalDateTime;

public class LockedUser {
    private int id;
    private int userId;
    private LocalDateTime lockDate;
    private boolean isActive;

    public LockedUser(int id, int userId, LocalDateTime lockDate, boolean isActive) {
        this.id = id;
        this.userId = userId;
        this.lockDate = lockDate;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getLockDate() {
        return lockDate;
    }

    public void setLockDate(LocalDateTime lockDate) {
        this.lockDate = lockDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
