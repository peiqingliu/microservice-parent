import java.util.UUID;

public class Test {
    public static void main(String[] args) {
        //Long i = System.currentTimeMillis();
       // String s=UUID.randomUUID().toString();
        String str = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        System.out.println(str);
    }

}
