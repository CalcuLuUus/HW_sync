package domain;

import java.util.Date;

import service.impl.BonusPointPoolServiceImpl;

public class AddAct extends Activity {
    Date expiredTime;
    int channelId;
    int credit;
    int eventId;

    public AddAct(int userid, int kind, Date expiredTime, int channelId, int credit) {
        super(userid, kind);
        this.expiredTime = expiredTime;
        this.channelId = channelId;
        this.credit = credit;
        eventId = channelId;
        bpps = new BonusPointPoolServiceImpl();
    }
    
    @Override
    public String toString() {
        return "AddAct [expiredTime=" + expiredTime + ", channelId=" + channelId + ", credit=" + credit + ", eventId="
                + eventId + "]";
    }

    @Override
    public void work() {
        if (eventId == 0) {
            bpps.add(userid, channelId, eventId, credit, expiredTime);
        } else {
            bpps.minus(userid, channelId, eventId, credit, expiredTime);
        }
    }

}
