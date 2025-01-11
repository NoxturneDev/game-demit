package main;

public class Leaderboard {
    GamePanel gp;

    public Player FIRST_PLACE;
    public Player SECOND_PLACE;
    public Player THIRD_PLACE;
    public Player FOURTH_PLACE;
    public Player FIFTH_PLACE;

    public Leaderboard(GamePanel gp) {
        this.gp = gp;
    }

    public void addRecord(Player player) {
        if (FIRST_PLACE == null) {
            FIRST_PLACE = player;
            return;
        }
        // Update leaderboard
        if (player.totalScore < FIFTH_PLACE.totalScore) {
            return;
        } else if (player.totalScore < FOURTH_PLACE.totalScore) {
            FIFTH_PLACE = player;
        } else if (player.totalScore < THIRD_PLACE.totalScore) {
            FOURTH_PLACE = player;
        } else if (player.totalScore < SECOND_PLACE.totalScore) {
            THIRD_PLACE = player;
        } else if (player.totalScore < FIRST_PLACE.totalScore) {
            SECOND_PLACE = player;
        } else {
            FIRST_PLACE = player;
        }
    }
}
