package data;

import java.util.ArrayList;
import java.util.List;

import domain.BonusPointPool;

public class Dataset {
    static public List<BonusPointPool> bonusPointPools;

    static public void insert(BonusPointPool bonusPointPool) {
        bonusPointPools.add(bonusPointPool);
    }

    static public void init() {
        bonusPointPools = new ArrayList<>();
        bonusPointPools.add(new BonusPointPool(1));
    }
}
