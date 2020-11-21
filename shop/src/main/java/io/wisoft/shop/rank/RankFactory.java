package io.wisoft.shop.rank;

public class RankFactory {
    public Rank getRank(final String rankId) {
        if (rankId.equalsIgnoreCase("SILVER")) {
            return new Silver(new None());
        } else if (rankId.equalsIgnoreCase("GOLD")) {
            return new Gold(new Silver(new None()));
        } else if (rankId.equalsIgnoreCase("PLATINUM")) {
            return new Platinum(new Gold(new Silver(new None())));
        } else {
            return new None();
        }
    }
}
