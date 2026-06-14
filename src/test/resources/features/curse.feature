Feature: Curse returns Defuse cards to the draw pile
  As a player,
  I want Curse to remove Defuse cards from the next active player after the action resolves,
  so that the Game, Player, Deck, and Card classes work together for the card effect.

  Scenario: Resolve Curse against a player with two Defuse cards
    Given a started game with an empty draw pile
    And the current player has a Curse card
    And the next player has 2 Defuse cards
    When the current player plays Curse
    And the pending action resolves
    Then the action returns no cards
    And the next player has no Defuse cards
    And the draw pile has 2 cards
    And the discard pile contains CURSE
