package 이진탐색;

import java.util.*;

public class 고정점찾기 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();
        }

        // 이진 탐색(Binary Search) 수행
        int index = binarySearch(array, 0, n - 1);

        // 결과 출력
        System.out.println(index);
    }
    // 이진 탐색 소스코드 구현(재귀 함수)
    public static int binarySearch(int[] arr, int start, int end) {
        if (start > end) return -1;
        int mid = (start + end) / 2;
        // 고정점을 찾은 경우 중간점 인덱스 반환
        if (arr[mid] == mid) return mid;
            // 중간점의 값보다 중간점이 작은 경우 왼쪽 확인
        else if (arr[mid] > mid) return binarySearch(arr, start, mid - 1);
            // 중간점의 값보다 중간점이 큰 경우 오른쪽 확인
        else return binarySearch(arr, mid + 1, end);
    }
}