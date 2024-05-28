public class Main {
    public static void main(String[] args) {
        BillCalculator calculator = new BillCalculator();

        calculator.askNumberOfPeople();
        calculator.addItems();
        calculator.printBill();
    }
}

