package service;

import java.util.Date;

public interface BonusPointPoolService {
    int add(int userId, int channelId, int eventId, int credit, Date expiredTime);
    int minus(int userId, int channelId, int eventId, int credit, Date expiredTime);
    int search(int userId);
    int searchDetail(int userId, int page);
    int addDetail(int userId, int page);
    int minusDetail(int userId, int page);
}
