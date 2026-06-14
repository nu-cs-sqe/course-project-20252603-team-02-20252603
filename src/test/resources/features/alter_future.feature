Feature: Alter Future reorders the draw pile
  As a player,
  I want Alter Future to reorder the top draw pile cards after the action resolves,
  so that the Game and Deck classes work together for the card effect.

  Scenario: Resolve Alter Future with four cards in the draw pile
    Given a started game with an empty draw pile
    And the current player has an Alter Future card
    And the draw pile contains FAVOR, SHUFFLE, NOPE, SKIP from top to bottom
    When the current player plays Alter Future with order NOPE, FAVOR, SHUFFLE
    And the pending action resolves
    Then the action returns no cards
    And the draw pile starts with NOPE, FAVOR, SHUFFLE, SKIP
    And the discard pile contains ALTER_FUTURE
