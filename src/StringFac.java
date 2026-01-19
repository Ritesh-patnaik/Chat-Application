import java.util.Scanner;
public class StringFac {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String X = scanner.nextLine().trim();
        String Y = scanner.nextLine().trim();
        int S = scanner.nextInt();
        int R = scanner.nextInt();
        System.out.println(minStringFactor(X, Y, S, R));

        scanner.close();
    }

    private static int minStringFactor(String X, String Y, int S, int R) {
        int lenX = X.length();
        int lenY = Y.length();
        String revY = new StringBuilder(Y).reverse().toString();

        int[] dp = new int[lenX + 1];

        for (int i = 1; i <= lenX; i++)
            dp[i] = Integer.MAX_VALUE;
        dp[0] = 0;

        for (int i = 1; i <= lenX; i++) {
            for (int j = 0; j < i; j++) {
                String substring = X.substring(j, i);

                if (Y.contains(substring))
                    dp[i] = Math.min(dp[i], dp[j] + 1);
                if (revY.contains(substring))
                    dp[i] = Math.min(dp[i], dp[j] + 1);
            }
        }
        if (dp[lenX] == Integer.MAX_VALUE) return -1;
        return (dp[lenX] * S);
    }
}