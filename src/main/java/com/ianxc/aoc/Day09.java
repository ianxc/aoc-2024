package com.ianxc.aoc;

public class Day09 {
    private static final boolean DBG = false;

    static long part1(String path) {
        var input = parseInput(path);
        var checksum = 0L;
        var leftFileId = 0L;
        var rightFileId = rightFileIdOf(input);
        var inputLeft = 0;
        var inputRight = input.length - 1;
        var checksumPosition = 0;
        while (inputLeft <= inputRight) {
            if (DBG) {
                System.out.printf(
                        "checksum=%d leftFileId=%d rightFileId=%d inputLeft=%d inputRight=%d checksumPosition=%d\n",
                        checksum, leftFileId, rightFileId, inputLeft, inputRight, checksumPosition);
            }
            if (isGapRegion(inputLeft)) {
                while (input[inputLeft] > 0 && input[inputRight] > 0) {
                    checksum += checksumPosition * rightFileId;
                    checksumPosition++;
                    input[inputLeft]--;
                    input[inputRight]--;
                }
                if (input[inputLeft] == 0) {
                    // There are no more gaps in the gap region.
                    inputLeft++;
                }
                if (input[inputRight] == 0) {
                    // We have placed all the items in the right position.
                    inputRight -= 2;
                    rightFileId--;
                }
            } else {
                for (int i = 0; i < input[inputLeft]; i++) {
                    checksum += checksumPosition * leftFileId;
                    checksumPosition++;
                }
                leftFileId++;
                inputLeft++;
            }
        }
        return checksum;
    }

    static int[] parseInput(String path) {
        var inputStr = Util.loadFile(path).get(0);
        var chars = inputStr.toCharArray();
        var n = chars.length;
        var nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = chars[i] - '0';
        }
        return nums;
    }

    static long rightFileIdOf(int[] nums) {
        return nums.length / 2;
    }

    static boolean isGapRegion(int inputLeft) {
        return (inputLeft & 1) == 1;
    }

    static long part2(String path) {
        return 0;
    }


}
