package domain;

import service.impl.BonusPointPoolServiceImpl;

public class SAct extends Activity {
    int page;
    int which;

    public SAct(int userid, int kind, int page, int which) {
        super(userid, kind);
        this.page = page;
        this.which = which;
        bpps = new BonusPointPoolServiceImpl();
    }

    @Override
    public void work() {
        switch (which) {
            case 0:
                bpps.search(userid);
                break;
            case 1:
                bpps.searchDetail(userid, page);
                break;
            case 2:
                bpps.addDetail(userid, page);
                break;
            case 3:
                bpps.minusDetail(userid, page);
                break;
        }
    }

}
