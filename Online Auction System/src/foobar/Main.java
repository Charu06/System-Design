package foobar;
import foobar.controller.*;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
//C
public class Main {
    public static void main(String[] args) {
        UserController userController = new UserController();
        AuctionController auctionController = new AuctionController(userController);
        BidController bidController = new BidController(userController, auctionController);

        // Test Case 1
        System.out.println("Test Case 1:");
        userController.addBuyer("buyer1");
        userController.addBuyer("buyer2");
        userController.addBuyer("buyer3");
        userController.addSeller("seller1");
        auctionController.createAuction("A1", 10, 50, 1, "seller1");
        bidController.createOrUpdateBid("buyer1", "A1", 17);
        bidController.createOrUpdateBid("buyer2", "A1", 15);
        bidController.createOrUpdateBid("buyer2", "A1", 19);
        bidController.createOrUpdateBid("buyer3", "A1", 19);
        auctionController.closeAuction("A1"); // Should give Buyer1 as winner
        auctionController.getProfitOrLoss("seller1", "A1"); // Expected Profit/Loss

        System.out.println("\nTest Case 2:");
        // Test Case 2
        userController.addSeller("seller2");
        auctionController.createAuction("A2", 5, 20, 2, "seller2");
        bidController.createOrUpdateBid("buyer3", "A2", 25); // Should fail due to bid limit
        bidController.createOrUpdateBid("buyer2", "A2", 5);
        bidController.withdrawBid("buyer2", "A2");
        auctionController.closeAuction("A2"); // No winner
        auctionController.getProfitOrLoss("seller2", "A2"); // Expected Profit/Loss

        // Additional test to demonstrate preferred buyer
        System.out.println("\nAdditional Test for Preferred Buyer:");
        auctionController.createAuction("A3", 10, 100, 5, "seller1");
        auctionController.createAuction("A4", 110, 200, 5, "seller1");
        bidController.createOrUpdateBid("buyer1", "A3", 70); // buyer1 has participated in 2 auctions so far
        bidController.createOrUpdateBid("buyer2", "A3", 70); // buyer2 has participated in 2 auctions so far
        bidController.createOrUpdateBid("buyer3", "A3", 70); // buyer3 has participated in 2 auctions so far
        bidController.createOrUpdateBid("buyer2", "A4", 115); // buyer2 has participated in 2 auctions so far
        bidController.createOrUpdateBid("buyer3", "A4", 135); // buyer3 has participated in 2 auctions so far
        auctionController.closeAuction("A3"); // Should give Buyer1 as winner due to preferred status
        auctionController.closeAuction("A4");
        auctionController.getProfitOrLoss("seller1", "A3");
        auctionController.getProfitOrLoss("seller1", "A4");
    }
}
