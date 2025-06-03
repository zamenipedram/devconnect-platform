package com.palindromic;

public class ExpandAroundCenter {

    static int expandAroundCenter(String s, int left, int right){

        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)){

            left--;
            right++;
        }
        return right - left - 1;
    }

}
