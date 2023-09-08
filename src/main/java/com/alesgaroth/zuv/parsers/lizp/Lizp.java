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
        return new ZConstant(Integer.parseInt(input));
    }
    
}
