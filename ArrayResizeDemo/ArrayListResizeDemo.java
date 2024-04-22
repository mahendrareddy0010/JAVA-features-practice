package ArrayResizeDemo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class ArrayListResizeDemo {
    public static void main(String[] args) {
        List<Integer> arrayList = new ArrayList<>();
        Set<Integer> hashSet = new HashSet<>();
        for (int i = 10; i < 10000; i = i +( i >> 1)) {
            hashSet.add(i+1);
        }
        System.out.println("Capacities : " + hashSet.stream().sorted().collect(Collectors.toList()));
        long start, end;
        int num;
        Random random = new Random();
        for (int i = 1; i < 10000; i = i + 1) {
            num = random.nextInt(100);
            start = System.nanoTime();
            arrayList.add(num);
            end = System.nanoTime();
            if (hashSet.contains(i) || hashSet.contains(i-1)) {
                System.out.println(i + " : " + (end - start) / 1000);
            }
        }
    }

}
