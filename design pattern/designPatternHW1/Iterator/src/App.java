import java.util.Random;

public class App {
    public static void main(String[] args) throws Exception {
        ItemList itemList = new ItemList(10);
        Random random = new Random();
        for(int i = 0; i < 5; i++)
        {
            itemList.add(new item(random.nextInt() % 100));
        }

        MyIterator myIterator = itemList.iterator();
        myIterator.setCntOfPage(2);
        
        while(myIterator.hasNext()){
            item[] ret =(item[]) myIterator.next();
            System.out.println("Page start >>>>>>>>>>>>>");
            for(item i : ret){
                System.out.println(i);
            }
            System.out.println("Page end <<<<<<<<<<<<<<<<");
            System.out.println();
        }
    }
}
