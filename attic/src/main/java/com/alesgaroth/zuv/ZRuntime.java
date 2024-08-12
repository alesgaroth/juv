package com.alesgaroth.zuv;

public class ZRuntime {
        public ZNode parse(String s) {
            switch (s.charAt(0)) {
                case '(': return parseFunction(s.substring(1));
                case '0': case '1': return new ZConstant(s.charAt(0) - '0');
            }
            return new ZConstant(s);
        }

        public ZNode parseFunction(String s) {
            ZNode op = null;
            switch (s.charAt(0)) {
                case '+': op = new ZAdd(); break;
                default: throw new RuntimeException("Unknown operator");
            }
            int input = 0;
            for (int k = 1; k < s.length(); k += 1) {
                switch (s.charAt(k)) {
                    case ' ': 
                        continue;
                    case ')':
                        return op;
                    case '0': case '1': {
                        ZConstant c = new ZConstant(s.charAt(k) - '0');
                        op.setInput(c.output(0), input);
                        input += 1;
                        break;
                    }
                }
            }
            throw new RuntimeException("The closing parenthises is missing");
        }

        static public String staticEval(String s) {
            ZRuntime self = new ZRuntime();
            String output = self.eval(s);
            return output;
        }

        public String eval(String s) {
            ZNode gn = parse(s);
            ZQueue q = getQueue();
            q.enqueue(gn);
            q.runTillEmpty();
            return gn.output(0).fetch(q).toString();
        }

     private ZQueue q = ZQueue.nullQueue;

     public ZQueue getQueue() {
        if (q == ZQueue.nullQueue) {
            q = new ZQueue();
        }
        
        return q;
    }
}