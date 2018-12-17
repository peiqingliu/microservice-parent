package testbims;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author liupeqing
 * @date 2018/11/27 15:11
 */
public class BimsTest {

    @Test
    public  void test() {
        List<Jzg> list1 =new ArrayList();
        Jzg j1 = new Jzg("小明","123");
        Jzg j2 = new Jzg("小红","124");
        Jzg j3 = new Jzg("小绿","125");

        list1.add(j1);
        list1.add(j2);
        list1.add(j3);

//        list1.add("1111");
//        list1.add("2222");
//        list1.add("3333");

        List<Jzg> list2 =new ArrayList();
        Jzg j4 = new Jzg("小明","123");
        Jzg j5 = new Jzg("小红","124");
        Jzg j6 = new Jzg("小绿","125");
        Jzg j7 = new Jzg("小白","127");
        list2.add(j4);
        list2.add(j5);
        list2.add(j6);
        list2.add(j7);
        for (Jzg jzg : list2){
            boolean isHas = list1.contains(jzg);
            System.out.println(isHas);
        }
//        list2.add("3333");
//        list2.add("4444");
//        list2.add("5555");
//        list2.add("777");
//        list2.add("999");
//
//        //差集
 //       list1.addAll(list2);
//
     //   Iterator<String> it=list1.iterator();
//        while (it.hasNext()) {
//            System.out.println(it.next());
//
//        }
    }
}
