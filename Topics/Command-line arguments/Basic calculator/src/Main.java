
class Problem {
    public static void main(String[] args) {

        String[] parts = args;
        switch(parts[0]) {
            case "+":
                System.out.print(Integer.parseInt(parts[1])+Integer.parseInt(parts[2]));
                break;
            case "-":
                System.out.print(Integer.parseInt(parts[1])-Integer.parseInt(parts[2]));
                break;
            case "*":
                System.out.print(Integer.parseInt(parts[1])*Integer.parseInt(parts[2]));
                break;
            default:
                System.out.print("Unknown operator");
                break;
        }
    }
}