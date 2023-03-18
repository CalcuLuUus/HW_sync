package domain;

import java.util.Date;

public class BonusPoint implements Comparable<BonusPoint> {
    static public int count = 0;

    Date createTime;
    Date expiredTime;
    int id;
    int userId;
    int channelId; // 0 + 1 -
    int eventId; // 0 + 1 -
    int credit;

    public BonusPoint(Date createTime, Date expiredTime, int id, int userId, int channelId, int eventId, int credit) {
        this.createTime = createTime;
        this.expiredTime = expiredTime;
        this.id = count++;
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

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    @Override
    public int compareTo(BonusPoint o) {
        BonusPoint bonusPoint = (BonusPoint) o;
        return this.expiredTime.compareTo(bonusPoint.expiredTime);
    }

    @Override
    public String toString() {
        return "BonusPoint [createTime=" + createTime + ", expiredTime=" + expiredTime + ", id=" + id + ", userId="
                + userId + ", channelId=" + channelId + ", eventId=" + eventId + ", credit=" + credit + "]";
    }

    

}
