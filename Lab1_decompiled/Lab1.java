import java.util.Scanner;

class Lab1 {
    Lab1() {
    }

    public static void main(String[] arrstring) {
        Scanner scanner = new Scanner(System.in);
        ShopSimulation shopSimulation = new ShopSimulation(scanner);
        new Simulator((Simulation)shopSimulation).run();
        scanner.close();
    }
}