package einez.practice.miscellaneous;

public class LastSubStrInLex {
    public static void main(String[] args) {
//        new LastSubStrInLex().lastSubstring("cacacb");
        new LastSubStrInLex().lastSubstring("vmjtxddvzmwrjvfamgpoowncslddrkjhchqswkamnsitrcmnhn");
    }

    public String lastSubstring(String ss) {
        char[] s = ss.toCharArray();
        int b1 = 0; // begin 1
        int b2 = 1; // begin 2
        int offset = 0;
        while (b1 + offset < s.length && b2 + offset < s.length) {
            if (s[b1 + offset] == s[b2 + offset]) {
                offset++;
                continue;
            }
            if (s[b1 + offset] < s[b2 + offset]) {
                b1 += offset + 1;
            } else {
                b2 += offset + 1;
            }
            if (b1 == b2) {
                b2++;
            }
            offset = 0;
        }
        int start = Math.min(b1, b2);
        return new String(s, start, s.length - start);
    }
}
