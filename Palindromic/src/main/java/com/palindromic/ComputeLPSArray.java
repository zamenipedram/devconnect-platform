package com.palindromic;

public class ComputeLPSArray {

    private static int [] computeLPSArray (String s) {

        int [] lps = new int[s.length()];
        int len = 0;
        int i = 1;

        while (i < s.length()) {
            if (s.charAt(i) == s.charAt(len)) {
                lps[i++] = ++len;
            }
            else  {
                if (len!=0){
                    len = lps[len-1];
                }
                else  {
                    lps[i++] = 0;
                }
            }
        }

        return lps;
    }
}
