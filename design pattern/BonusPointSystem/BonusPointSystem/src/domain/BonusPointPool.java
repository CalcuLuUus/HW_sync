package domain;

import java.util.List;

public class BonusPointPool {
    private int userId;
    private int totalCredit;
    private List<BonusPoint> pool;
    
    public BonusPointPool(int userId) {
        this.userId = userId;
        this.totalCredit = 0;
    }

    public int getUserId() {
        return userId;
    }

    public void add(BonusPoint b){
        pool.add(b);
    }
    
}
