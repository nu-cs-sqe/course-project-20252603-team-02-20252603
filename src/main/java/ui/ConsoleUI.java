package ui;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import model.Card;
import model.CardType;
import model.Game;
import model.Player;

public class ConsoleUI {

  private static final int MAX_FUTURE_CARDS = 3;
  private static final int COMBO_SIZE_THREE = 3;
  private static final int COMBO_SIZE_FIVE = 5;

  private final Scanner scanner;
  private Game game;

  public ConsoleUI() {
    this.scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
  }

  public void start() {
    System.out.println("Enter number of players (3-5):");
    int numPlayers = Integer.parseInt(scanner.nextLine().trim());

    game = new Game(numPlayers, new Random());
    game.startGame();

    while (!game.isGameOver()) {
      playTurn();
    }

    System.out.println("Game Over!");
  }

  private void playTurn() {
    Player currentPlayer = game.getCurrentPlayer();

    if (!currentPlayer.isAlive() || currentPlayer.getTurnsOwed() == 0) {
      game.handleTurn();
      return;
    }

    System.out.println("\n--- Player " + game.getCurrentPlayerIndex() + "'s Turn ---");
    System.out.println("Turns owed: " + currentPlayer.getTurnsOwed());
    List<Card> hand = currentPlayer.getHand();

    for (int i = 0; i < hand.size(); i++) {
      System.out.println("[" + i + "] " + hand.get(i).getType());
    }

    System.out.println("Action: [number(s) separated by commas] to play, [D] to draw");
    String input = scanner.nextLine().trim().toUpperCase();

    if (input.equals("D")) {
      int defusesBefore = countDefuses(currentPlayer);
      game.drawCard();
      int defusesAfter = countDefuses(currentPlayer);

      if (!currentPlayer.isAlive()) {
        System.out.println("KABOOM! Player " + game.getCurrentPlayerIndex() + " exploded.");
      } else if (defusesAfter < defusesBefore) {
        System.out.println("\n*** PHEW! You drew an Exploding Kitten! ***");
        System.out.println("*** Your model auto-defused it and placed it back on top! ***");
      }
    } else {
      try {
        String[] parts = input.split(",");
        List<Card> cardsToPlay = new ArrayList<>();
        List<Integer> selectedIndices = new ArrayList<>();

        for (String part : parts) {
          int idx = Integer.parseInt(part.trim());
          if (selectedIndices.contains(idx)) {
            throw new IllegalArgumentException("Cannot select the same card twice.");
          }
          selectedIndices.add(idx);
          cardsToPlay.add(hand.get(idx));
        }

        if (cardsToPlay.size() == 1) {
          handleSingleCard(cardsToPlay.get(0));
        } else {
          handleMultiCard(cardsToPlay);
        }

      }
      catch (Exception e) {
        System.out.println("Invalid input or move (" + e.getMessage() + "). Try again.");
      }
    }
  }

