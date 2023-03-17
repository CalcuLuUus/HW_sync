package repo.impl;

import data.Dataset;
import domain.BonusPoint;
import domain.BonusPointPool;
import repo.BounsPointPoolDao;

public class BonusPointPoolDaoImpl implements BounsPointPoolDao {

    @Override
    public BonusPointPool search(int userId) {
        for(BonusPointPool bonusPointPool : Dataset.bonusPointPools){
            if(bonusPointPool.getUserId() == userId){
                return bonusPointPool;
            }
        }
        
        return null;
    }

    @Override
    public int add(int userId, BonusPoint bonusPoint) {
        BonusPointPool userBonusPointPool = search(userId);
        if(userBonusPointPool != null){
            userBonusPointPool.add(bonusPoint);
            return 1;
        }
        return 0;
    }

    @Override
    public int minus(int userId, BonusPoint bonusPoint) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'minus'");
    }

    @Override
    public BonusPointPool searchDetail(int userId, int page) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchDetail'");
    }

    @Override
    public BonusPointPool addDetail(int userId, int page) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addDetail'");
    }

    @Override
    public BonusPointPool minusDetail(int userId, int page) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'minusDetail'");
    }
    
}
