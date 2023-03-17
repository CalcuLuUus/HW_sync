package domain;

import java.util.Date;

public class BonusPoint {
    Date createTime;
    Date expiredTime;
    int id;
    int userId;
    int channelId;
    int eventId;
    int credit;

    public BonusPoint(Date createTime, Date expiredTime, int id, int userId, int channelId, int eventId, int credit) {
        this.createTime = createTime;
        this.expiredTime = expiredTime;
        this.id = id;
        this.userId = userId;
        this.channelId = channelId;
        this.eventId = eventId;
        this.credit = credit;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getChannelId() {
        return channelId;
    }

    public int getEventId() {
        return eventId;
    }

    public int getCredit() {
        return credit;
    }

    
    
}
