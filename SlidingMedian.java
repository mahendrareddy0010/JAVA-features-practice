
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

public class SlidingMedian {
    public static void main(String[] args) {
        Random random = new Random();
        Comparator<Pair> pairComparator = new Comparator<>() {
            @Override
            public int compare(Pair p1, Pair p2) {
                if (p1.value < p2.value) {
                    return -1;
                }
                if (p1.value > p2.value) {
                    return 1;
                }
                if (p1.value == p2.value) {
                    if (p1.index < p2.index) {
                        return -1;
                    }
                    if (p1.index > p2.index) {
                        return 1;
                    }
                }
                return 0;
            }
        };
        TreeSet<Pair> firstHalfSet = new TreeSet<>(pairComparator);
        TreeSet<Pair> secondHalfSet = new TreeSet<>(pairComparator);

        List<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < 10; i = i + 1) {
            arrayList.add(random.nextInt(10));
        }
        System.out.println(arrayList);

        int k = 3;
        for (int i = 0; i < arrayList.size(); i = i + 1) {
            int num = arrayList.get(i);
            if (firstHalfSet.size() == 0) {
                firstHalfSet.add(new Pair(num, i));
                continue;
            }
            // remove expired element
            if (i >= k) {
                firstHalfSet.remove(new Pair(arrayList.get(i - k), i - k));
                secondHalfSet.remove(new Pair(arrayList.get(i - k), i - k));
            }

            if (num > firstHalfSet.last().value) {
                secondHalfSet.add(new Pair(num, i));
            } else {
                firstHalfSet.add(new Pair(num, i));
            }

            // rebalance
            if (firstHalfSet.size() > (k + 1) / 2) {
                secondHalfSet.add(firstHalfSet.pollLast());
            }
            if (secondHalfSet.size() > k / 2) {
                firstHalfSet.add(secondHalfSet.pollFirst());
            }

            if (i >= k - 1) {
                if (k % 2 == 0) {
                    System.out.print((firstHalfSet.last().value + secondHalfSet.first().value) / 2.0 + " ");
                } else {
                    System.out.print(firstHalfSet.last().value + " ");
                }
            }
        }
        System.out.println();

    }

    private static class Pair {
        int value;
        int index;

        public Pair(int value, int index) {
            this.value = value;
            this.index = index;
        }

    }
}
