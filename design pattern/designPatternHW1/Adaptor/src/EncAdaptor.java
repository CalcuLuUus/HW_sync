public class EncAdaptor extends DBEnc{
    private ThirdEnc thirdEnc;

    public EncAdaptor(String data){
        thirdEnc = new ThirdEnc(data);
    }

    @Override
    public String Enc() {
        thirdEnc.ThirdSetEnc();
        return thirdEnc.getSecret();
    }
    
}
