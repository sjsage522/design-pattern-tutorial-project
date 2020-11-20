package io.wisoft.shop.rank;

public class RankDecorator extends Rank {
    private final Rank decoratedRank;

    public RankDecorator(Rank decoratedRank) {
        this.decoratedRank = decoratedRank;
    }

    @Override
    public double getDiscountRate() {
        return decoratedRank.getDiscountRate();
    }
}
