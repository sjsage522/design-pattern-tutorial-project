package io.wisoft.shop.rank;

public class Silver extends RankDecorator {
    public Silver(final Rank decoratedRank) {
        super(decoratedRank);
    }

    @Override
    public double getDiscountRate() {
        double rate = super.getDiscountRate();
        return silverRankDiscountRate() + rate;
    }

    private double silverRankDiscountRate() {
        return 1.0;
    }
}
