package de.libaurea.adventofcode.year2021;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import de.libaurea.adventofcode.common.Solution;

public class PacketDecoder extends Solution {
    public void solve() {
        String transmission = toBinary(input.get(0));
        Packet root = new Packet(transmission);
        LOGGER.info("Transmission: " + root.toString());
        LOGGER.info("Part 1: " + root.sumVersion());
        LOGGER.info("Part 2: " + root.value());
    }

    // Lazy, but effective
    private String toBinary(String hex) {
        String result = hex;
        result = result.replace("0", "0000");
        result = result.replace("1", "0001");
        result = result.replace("2", "0010");
        result = result.replace("3", "0011");
        result = result.replace("4", "0100");
        result = result.replace("5", "0101");
        result = result.replace("6", "0110");
        result = result.replace("7", "0111");
        result = result.replace("8", "1000");
        result = result.replace("9", "1001");
        result = result.replace("A", "1010");
        result = result.replace("B", "1011");
        result = result.replace("C", "1100");
        result = result.replace("D", "1101");
        result = result.replace("E", "1110");
        result = result.replace("F", "1111");
        return result;
    }

    public static void main(String... args) {
        LOGGER.info("=== Day 16 - Packet Decoder ===");
        LOGGER.info("--- Example ---");
        new PacketDecoder().with("2021/day16-example.txt").solve();
        LOGGER.info("--- Task ---");
        new PacketDecoder().with("2021/day16.txt").solve();
    }

    private class Packet {
        private static final int LITERAL = 4;
        private int version;
        private int type;
        private long value;
        private int used;
        private int subLength;
        private int subAmount;
        private List<Packet> sub;

        public Packet(String transmission) {
            version(transmission.substring(0, 3));
            type(transmission.substring(3, 6));
            sub = new ArrayList<>();
            LOGGER.debug("Transmission: " + version + " " + type + " " + transmission.substring(6));

            if (isLeaf()) {
                literal(transmission.substring(6));
            } else {
                if (transmission.charAt(6) == '0') {
                    this.subLength = Integer.parseInt(transmission.substring(7, 22), 2);
                    subByLength(transmission.substring(22));
                } else {
                    this.subAmount = Integer.parseInt(transmission.substring(7, 18), 2);
                    subByAmount(transmission.substring(18));
                }
            }
            LOGGER.debug("Read: " + toString());
        }

        public boolean isLeaf() {
            return this.type == LITERAL;
        }

        @Override
        public String toString() {
            String result = "";
            if (isLeaf()) {
                result += value;
            } else {
                StringJoiner join = new StringJoiner("");
                switch (type) {
                    case 0:
                        join = new StringJoiner(" + ");
                        break;
                    case 1:
                        join = new StringJoiner(" * ");
                        break;
                    case 2:
                        join = new StringJoiner(", ");
                        result += "min";
                        break;
                    case 3:
                        join = new StringJoiner(", ");
                        result += "max";
                        break;
                    case 5:
                        join = new StringJoiner(" > ");
                        break;
                    case 6:
                        join = new StringJoiner(" < ");
                        break;
                    case 7:
                        join = new StringJoiner(" == ");
                        break;
                    default:
                        break;
                }
                result += "(";
                for (Packet packet : sub) {
                    join.add(packet.toString());
                }
                result += join.toString() + ")";
            }
            return result;
        }

        public int sumVersion() {
            int result = version;
            if (!isLeaf()) {
                for (Packet s : sub) {
                    result += s.sumVersion();
                }
            }
            return result;
        }

        public long value() {
            long result = value;
            if (!isLeaf()) {
                switch (type) {
                    case 0:
                        for (Packet packet : sub) {
                            result += packet.value();
                        }
                        break;
                    case 1:
                        result = 1L;
                        for (Packet packet : sub) {
                            result *= packet.value();
                        }
                        break;
                    case 2:
                        result = sub.get(0).value();
                        for (Packet packet : sub) {
                            result = Math.min(result, packet.value());
                        }
                        break;
                    case 3:
                        result = sub.get(0).value();
                        for (Packet packet : sub) {
                            result = Math.max(result, packet.value());
                        }
                        break;
                    case 5:
                        result = sub.get(0).value() > sub.get(1).value() ? 1 : 0;
                        break;
                    case 6:
                        result = sub.get(0).value() < sub.get(1).value() ? 1 : 0;
                        break;
                    case 7:
                        result = sub.get(0).value() == sub.get(1).value() ? 1 : 0;
                        break;
                    default:
                        break;
                }
            }
            return result;
        }

        private void version(String bytes) {
            this.version = Integer.parseInt(bytes, 2);
        }

        private void type(String bytes) {
            this.type = Integer.parseInt(bytes, 2);
        }

        private void literal(String bytes) {
            String result = "";
            boolean next = true;
            for (int i = 0; i < bytes.length() && next; i += 5) {
                result += bytes.substring(i + 1, i + 5);
                if (bytes.charAt(i) == '0') {
                    next = false;
                    this.used = i + 5 + 6;
                }
            }
            this.value = Long.parseLong(result, 2);
        }

        private void subByLength(String bytes) {
            Packet next;
            int read = 0;
            while (read < subLength) {
                next = new Packet(bytes.substring(read));
                sub.add(next);
                read += next.used;
            }
            this.used = subLength + 7 + 15;
        }

        private void subByAmount(String bytes) {
            Packet next;
            int read = 0;
            for (int i = 0; i < subAmount; i++) {
                next = new Packet(bytes.substring(read));
                sub.add(next);
                read += next.used;
            }
            this.used = read + 7 + 11;
        }
    }
}
