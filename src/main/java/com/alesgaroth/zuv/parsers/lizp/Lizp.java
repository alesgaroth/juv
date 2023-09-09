package com.alesgaroth.zuv.parsers.lizp;

import com.alesgaroth.zuv.ZConstant;
import com.alesgaroth.zuv.ZNode;
import com.alesgaroth.zuv.parsers.ZParser;

/** 
 * LIsp Zuv Parser
 */

public class Lizp implements ZParser {

    @Override
    public ZNode parse(String input) {
        char ch = input.charAt(0);
        if (Character.isDigit(ch)) {
          return new ZConstant(Integer.parseInt(input));
        } else if (ch == '"') {
          return new ZConstant(input.substring(1, input.length() -1 ));
        }
        return new ZConstant(input);
    }
    
}
