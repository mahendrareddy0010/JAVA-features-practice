public class VolatileDemo {
    public static void main(String[] args) throws InterruptedException {
        Test test = new Test();
        Thread t1 = Thread.ofVirtual().unstarted(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i = i + 1) {
                test.increment();
            }
        });
        Thread t2 = Thread.ofVirtual().unstarted(() -> {
            while (true) {
                test.checkCondition();
            }
        });
        t2.start();
        t1.start();
        t2.join(3000);
    }

    private static class Test {
        int x = 0, y = 0;

        public void increment() {
            x++;
            y++;
        }

        public void checkCondition() {
            if (x < y) {
                System.out.println("x < y");
            }
        }
    }
}
