package foobar.service;
import foobar.entities.*;
public class ProfitCalculator {
    public double calculateProfitOrLoss(AuctionService auction, BidManager bidManager) {
        double winningBidAmount = 0.0;
        Bid winningBid = auction.getWinningBid();
        if (winningBid != null) {
            winningBidAmount = winningBid.getAmount();
        }
        int totalBids = bidManager.user;
        double participationShare = totalBids * auction.getParticipationCost() * 0.2;
        if(winningBidAmount!=0.0) {
            double avgBidLimit = (auction.getLowestBidLimit() + auction.getHighestBidLimit()) / 2;
            double profitOrLoss = winningBidAmount + participationShare - avgBidLimit;
            return profitOrLoss;
        }
        return participationShare;
    }
}