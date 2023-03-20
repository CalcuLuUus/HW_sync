public class ThirdEnc {
    private String data;
    private String secret;

    public ThirdEnc(String data){
        this.data = data;
    }

    public void ThirdSetEnc(){
        secret = "";
        for(int i = 0 ; i < data.length(); i++)
        {
            char now = data.charAt(i);
            secret += (char)(now + 1);
        }
    }

    public String getSecret(){
        return secret;
    }
    
}
