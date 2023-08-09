package numbers;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to Amazing Numbers!\n" +
                "\n" +
                "Supported requests:\n" +
                "- enter a natural number to know its properties;\n" +
                "- enter two natural numbers to obtain the properties of the list:\n" +
                "  * the first parameter represents a starting number;\n" +
                "  * the second parameter shows how many consecutive numbers are to be processed;\n" +
                "- two natural numbers and properties to search for;\n" +
                "- a property preceded by minus must not be present in numbers;\n" +
                "- separate the parameters with one space;\n" +
                "- enter 0 to exit.\n");

        boolean exit = false;
        while(!exit) {
            System.out.println("\nEnter a request:");
            String input = scan.nextLine();
            String[] inputs = input.split(" ");
            if (inputs.length == 1) {
                long a = Long.parseLong(inputs[0]);
                switch (isNatural(a)) {
                    case 1:
                        System.out.printf("Properties of %d%n", a);
                        System.out.printf(" even: %s%n odd: %s%n", isEven(a), !isEven(a));
                        System.out.printf(" buzz: %s%n", isBuzzNumber(a));
                        System.out.printf(" duck: %s%n", isDuckNumber(a));
                        System.out.printf(" palindromic: %s%n", isPalindromic(a));
                        System.out.printf(" gapful: %s%n", isGapful(a));
                        System.out.printf(" spy: %s%n", isSpy(a));
                        System.out.printf(" sunny: %s%n", isSunny(a));
                        System.out.printf(" square: %s%n", isSquare(a));
                        System.out.printf(" jumping: %s%n", isJumping(a));
                        System.out.printf(" happy: %s%n", isHappy(a));
                        System.out.printf(" sad: %s%n", !isHappy(a));
                        break;
                    case -1:
                        System.out.println("The first parameter should be a natural number or zero.");
                        break;
                    case 0:
                        System.out.println("\nGoodbye!");
                        exit = true;
                        break;
                }
            } else {
                long from = Long.parseLong(inputs[0]);
                long to = Long.parseLong(inputs[1]);
                if (!(isNatural(from) == 1 && isNatural(to) == 1)) {
                    switch (isNatural(from)) {
                        case -1:
                            System.out.println("The first parameter should be a natural number or zero.");
                            break;
                        case 0:
                            System.out.println("\nGoodbye!");
                            exit = true;
                            break;
                    }
                    switch (isNatural(to)) {
                        case -1:
                            System.out.println("The second parameter should be a natural number or zero.");
                            break;
                        case 0:
                            System.out.println("\nGoodbye!");
                            exit = true;
                            break;
                    }
                } else if (inputs.length == 2) {
                    for (long i = from; i < from + to; i++) {
                        printProperties(i);
                    }
                } else {
                    String[] props = new String[inputs.length - 2];
                    int countBad = 0;
                    String bad = "";
                    int indBad = 0;
                    for (int i = 2; i < inputs.length; i++) {
                        props[i-2] = inputs[i].toLowerCase();
                        if (!isValidProperty(props[i-2] )){
                            countBad++;
                            indBad = i-2;
                            bad += "" + inputs[i] + ", ";
                        }
                    }
                    if (countBad == 0) {
                        fillFilter(props);
                        //System.out.println("here");
                        if (isNotIntersectingProperties()) {
                            //System.out.println("here");
                            filterBy(from, to);
                        }
                    } else {
                        if (countBad == 1) {
                            System.out.println("The property [" + props[indBad].toUpperCase() +"] is wrong.");
                        } else {
                            System.out.println("The properties [" + bad +"] are wrong.");
                        }
                        System.out.println("Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD]");
                    }
                }
            }

        }
    }

    public static ArrayList<Integer> filterBy = new ArrayList<>();
    public static ArrayList<Integer> filterByNot = new ArrayList<>();

    public static HashMap<String, Integer> myMap = createMap();

    private static HashMap<String, Integer> createMap() {
        HashMap<String,Integer> myMap = new HashMap<>();
        myMap.put("even", 0);
        myMap.put("odd", 1);
        myMap.put("buzz", 2);
        myMap.put("duck", 3);
        myMap.put("palindromic", 4);
        myMap.put("gapful", 5);
        myMap.put("spy", 6);
        myMap.put("square", 7);
        myMap.put("sunny", 8);
        myMap.put("jumping", 9);
        myMap.put("sad", 10);
        myMap.put("happy", 11);
        return myMap;
    }

    public static HashMap<String, Integer> myMapNot = createMap1();

    private static HashMap<String, Integer> createMap1() {
        HashMap<String,Integer> myMapNot = new HashMap<>();
        myMapNot.put("-even", 0);
        myMapNot.put("-odd", 1);
        myMapNot.put("-buzz", 2);
        myMapNot.put("-duck", 3);
        myMapNot.put("-palindromic", 4);
        myMapNot.put("-gapful", 5);
        myMapNot.put("-spy", 6);
        myMapNot.put("-square", 7);
        myMapNot.put("-sunny", 8);
        myMapNot.put("-jumping", 9);
        myMapNot.put("-sad", 10);
        myMapNot.put("-happy", 11);
        return myMapNot;
    }

    public static void fillFilter(String[] p) {
        filterBy = new ArrayList<>();
        filterByNot = new ArrayList<>();
        for (String pr : p) {
            if (myMap.containsKey(pr)) {
                if (!filterBy.contains(myMap.get(pr))){
                    filterBy.add(myMap.get(pr));
                }
            }
            if (myMapNot.containsKey(pr)) {
                if (!filterByNot.contains(myMapNot.get(pr))){
                    filterByNot.add(myMapNot.get(pr));
                }
            }
        }
    }
    public static void printProperties(long i) {
        String response = "";
        response += isBuzzNumber(i) ? "buzz, " : "";
        response += isDuckNumber(i) ? "duck, " : "";
        response += isPalindromic(i) ? "palindromic, " : "";
        response += isGapful(i) ? "gapful, " : "";
        response += isSpy(i) ? "spy, " : "";
        response += isSunny(i) ? "sunny, " : "";
        response += isSquare(i) ? "square, " : "";
        response += isJumping(i) ? "jumping, " : "";
        response += isHappy(i) ? "happy, " : "sad, ";
        response += isEven(i) ? "even" : "odd";
        System.out.println(i + " is " + response);
    }

    public static void filterBy(long from, long to) {
        while ( to > 0) {
            //System.out.println("checking " + from);
            boolean[] curProperties = new boolean[12];
            Arrays.fill(curProperties, false);
            curProperties[0] = isEven(from);
            curProperties[1] = !curProperties[0];
            curProperties[2] = isBuzzNumber(from);
            curProperties[3] = isDuckNumber(from);
            curProperties[4] = isPalindromic(from);
            curProperties[5] = isGapful(from);
            curProperties[6] = isSpy(from);
            curProperties[7] = isSquare(from);
            curProperties[8] = isSunny(from);
            curProperties[9] = isJumping(from);
            curProperties[10] = !isHappy(from);
            curProperties[11] = !curProperties[10];

            boolean ok = true;
            for ( int i = 0; i < filterBy.size(); i++) {
                if (!curProperties[filterBy.get(i)]) {
                    ok = false;
                    break;
                }
            }
            for ( int i = 0; i < filterByNot.size(); i++) {
                if (curProperties[filterByNot.get(i)]) {
                    ok = false;
                    break;
                }
            }
            if (ok) {
                printProperties(from);
                to--;
            }
            from++;

        }
    }

    public static boolean isValidProperty(String property) {
        return (property.matches("-?even") ||
                property.matches("-?odd") ||
                property.matches("-?buzz") ||
                property.matches("-?duck") ||
                property.matches("-?sunny") ||
                property.matches("-?square") ||
                property.matches("-?jumping") ||
                property.matches("-?palindromic") ||
                property.matches("-?gapful") ||
                property.matches("-?sad") ||
                property.matches("-?happy") ||
                property.matches("-?spy") );
    }

    public static boolean isNotIntersectingProperties() {
        if (filterBy.contains(0) && filterBy.contains(1)) {
            System.out.println("The request contains mutually exclusive properties: [ODD, EVEN]");
            return false;
        }
        if (filterByNot.contains(0) && filterByNot.contains(1)) {
            System.out.println("The request contains mutually exclusive properties: [ODD, EVEN]");
            return false;
        }
        if (filterBy.contains(7) && filterBy.contains(8)) {
            System.out.println("The request contains mutually exclusive properties: [SQUARE, SUNNY]");
            return false;
        }
        if (filterBy.contains(3) && filterBy.contains(6)) {
            System.out.println("The request contains mutually exclusive properties: [DUCK, SPY]");
            return false;
        }
        for (int i =0 ; i < 12; i++) {
            if (filterBy.contains(i) && filterByNot.contains(i)) {
                String match = "";
                for (Map.Entry<String,Integer> e : myMap.entrySet()) {
                    if (e.getValue() == i) {
                        match = e.getKey().toUpperCase();
                        break;
                    }
                }
                System.out.println("The request contains mutually exclusive properties: [" + match + ", -" + match + "]" );
                return false;
            }
        }

        return true;
    }

    public static int isNatural(long a) {
        if (a == 0) {
            return 0;
        } else {
            if (a < 0) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    public static boolean isEven(long a) { return a % 2 == 0; }

    public static boolean isBuzzNumber(long a) {
        return a % 7 == 0 || a % 10 == 7;
    }

    public static boolean isDuckNumber(long a) {
        while(a > 0) {
            if (a % 10 == 0){
                return true;
            }
            a = a / 10;
        }
        return false;
    }

    public static boolean isPalindromic(long a) {
        String s = Long.toString(a);
        for (int i = 0; i < s.length() / 2;  i++) {
            if (s.charAt(i) != s.charAt(s.length() - i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isGapful(long a) {
        String s = Long.toString(a);
        String d = "" + s.charAt(0) + s.charAt(s.length()-1);
        long b = Long.parseLong(d);
        return (s.length() > 2 && a%b == 0);
    }

    public static boolean isSpy(long a) {
        String s = Long.toString(a);
        long sum = 0;
        long mult = 1;
        for (int i = 0; i < s.length(); i++) {
            long temp = Long.parseLong("" + s.charAt(i));
            sum += temp;
            mult *= temp;
        }
        return sum == mult;
    }

    public static boolean isSquare(long a) {
        long b =(long) Math.sqrt(a);
        return b*b == a;
    }

    public static boolean isSunny(long a) { return isSquare(a+1); }

    public static boolean isJumping(long a) {
        long carry = (a % 10) - 1;
        //System.out.println(carry);
        while( a > 0 ) {
            if (Math.abs((a % 10) - carry) != 1) {
                return false;
            } else {
                carry = a % 10;
                a /= 10;
            }
        }
        return true;
    }

    public static boolean isHappy(long a) {
       // long start = a;
        if (a == 1) return true;
        HashSet<Long> nums = new HashSet<>();
        nums.add(a);
        while (a != 1) {
           // System.out.println(a);
            long buff = 0;
            while (a != 0) {
                buff += (a % 10) * (a % 10);
                a /= 10;
            }
            if (nums.contains(buff)) {
                a = 1;
                return false;
            }
            nums.add(buff);
            a = buff;
        }
        return true;
    }

}
