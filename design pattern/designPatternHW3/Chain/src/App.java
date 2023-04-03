public class App {
    public static void main(String[] args) throws Exception {
        Support s1 = new CharSupport("字符编码转换过滤器");
        Support s2 = new TransSupport("数据类型转换过滤器");
        Support s3 = new DataCheckSupport("数据校验过滤器");

        s1.setNext(s2).setNext(s3);

        for(int i = -1; i <= 3; i++){
            Trouble trouble = new Trouble(i);
            s1.support(trouble);
        }
    }
}
