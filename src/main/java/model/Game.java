package model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game {
  private static final int MIN_PLAYERS = 3;
  private static final int MAX_PLAYERS = 5;
  private static final int TWO_CAT_COMBO_SIZE = 2;
  private static final int THREE_CAT_COMBO_SIZE = 3;
  private static final int FIVE_CAT_COMBO_SIZE = 5;
  private static final int NUM_NEKO_CARDS = 3;

  private final int numberOfPlayers;
  private final Random random;
  private List<Player> players;
  private Deck deck;
  private boolean gameLaunched;
  private boolean gameOver;
  private int currentPlayerIndex;

  private ActionType pendingAction = ActionType.NONE;
  private int nopeCount = 0;

  private Player pendingTarget;
  private Card pendingGivenCard;
  private CardType pendingWantedCardType;
  private List<Card> pendingCardList;

  public Game(int numberOfPlayers, Random random) {
    this.numberOfPlayers = numberOfPlayers;
    this.random = new Random(random.nextLong());
    this.players = new ArrayList<>();
    this.currentPlayerIndex = 0;
  }

  @SuppressFBWarnings(
          value = "EI_EXPOSE_REP2",
          justification = "Needed for mocking."
  )
  public Game(List<Player> players, Deck deck, Random random) {
    this.players = new ArrayList<>(players);
    this.numberOfPlayers = players.size();
    this.deck = deck;
    this.random = new Random(random.nextLong());
    this.currentPlayerIndex = 0;
  }

  public void startGame() {
    if (gameLaunched) {
      throw new IllegalStateException("game already started");
    }
    validatePlayerCount();

    if (this.players == null || this.players.isEmpty()) {
      this.players = new ArrayList<>();
      for (int i = 0; i < numberOfPlayers; i++) {
        this.players.add(new Player());
      }
    }
    if (this.deck == null) {
      this.deck = new Deck(players, random);
    }

    initializeTurnOrder();
    gameLaunched = true;
    gameOver = false;
  }

  public void validatePlayerCount() {
    if (numberOfPlayers < MIN_PLAYERS || numberOfPlayers > MAX_PLAYERS) {
      throw new IllegalArgumentException("invalid player count");
    }
  }

  public void initializeTurnOrder() {
    if (players.isEmpty()) {
      throw new IllegalStateException("cannot initialize turn order without players");
    }
    currentPlayerIndex = 0;
  }

  public Player getCurrentPlayer() {
    if (currentPlayerIndex < 0 || currentPlayerIndex >= players.size()) {
      throw new IllegalStateException("current player index out of bounds");
    }
    return players.get(currentPlayerIndex);
  }

  public void runGame() {
    if (!gameLaunched) {
      throw new IllegalStateException("game has not started");
    }
    if (!gameOver) {
      drawCard(0);
    }
  }

  public void handleTurn() {
    Player currentPlayer = getCurrentPlayer();
    if (!currentPlayer.isAlive() || currentPlayer.getTurnsOwed() == 0) {
      moveToNextPlayer();
      return;
    }

    completeOneTurn();
  }

  public void completeOneTurn() {
    Player currentPlayer = getCurrentPlayer();
    currentPlayer.removeTurn();
    if (currentPlayer.getTurnsOwed() == 0) {
      moveToNextPlayer();
    }
  }

  public void drawCard(int position) {
    Player currentPlayer = getCurrentPlayer();
    Card card = deck.drawCard();
    if (card.getType() == CardType.EXPLODING_KITTEN && currentPlayer.hasDefuse()) {
      Card defuse = new Card(CardType.DEFUSE);
      currentPlayer.removeCard(defuse);
      deck.discardCard(defuse);
      defuse(position);
      completeOneTurn();
      return;
    }
    if (card.getType() == CardType.EXPLODING_KITTEN) {
      currentPlayer.die();
      checkWinner();
      return;
    }
    currentPlayer.addCard(card);
    completeOneTurn();
  }

  public void playCard(Card card) {
    if (!gameLaunched) {
      throw new IllegalStateException("game has not started");
    }
    if (gameOver) {
      throw new IllegalStateException("game is over");
    }
    if (!isPlayableCard(card)) {
      throw new IllegalArgumentException("card is not playable");
    }
    Player currentPlayer = getCurrentPlayer();
    currentPlayer.removeCard(card);
    deck.discardCard(card);

    this.nopeCount = 0;

    if (card.getType() == CardType.SKIP) {
      this.pendingAction = ActionType.SKIP;
    }

    if (card.getType() == CardType.SUPER_SKIP) {
      this.pendingAction = ActionType.SUPER_SKIP;
    }

    if (card.getType() == CardType.SEE_THE_FUTURE) {
      this.pendingAction = ActionType.SEE_THE_FUTURE;
    }

    if (card.getType() == CardType.SHUFFLE) {
      this.pendingAction = ActionType.SHUFFLE;
    }

    if (card.getType() == CardType.SWAP_TOP_BOTTOM) {
      this.pendingAction = ActionType.SWAP_TOP_BOTTOM;
    }

    if (card.getType() == CardType.ATTACK) {
      this.pendingAction = ActionType.ATTACK;
    }

    if (card.getType() == CardType.BUBONIC_PLAGUE) {
      this.pendingAction = ActionType.BUBONIC_PLAGUE;
    }

    if (card.getType() == CardType.DRAW_FROM_BOTTOM) {
      this.pendingAction = ActionType.DRAW_FROM_BOTTOM;
    }

    if (card.getType() == CardType.CURSE) {
      this.pendingAction = ActionType.CURSE;
    }
  }

  @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "Required checkstyle.")
  public void playCard(Card card, Player target) {
    if (!gameLaunched) {
      throw new IllegalStateException("game has not started");
    }
    if (gameOver) {
      throw new IllegalStateException("game is over");
    }
    if (!isPlayableCard(card)) {
      throw new IllegalArgumentException("card is not playable");
    }
    Player currentPlayer = getCurrentPlayer();
    currentPlayer.removeCard(card);
    deck.discardCard(card);

    this.nopeCount = 0;

    if (card.getType() == CardType.TARGETED_ATTACK) {
      this.pendingAction = ActionType.TARGETED_ATTACK;
      this.pendingTarget = target;
    }

    if (card.getType() == CardType.BLESSING) {
      this.pendingAction = ActionType.BLESSING;
      this.pendingTarget = target;
    }
  }

  public void playCard(Card card, List<Card> reorderedCards) {
    if (!gameLaunched) {
      throw new IllegalStateException("game has not started");
    }
    if (gameOver) {
      throw new IllegalStateException("game is over");
    }
    if (!isPlayableCard(card)) {
      throw new IllegalArgumentException("card is not playable");
    }
    Player currentPlayer = getCurrentPlayer();
    currentPlayer.removeCard(card);
    deck.discardCard(card);

    this.nopeCount = 0;

    if (card.getType() == CardType.ALTER_FUTURE) {
      this.pendingAction = ActionType.ALTER_FUTURE;
      this.pendingCardList = new ArrayList<>(reorderedCards);
    }
  }

  @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "Required checkstyle.")
  public void playCard(Card card, Player target, Card given) {
    if (!gameLaunched) {
      throw new IllegalStateException("game has not started");
    }
    if (gameOver) {
      throw new IllegalStateException("game is over");
    }
    if (!isPlayableCard(card)) {
      throw new IllegalArgumentException("card is not playable");
    }
    if (card.getType() != CardType.FAVOR) {
      throw new IllegalArgumentException("card is not a favor card");
    }

    Player currentPlayer = getCurrentPlayer();
    currentPlayer.removeCard(card);
    deck.discardCard(card);

    this.pendingAction = ActionType.FAVOR;
    this.pendingTarget = target;
    this.pendingGivenCard = given;
  }

  public void playCard(List<Card> cards) {
    if (!gameLaunched) {
      throw new IllegalStateException("game has not started");
    }
    if (gameOver) {
      throw new IllegalStateException("game is over");
    }
    if (cards == null || cards.isEmpty()) {
      throw new IllegalArgumentException("cards cannot be null or empty");
    }
    for (Card c : cards) {
      if (c == null || c.getType() != CardType.NEKO) {
        throw new IllegalArgumentException("all cards must be neko cards");
      }
    }

    this.pendingAction = ActionType.NEKO_COMBO;
    this.pendingCardList = new ArrayList<>(cards);
  }

  @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "Required checkstyle.")
  public void playCard(List<Card> cards, Player target, CardType named) {
    if (!gameLaunched) {
      throw new IllegalStateException("game has not started");
    }
    if (gameOver) {
      throw new IllegalStateException("game is over");
    }
    if (!isValidCatCombo(cards)) {
      throw new IllegalArgumentException("invalid cat combo");
    }

    Player currentPlayer = getCurrentPlayer();
    int size = cards.size();

    if (size == TWO_CAT_COMBO_SIZE) {
      if (target == null || !target.isAlive() || target == currentPlayer) {
        throw new IllegalArgumentException("invalid target for 2-cat combo");
      }
    } else if (size == THREE_CAT_COMBO_SIZE) {
      if (target == null || !target.isAlive() || target == currentPlayer) {
        throw new IllegalArgumentException("invalid target for 3-cat combo");
      }
      if (named == null || named == CardType.EXPLODING_KITTEN || named == CardType.DEFUSE) {
        throw new IllegalArgumentException("invalid wanted card type for 3-cat combo");
      }
    } else if (size == FIVE_CAT_COMBO_SIZE) {
      if (named == null || named == CardType.EXPLODING_KITTEN || named == CardType.DEFUSE) {
        throw new IllegalArgumentException("invalid wanted card type for 5-cat combo");
      }
    }

    List<Card> handCopy = new ArrayList<>(currentPlayer.getHand());
    for (Card c : cards) {
      if (!handCopy.remove(c)) {
        throw new IllegalArgumentException("cards not in hand");
      }
    }

    for (Card c : cards) {
      currentPlayer.removeCard(c);
      deck.discardCard(c);
    }

    this.nopeCount = 0;
    this.pendingAction = ActionType.CAT_COMBO;
    this.pendingCardList = new ArrayList<>(cards);
    this.pendingTarget = target;
    this.pendingWantedCardType = named;
  }

  public void playNope(Player player, Card nopeCard) {
    if (pendingAction == ActionType.NONE) {
      throw new IllegalStateException("There is no pending action to Nope!");
    }
    if (nopeCard == null || nopeCard.getType() != CardType.NOPE) {
      throw new IllegalArgumentException("Card must be a NOPE card.");
    }
    if (!player.getHand().contains(nopeCard)) {
      throw new IllegalArgumentException("Player does not have that card.");
    }

    player.removeCard(nopeCard);
    deck.discardCard(nopeCard);
    nopeCount++;
  }

  public List<Card> resolvePendingAction() {
    if (pendingAction == ActionType.NONE) {
      return Collections.emptyList();
    }

    boolean actionIsNoped = (nopeCount % 2 != 0);

    if (!actionIsNoped) {
      switch (pendingAction) {
        case SKIP:
          playSkip();
          break;
        case ATTACK:
          playAttack();
          break;
        case SHUFFLE:
          clearPendingState();
          return playShuffle();
        case FAVOR:
          playFavor(this.pendingTarget, this.pendingGivenCard);
          break;
        case TARGETED_ATTACK:
          playTargetedAttack(pendingTarget);
          break;
        case SEE_THE_FUTURE:
          clearPendingState();
          return playSeeTheFuture();
        case SWAP_TOP_BOTTOM:
          playSwap();
          break;
        case BUBONIC_PLAGUE:
          playBubonicPlague();
          break;
        case SUPER_SKIP:
          playSuperSkip();
          break;
        case CURSE:
          playCurse();
          break;
        case ALTER_FUTURE:
          playAlterTheFuture(this.pendingCardList);
          break;
        case DRAW_FROM_BOTTOM:
          playDrawFromBottom();
          break;
        case BLESSING:
          this.pendingTarget.removeTurn();
          break;
        case NEKO_COMBO:
          playNeko(this.pendingCardList);
          break;
        case CAT_COMBO:
          playCatCards(this.pendingCardList, this.pendingTarget, this.pendingWantedCardType);
          break;
        default:
          break;
      }
    }

    clearPendingState();
    return Collections.emptyList();
  }

  private void clearPendingState() {
    this.pendingAction = ActionType.NONE;
    this.nopeCount = 0;
    this.pendingTarget = null;
    this.pendingGivenCard = null;
    this.pendingWantedCardType = null;
    this.pendingCardList = null;
  }

  private boolean isPlayableCard(Card card) {
    return card != null &&
            card.getType() != CardType.EXPLODING_KITTEN &&
            card.getType() != CardType.DEFUSE;
  }

  public void checkWinner() {
    int alivePlayers = 0;
    for (Player player : players) {
      if (player.isAlive()) {
        alivePlayers++;
      }
    }
    if (alivePlayers > 1) {
      gameOver = false;
    } else if (alivePlayers == 1) {
      gameOver = true;
    } else {
      throw new IllegalStateException("no players alive");
    }
  }

  public Player getNextActivePlayer() {
    for (int i = 1; i < players.size(); i++) {
      Player player = players.get((currentPlayerIndex + i) % players.size());
      if (player.isAlive()) {
        return player;
      }
    }
    gameOver = true;
    return null;
  }

  public void moveToNextPlayer() {
    do {
      currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    } while (!getCurrentPlayer().isAlive());
    if (getCurrentPlayer().getTurnsOwed() == 0) {
      getCurrentPlayer().addTurn();
    }
  }

  private void playSuperSkip() {
    Player currentPlayer = getCurrentPlayer();
    int turnsOwed = currentPlayer.getTurnsOwed();
    if (turnsOwed > 1) {
      currentPlayer.removeTurn();
      currentPlayer.removeTurn();
    } else {
      currentPlayer.removeTurn();
    }
    if (currentPlayer.getTurnsOwed() == 0) {
      moveToNextPlayer();
    }
  }

  public void defuse(int position) {

    if (position < 0 || position > deck.getDeck().size()) {
      throw new IllegalArgumentException("position cannot be negative");
    }
    deck.addToDrawPile(new Card(CardType.EXPLODING_KITTEN), position);
  }

  private void playSkip() {
    completeOneTurn();
  }

  public List<Player> getPlayers() {
    return new ArrayList<>(players);
  }

  public List<Card> playNosy(int targetPlayerIndex) {
    Player currentPlayer = getCurrentPlayer();
    Card nosy = new Card(CardType.NOSY);

    if (!currentPlayer.getHand().contains(nosy)) {
      throw new IllegalArgumentException("card is not in player's hand");
    }
    if (targetPlayerIndex < 0 || targetPlayerIndex >= players.size()) {
      throw new IllegalArgumentException("invalid player index");
    }
    if (targetPlayerIndex == currentPlayerIndex) {
      throw new IllegalArgumentException("cannot target yourself");
    }
    if (!players.get(targetPlayerIndex).isAlive()) {
      throw new IllegalArgumentException("target player is not alive");
    }
    return players.get(targetPlayerIndex).getHand();
  }

  public List<Card> getDrawPile() {
    return deck.getDeck();
  }

  public List<Card> getDiscardPile() {
    return deck.getDiscard();
  }

  public Card takeFromDiscard(CardType type) {
    return deck.takeFromDiscard(type);
  }

  public void addToDrawPile(Card card, int position) {
    deck.addToDrawPile(card, position);
  }

  public Card drawFromDeck() {
    return deck.drawCard();
  }

  public void addToDiscard(Card card) {
    deck.discardCard(card);
  }

  public boolean isGameLaunched() {
    return gameLaunched;
  }

  public boolean isGameOver() {
    return gameOver;
  }

  public int getCurrentPlayerIndex() {
    return currentPlayerIndex;
  }

  /** Bubonic Plague: Each player excl. the player who
   * played the card loses a random card from their hand **/
  public void playBubonicPlague() {
    Player currentPlayer = getCurrentPlayer();
    for (Player player : players) {
      if (player == currentPlayer || !player.isAlive() || player.getHand().isEmpty()) {
        continue;
      }
      List<Card> hand = player.getHand();
      Card randomCard = hand.get(random.nextInt(hand.size()));
      player.removeCard(randomCard);
      deck.addToDrawPile(randomCard, random.nextInt(deck.getDeck().size() + 1));
    }
    deck.shuffle();
  }

  public void playTargetedAttack(Player target) {
    if (target == null) {
      throw new IllegalArgumentException("target cannot be null");
    }
    if (target == getCurrentPlayer()) {
      throw new IllegalArgumentException("cannot target yourself");
    }
    if (!target.isAlive()) {
      throw new IllegalArgumentException("target is not alive");
    }

    currentPlayerIndex = players.indexOf(target);
    getCurrentPlayer().addTurn();
  }

  public List<Card> playSeeTheFuture(){
    return deck.peekTopCards();
  }

  public List<Card> playShuffle() {
    deck.shuffle();
    return deck.getDeck();
  }

  public void playSwap() {
    deck.swapTopBottomCards();
  }

  public void playDrawFromBottom() {
    Card card = deck.drawFromBottom();
    getCurrentPlayer().addCard(card);
    completeOneTurn();
  }

  public void playAlterTheFuture(List<Card> reorderedCards) {
    deck.reorderTopCards(reorderedCards);
  }

  public void playCurse() {
    Player nextPlayer = getNextActivePlayer();
    while (nextPlayer.hasDefuse()) {
      Card defuse = new Card(CardType.DEFUSE);
      nextPlayer.removeCard(defuse);
      deck.addToDrawPile(defuse, 0);
    }
    deck.shuffle();
  }

  public boolean isCatCard(CardType type) {
    if (type == null) {
      throw new IllegalArgumentException("invalid card");
    }
    return type == CardType.TACOCAT
            || type == CardType.HAIRY_POTATO_CAT
            || type == CardType.RAINBOW_RALPHING_CAT
            || type == CardType.BEARD_CAT
            || type == CardType.CATTERMELON
            || type == CardType.FERAL_CAT;
  }

  public boolean isValidCatCombo(List<Card> cards) {
    if (cards == null || cards.isEmpty()) {
      throw new IllegalArgumentException("cards cannot be null or empty");
    }

    for (Card c : cards) {
      if (!isCatCard(c.getType())) {
        return false;
      }
    }

    int size = cards.size();

    if (size == TWO_CAT_COMBO_SIZE) {
      CardType a = cards.get(0).getType();
      CardType b = cards.get(1).getType();
      boolean aFeral = a == CardType.FERAL_CAT;
      boolean bFeral = b == CardType.FERAL_CAT;
      if (aFeral || bFeral) {
        return true;
      }
      return a == b;
    }
    else if (size == THREE_CAT_COMBO_SIZE) {
      long ferals = cards.stream()
              .filter(c -> c.getType() == CardType.FERAL_CAT).count();
      if (ferals == THREE_CAT_COMBO_SIZE) {
        return true;
      }
      List<CardType> realCats = new ArrayList<>();
      for (Card c : cards) {
        if (c.getType() != CardType.FERAL_CAT) {
          realCats.add(c.getType());
        }
      }
      CardType first = realCats.get(0);
      for (CardType t : realCats) {
        if (t != first) {
          return false;
        }
      }
      return true;
    }
    else if (size == FIVE_CAT_COMBO_SIZE) {
      long distinctTypes = cards.stream()
              .map(Card::getType)
              .distinct()
              .count();
      return distinctTypes == FIVE_CAT_COMBO_SIZE;
    }
    return false;
  }

  public void playTwoMatchingCats(List<Card> cards, Player target) {
    Player currentPlayer = getCurrentPlayer();

    List<Card> targetHand = target.getHand();
    if (targetHand.isEmpty()) {
      throw new IllegalArgumentException("target player has no cards left");
    }

    int index = random.nextInt(targetHand.size());
    Card stolenCard = targetHand.get(index);
    target.removeCard(stolenCard);
    currentPlayer.addCard(stolenCard);
  }

  public void playNeko(List<Card> cards) {
    if (!gameLaunched) {
      throw new IllegalStateException("game has not started");
    }
    if (cards == null) {
      throw new IllegalArgumentException("cards cannot be null");
    }
    if (cards.size() != NUM_NEKO_CARDS) {
      throw new IllegalArgumentException("must play exactly 3 neko cards");
    }
    for (Card c : cards) {
      if (c == null || c.getType() != CardType.NEKO) {
        throw new IllegalArgumentException("all cards must be neko cards");
      }
    }
    Player currentPlayer = getCurrentPlayer();
    List<Card> hand = new ArrayList<>(currentPlayer.getHand());
    for (Card c : cards) {
      if (!hand.remove(c)) {
        throw new IllegalArgumentException("cards not in hand");
      }
    }
    for (Card c : cards) {
      currentPlayer.removeCard(c);
      deck.discardCard(c);
    }

    for (Player p : players) {
      if (p != currentPlayer) {
        p.die();
      }
    }
    gameOver = true;
  }

  public void playThreeMatchingCats(List<Card> cards, Player target, CardType wantedCard) {
    Player currentPlayer = getCurrentPlayer();

    Card namedCard = new Card(wantedCard);
    List<Card> targetHand = target.getHand();

    if (!targetHand.contains(namedCard)) {
      throw new IllegalArgumentException("target does not have that card");
    }

    target.removeCard(namedCard);
    currentPlayer.addCard(namedCard);
  }

  public void playFavor(Player target, Card given) {
    if (target == null || !target.isAlive()) {
      throw new IllegalArgumentException("invalid target");
    }
    Player currentPlayer = getCurrentPlayer();
    if (target == currentPlayer) {
      throw new IllegalArgumentException("cannot target yourself");
    }
    if (given == null) {
      throw new IllegalArgumentException("given card cannot be null");
    }
    if (!target.getHand().contains(given)) {
      throw new IllegalArgumentException("target does not have that card");
    }

    target.removeCard(given);
    currentPlayer.addCard(given);
  }

  public void playFiveDifferentCats(List<Card> cards, CardType wantedCard) {
    Player currentPlayer = getCurrentPlayer();

    Card namedCard = new Card(wantedCard);

    if (!getDiscardPile().contains(namedCard)) {
      throw new IllegalArgumentException("discard pile does not have that card");
    }

    Card receivedCard = takeFromDiscard(wantedCard);
    currentPlayer.addCard(receivedCard);
  }

  public void playCatCards(List<Card> cards, Player target, CardType named) {
    if (!gameLaunched) {
      throw new IllegalStateException("game has not started");
    }
    if (gameOver) {
      throw new IllegalStateException("game is over");
    }
    if (!isValidCatCombo(cards)) {
      throw new IllegalArgumentException("invalid cat combo");
    }
    int size = cards.size();
    if (size == TWO_CAT_COMBO_SIZE) {
      playTwoMatchingCats(cards, target);
    } else if (size == THREE_CAT_COMBO_SIZE) {
      playThreeMatchingCats(cards, target, named);
    } else {
      playFiveDifferentCats(cards, named);
    }
  }

  public void playAttack() {
    moveToNextPlayer();
    getCurrentPlayer().addTurn();
  }
}