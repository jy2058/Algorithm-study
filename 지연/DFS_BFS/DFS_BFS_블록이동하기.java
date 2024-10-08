package 지연.DFS_BFS;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 이것이 코딩 테스트다 / 기출문제
 * p.355 블록 이동하기 / 난이도 상
 * 프로그래머스 https://school.programmers.co.kr/learn/courses/30/lessons/60063
 */
public class DFS_BFS_블록이동하기 {
    public static void main(String[] args) {
        int[][] board = {
                {0, 0, 0, 1, 1},
                {0, 0, 0, 1, 0},
                {0, 1, 0, 1, 1},
                {1, 1, 0, 0, 1},
                {0, 0, 0, 0, 0}
        };

        System.out.println(Solution.solution(board));

    }



    static class Robot {
        private int pos1X;
        private int pos1Y;
        private int pos2X;
        private int pos2Y;
        private int distance;

        public int getPos1X() {
            return this.pos1X;
        }
        public int getPos1Y() {
            return this.pos1Y;
        }
        public int getPos2X() {
            return this.pos2X;
        }
        public int getPos2Y() {
            return this.pos2Y;
        }
        public int getDistance() {
            return this.distance;
        }

        public Robot(int pos1X, int pos1Y, int pos2X, int pos2Y, int distance) {
            this.pos1X = pos1X;
            this.pos1Y = pos1Y;
            this.pos2X = pos2X;
            this.pos2Y = pos2Y;
            this.distance = distance;
        }
    }

    static class Solution {

        public static ArrayList<Robot> getNextPos(Robot pos, int[][] board) {
            // 반환 결과(이동 가능한 위치들)
            ArrayList<Robot> nextPos = new ArrayList<Robot>();
            // (상, 하, 좌, 우)로 이동하는 경우에 대해서 처리
            int[] dx = {-1, 1, 0, 0};
            int[] dy = {0, 0, -1, 1};
            for (int i = 0; i < 4; i++) {
                int pos1NextX = pos.getPos1X() + dx[i];
                int pos1NextY = pos.getPos1Y() + dy[i];
                int pos2NextX = pos.getPos2X() + dx[i];
                int pos2NextY = pos.getPos2Y() + dy[i];
                int distanceNext = pos.getDistance() + 1;
                // 이동하고자 하는 두 칸이 모두 비어 있다면
                if (board[pos1NextX][pos1NextY] == 0 && board[pos2NextX][pos2NextY] == 0) {
                    nextPos.add(new Robot(pos1NextX, pos1NextY, pos2NextX, pos2NextY, distanceNext));
                }
            }
            // 현재 로봇이 가로로 놓여 있는 경우
            int[] hor = {-1, 1};
            if (pos.getPos1X() == pos.getPos2X()) {
                for (int i = 0; i < 2; i++) { // 위쪽으로 회전하거나, 아래쪽으로 회전
                    // 위쪽 혹은 아래쪽 두 칸이 모두 비어 있다면
                    if (board[pos.getPos1X() + hor[i]][pos.getPos1Y()] == 0 && board[pos.getPos2X() + hor[i]][pos.getPos2Y()] == 0) {
                        nextPos.add(new Robot(pos.getPos1X(), pos.getPos1Y(), pos.getPos1X() + hor[i], pos.getPos1Y(), pos.getDistance() + 1));
                        nextPos.add(new Robot(pos.getPos2X(), pos.getPos2Y(), pos.getPos2X() + hor[i], pos.getPos2Y(), pos.getDistance() + 1));
                    }
                }
            }
            // 현재 로봇이 세로로 놓여 있는 경우
            int[] ver = {-1, 1};
            if (pos.getPos1Y() == pos.getPos2Y()) {
                for (int i = 0; i < 2; i++) { // 왼쪽으로 회전하거나, 오른쪽으로 회전
                    // 왼쪽 혹은 오른쪽 두 칸이 모두 비어 있다면
                    if (board[pos.getPos1X()][pos.getPos1Y() + ver[i]] == 0 && board[pos.getPos2X()][pos.getPos2Y() + ver[i]] == 0) {
                        nextPos.add(new Robot(pos.getPos1X(), pos.getPos1Y(), pos.getPos1X(), pos.getPos1Y() + ver[i], pos.getDistance() + 1));
                        nextPos.add(new Robot(pos.getPos2X(), pos.getPos2Y(), pos.getPos2X(), pos.getPos2Y() + ver[i], pos.getDistance() + 1));
                    }
                }
            }
            // 현재 위치에서 이동할 수 있는 위치를 반환
            return nextPos;
        }

        public static int solution(int[][] board) {
            // 맵 외곽에 벽을 두는 형태로 맵 변형
            int n = board.length;
            int[][] newBoard = new int[n + 2][n + 2];
            for (int i = 0; i < n + 2; i++) {
                for (int j = 0; j < n + 2; j++) {
                    newBoard[i][j] = 1;
                }
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    newBoard[i + 1][j + 1] = board[i][j];
                }
            }
            // 너비 우선 탐색(BFS) 수행
            Queue<Robot> queue = new LinkedList<>();
            ArrayList<Robot> visited = new ArrayList<>();
            Robot pos = new Robot(1, 1, 1, 2, 0); // 시작 위치 설정
            queue.offer(pos); // 큐에 삽입한 뒤에
            visited.add(pos); // 방문 처리
            // 큐가 빌 때까지 반복
            while (!queue.isEmpty()) {
                pos = queue.poll();
                // (n, n) 위치에 로봇이 도달했다면, 최단 거리이므로 반환
                if ((pos.getPos1X() == n && pos.getPos1Y() == n) || (pos.getPos2X() == n && pos.getPos2Y() == n)) {
                    return pos.getDistance();
                }
                // 현재 위치에서 이동할 수 있는 위치 확인
                ArrayList<Robot> nextPos = getNextPos(pos, newBoard);
                for (int i = 0; i < nextPos.size(); i++) {
                    // 아직 방문하지 않은 위치라면 큐에 삽입하고 방문 처리
                    boolean check = true;
                    pos = nextPos.get(i);
                    for (int j = 0; j < visited.size(); j++) {
                        if (pos.getPos1X() == visited.get(j).getPos1X() && pos.getPos1Y() == visited.get(j).getPos1Y() && pos.getPos2X() == visited.get(j).getPos2X() && pos.getPos2Y() == visited.get(j).getPos2Y()) {
                            check = false;
                            break;
                        }
                    }
                    if (check) {
                        queue.offer(pos);
                        visited.add(pos);
                    }
                }
            }
            return 0;
        }
    }
}

/*
이 문제는 최단거리 문제이기 때문에 BFS를 이용하여 해결 할 수 있다.
가장 큰 특징은 로봇의 크기가 2 * 1인 점.
세로로 놓인 상태에서 오른쪽, 왼쪽으로 회전하는 경우,
가로로 놓인 상태에서 위, 아래로 회전하는 경우를 체크해야 한다.
 */