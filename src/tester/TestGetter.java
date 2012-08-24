package tester;

import java.util.ArrayList;
import java.util.Arrays;

public class TestGetter {
    private int n, maxNum;
    private ArrayList<Integer> list;
    private int b;
    private int[] answer;
    private boolean[] used;

    public ArrayList<int[]> answers;

    public TestGetter(ArrayList<Integer> arrayA) {
        list = new ArrayList<Integer>(arrayA);
        answers = new ArrayList<int[]>();
        this.n = arrayA.size();

        answer = new int[n];
        used = new boolean[n];

        maxNum = -1;

        for (int i = 0; i < n; i++) {
            int tmp = list.get(i);
            //tmp--;
            list.set(i, tmp);
            maxNum = Math.max(maxNum, tmp);
        }

        b = maxNum * 2;
    }

    private int rand(int index) {
        return ((int) (Math.random() * n * list.get(index)) % list.get(index) + 1);
    }

    private int[] getNextTest(int[] answer) {
        int[] result = new int[n];

        for (int i = 0; i < n; i++) {
            if (!used[i] && (answer[i] + 1) <= list.get(i)) {
                result[i] = (answer[i] + 1);
            } else {
                used[i] = true;
                result[i] = rand(i);
            }
        }
        return result;
    }

    private int[] getRandomTest() {
        int[] result = new int[n];

        for (int i = 0; i < n; i++) {
            result[i] = rand(i);
        }

        return result;
    }

    public void run() {
        for (int i = 0; i < maxNum && i < b; i++) {
            answer = getNextTest(answer);
            answers.add(answer);
        }
        int size = b - maxNum;
        int ret = n * b;

        for (int i = 0; i < size && ret > 0; i++) {
            int[] rand = getRandomTest();
            boolean contains = false;
            for (int[] test : answers) {
                if (Arrays.equals(test, rand)) {
                    contains = true;
                    break;
                }
            }

            if (!contains) {
                answers.add(rand);
            } else {
                i--;
                ret--;
            }
        }
    }
}
