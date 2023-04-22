import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BookFileManager bookFileManager = new BookFileManager();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter book name: ");
        String fileName = scanner.nextLine();

        bookFileManager.processBook(fileName);
    }

}
