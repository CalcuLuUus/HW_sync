package domain;

import service.BonusPointPoolService;

public abstract class Activity {
    BonusPointPoolService bpps;

    int userid;
    int kind; // 0 + 1 - 2 s

    public Activity(int userid, int kind) {
        this.userid = userid;
        this.kind = kind;
    }

    public abstract void work();
}
