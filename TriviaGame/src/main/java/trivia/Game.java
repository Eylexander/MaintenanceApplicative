package trivia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

// REFACTOR ME
public class Game implements IGame {
   ArrayList<Player> players = new ArrayList<Player>();
   int[] places = new int[6];
   int[] purses = new int[6];
   boolean[] inPenaltyBox = new boolean[6];
   Map<Category, LinkedList<String>> questions = new HashMap<>();

   Player currentPlayer;
   boolean isGettingOutOfPenaltyBox;

   public Game() {
      for (Category category : Category.values()) {
         LinkedList<String> categoryQuestions = new LinkedList<>();
         for (int i = 0; i < 50; i++) {
            categoryQuestions.add(category.getName() + " Question " + i);
         }
         questions.put(category, categoryQuestions);
      }
   }

   public boolean add(String playerName) {
      Player p = new Player(playerName);
      players.add(p);
      currentPlayer = players.get(0);

      System.out.println(playerName + " was added");
      System.out.println("They are player number " + players.size());
      return true;
   }

   public int getNbPlayers() {
      return players.size();
   }

   public void roll(int roll) {
      System.out.println(currentPlayer.getName() + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (currentPlayer.isInPenaltyBox()) {
         isGettingOutOfPenaltyBox = (roll % 2 != 0);
         System.out.println(currentPlayer.getName() + (isGettingOutOfPenaltyBox ? " is getting out of the penalty box" : " is not getting out of the penalty box"));

         if (!isGettingOutOfPenaltyBox) return;
      }

      currentPlayer.setPlace(((currentPlayer.getPlace() + roll - 1) % 12) + 1);

      System.out.println(currentPlayer.getName() + "'s new location is " + currentPlayer.getPlace());
      System.out.println("The category is " + currentCategory().getName());
      askQuestion();
   }

   private void askQuestion() {
      Category category = currentCategory();
      System.out.println(questions.get(category).removeFirst());
   }

   private Category currentCategory() {
      Category[] categories = Category.values();
      int nbCat = categories.length;
      int index = (currentPlayer.getPlace() + nbCat - 1) % nbCat;
      return categories[index];
   }

   public boolean handleCorrectAnswer() {
      if (currentPlayer.isInPenaltyBox() && !isGettingOutOfPenaltyBox) {
         nextPlayer();
         return true;
      }

      System.out.println("Answer was correct!!!!");
      currentPlayer.addPurse();
      System.out.println(currentPlayer.getName() + " now has " + currentPlayer.getPurse() + " Gold Coins.");

      boolean winner = didPlayerWin();
      nextPlayer();
      return winner;
   }

   private void nextPlayer() {
      currentPlayer = players.get((players.indexOf(currentPlayer) + 1) % players.size());
   }

   public boolean wrongAnswer() {
      System.out.println("Question was incorrectly answered");
      System.out.println(currentPlayer.getName() + " was sent to the penalty box");

      currentPlayer.setInPenaltyBox(true);
      nextPlayer();

      return true;
   }

   private boolean didPlayerWin() {
      return !(currentPlayer.getPurse() == 6);
   }
}
