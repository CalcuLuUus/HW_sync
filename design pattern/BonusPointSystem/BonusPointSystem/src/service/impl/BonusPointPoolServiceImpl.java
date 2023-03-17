package service.impl;

import java.util.Date;

import repo.BounsPointPoolDao;
import service.BonusPointPoolService;

public class BonusPointPoolServiceImpl implements BonusPointPoolService{

    public BounsPointPoolDao bounsPointPoolDao;

    @Override
    public int add(int userId, int channelId, int eventId, int credit, Date expiredTime) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public int minus(int userId, int channelId, int eventId, int credit, Date expiredTime) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'minus'");
    }

    @Override
    public int search(int userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'search'");
    }

    @Override
    public int searchDetail(int userId, int page) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchDetail'");
    }

    @Override
    public int addDetail(int userId, int page) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addDetail'");
    }

    @Override
    public int minusDetail(int userId, int page) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'minusDetail'");
    }
    
}
