package ui;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
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
  private Locale locale;

  public ConsoleUI(Locale locale) {
    this.scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
    this.locale = locale;
  }

  public void start() {
    ResourceBundle labels = ResourceBundle.getBundle("labels", locale);
    System.out.println(labels.getString("promptNumPlayers"));
    int numPlayers = Integer.parseInt(scanner.nextLine().trim());

    game = new Game(numPlayers, new Random());
    game.startGame();

    while (!game.isGameOver()) {
      playTurn();
    }

    System.out.println(labels.getString("gameOver"));
    List<Player> allPlayers = game.getPlayers();
    for (int i = 0; i < allPlayers.size(); i++) {
      if (allPlayers.get(i).isAlive()) {
        System.out.println(MessageFormat.format(labels.getString("gameWinner"), i));
        break;
      }
    }
  }

  private void playTurn() {
    ResourceBundle labels = ResourceBundle.getBundle("labels", locale);
    Player currentPlayer = game.getCurrentPlayer();

    if (!currentPlayer.isAlive() || currentPlayer.getTurnsOwed() == 0) {
      game.handleTurn();
      return;
    }

    System.out.println(
            MessageFormat.format(labels.getString("turnHeader"), game.getCurrentPlayerIndex()));
    System.out.println(
            MessageFormat.format(labels.getString("turnTurnsOwed"), currentPlayer.getTurnsOwed()));
    List<Card> hand = currentPlayer.getHand();

    for (int i = 0; i < hand.size(); i++) {
      System.out.println("[" + i + "] " + hand.get(i).getType());
    }

    System.out.println(labels.getString("turnAction"));
    String input = scanner.nextLine().trim().toUpperCase();

    if (input.equals("D")) {
      List<Card> drawPile = game.getDrawPile();
      boolean willDefuse = !drawPile.isEmpty()
              && drawPile.get(0).getType() == CardType.EXPLODING_KITTEN
              && currentPlayer.hasDefuse();

      int position = 0;
      if (willDefuse) {
        System.out.println(labels.getString("turnDefused"));
        int maxPos = drawPile.size() - 1;
        System.out.println(MessageFormat.format(labels.getString("turnDefusedPlace"), maxPos));
        position = Integer.parseInt(scanner.nextLine().trim());
      }

      game.drawCard(position);

      if (!currentPlayer.isAlive()) {
        String msg = MessageFormat.format(
                labels.getString("turnExploded"), game.getCurrentPlayerIndex());
        System.out.println(msg);
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
        System.out.println(
                MessageFormat.format(labels.getString("errorInvalidMove"), e.getMessage()));
      }
    }
  }

  private void handleSingleCard(Card cardToPlay) {
    ResourceBundle labels = ResourceBundle.getBundle("labels", locale);
    CardType type = cardToPlay.getType();

    if (type == CardType.NOSY) {
      System.out.println(labels.getString("cardNosyPrompt"));
      int targetId = Integer.parseInt(scanner.nextLine().trim());
      List<Card> targetHand = game.playNosy(targetId);
      System.out.println(MessageFormat.format(labels.getString("cardNosyResult"), targetId));
      for (Card c : targetHand) {
        System.out.println("- " + c.getType());
      }
      return;
    }

    if (type == CardType.FAVOR) {
      System.out.println(labels.getString("cardFavorPrompt"));
      int targetId = Integer.parseInt(scanner.nextLine().trim());
      Player target = game.getPlayers().get(targetId);

      System.out.println(MessageFormat.format(labels.getString("cardFavorChoose"),
              targetId, target.getHand().size() - 1));
      for (int i = 0; i < target.getHand().size(); i++) {
        System.out.println("[" + i + "] " + target.getHand().get(i).getType());
      }
      int giveIndex = Integer.parseInt(scanner.nextLine().trim());
      Card givenCard = target.getHand().get(giveIndex);

      game.playCard(cardToPlay, target, givenCard);

    } else if (type == CardType.TARGETED_ATTACK || type == CardType.BLESSING) {
      System.out.println(labels.getString("cardTargetPrompt"));
      int targetId = Integer.parseInt(scanner.nextLine().trim());
      Player target = game.getPlayers().get(targetId);

      game.playCard(cardToPlay, target);

    } else if (type == CardType.ALTER_FUTURE) {
      List<Card> drawPile = game.getDrawPile();
      int numCards = Math.min(MAX_FUTURE_CARDS, drawPile.size());
      List<Card> topCards = new ArrayList<>();

      System.out.println(labels.getString("cardAlterFutureTop"));
      for (int i = 0; i < numCards; i++) {
        topCards.add(drawPile.get(i));
        System.out.println("[" + i + "] " + drawPile.get(i).getType());
      }

      System.out.println(labels.getString("cardAlterFutureReorder"));
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
      System.out.println(labels.getString("cardFutureResult"));
      for (Card c : resolvedResult) {
        System.out.println("- " + c.getType());
      }
    }
  }

  private void handleMultiCard(List<Card> cardsToPlay) {
    ResourceBundle labels = ResourceBundle.getBundle("labels", locale);
    int size = cardsToPlay.size();

    if (size == COMBO_SIZE_THREE && cardsToPlay.get(0).getType() == CardType.NEKO) {
      game.playCard(cardsToPlay);
    } else if (size == 2) {
      System.out.println(labels.getString("comboTwoPrompt"));
      int targetId = Integer.parseInt(scanner.nextLine().trim());
      Player target = game.getPlayers().get(targetId);
      game.playCard(cardsToPlay, target, null);
    } else if (size == COMBO_SIZE_THREE) {
      System.out.println(labels.getString("comboThreePrompt"));
      int targetId = Integer.parseInt(scanner.nextLine().trim());
      Player target = game.getPlayers().get(targetId);
      System.out.println(labels.getString("comboThreeDemand"));
      CardType named = CardType.valueOf(scanner.nextLine().trim().toUpperCase());
      game.playCard(cardsToPlay, target, named);
    } else if (size == COMBO_SIZE_FIVE) {
      System.out.println(labels.getString("comboFiveDemand"));
      CardType named = CardType.valueOf(scanner.nextLine().trim().toUpperCase());
      game.playCard(cardsToPlay, null, named);
    } else {
      throw new IllegalArgumentException(labels.getString("comboInvalid"));
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
    ResourceBundle labels = ResourceBundle.getBundle("labels", locale);
    boolean acceptingNopes = true;

    while (acceptingNopes) {
      System.out.println(labels.getString("nopePrompt"));
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
            System.out.println(MessageFormat.format(labels.getString("nopePlayed"), playerId));
          } else {
            System.out.println(MessageFormat.format(labels.getString("nopeNoCard"), playerId));
          }
        }
        catch (Exception e) {
          System.out.println(labels.getString("nopeInvalid"));
          acceptingNopes = false;
        }
      }
    }
  }
}