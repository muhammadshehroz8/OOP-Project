import java.util.*;

class Auction {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Creating Auction and Lot objects
        Auction auction = new Auction();
        Lot lot = new Lot();
        
        // Create Bidder objects to collect bids
        List<Bidder> bidders = new ArrayList<>();
        
        // Run the auction process
        auction.startAuction(scanner, bidders, lot);
    }

    public void startAuction(Scanner scanner, List<Bidder> bidders, Lot lot) {
        int nft, n, sb;
        String confirmation;

        // Choose a lot
        nft = chooseLot(scanner);

        // Number of bids (minimum 3)
        System.out.print("Enter NUMBER OF BIDS: ");
        n = scanner.nextInt();
        if (n < 3) {
            System.out.println("Minimum number of bids must be 3.");
            n = 3;
        }

        // Starting bid (validate range)
        System.out.print("Enter OPENING BID: $");
        sb = scanner.nextInt();
        if (sb < 100) {
            System.out.println("Minimum opening bid must be $100.");
            sb = 100;
        } else if (sb > 100000000) {
            System.out.println("Maximum opening bid is $100 Million.");
            sb = 100000000;
        }

        // Ask for confirmation
        System.out.print("Are you sure about these entries? Y/N: ");
        confirmation = scanner.next();
        if (confirmation.equalsIgnoreCase("Y")) {
            System.out.println("Starting the auction...");
        } else {
            return;  // Exit auction process if not confirmed
        }

        // Collect bids
        int previousBid = sb;  // Initially set previous bid to the starting bid (sb)
        for (int i = 0; i < n; i++) {
            Bidder bidder = new Bidder();
            System.out.println("Enter information for Bidder " + (i + 1));

            // Collect bidder's information
            System.out.print("Name: ");
            bidder.setName(scanner.next());

            System.out.print("Bank Name: ");
            bidder.setBankName(scanner.next());

            System.out.print("Account Number: ");
            bidder.setAccountNumber(scanner.next());

            // Collect bid offer with validation
            int bid;
            while (true) {
                System.out.print("Bid Offer: $");
                bid = scanner.nextInt();
                if (bid <= previousBid) {
                    System.out.println("ERROR: Bid must be higher than the previous bid. Please enter a higher bid.");
                } else {
                    break;  // Valid bid, break out of the loop
                }
            }

            bidder.setBid(bid);  // Set the bid for this bidder
            bidders.add(bidder);  // Add the bidder to the list
            previousBid = bid;  // Update the previous bid for the next iteration
        }

        // After collecting all bids, process them and determine the winner
        Bidder highestBidder = getHighestBidder(bidders);

        // Print the details of the highest bidder
        printBidderDetails(highestBidder);

        // Ask if they want to auction another item
        System.out.print("Would you like to auction off another item? Y/N: ");
        confirmation = scanner.next();
        if (confirmation.equalsIgnoreCase("Y")) {
            startAuction(scanner, bidders, lot);  // Restart auction
        } else {
            System.out.println("Auction session has ended.");
        }
    }

    private int chooseLot(Scanner scanner) {
        int nft;
        do {
            System.out.println("Choose the lot:\n 1) DIAMOND NFT\n 2) RUBY NFT");
            System.out.print("ENTER LOT'S SERIAL NUMBER: ");
            nft = scanner.nextInt();
        } while (nft != 1 && nft != 2);
        return nft;
    }

    // Get the bidder with the highest bid
    private Bidder getHighestBidder(List<Bidder> bidders) {
        Bidder highestBidder = bidders.get(0);
        for (Bidder bidder : bidders) {
            if (bidder.getBid() > highestBidder.getBid()) {
                highestBidder = bidder;
            }
        }
        return highestBidder;
    }

    // Print the details of the highest bidder
    private void printBidderDetails(Bidder bidder) {
        int bid = bidder.getBid();
        float bill = bidder.calculateBill(bid);

        System.out.println("\n\nCongratulations " + bidder.getName() + "!");
        System.out.println("You are the highest bidder with a bid of $" + bid);
        System.out.println("\nBidder Details:");
        System.out.println("Name: " + bidder.getName());
        System.out.println("Bank Name: " + bidder.getBankName());
        System.out.println("Account Number: " + bidder.getAccountNumber());

        System.out.println("\nFinal Bill Details:");
        System.out.println("Hammer Price: $" + bid);
        System.out.println("Sales Tax (10%): $" + (bid * 0.1f));
        System.out.println("Buyer's Premium (5%): $" + (bid * 0.05f));
        System.out.println("Total Bill: $" + bill);
    }
}

class Bidder {
    private String name;
    private String bankName;
    private String accountNumber;
    private int bid;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    // Calculate the final bill with tax and premium
    public float calculateBill(int bid) {
        float salesTax = bid * 0.1f;
        float buyersPremium = bid * 0.05f;
        return bid + salesTax + buyersPremium;
    }
}

class Lot {
    public static void displayDiamond() {
        int r = 5;
        for (int i = 1; i <= r; i++) {
            for (int j = 1; j <= r - i; j++) System.out.print(" ");
            for (int j = 1; j <= (2 * i) - 1; j++) System.out.print("*");
            System.out.println();
        }
        for (int i = r - 1; i >= 1; i--) {
            for (int j = 1; j <= r - i; j++) System.out.print(" ");
            for (int j = 1; j <= 2 * i - 1; j++) System.out.print("*");
            System.out.println();
        }
    }

    public static void displayRuby() {
        int r = 5;
        for (int i = r - 1; i >= 1; i--) {
            for (int j = 1; j <= r - i; j++) System.out.print(" ");
            for (int j = 1; j <= 2 * i - 1; j++) System.out.print("*");
            System.out.println();
        }
    }
}
