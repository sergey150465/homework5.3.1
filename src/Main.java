import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger count3 = new AtomicInteger();
    public static AtomicInteger count4 = new AtomicInteger();
    public static AtomicInteger count5 = new AtomicInteger();

    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        new Thread(() -> {
            for (String t : texts) {
                if (t.equals(new StringBuilder(t).reverse().toString())) {
                    switchAction(t);
                }
            }
        }).start();

        new Thread(() -> {
            for (String t : texts) {
                char[] textChar = t.toCharArray();
                char[] textCharNew = new char[textChar.length];
                Arrays.fill(textCharNew, textChar[0]);
                if (t.equals(Arrays.toString(textCharNew))) {
                    switchAction(t);
                }
            }
        }).start();

        new Thread(() -> {
            for (String t : texts) {
                char[] textChar = t.toCharArray();
                Arrays.sort(textChar);
                if (t.equals(Arrays.toString(textChar))) {
                    switchAction(t);
                }
            }
        }).start();
        System.out.println("Красивых слов с длиной 3 : " + count3);
        System.out.println("Красивых слов с длиной 4 : " + count4);
        System.out.println("Красивых слов с длиной 5 : " + count5);
    }

    public static void switchAction(String t) {
        switch (t.length()) {
            case 3:
                count3.incrementAndGet();
            case 4:
                count4.incrementAndGet();
            case 5:
                count5.incrementAndGet();
        }
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}