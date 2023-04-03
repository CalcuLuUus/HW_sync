public class BackupFacade {
    MsgManageSystem msgManageSystem;
    ContactManageSystem contactManageSystem;

    public BackupFacade() {
        this.msgManageSystem = new MsgManageSystem();
        this.contactManageSystem = new ContactManageSystem();
    }

    public void backup(){
        msgManageSystem.transInfo();
        contactManageSystem.transInfo();
        System.out.println("Back up completed");
    }

    
}
