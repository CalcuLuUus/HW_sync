package repo.impl;

import java.util.List;

import data.Dataset;
import domain.BonusPoint;
import domain.BonusPointPool;
import repo.BounsPointPoolDao;

public class BonusPointPoolDaoImpl implements BounsPointPoolDao {

    @Override
    public BonusPointPool search(int userId) throws Exception {
        for (BonusPointPool bonusPointPool : Dataset.bonusPointPools) {
            if (bonusPointPool.getUserId() == userId) {
                return bonusPointPool;
            } else {
                throw new Exception("Credit minus failed: User not found");
            }
        }

        return null;
    }

    @Override
    public int add(int userId, BonusPoint bonusPoint) throws Exception {
        BonusPointPool userBonusPointPool = search(userId);
        if (userBonusPointPool != null) {
            userBonusPointPool.add(bonusPoint);
            return 0;
        }
        return 1;
    }

    @Override
    public int minus(int userId, BonusPoint bonusPoint) throws Exception {
        BonusPointPool userBonusPointPool = search(userId);
        if (userBonusPointPool != null) {
            int ret = userBonusPointPool.sub(bonusPoint);
            if (ret != 0) {
                throw new Exception("Credit minus failed: Not enough credit");
            }
            return 0;
        }
        return 1;
    }

    @Override
    public List<BonusPoint> searchDetail(int userId, int page) throws Exception {
        BonusPointPool userBonusPointPool = search(userId);
        if (userBonusPointPool != null) {
            List<BonusPoint> res = userBonusPointPool.detail(page, 0);
            if (res == null) {
                throw new Exception("Search faild: Invalid input");
            }
            return res;
        }
        return null;
    }

    @Override
    public List<BonusPoint> addDetail(int userId, int page) throws Exception {
        BonusPointPool userBonusPointPool = search(userId);
        if (userBonusPointPool != null) {
            List<BonusPoint> res = userBonusPointPool.detail(page, 1);
            if (res == null) {
                throw new Exception("Search faild: Invalid input");
            }
            return res;
        }
        return null;
    }

    @Override
    public List<BonusPoint> minusDetail(int userId, int page) throws Exception {
        BonusPointPool userBonusPointPool = search(userId);
        if (userBonusPointPool != null) {
            List<BonusPoint> res = userBonusPointPool.detail(page, 2);
            if (res == null) {
                throw new Exception("Search faild: Invalid input");
            }
            return res;
        }
        return null;
    }

}
