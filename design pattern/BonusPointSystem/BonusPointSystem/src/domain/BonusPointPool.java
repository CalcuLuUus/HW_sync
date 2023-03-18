package domain;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class BonusPointPool {
    private int userId;
    private int totalCredit;
    private List<BonusPoint> pool;
    private List<BonusPoint> addRecord;
    private List<BonusPoint> minusRecord;

    public BonusPointPool(int userId) {
        this.userId = userId;
        this.totalCredit = 0;
        pool = new LinkedList<>();
        addRecord = new LinkedList<>();
        minusRecord = new LinkedList<>();
    }

    public int getUserId() {
        return userId;
    }

    public int getTotalCredit() {
        return totalCredit;
    }

    public void add(BonusPoint b) {
        pool.add(b);
        addRecord.add(b);
        totalCredit += b.getCredit();
    }

    public void update() {
        Date nowDate = new Date();
        while (pool.size() > 0 && nowDate.after(pool.get(0).expiredTime)) {
            pool.remove(0);
        }
    }

    public int sub(BonusPoint b) {
        update();
        int num = b.getCredit();
        Collections.sort(pool);
        if (totalCredit < num) {
            return 1;
        }
        totalCredit -= num;
        while (num > 0) {
            BonusPoint pf = pool.get(0);
            if (pf.getCredit() > num) {
                pf.setCredit(pf.getCredit() - num);
                num = 0;
            } else {
                num -= pf.getCredit();
            }

        }
        b.setCredit(-b.getCredit());
        minusRecord.add(b);
        return 0;
    }

    public List<BonusPoint> detail(int page, int kind) {
        update();
        page--;
        int st = page * 10;
        int ed = (page + 1) * 10;
        List<BonusPoint> list = null;
        switch (kind) {
            case 0:
                list = pool;
                break;
            case 1:
                list = addRecord;
                break;
            case 2:
                list = minusRecord;
                break;
        }
        ed = Math.min(list.size(), ed);

        if (st >= list.size())
            return null;
        return list.subList(st, ed);
    }

}
