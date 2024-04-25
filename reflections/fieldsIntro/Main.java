package reflections.fieldsIntro;

import java.lang.reflect.Field;
import java.util.Arrays;

public class Main {
    public static void main(String[] args)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

        Movie movie = new Movie("movie-1", 2000, true, Category.DRAMA, 12.99);
        printDeclaredFieldsInfo(Movie.class, movie);
        
        System.out.println(Arrays.stream(Movie.class.getFields()).map(field -> field.getName()).toList());
    }

    private static <T> void printDeclaredFieldsInfo(Class<?> clazz, T instance)
            throws IllegalArgumentException, IllegalAccessException {
        if (clazz == null) {
            return;
        }
        for (Field field : clazz.getDeclaredFields()) {
            System.out.println(field.getType().getSimpleName() + " : " + field.getName() + " : " + field.get(instance));
            // System.out.println("is Synthetic : " + field.isSynthetic());
        }
        printDeclaredFieldsInfo(clazz.getSuperclass(), instance);
    }

    public static class Movie extends Product {
        public static final double MINIMMUM_PRICE = 10.99;
        private boolean isReleased;
        private Category category;
        private double actualPrice;

        public Movie(String name, int year, boolean isReleased, Category category, double price) {
            super(name, year);
            this.isReleased = isReleased;
            this.category = category;
            this.actualPrice = Math.max(price, MINIMMUM_PRICE);
        }

        public class MovieStats {
            private int timesWatched;

            public MovieStats(int timesWatched) {
                this.timesWatched = timesWatched;
            }

            public double getRevenue() {
                return timesWatched * actualPrice;
            }
        }
    }

    public enum Category {
        DRAMA, COMEDY, FIGHTING
    }

    public static class Product {
        protected String name;
        protected int year;
        protected double actualPrice;

        public Product(String name, int year) {
            this.name = name;
            this.year = year;
        }
    }
}
