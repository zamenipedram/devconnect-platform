package com.palindromic;

import lombok.Data;

@Data
public class MinimumCharactersToMakePalindrome {


    public static int minCharsToAdd(String s) {
         ComputeLPSArray computeLPSArray;

        String reversed = new StringBuilder(s).reverse().toString();
        String concat = s + '#' + reversed;

        int [] lps = computeLPSArray(concat);
        return s.length() - lps[lps.length() - 1];

    }
}
