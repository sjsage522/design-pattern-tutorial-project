package io.wisoft.shop.rank;

public class Platinum extends RankDecorator {
    public Platinum(Rank decoratedRank) {
        super(decoratedRank);
    }

    @Override
    public double getDiscountRate() {
        double rate = super.getDiscountRate();
        return platinumRankDiscountRate() + rate;
    }

    private double platinumRankDiscountRate() {
        return 3.0;
    }
}
