import java.util.*;

class Account {
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private List<String> transactionHistory;

    public Account(String accountNumber, String accountHolderName) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add("Deposited: $" + amount);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrew: $" + amount);
            return true;
        } else {
            System.out.println("Insufficient funds or invalid amount.");
            return false;
        }
    }

    public boolean transfer(Account targetAccount, double amount) {
        if (withdraw(amount)) {
            targetAccount.deposit(amount);
            transactionHistory.add("Transferred: $" + amount + " to " + targetAccount.getAccountNumber());
            targetAccount.transactionHistory.add("Received: $" + amount + " from " + this.accountNumber);
            return true;
        }
        return false;
    }

    public void viewTransactionHistory() {
        System.out.println("Transaction history for account " + accountNumber + ":");
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }

    public void updateAccountHolderName(String newName) {
        this.accountHolderName = newName;
        transactionHistory.add("Account holder name changed to: " + newName);
    }
}

public class OnlineBankingSystem {
    private static final Map<String, Account> accounts = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Online Banking System");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit Funds");
            System.out.println("3. Withdraw Funds");
            System.out.println("4. Transfer Money");
            System.out.println("5. View Transaction History");
            System.out.println("6. Manage Personal Information");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    depositFunds();
                    break;
                case 3:
                    withdrawFunds();
                    break;
                case 4:
                    transferMoney();
                    break;
                case 5:
                    viewTransactionHistory();
                    break;
                case 6:
                    managePersonalInformation();
                    break;
                case 7:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void createAccount() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter account holder name: ");
        String accountHolderName = scanner.nextLine();

        if (accounts.containsKey(accountNumber)) {
            System.out.println("Account already exists with this account number.");
        } else {
            Account newAccount = new Account(accountNumber, accountHolderName);
            accounts.put(accountNumber, newAccount);
            System.out.println("Account created successfully.");
        }
    }

    private static void depositFunds() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        Account account = accounts.get(accountNumber);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();  // Consume newline

        account.deposit(amount);
        System.out.println("Deposit successful.");
    }

    private static void withdrawFunds() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        Account account = accounts.get(accountNumber);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();  // Consume newline

        if (account.withdraw(amount)) {
            System.out.println("Withdrawal successful.");
        }
    }

    private static void transferMoney() {
        System.out.print("Enter source account number: ");
        String sourceAccountNumber = scanner.nextLine();
        Account sourceAccount = accounts.get(sourceAccountNumber);

        if (sourceAccount == null) {
            System.out.println("Source account not found.");
            return;
        }

        System.out.print("Enter target account number: ");
        String targetAccountNumber = scanner.nextLine();
        Account targetAccount = accounts.get(targetAccountNumber);

        if (targetAccount == null) {
            System.out.println("Target account not found.");
            return;
        }

        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();  // Consume newline

        if (sourceAccount.transfer(targetAccount, amount)) {
            System.out.println("Transfer successful.");
        }
    }

    private static void viewTransactionHistory() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        Account account = accounts.get(accountNumber);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        account.viewTransactionHistory();
    }

    private static void managePersonalInformation() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        Account account = accounts.get(accountNumber);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.print("Enter new account holder name: ");
        String newName = scanner.nextLine();
        account.updateAccountHolderName(newName);
        System.out.println("Account holder name updated.");
    }
}

