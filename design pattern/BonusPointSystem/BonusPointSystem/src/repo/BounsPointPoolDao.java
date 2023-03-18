package repo;

import java.util.List;

import domain.BonusPoint;
import domain.BonusPointPool;

public interface BounsPointPoolDao {
    BonusPointPool search(int userId) throws Exception;

    int add(int userId, BonusPoint bonusPoint) throws Exception;

    int minus(int userId, BonusPoint bonusPoint) throws Exception;

    List<BonusPoint> searchDetail(int userId, int page) throws Exception;

    List<BonusPoint> addDetail(int userId, int page) throws Exception;

    List<BonusPoint> minusDetail(int userId, int page) throws Exception;
}
