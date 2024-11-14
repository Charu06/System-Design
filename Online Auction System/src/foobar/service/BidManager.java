package foobar.service;
import foobar.entities.*;
import java.util.*;
public class BidManager {
    private Map<Buyer, Bid> bids;
    int user;
    public BidManager() {
        this.bids = new HashMap<>();
    }

    public void addBid(Bid bid) {
        bids.put(bid.getBuyer(), bid);
        user++;
    }

    public void updateBid(Buyer buyer, double amount) {
        if (bids.containsKey(buyer)) {
            bids.get(buyer).setAmount(amount);
        } else {
            System.out.println("Bid not found for buyer.");
        }
    }

    public boolean removeBid(Buyer buyer) {
        if (bids.containsKey(buyer)) {
            bids.remove(buyer);
            return true;
        } else {
            System.out.println("Bid not found for buyer.");
            return false;
        }
    }

    public boolean hasBid(Buyer buyer) {
        return bids.containsKey(buyer);
    }

    public int getTotalBids() {
        return bids.size();
    }

    public Bid getWinningBid() {
        Map<Double, List<Buyer>> amountToBuyers = new TreeMap<>(Collections.reverseOrder());
        for (Bid bid : bids.values()) {
            double amount = bid.getAmount();
            amountToBuyers.putIfAbsent(amount, new ArrayList<>());
            amountToBuyers.get(amount).add(bid.getBuyer());
        }

        for (Map.Entry<Double, List<Buyer>> entry : amountToBuyers.entrySet()) {
            List<Buyer> buyers = entry.getValue();
            if (buyers.size() == 1) {
                return new Bid(buyers.get(0), entry.getKey());
            } else {
                // Check for preferred buyers
                List<Buyer> preferredBuyers = new ArrayList<>();
                for (Buyer buyer : buyers) {
                    if (buyer.isPreferred()) {
                        preferredBuyers.add(buyer);
                    }
                }
                if (preferredBuyers.size() == 1) {
                    return new Bid(preferredBuyers.get(0), entry.getKey());
                } else if (preferredBuyers.size() > 1) {
                    // Tie between multiple preferred buyers, move to next highest bid
                    continue;
                }
            }
        }
        // No unique highest bid
        System.out.println("No unique highest bid.");
        return null;
    }
}
