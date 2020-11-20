package io.wisoft.shop.rank;

public class Gold extends RankDecorator {
    public Gold(Rank decoratedRank) {
        super(decoratedRank);
    }

    @Override
    public double getDiscountRate() {
        double rate = super.getDiscountRate();
        return goldRankDiscountRate() + rate;
    }

    private double goldRankDiscountRate() {
        return 2.0;
    }
}
