public class TestDB {
    public static void main(String[] args) {
        try {
            System.out.println("Testing DB...");
            DatabaseHelper.initializeDatabase();
            System.out.println("DB Initialization SUCCESS!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
