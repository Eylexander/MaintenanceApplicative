package trivia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

//initialisation des questions avec Map
enum Category {
   POP("Pop"),
   SCIENCE("Science"),
   SPORTS("Sports"),
   ROCK("Rock");

   private final String name;

   Category(String name) {
      this.name = name;
   }

   public String getName() {
      return name;
   }
}

class Player {
   private final String name;
   private int place = 0;
   private int purse = 0;
   private boolean inPenaltyBox = false;

   public Player(String name) {
      this.name = name;
   }

   public String getName() {
      return name;
   }

}


// REFACTOR ME
public class Game implements IGame {
   ArrayList<Player> players = new ArrayList<Player>();
   int[] places = new int[6];
   int[] purses = new int[6];
   boolean[] inPenaltyBox = new boolean[6];
   Map<Category, LinkedList<String>> questions = new HashMap<>();

   int currentPlayer = 0;
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

   // A utiliser
   public boolean isPlayable() {
      return (getNbPlayers() >= 2);
   }

   public boolean add(String playerName) {
      places[getNbPlayers()] = 1;
      purses[getNbPlayers()] = 0;
      inPenaltyBox[getNbPlayers()] = false;
      Player p = new Player(playerName);
      players.add(p);

      System.out.println(playerName + " was added");
      System.out.println("They are player number " + players.size());
      return true;
   }

   public int getNbPlayers() {
      return players.size();
   }

   public void roll(int roll) {
      System.out.println(players.get(currentPlayer).getName() + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (inPenaltyBox[currentPlayer]) {
         isGettingOutOfPenaltyBox = (roll % 2 != 0);
         System.out.println(players.get(currentPlayer).getName() + (isGettingOutOfPenaltyBox ? " is getting out of the penalty box" : " is not getting out of the penalty box"));

         if (!isGettingOutOfPenaltyBox) return;
      }

      places[currentPlayer] = ((places[currentPlayer] + roll - 1) % 12) + 1;

      System.out.println(players.get(currentPlayer).getName() + "'s new location is " + places[currentPlayer]);
      System.out.println("The category is " + currentCategory().getName());
      askQuestion();
   }


   private void askQuestion() {
      Category category = currentCategory();
      System.out.println(questions.get(category).removeFirst());
   }

   private Category currentCategory() {
      Category[] categories = {Category.POP, Category.SCIENCE, Category.SPORTS, Category.ROCK};
      return categories[(places[currentPlayer] - 1 + 4) % 4];
   }




   public boolean handleCorrectAnswer() {
      if (inPenaltyBox[currentPlayer] && !isGettingOutOfPenaltyBox) {
         nextPlayer();
         return true;
      }

      System.out.println("Answer was correct!!!!");
      purses[currentPlayer]++;
      System.out.println(players.get(currentPlayer).getName() + " now has " + purses[currentPlayer] + " Gold Coins.");

      boolean winner = didPlayerWin();
      nextPlayer();
      return winner;
   }

   private void nextPlayer() {
      currentPlayer = (currentPlayer + 1) % players.size();
   }


   public boolean wrongAnswer() {
      System.out.println("Question was incorrectly answered");
      System.out.println(players.get(currentPlayer).getName() + " was sent to the penalty box");

      inPenaltyBox[currentPlayer] = true;
      nextPlayer();

      return true;
   }


   private boolean didPlayerWin() {
      return !(purses[currentPlayer] == 6);
   }
}
