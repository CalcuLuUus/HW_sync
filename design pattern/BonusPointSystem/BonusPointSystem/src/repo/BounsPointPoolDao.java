package repo;

import domain.BonusPoint;
import domain.BonusPointPool;

public interface BounsPointPoolDao {
    BonusPointPool search(int userId);
    int add(int userId, BonusPoint bonusPoint);
    int minus(int userId, BonusPoint bonusPoint);
    BonusPointPool searchDetail(int userId, int page);
    BonusPointPool addDetail(int userId, int page);
    BonusPointPool minusDetail(int userId, int page);
}
