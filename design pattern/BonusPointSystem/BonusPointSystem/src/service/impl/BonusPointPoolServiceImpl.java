package service.impl;

import java.util.Date;
import java.util.List;

import domain.BonusPoint;
import repo.BounsPointPoolDao;
import repo.impl.BonusPointPoolDaoImpl;
import service.BonusPointPoolService;

public class BonusPointPoolServiceImpl implements BonusPointPoolService {

    public BounsPointPoolDao bounsPointPoolDao = new BonusPointPoolDaoImpl();

    @Override
    public int add(int userId, int channelId, int eventId, int credit, Date expiredTime) {
        Date createTime = new Date();
        int id = 0;
        BonusPoint bonusPoint = new BonusPoint(createTime, expiredTime, id, userId, channelId, eventId, credit);

        try {
            bounsPointPoolDao.add(userId, bonusPoint);
            System.out.println("Success + " + credit);
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }

        return 0;
    }

    @Override
    public int minus(int userId, int channelId, int eventId, int credit, Date expiredTime) {
        Date createTime = new Date();
        int id = 0;
        BonusPoint bonusPoint = new BonusPoint(createTime, expiredTime, id, userId, channelId, eventId, credit);

        try {
            bounsPointPoolDao.minus(userId, bonusPoint);
            System.out.println("Success -" + credit);
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }

        return 0;
    }

    @Override
    public int search(int userId) {
        try {
            int result = bounsPointPoolDao.search(userId).getTotalCredit();
            System.out.println("User " + userId + ": Credit : " + result);
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return 0;
    }

    @Override
    public int searchDetail(int userId, int page) {
        try {
            List<BonusPoint> result = bounsPointPoolDao.searchDetail(userId, page);
            for (BonusPoint bp : result) {
                System.out.println(bp);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return 0;
    }

    @Override
    public int addDetail(int userId, int page) {
        try {
            List<BonusPoint> result = bounsPointPoolDao.addDetail(userId, page);
            for (BonusPoint bp : result) {
                System.out.println(bp);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return 0;
    }

    @Override
    public int minusDetail(int userId, int page) {
        try {
            List<BonusPoint> result = bounsPointPoolDao.minusDetail(userId, page);
            for (BonusPoint bp : result) {
                System.out.println(bp);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return 0;
    }

}
