import java.util.*;

public class SelfPlayedGame {
    private Deck deck;
    private Player player1;
    private Player player2;
    private int player1Score;
    private int player2Score;

    public SelfPlayedGame(String player1Name, String player2Name) {
        // 创建并洗牌
        deck = new Deck();
        deck.shuffle();
        // 创建玩家
        player1 = new Player(player1Name, 20); // 年龄设为默认值
        player2 = new Player(player2Name, 20);
        player1Score = 0;
        player2Score = 0;
    }

    /**
     * 给玩家发牌
     */
    public void dealCards() {
        // 每个玩家发5张牌
        for (int i = 0; i < 5; i++) {
            player1.draw(deck);
            player2.draw(deck);
        }
    }

    /**
     * 进行一轮游戏
     * @param round 当前轮数
     */
    public void playRound(int round) {
        System.out.println("\n--- Round " + round + " ---");
        // 玩家1打出最高价值的牌
        Card player1Card = player1.getHighestValueCard();
        player1.removeCardFromHand(player1Card);
        // 玩家2打出最高价值的牌
        Card player2Card = player2.getHighestValueCard();
        player2.removeCardFromHand(player2Card);
        System.out.println(player1.getName() + " plays: " + player1Card + " (Value: " + player1Card.getValue() + ")");
        System.out.println(player2.getName() + " plays: " + player2Card + " (Value: " + player2Card.getValue() + ")");
        // 比较牌的价值
        if (player1Card.getValue() > player2Card.getValue()) {
            player1Score++;
            System.out.println(player1.getName() + " wins this round!");
        } else if (player2Card.getValue() > player1Card.getValue()) {
            player2Score++;
            System.out.println(player2.getName() + " wins this round!");
        } else {
            System.out.println("It's a tie! No points awarded.");
        }
        System.out.println("Current Score: " + player1.getName() + " " + player1Score + " - " +
                player2.getName() + " " + player2Score);
    }
    /**
     * 显示最终结果
     */
    public void displayResults() {
        System.out.println("\n=== FINAL RESULTS ===");
        System.out.println(player1.getName() + ": " + player1Score + " points");
        System.out.println(player2.getName() + ": " + player2Score + " points");
        if (player1Score > player2Score) {
            System.out.println(player1.getName() + " WINS THE GAME!");
        } else if (player2Score > player1Score) {
            System.out.println(player2.getName() + " WINS THE GAME!");
        } else {
            System.out.println("THE GAME IS A TIE!");
        }
    }
    /**
     * 运行游戏
     */
    public void playGame() {
        System.out.println("Starting High Card Game!");
        System.out.println("Players: " + player1.getName() + " vs " + player2.getName());
        // 发牌
        dealCards();
        System.out.println("\nCards have been dealt:");
        System.out.println(player1.getName() + "'s hand: " + player1);
        System.out.println(player2.getName() + "'s hand: " + player2);
        // 进行5轮游戏
        for (int round = 1; round <= 5; round++) {
            playRound(round);
        }
        // 显示最终结果
        displayResults();
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to High Card Game!");
        // 获取玩家1名称
        System.out.print("Enter Player 1 name: ");
        String player1Name = scanner.nextLine().trim();
        if (player1Name.isEmpty()) {
            player1Name = "Player 1";
        }
        // 获取玩家2名称
        System.out.print("Enter Player 2 name: ");
        String player2Name = scanner.nextLine().trim();
        if (player2Name.isEmpty()) {
            player2Name = "Player 2";
        }
        // 创建并运行游戏
        SelfPlayedGame game = new SelfPlayedGame(player1Name, player2Name);
        game.playGame();

        scanner.close();
    }
}