  private void handleSingleCard(Card cardToPlay) {
    CardType type = cardToPlay.getType();

    if (type == CardType.NOSY) {
      System.out.println("Enter target Player ID to see their hand:");
      int targetId = Integer.parseInt(scanner.nextLine().trim());
      List<Card> targetHand = game.playNosy(targetId);
      System.out.println("Player " + targetId + "'s hand:");
      for (Card c : targetHand) {
        System.out.println("- " + c.getType());
      }
      return;
    }

    if (type == CardType.FAVOR) {
      System.out.println("Enter target Player ID:");
      int targetId = Integer.parseInt(scanner.nextLine().trim());
      Player target = game.getPlayers().get(targetId);

      System.out.println("Player " + targetId + ", choose a card to give (0-"
          + (target.getHand().size() - 1) + "):");
      for (int i = 0; i < target.getHand().size(); i++) {
        System.out.println("[" + i + "] " + target.getHand().get(i).getType());
      }
      int giveIndex = Integer.parseInt(scanner.nextLine().trim());
      Card givenCard = target.getHand().get(giveIndex);

      game.playCard(cardToPlay, target, givenCard);

    } else if (type == CardType.TARGETED_ATTACK || type == CardType.BLESSING) {
      System.out.println("Enter target Player ID:");
      int targetId = Integer.parseInt(scanner.nextLine().trim());
      Player target = game.getPlayers().get(targetId);

      game.playCard(cardToPlay, target);

    } else if (type == CardType.ALTER_FUTURE) {
      List<Card> drawPile = game.getDrawPile();
      int numCards = Math.min(MAX_FUTURE_CARDS, drawPile.size());
      List<Card> topCards = new ArrayList<>();

      System.out.println("Top cards are:");
      for (int i = 0; i < numCards; i++) {
        topCards.add(drawPile.get(i));
        System.out.println("[" + i + "] " + drawPile.get(i).getType());
      }

      System.out.println("Enter the new order of indices separated by commas (e.g. 2,0,1):");
      String[] orderParts = scanner.nextLine().trim().split(",");
      List<Card> reordered = new ArrayList<>();
      for (String orderPart : orderParts) {
        reordered.add(topCards.get(Integer.parseInt(orderPart.trim())));
      }

      game.playCard(cardToPlay, reordered);

    } else {
      game.playCard(cardToPlay);
    }

    handleNopePhase();
    List<Card> resolvedResult = game.resolvePendingAction();

    if (type == CardType.SEE_THE_FUTURE && !resolvedResult.isEmpty()) {
      System.out.println("The future holds:");
      for (Card c : resolvedResult) {
        System.out.println("- " + c.getType());
      }
    }
  }

  private void handleMultiCard(List<Card> cardsToPlay) {
    int size = cardsToPlay.size();

    if (size == COMBO_SIZE_THREE && cardsToPlay.get(0).getType() == CardType.NEKO) {
      game.playCard(cardsToPlay);
    } else if (size == 2) {
      System.out.println("Enter target Player ID to steal a random card:");
      int targetId = Integer.parseInt(scanner.nextLine().trim());
      Player target = game.getPlayers().get(targetId);
      game.playCard(cardsToPlay, target, null);
    } else if (size == COMBO_SIZE_THREE) {
      System.out.println("Enter target Player ID:");
      int targetId = Integer.parseInt(scanner.nextLine().trim());
      Player target = game.getPlayers().get(targetId);
      System.out.println("Enter exact CardType you want to demand (e.g., DEFUSE, TACOCAT):");
      CardType named = CardType.valueOf(scanner.nextLine().trim().toUpperCase());
      game.playCard(cardsToPlay, target, named);
    } else if (size == COMBO_SIZE_FIVE) {
      System.out.println("Enter exact CardType you want from the discard pile:");
      CardType named = CardType.valueOf(scanner.nextLine().trim().toUpperCase());
      game.playCard(cardsToPlay, null, named);
    } else {
      throw new IllegalArgumentException("Invalid multi-card combo size.");
    }

    handleNopePhase();
    game.resolvePendingAction();
  }

  private int countDefuses(Player p) {
    int count = 0;
    for (Card c : p.getHand()) {
      if (c.getType() == CardType.DEFUSE) {
        count++;
      }
    }
    return count;
  }

  private void handleNopePhase() {
    boolean acceptingNopes = true;

    while (acceptingNopes) {
      System.out.println("Does anyone want to play a NOPE card? "
          + "Enter Player ID, or 'N' to skip:");
      String input = scanner.nextLine().trim().toUpperCase();

      if (input.equals("N")) {
        acceptingNopes = false;
      } else {
        try {
          int playerId = Integer.parseInt(input);
          Player p = game.getPlayers().get(playerId);

          Card nopeCard = null;
          for (Card c : p.getHand()) {
            if (c.getType() == CardType.NOPE) {
              nopeCard = c;
              break;
            }
          }

          if (nopeCard != null) {
            game.playNope(p, nopeCard);
            System.out.println("NOPE played automatically for Player "
                + playerId + "!");
          } else {
            System.out.println("Player " + playerId
                + " does not have a NOPE card. Try again.");
          }
        }
        catch (Exception e) {
          System.out.println("Invalid Nope attempt. Resuming...");
          acceptingNopes = false;
        }
      }
    }
  }

  public static void main(String[] args) {
    new ConsoleUI().start();
  }
}