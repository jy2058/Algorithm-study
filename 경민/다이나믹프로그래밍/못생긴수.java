package 다이나믹프로그래밍;

import java.util.*;

public class 못생긴수 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        // 2배, 3배, 5배를 위한 인덱스
        int i2 = 0, i3 = 0, i5 = 0;
        // 처음에 곱셈 값을 초기화
        int next2 = 2, next3 = 3, next5 = 5;

        int[] ugly = new int[1000]; // 못생긴 수를 담기 위한 테이블 (1차원 DP 테이블)

        ugly[0] = 1; // 첫 번째 못생긴 수는 1
        // 1부터 n까지의 못생긴 수들을 찾기
        for (int l = 1; l < n; l++) {
            // 가능한 곱셈 결과 중에서 가장 작은 수를 선택
            ugly[l] = Math.min(next2, Math.min(next3, next5));
            // 인덱스에 따라서 곱셈 결과를 증가
            if (ugly[l] == next2) {
                i2 += 1;
                next2 = ugly[i2] * 2;
            }
            if (ugly[l] == next3) {
                i3 += 1;
                next3 = ugly[i3] * 3;
            }
            if (ugly[l] == next5) {
                i5 += 1;
                next5 = ugly[i5] * 5;
            }
        }
        // n번째 못생긴 수를 출력
        System.out.println(ugly[n - 1]);
    }
}