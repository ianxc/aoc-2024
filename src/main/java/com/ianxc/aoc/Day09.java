package com.ianxc.aoc;

import java.util.ArrayList;
import java.util.List;

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
        var input = parseInput(path);
        var regions = new Region[input.length];
        for (int i = 0, fileId = 0; i < input.length; i += 2, fileId++) {
            regions[i] = Region.newFull(fileId, input[i]);
        }
        for (int i = 1; i < input.length; i += 2) {
            regions[i] = Region.newFree(input[i]);
        }

        var minUnfilledRegion = 1;
        for (int blockToMoveIdx = input.length - 1; blockToMoveIdx > minUnfilledRegion; blockToMoveIdx -= 2) {
            for (int unfilledRegion = 1; unfilledRegion < blockToMoveIdx; unfilledRegion += 2) {
                var freeSpace = regions[unfilledRegion].getFree();
                if (freeSpace == 0 && unfilledRegion == minUnfilledRegion) {
                    // optimization
                    minUnfilledRegion += 2;
                    continue;
                }
                var blockToMoveCapacity = regions[blockToMoveIdx].capacity;
                if (freeSpace >= blockToMoveCapacity) {
                    regions[unfilledRegion].addBlock(regions[blockToMoveIdx].getSingle());
                    regions[blockToMoveIdx] = Region.newFree(blockToMoveCapacity);
                    break;
                }
            }
        }

        var checksum = 0L;
        var checksumPosition = 0;
        for (var r: regions) {
            for (var b: r.getBlocks()) {
                for (int i = 0; i < b.count; i++) {
                    checksum += checksumPosition * b.fileId;
                    checksumPosition++;
                }
            }
            checksumPosition += r.getFree();
        }

        return checksum;

    }

    record Block(long fileId, int count) {
    }

    static class Region {
        final int capacity;
        private final List<Block> blocks;
        private int free;

        private Region(int capacity, List<Block> blocks) {
            this.capacity = capacity;
            this.blocks = blocks;
            this.free = capacity - blocks.stream().mapToInt(Block::count).sum();
            if (this.free < 0) {
                throw new IllegalArgumentException("capacity too small");
            }
        }

        static Region newFree(int capacity) {
            return new Region(capacity, new ArrayList<>());
        }

        static Region newFull(int fileId, int count) {
            return new Region(count, List.of(new Block(fileId, count)));
        }

        int getFree() {
            return free;
        }

        Block getSingle() {
            return this.blocks.get(0);
        }

        void addBlock(Block block) {
            this.blocks.add(block);
            this.free -= block.count;
            if (this.free < 0) {
                throw new IllegalArgumentException("free should not become negative");
            }
        }

        List<Block> getBlocks() {
            return this.blocks;
        }
    }

}
