# BVA Analysis for Game

### Method under test: `startGame()`

- **TC1: Start game with valid player count** (:white_check_mark:)
    - **State of the system**: Game has 4 players and has not launched yet
    - **Expected output**: Game is launched, players are created, deck is created, turn order is initialized

- **TC2: Start game with lower boundary player count** (:white_check_mark:)
    - **State of the system**: Game has 3 players
    - **Expected output**: Game starts successfully

- **TC3: Start game with upper boundary player count** (:white_check_mark:)
    - **State of the system**: Game has 5 players
    - **Expected output**: Game starts successfully

- **TC2: Start game with lower boundary player count** (:white_check_mark:)
    - **State of the system**: Game has 3 players
    - **Expected output**: Game starts successfully

- **TC3: Start game with upper boundary player count** (:white_check_mark:)
    - **State of the system**: Game has 5 players
    - **Expected output**: Game starts successfully
- **TC1: Start game with valid player count** ( :white_check_mark: )
  - **State of the system**: Game has 4 players and has not launched yet
  - **Expected output**: Game is launched, players are created, deck is created, turn order is initialized

- **TC2: Start game with lower boundary player count** ( :white_check_mark: )
  - **State of the system**: Game has 3 players
  - **Expected output**: Game starts successfully

- **TC3: Start game with upper boundary player count** ( :white_check_mark: )
  - **State of the system**: Game has 5 players
  - **Expected output**: Game starts successfully

- **TC4: Start game with too few players** ( :white_check_mark: )
    - **State of the system**: Game has 2 players
    - **Expected output**: Throws `IllegalArgumentException`

- **TC5: Start game with too many players** ( :white_check_mark: )
    - **State of the system**: Game has 6 players
    - **Expected output**: Throws `IllegalArgumentException`

- **TC6: Start game twice** ( :white_check_mark: )
    - **State of the system**: Game has already launched
    - **Expected output**: Throws `IllegalStateException`

### Method under test: `validatePlayerCount()`

- **TC7: Validate 3 players** ( :white_check_mark: )
    - **State of the system**: Player count is 3
    - **Expected output**: No exception thrown

- **TC8: Validate 5 players** ( :white_check_mark: )
    - **State of the system**: Player count is 5
    - **Expected output**: No exception thrown

- **TC9: Validate too few players** ( :white_check_mark: )
    - **State of the system**: Player count is 2
    - **Expected output**: Throws `IllegalArgumentException`

- **TC10: Validate too many players** ( :white_check_mark: )
    - **State of the system**: Player count is 6
    - **Expected output**: Throws `IllegalArgumentException`

### Method under test: `initializeTurnOrder()`

- **TC11: Initialize turn order for 3 players** ( :white_check_mark: )
    - **State of the system**: Game has 3 players
    - **Expected output**: Current player index is 0 and turn order is clockwise

- **TC12: Initialize turn order for 4 players** ( :white_check_mark: )
    - **State of the system**: Game has 4 players
    - **Expected output**: Current player index is 0 and all players are in turn order

- **TC12.5: Initialize turn order for 5 players** ( :white_check_mark: )
    - **State of the system**: Game has 5 players
    - **Expected output**: Current player index is 0 and all players are in turn order

- **TC13: Initialize turn order before players exist** ( :white_check_mark: )
    - **State of the system**: Player list is empty
    - **Expected output**: Throws `IllegalStateException`

### Method under test: `getCurrentPlayer()`

- **TC14: Get first current player** ( :white_check_mark: )
    - **State of the system**: Current player index is 0
    - **Expected output**: Returns player at index 0

- **TC15: Get last current player** ( :white_check_mark: )
    - **State of the system**: Current player index is 2 in a 3-player game
    - **Expected output**: Returns player at index 2

- **TC16: Get current player when index is out of bounds** ( :white_check_mark: )
    - **State of the system**: Current player index is outside the player list
    - **Expected output**: Throws `IllegalStateException`

### Method under test: `runGame()`

- **TC17: Run game while game is not over** ( :white_check_mark: )
    - **State of the system**: Game is launched and more than 1 player is alive
    - **Expected output**: Game handles turns until the game is over

- **TC18: Run game when game is already over** ( :white_check_mark: )
    - **State of the system**: Game is over before `runGame()` is called
    - **Expected output**: No turns are handled and winner can be declared

- **TC19: Run game before startGame** ( :white_check_mark: )
    - **State of the system**: Game has not launched
    - **Expected output**: Throws `IllegalStateException`

- **TC20: Run game when game becomes over during a turn** ( :white_check_mark: )
    - **State of the system**: Game is running and a turn eliminates a player so only 1 player remains
    - **Expected output**: Game stops running and winner can be declared

### Method under test: `handleTurn()`

- **TC21: Handle normal current player turn** ( :white_check_mark: )
    - **State of the system**: Current player is alive and game is not over
    - **Expected output**: Current player may play, then draws if still required

- **TC22: Handle turn for player owing 2 turns** ( :white_check_mark: )
    - **State of the system**: Current player is alive and has `turnsOwed = 2`
    - **Expected output**: One turn is handled and current player still owes 1 turn

- **TC23: Handle turn for player owing 0 turns** ( :white_check_mark: )
    - **State of the system**: Current player is alive and has `turnsOwed = 0`
    - **Expected output**: Game moves to next active player

- **TC24: Handle turn for eliminated current player** ( :white_check_mark: )
    - **State of the system**: Current player is not alive
    - **Expected output**: Game moves to next active player

### Method under test: `getNextActivePlayer()`

- **TC25: Next player is active** ( :white_check_mark: )
    - **State of the system**: 3 players; current player is player 0; player 1 is alive
    - **Expected output**: Returns player 1

- **TC26: Current player is dead** ( :white_check_mark: )
    - **State of the system**: 3 players; current player is player 0 and is dead; player 1 is alive
    - **Expected output**: Returns player 1

- **TC27: Next player is eliminated** ( :white_check_mark: )
    - **State of the system**: 3 players; current player is player 0; player 1 is dead; player 2 is alive
    - **Expected output**: Returns player 2

- **TC28: Multiple dead players in a row** ( :white_check_mark: )
    - **State of the system**: 5 players; players 1 and 2 are dead; current player is player 0; player 3 is alive
    - **Expected output**: Returns player 3

- **TC29: Wrap around to first player** ( :white_check_mark: )
    - **State of the system**: 3 players; current player is player 2; player 0 is alive
    - **Expected output**: Returns player 0

- **TC30: Only one alive player left** ( :white_check_mark: )
    - **State of the system**: Only current player is alive
    - **Expected output**: Game is over and no next player is returned

### Method under test: `moveToNextPlayer()`

- **TC31: Move to next player normally** ( :white_check_mark: )
    - **State of the system**: 3 alive players; current player index is 0
    - **Expected output**: Current player index becomes 1

- **TC32: Move from last player to first player** ( :white_check_mark: )
    - **State of the system**: 3 alive players; current player index is 2
    - **Expected output**: Current player index becomes 0

- **TC33: Move past eliminated player** ( :white_check_mark: )
    - **State of the system**: Player 1 is dead; current player index is 0
    - **Expected output**: Current player index becomes 2

- **TC34: Move when next player has zero turns owed** ( :white_check_mark: )
    - **State of the system**: Next active player has `turnsOwed = 0`
    - **Expected output**: Next active player is set up to owe 1 turn

### Method under test: `completeOneTurn()`

- **TC35: Complete one owed turn** ( :white_check_mark: )
    - **State of the system**: Current player has `turnsOwed = 1`
    - **Expected output**: Current player's `turnsOwed` becomes 0 and game moves to next player

- **TC36: Complete one of two owed turns** ( :white_check_mark: )
    - **State of the system**: Current player has `turnsOwed = 2`
    - **Expected output**: Current player's `turnsOwed` becomes 1 and current player does not change

- **TC37: Complete turn when zero turns are owed** ( :white_check_mark: )
    - **State of the system**: Current player has `turnsOwed = 0`
    - **Expected output**: Throws `IllegalStateException`

### Method under test: `drawCard()`

- **TC38: Draw normal card for current player** ( :white_check_mark: )
    - **State of the system**: Top card is not `EXPLODING_KITTEN`; current player owes 1 turn
    - **Expected output**: Current player receives 1 card and one turn is completed

- **TC39: Draw last card from deck** ( :white_check_mark: )
    - **State of the system**: Draw pile has exactly 1 card
    - **Expected output**: Current player receives that card and draw pile becomes empty

- **TC40: Draw from empty deck** ( :white_check_mark: )
    - **State of the system**: Draw pile has 0 cards
    - **Expected output**: Throws `IllegalStateException`

- **TC41: Draw Exploding Kitten with Defuse** ( :white_check_mark: )
    - **State of the system**: Current player draws `EXPLODING_KITTEN` and has a `DEFUSE`
    - **Expected output**: Defuse is used, player stays alive, and Exploding Kitten returns to draw pile

- **TC42: Draw Exploding Kitten without Defuse** ( :white_check_mark: )
    - **State of the system**: Current player draws `EXPLODING_KITTEN` and has no `DEFUSE`
    - **Expected output**: Current player dies and game checks for winner

### Method under test: `playDrawFromBottom()`

- **TC#: Draw from bottom with many cards** ( :white_check_mark: )
    - **State of the system**: Current player plays `DRAW_FROM_BOTTOM` and draw pile has more than 1 card
    - **Expected output**: Bottom card is added to current player's hand, played card is discarded, one turn is completed, and empty list is returned

- **TC#: Draw from bottom with exactly 1 card** ( :white_check_mark: )
    - **State of the system**: Current player plays `DRAW_FROM_BOTTOM` and draw pile has exactly 1 card
    - **Expected output**: Only card is added to current player's hand, draw pile becomes empty, one turn is completed, and empty list is returned

- **TC#: Draw from bottom with empty draw pile** ( :white_check_mark: )
    - **State of the system**: Current player resolves `DRAW_FROM_BOTTOM` and draw pile has 0 cards
    - **Expected output**: Throws `IllegalStateException`

### Method under test: `playAlterTheFuture()`

- **TC#: Alter the Future with more than 3 cards** ( :white_check_mark: )
    - **State of the system**: Current player plays `ALTER_FUTURE`, draw pile has more than 3 cards, and player gives a valid order for the top 3 cards
    - **Expected output**: Played card is discarded, top 3 draw pile cards are reordered, cards after the top 3 stay in place, and empty list is returned

- **TC#: Alter the Future with exactly 3 cards** ( :white_check_mark: )
    - **State of the system**: Current player plays `ALTER_FUTURE`, draw pile has exactly 3 cards, and player gives a valid order for all 3 cards
    - **Expected output**: Played card is discarded, all 3 draw pile cards are reordered, and empty list is returned

- **TC#: Alter the Future with exactly 1 card** ( :white_check_mark: )
    - **State of the system**: Current player plays `ALTER_FUTURE`, draw pile has exactly 1 card, and player gives that card back
    - **Expected output**: Played card is discarded, the only draw pile card remains on top, and empty list is returned

- **TC#: Alter the Future with empty draw pile** ( :white_check_mark: )
    - **State of the system**: Current player plays `ALTER_FUTURE` and draw pile has 0 cards
    - **Expected output**: Throws `IllegalStateException`

- **TC#: Alter the Future with invalid order** ( :white_check_mark: )
    - **State of the system**: Current player plays `ALTER_FUTURE` and reordered cards do not match the visible top cards
    - **Expected output**: Throws `IllegalArgumentException`

### Method under test: `playCurse()`

- **TC#: Curse next player with no Defuse cards** ( :white_check_mark: )
    - **State of the system**: Current player plays `CURSE` and next player has 0 Defuse cards
    - **Expected output**: Played card is discarded, next player's hand is unchanged, draw pile size is unchanged, and empty list is returned

- **TC#: Curse next player with exactly 1 Defuse card** ( :white_check_mark: )
    - **State of the system**: Current player plays `CURSE` and next player has exactly 1 Defuse card
    - **Expected output**: Defuse is removed from next player's hand, returned to draw pile, draw pile is shuffled, and empty list is returned

- **TC#: Curse next player with multiple Defuse cards** ( :white_check_mark: )
    - **State of the system**: Current player plays `CURSE` and next player has more than 1 Defuse card
    - **Expected output**: All Defuse cards are removed from next player's hand, returned to draw pile, draw pile is shuffled, and empty list is returned

- **TC#: Curse when exactly one other player is alive** ( :white_check_mark: )
    - **State of the system**: Current player plays `CURSE`, one other player is alive, and remaining player has a Defuse card
    - **Expected output**: Defuse is removed from the only other alive player, returned to draw pile, draw pile is shuffled, and empty list is returned

### Method under test: `playCard()`

- **TC43: Player plays a playable card** ( :white_check_mark: )
    - **State of the system**: Game is started, game is not over, and current player has a playable card
    - **Expected output**: Card is removed from hand, card is discarded, and turn flow continues

- **TC44: Player tries to play a card not in hand** ( :white_check_mark: )
    - **State of the system**: Selected card is not in current player's hand
    - **Expected output**: Throws `IllegalArgumentException`

- **TC45: Player tries to play a non-playable card** ( :white_check_mark: )
    - **State of the system**: Selected card is not playable as an action card
    - **Expected output**: Throws `IllegalArgumentException`

- **TC46: Player tries to play before game starts** ( :white_check_mark: )
    - **State of the system**: Game has not launched
    - **Expected output**: Throws `IllegalStateException`

- **TC47: Player tries to play after game is over** ( :white_check_mark: )
    - **State of the system**: Game is over
    - **Expected output**: Throws `IllegalStateException`

- **TC47: Player tries to play Blessing card** ( :white_check_mark: )
  - **State of the system**: Game is started, game is not over, and current player has a Blessing card
  - **Expected output**: Card is removed from hand, card is discarded, and turn flow continues, targeted player drops one turn

### Method under test: `checkWinner()`

- **TC48: More than one player alive** ( :white_check_mark: )
    - **State of the system**: 2 or more players are alive
    - **Expected output**: Game is not over

- **TC49: Exactly one player alive** ( :white_check_mark: )
    - **State of the system**: 1 player is alive
    - **Expected output**: Game is over and that player is the winner

- **TC50: No players alive** ( :white_check_mark: )
    - **State of the system**: 0 players are alive
    - **Expected output**: Throws `IllegalStateException`

### Method under test: `playSkipCard()`

- **TC51: Player plays Skip but does not have one in hand** ( :white_check_mark: )
    - **State of the system**:  Game is started, current player does not have a `SKIP` card
    - **Expected output**:Throws `IllegalArgumentException`


- **TC52: Skip card appears in discard pile after play** ( :white_check_mark: )
    - **State of the system**: Game is started, current player plays a `SKIP` card
    - **Expected output**: `SKIP` card is present in discard pile

- **TC53: Player plays Skip card and owes 1 turns** ( :white_check_mark: )
    - **State of the system**: Game is started, game is not over, current player has a Skip card, turnsOwed = 1
    - **Expected output**: turnsOwed decreases to 1, card removed from hand, card added to discard pile, turn does not
      move to next player

- **TC54: Player plays Skip card and owes 2 turns** ( :white_check_mark: )
    - **State of the system**: Game is started, game is not over, current player has a Skip card, turnsOwed = 2
    - **Expected output**:  turnsOwed decreases to 1, card removed from hand, card added to discard pile, player
          remains current player (still has 1 turn left)

### Method under test: `playBubonicPlague()`
- **TC#55: All other players have cards** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Bubonic Plague card, all other players have at least 1 card in hand
  - **Expected output**: Card removed from hand, discarded, one random card removed from each other player's hand and added to draw pile, draw pile shuffled, empty list returned

- **TC#56: One other player has no cards** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Bubonic Plague card, one other player has no cards in hand
  - **Expected output**: Card removed from hand, discarded, player with no cards is skipped, one random card removed from each other player with cards, draw pile shuffled, empty list returned

- **TC#57: All other players have no cards** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Bubonic Plague card, all other players have empty hands
  - **Expected output**: Card removed from hand, discarded, no cards moved to draw pile, draw pile shuffled, empty list returned

- **TC#58: Exactly one other player alive** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Bubonic Plague card, exactly 1 other player alive with cards
  - **Expected output**: Card removed from hand, discarded, one random card removed from that player's hand and added to draw pile, draw pile shuffled, empty list returned

- **TC#59: Current player is not affected** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Bubonic Plague card, all other players have cards
  - **Expected output**: Current player's hand size unchanged, other players each lose one card

### Method under test: `playTargetedAttack()`
- **TC#60: Targeted Attack, target is the next player in order** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Targeted Attack card, target is the next player in turn order, target is alive
  - **Expected output**: Card removed from hand, discarded, turn moves to target player, target player owes 2 turns, empty list returned

- **TC#61: Targeted Attack, target is not the next player in order** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Targeted Attack card, target is a player further ahead in turn order, target is alive
  - **Expected output**: Card removed from hand, discarded, turn moves to target player skipping players in between, target player owes 2 turns, empty list returned

- **TC#62: Targeted Attack, target is the only other player alive** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Targeted Attack card, exactly 1 other player alive
  - **Expected output**: Card removed from hand, discarded, turn moves to target player, target player owes 2 turns, empty list returned

- **TC#63: Targeted Attack, target is a dead player** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Targeted Attack card, target player is not alive
  - **Expected output**: Throws `IllegalArgumentException`

- **TC#64: Targeted Attack, target is the current player** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Targeted Attack card, target is themselves
  - **Expected output**: Throws `IllegalArgumentException`

### Method under test: `playSeeTheFuture()`

- **TC#65: Deck has 0 cards** ( :white_check_mark: )
    - **State of the system**: Game is running, current player has See the Future, deck is empty
    - **Expected output**: Card removed from hand, discarded, throws `IllegalStateException`

- **TC#66: Deck has 1 card** ( :white_check_mark: )
    - **State of the system**: Game is running, current player has See the Future, deck has 1 card
    - **Expected output**: Card removed from hand, discarded, list of 1 card returned

- **TC#67: Deck has exactly 3 cards** ( :white_check_mark: )
    - **State of the system**: Game is running, current player has See the Future, deck has exactly 3 cards
    - **Expected output**: Card removed from hand, discarded, list of 3 cards returned

- **TC#68: Deck has more than 3 cards** ( :white_check_mark: )
    - **State of the system**: Game is running, current player has See the Future, deck has more than 3 cards
    - **Expected output**: Card removed from hand, discarded, list of exactly 3 cards returned

### Method under test: `defuse(int position)`

- **TC69: Defuse when player has no Defuse card** ( :white_check_mark: )
    - **State of the system**: Current player does not have a Defuse card
    - **Expected output**: Throws `IllegalStateException`

- **TC70: Defuse with position out of bounds (negative)** ( :white_check_mark: )
    - **State of the system**: Position is -1
    - **Expected output**: Throws `IllegalArgumentException`

- **TC71: Defuse with position out of bounds (too large)** ( :white_check_mark: )
    - **State of the system**: Position is greater than drawPile.size()
    - **Expected output**: Throws `IllegalArgumentException`

- **TC72: Defuse with position 0 (top of draw pile)** ( :white_check_mark: )
    - **State of the system**: Current player has a Defuse card, position is 0
    - **Expected output**: Exploding Kitten inserted at top of draw pile

- **TC73: Defuse with position equal to drawPile.size() (bottom of draw pile)** ( :white_check_mark: )
    - **State of the system**: Current player has a Defuse card, position is drawPile.size()
    - **Expected output**: Exploding Kitten inserted at bottom of draw pile

- **TC74: Defuse with valid position in middle of draw pile** ( :white_check_mark: )
    - **State of the system**: Current player has a Defuse card, position is between 0 and drawPile.size()
    - **Expected output**: Defuse removed from hand, Defuse added to discard, Exploding Kitten inserted at given
      position
      
### Method under test: `playSwap()`
- **TC75: Swap when draw pile has no cards** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Swap, Draw pile has no cards
  - **Expected output**: Card removed from hand, discarded, Draw pile unchanged

- **TC76: Swap when draw pile has 1 card** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Swap, Draw pile has 1 card
  - **Expected output**: Card removed from hand, discarded, Draw pile unchanged

- **TC77: Swap when draw pile has 2 cards** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Swap, Draw pile has 2 cards
  - **Expected output**: Card removed from hand, discarded, Draw pile has first and second cards swapped

- **TC78: Swap when draw pile has more than 2 cards** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Swap, Draw pile has 3 cards
  - **Expected output**: Card removed from hand, discarded, Draw pile has first and last cards swapped
  
### Method under test: `isCatCard()`
- **TC#79: type is null** ( :white_check_mark: )
  - **State of the system**: Game is running, card type is null 
  - **Expected output**: IllegalArgumentException, "invalid card"

- **TC#80: type is TACOCAT** ( :white_check_mark: )
  - **State of the system**: Game is running, card type is TACOCAT
  - **Expected output**: true 
  
- **TC#81: type is RAINBOW_RALPHING_CAT** ( :white_check_mark: )
  - **State of the system**: Game is running, card type is RAINBOW_RALPHING_CAT
  - **Expected output**: true

- **TC#82: type is BEARD_CAT** ( :white_check_mark: )
  - **State of the system**: Game is running, card type is BEARD_CAT
  - **Expected output**: true

- **TC#83: type is CATTERMELON** ( :white_check_mark: )
  - **State of the system**: Game is running, card type is CATTERMELON
  - **Expected output**: true

- **TC#84: type is FERAL_CAT** ( :white_check_mark: )
  - **State of the system**: Game is running, card type is FERAL_CAT
  - **Expected output**: true

- **TC#85: type is a non-cat playable card (ATTACK)** ( :white_check_mark: )
  - **State of the system**: Game is running, card type is ATTACK
  - **Expected output**: false

- **TC#86: type is EXPLODING_KITTEN** ( :white_check_mark: )
  - **State of the system**: Game is running, card type is EXPLODING_KITTEN
  - **Expected output**: false

- **TC#87: type is DEFUSE** ( :white_check_mark: )
  - **State of the system**: Game is running, card type is DEFUSE
  - **Expected output**: false

### Method under test: `isValidCatCombo()`
- **TC#88: list is null** ( :white_check_mark: )
  - **State of the system**: Game is running, cards list is null
  - **Expected output**: IllegalArgumentException

- **TC#689: list is empty (size 0)** ( :white_check_mark: )
  - **State of the system**: Game is running, cards list is empty
  - **Expected output**: IllegalArgumentException

- **TC#90: list has 1 card (below minimum valid size)** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT]
  - **Expected output**: false

- **TC#91: list has 2 cards — both same real cat** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT, TACOCAT]
  - **Expected output**: true

- **TC#92: list has 2 cards — two different real cats** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT, BEARD_CAT]
  - **Expected output**: false

- **TC#93: list has 2 cards — one FERAL_CAT + one real cat** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [FERAL_CAT, TACOCAT]
  - **Expected output**: true

- **TC#94: list has 2 cards — both FERAL_CAT** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [FERAL_CAT, FERAL_CAT]
  - **Expected output**: true

- **TC#95: list has 2 cards — one cat, one non-cat** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT, ATTACK]
  - **Expected output**: false

- **TC#96: list has 3 cards — all same real cat** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT, TACOCAT, TACOCAT]
  - **Expected output**: true

- **TC#97: list has 3 cards — 2 matching + 1 FERAL_CAT** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT, TACOCAT, FERAL_CAT]
  - **Expected output**: true

- **TC#98: list has 3 cards — 1 real cat + 2 FERAL_CAT** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT, FERAL_CAT, FERAL_CAT]
  - **Expected output**: true

- **TC#99: list has 3 cards — all FERAL_CAT** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [FERAL_CAT, FERAL_CAT, FERAL_CAT]
  - **Expected output**: true

- **TC#100: list has 3 cards — 3 different real cats** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT, BEARD_CAT, CATTERMELON]
  - **Expected output**: false

- **TC#101: list has 3 cards — 2 different real cats + 1 FERAL_CAT** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT, BEARD_CAT, FERAL_CAT]
  - **Expected output**: false

- **TC#102: list has 3 cards — includes a non-cat card** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT, TACOCAT, ATTACK]
  - **Expected output**: false

- **TC#103: list has 4 cards (gap between valid sizes)** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT, TACOCAT, TACOCAT, TACOCAT]
  - **Expected output**: false

- **TC#104: list has 5 cards — all 5 distinct real cat types** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT, HAIRY_POTATO_CAT, RAINBOW_RALPHING_CAT, BEARD_CAT, CATTERMELON]
  - **Expected output**: true

- **TC#105: list has 5 cards — 4 distinct real cats + 1 FERAL_CAT** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT, HAIRY_POTATO_CAT, RAINBOW_RALPHING_CAT, BEARD_CAT, FERAL_CAT]
  - **Expected output**: true

- **TC#106: list has 5 cards — duplicate real cat type present** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT, TACOCAT, RAINBOW_RALPHING_CAT, BEARD_CAT, CATTERMELON]
  - **Expected output**: false

- **TC#107: list has 5 cards — 2 FERAL_CAT (duplicate feral)** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT, HAIRY_POTATO_CAT, RAINBOW_RALPHING_CAT, FERAL_CAT, FERAL_CAT]
  - **Expected output**: false

- **TC#108: list has 5 cards — includes a non-cat card** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT, HAIRY_POTATO_CAT, RAINBOW_RALPHING_CAT, BEARD_CAT, ATTACK]
  - **Expected output**: false

- **TC#109: list has 6 cards (above maximum valid size)** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT, TACOCAT, TACOCAT, TACOCAT, TACOCAT, TACOCAT]
  - **Expected output**: false

### Method under test: `playTwoMatchingCats()`
- **TC#110: target is null** ( :white_check_mark: )
  - **State of the system**: `current player has 2 matching cats, target is null
  - **Expected output**: Throws `IllegalArgumentException`, "target cannot be null or dead"

- **TC#111: target is dead** ( :white_check_mark: )
  - **State of the system**: current player has 2 matching cats, target player is not alive
  - **Expected output**: Throws `IllegalArgumentException`, "target cannot be null or dead"

- **TC#112: target is current player** ( :white_check_mark: )
  - **State of the system**: current player has 2 matching cats, target == currentPlayer
  - **Expected output**: Throws `IllegalArgumentException`, "cannot target yourself"

- **TC#113: combo is invalid because cats do not match** ( :white_check_mark: )
  - **State of the system**: cards = [TACOCAT, BEARD_CAT], target is valid
  - **Expected output**: Throws `IllegalArgumentException`, "invalid two-cat combo"

- **TC#114: combo is invalid because size is 3 instead of 2** ( :white_check_mark: )
  - **State of the system**: cards = [TACOCAT, TACOCAT, TACOCAT], target is valid
  - **Expected output**: Throws `IllegalArgumentException`, "invalid two-cat combo"

- **TC#115: cards are not in current player's hand** ( :white_check_mark: )
  - **State of the system**: current player's hand does not contain the passed cards, target is valid
  - **Expected output**: Throws `IllegalArgumentException`, "cards not in hand"

- **TC#116: current player has only 1 of the 2 required cards** ( :white_check_mark: )
  - **State of the system**: current player has 1 TACOCAT in hand, cards = [TACOCAT, TACOCAT], target is valid
  - **Expected output**: Throws `IllegalArgumentException`, "cards not in hand"

- **TC#117: target has exactly 1 card in hand** ( :white_check_mark: )
  - **State of the system**: current player has valid 2-card combo in hand, target hand = [SKIP]
  - **Expected output**: Both cat cards removed from current player's hand, both cards added to discard pile, SKIP transferred to current player, target hand is empty, returns SKIP

- **TC#118: target has exactly 2 cards in hand** ( :white_check_mark: )
  - **State of the system**: current player has valid 2-card combo in hand, target hand = [SKIP, ATTACK]
  - **Expected output**: Both cat cards removed from current player's hand, both cards added to discard pile, exactly 1 target card transferred to current player, target hand has 1 card remaining, returns stolen card

- **TC#119: target has many cards in hand** ( :white_check_mark: )
  - **State of the system**: current player has valid 2-card combo in hand, target hand has 5 cards
  - **Expected output**: Both cat cards removed from current player's hand, both cards added to discard pile, exactly 1 target card transferred to current player, target hand size goes from 5 to 4, returns stolen card

### Method under test: `playThreeMatchingCats()`
- **TC#120: target is null** ( :white_check_mark: )
  - **State of the system**: current player has valid 3-cat combo, named card = FAVOR
  - **Expected output**: Throws `IllegalArgumentException`, "target cannot be null or dead"

- **TC#121: target is dead** ( :white_check_mark: )
  - **State of the system**: current player has valid 3-cat combo, target player is not alive, named card = FAVOR
  - **Expected output**: Throws `IllegalArgumentException`, "target cannot be null or dead"

- **TC#122: target is current player** ( :white_check_mark: )
  - **State of the system**: current player has valid 3-cat combo, target == currentPlayer, named card = FAVOR
  - **Expected output**: Throws `IllegalArgumentException`, "cannot target yourself"

- **TC#123: named is null** ( :white_check_mark: )
  - **State of the system**: current player has valid 3-cat combo, target is valid, named card = null
  - **Expected output**: Throws `IllegalArgumentException`, "invalid wanted card type"

- **TC#124: named is EXPLODING_KITTEN** ( :white_check_mark: )
  - **State of the system**: current player has valid 3-cat combo, target is valid, named card = EXPLODING_KITTEN
  - **Expected output**: Throws `IllegalArgumentException`, "invalid wanted card type"

- **TC#125: combo is invalid because cats do not match** ( :white_check_mark: )
  - **State of the system**: cards = [TACOCAT, BEARD_CAT, CATTERMELON], target is valid, named card = FAVOR
  - **Expected output**: Throws `IllegalArgumentException`, "invalid three-cat combo"

- **TC#126: combo is invalid because size is 2 instead of 3** ( :white_check_mark: )
  - **State of the system**: cards = [TACOCAT, TACOCAT], target is valid, named = FAVOR
  - **Expected output**: Throws `IllegalArgumentException`, "invalid three-cat combo"

- **TC#127: cards are not in current player's hand** ( :white_check_mark: )
  - **State of the system**: combo cards are not held by current player, target is valid, named card = FAVOR
  - **Expected output**: Throws `IllegalArgumentException`, "card not in hand"

- **TC#128: target has 0 copies of named card** ( :white_check_mark: )
  - **State of the system**: current player has valid 3-cat combo in hand, target has no FAVOR, named card = FAVOR
  - **Expected output**: Three cats removed from current player's hand, three cats added to discard pile, no card transferred, returns false

- **TC#129: target has exactly 1 copy of named card** ( :white_check_mark: )
  - **State of the system**: current player has valid 3-cat combo in hand, target hand = [FAVOR], named card = FAVOR
  - **Expected output**: Three cats removed from current player's hand, three cats added to discard pile, FAVOR transferred to current player, target hand is empty, returns true

- **TC#130: target has exactly 2 copies of named card** ( :white_check_mark: )
  - **State of the system**: current player has valid 3-cat combo in hand, target hand = [FAVOR, FAVOR], named card = FAVOR
  - **Expected output**: Three cats removed from current player's hand, three cats added to discard pile, exactly 1 FAVOR transferred, target still has 1 FAVOR, returns true

- **TC#131: target has named card plus other cards** ( :white_check_mark: )
  - **State of the system**: current player has valid 3-cat combo in hand, target hand = [FAVOR, ATTACK, SKIP], named card = FAVOR
  - **Expected output**: Three cats removed from current player's hand, three cats added to discard pile, FAVOR transferred, target hand = [ATTACK, SKIP], returns true

### Method under test: `playFiveDifferentCats()`
- **TC#132: cards is null** ( :white_check_mark: )
  - **State of the system**: cards = null, wanted_card = FAVOR
  - **Expected output**: Throws `IllegalArgumentException`, "cards cannot be null"

- **TC#133: wanted_card is null** ( :white_check_mark: )
  - **State of the system**: Current player has valid 5-card combo, wanted_card = null
  - **Expected output**: Throws `IllegalArgumentException`, "invalid wanted card type"

- **TC#134: wanted_card is EXPLODING_KITTEN** ( :white_check_mark: )
  - **State of the system**: Current player has valid 5-card combo, wanted_card = EXPLODING_KITTEN
  - **Expected output**: Throws `IllegalArgumentException`, "invalid wanted card type"

- **TC#135: combo is invalid because duplicate cat type is present** ( :white_check_mark: )
  - **State of the system**: Cards = [TACOCAT, TACOCAT, RAINBOW_RALPHING_CAT, BEARD_CAT, CATTERMELON], wanted_card = FAVOR
  - **Expected output**: Throws `IllegalArgumentException`, "invalid five-cat combo"

- **TC#136: combo is invalid because size is 3 instead of 5** ( :white_check_mark: )
  - **State of the system**: Cards = [TACOCAT, TACOCAT, TACOCAT], wanted_card = FAVOR
  - **Expected output**: Throws `IllegalArgumentException`, "invalid five-cat combo"

- **TC#137: cards are not in current player's hand** ( :white_check_mark: )
  - **State of the system**: Valid 5-card combo shape, but current player does not hold those cards
  - **Expected output**: Throws `IllegalArgumentException`, "cards not in hand"

- **TC#138: discard pile is empty** ( :white_check_mark: )
  - **State of the system**: Valid combo in hand, discard pile is empty, wanted_card = FAVOR
  - **Expected output**: Throws `IllegalArgumentException`, "wanted card not in discard pile"

- **TC#139: discard has 1 card that does not match wanted_card** ( :white_check_mark: )
  - **State of the system**: Discard has [ATTACK], wanted_card = FAVOR
  - **Expected output**: Throws `IllegalArgumentException`, "wanted card not in discard pile"

- **TC#140: discard has 1 card that matches wanted_card** ( :white_check_mark: )
  - **State of the system**: Discard has [FAVOR], wanted_card = FAVOR
  - **Expected output**: Five cats removed from hand and discarded, FAVOR transferred to current player, returns FAVOR

- **TC#141: discard has 2 copies of wanted_card** ( :white_check_mark: )
  - **State of the system**: Discard has [FAVOR, FAVOR], wanted_card = FAVOR
  - **Expected output**: Five cats removed from hand and discarded, exactly 1 FAVOR transferred, discard still has 1 FAVOR remaining, returns FAVOR

- **TC#142: discard has wanted_card among other cards** ( :white_check_mark: )
  - **State of the system**: Discard has [ATTACK, FAVOR, SKIP], wanted_card = FAVOR
  - **Expected output**: Five cats removed from hand and discarded, FAVOR transferred, ATTACK and SKIP remain in discard, returns FAVOR

### Method under test: `playCatCards()`
- **TC#143: game has not been launched** ( :white_check_mark: )
  - **State of the system**: gameLaunched = false, cards = [TACOCAT, TACOCAT], target = player2, named = null
  - **Expected output**: Throws `IllegalStateException`, "game has not started"

- **TC#144: game is over** ( :white_check_mark: )
  - **State of the system**: gameOver = true, cards = [TACOCAT, TACOCAT], target = player2, named = null
  - **Expected output**: Throws `IllegalStateException`, "game is over"

- **TC#145: cards is null** ( :white_check_mark: )
  - **State of the system**: Game running, cards = null, target = player2, named = FAVOR
  - **Expected output**: Throws `IllegalArgumentException`, "cards cannot be null or empty"

- **TC#146: combo is invalid — size 1** ( :white_check_mark: )
  - **State of the system**: Game running, cards = [TACOCAT], target = player2, named = null
  - **Expected output**: Throws `IllegalArgumentException`, "invalid cat combo"

- **TC#147: combo is invalid — size 4** ( :white_check_mark: )
  - **State of the system**: Game running, cards = [TACOCAT, TACOCAT, TACOCAT, TACOCAT], target = player2, named = null
  - **Expected output**: Throws `IllegalArgumentException`, "invalid cat combo"

- **TC#148: valid 2-cat combo, target has 1 card — routes to playTwoMatchingCats** ( :white_check_mark: )
  - **State of the system**: Game running, current player has [TACOCAT, TACOCAT], target has [SKIP]
  - **Expected output**: Two `TACOCAT`s added to discard, `SKIP` transferred to current player, target hand empty

- **TC#149: valid 3-cat combo, target has named card — routes to playThreeMatchingCats** ( :white_check_mark: )
  - **State of the system**: Game running, current player has [TACOCAT, TACOCAT, TACOCAT], target has `FAVOR`, named = `FAVOR`
  - **Expected output**: Three `TACOCAT`s added to discard, `FAVOR` transferred to current player

- **TC#150: valid 3-cat combo, target lacks named card — routes to playThreeMatchingCats** ( :white_check_mark: )
  - **State of the system**: Game running, current player has [TACOCAT, TACOCAT, TACOCAT], target has no `FAVOR`, named = `FAVOR`
  - **Expected output**: Three `TACOCAT`s added to discard, nothing transferred

- **TC#151: valid 5-cat combo, named card in discard — routes to playFiveDifferentCats** ( :white_check_mark: )
  - **State of the system**: Game running, current player has [TACOCAT, HAIRY_POTATO_CAT, RAINBOW_RALPHING_CAT, BEARD_CAT, CATTERMELON], discard has `FAVOR`, named = `FAVOR`
  - **Expected output**: Five cats added to discard, `FAVOR` transferred to current player

- **TC#152: valid 5-cat combo, named card not in discard — routes to playFiveDifferentCats** ( :white_check_mark: )
  - **State of the system**: Game running, current player has [TACOCAT, HAIRY_POTATO_CAT, RAINBOW_RALPHING_CAT, BEARD_CAT, CATTERMELON], discard has no `FAVOR`, named = `FAVOR`
  - **Expected output**: Throws `IllegalArgumentException`, "card type not in discard pile"

### Method under test: `playNeko()`
- **TC#153: cards is null** ( :white_check_mark: )
  - **State of the system**: cards = null
  - **Expected output**: Throws `IllegalArgumentException`, "cards cannot be null"

- **TC#154: cards has 0 cards** ( :white_check_mark: )
  - **State of the system**: cards = []
  - **Expected output**: Throws `IllegalArgumentException`, "must play exactly 3 neko cards"

- **TC#155: cards has 1 NEKO** ( :white_check_mark: )
  - **State of the system**: current player has 1 NEKO, cards = [NEKO]
  - **Expected output**: Throws `IllegalArgumentException`, "must play exactly 3 neko cards"

- **TC#156: cards has 2 NEKOs** ( :white_check_mark: )
  - **State of the system**: current player has 2 NEKOs, cards = [NEKO, NEKO]
  - **Expected output**: Throws `IllegalArgumentException`, "must play exactly 3 neko cards"

- **TC#157: cards has exactly 3 NEKOs** ( :white_check_mark: )
  - **State of the system**: current player has 3 NEKOs in hand
  - **Expected output**: All 3 `NEKO`s discarded, all other players dead, `gameOver = true`

- **TC#158: cards has 4 NEKOs** ( :white_check_mark: )
  - **State of the system**: current player has 4 NEKOs, cards = [NEKO, NEKO, NEKO, NEKO]
  - **Expected output**: Throws `IllegalArgumentException`, "must play exactly 3 neko cards"

- **TC#159: cards has 3 cards but one is null** ( :white_check_mark: )
  - **State of the system**: cards = [NEKO, null, NEKO]
  - **Expected output**: Throws `IllegalArgumentException`, "all cards must be neko cards"

- **TC#160: cards has 3 cards but one is not NEKO** ( :white_check_mark: )
  - **State of the system**: cards = [NEKO, NEKO, ATTACK]
  - **Expected output**: Throws `IllegalArgumentException`, "all cards must be neko cards"

- **TC#161: cards has 3 NEKOs but current player only has 2 in hand** ( :white_check_mark: )
  - **State of the system**: current player hand has only 2 NEKO cards, cards = [NEKO, NEKO, NEKO]
  - **Expected output**: Throws `IllegalArgumentException`, "cards not in hand"

- **TC#162: cards has 3 NEKOs, current player has exactly 3 in hand, 2 other players alive** ( :white_check_mark: )
  - **State of the system**: with 3 players, current player has 3 NEKOs
  - **Expected output**: All 3 `NEKO`s discarded, both other players dead, `gameOver = true`, current player still alive

- **TC#163: cards has 3 NEKOs, current player has exactly 3 in hand, 4 other players alive** ( :white_check_mark: )
  - **State of the system**: with 5 players, current player has 3 NEKOs
  - **Expected output**: All 3 `NEKO`s discarded, all 4 other players dead, `gameOver = true`, current player still alive

- **TC#164: cards has 3 NEKOs, one other player is already dead** ( :white_check_mark: )
  - **State of the system**: with 3 players, one opponent already dead, current player has 3 NEKOs
  - **Expected output**: All 3 `NEKO`s discarded, remaining alive opponent now dead, `gameOver = true`, current player still alive

### Method under test: `playFavor()`
- **TC#165: target is null** ( :white_check_mark: )
  - **State of the system**: target = null, given = SKIP
  - **Expected output**: Throws `IllegalArgumentException`, "invalid target"

- **TC#166: target is dead** ( :white_check_mark: )
  - **State of the system**: target.alive = false, given = SKIP
  - **Expected output**: Throws `IllegalArgumentException`, "invalid target"

- **TC#167: target is current player** ( :white_check_mark: )
  - **State of the system**: target == currentPlayer, given = SKIP
  - **Expected output**: Throws `IllegalArgumentException`, "cannot target yourself"

- **TC#168: given is null** ( :white_check_mark: )
  - **State of the system**: valid target, given = null
  - **Expected output**: Throws `IllegalArgumentException`, "given card cannot be null"

- **TC#169: target has empty hand** ( :white_check_mark: )
  - **State of the system**: valid target with 0 cards, given = SKIP
  - **Expected output**: Throws `IllegalArgumentException`, "target does not have that card"

- **TC#170: target has 1 card, given does not match** ( :white_check_mark: )
  - **State of the system**: target hand = [ATTACK], given = SKIP
  - **Expected output**: Throws `IllegalArgumentException`, "target does not have that card"

- **TC#171: target has exactly 1 card, given matches** ( :white_check_mark: )
  - **State of the system**: target hand = [SKIP], given = SKIP
  - **Expected output**: `SKIP` transferred to current player, target hand is now empty

- **TC#172: target has 2 cards, given matches one of them** ( :white_check_mark: )
  - **State of the system**: target hand = [SKIP, ATTACK], given = SKIP
  - **Expected output**: `SKIP` transferred to current player, target hand = [ATTACK]

- **TC#173: target has 2 copies of the given card** ( :white_check_mark: )
  - **State of the system**: target hand = [SKIP, SKIP], given = SKIP
  - **Expected output**: Exactly 1 `SKIP` transferred to current player, target hand still has 1 `SKIP`

### Method under test: `playAttack()`
- **TC#174: Attack, more than one other player alive** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Attack card, 2+ other players alive
  - **Expected output**: Card removed from hand, discarded, turn moves to next player, next player owes 2 turns, empty list returned

- **TC#175: Attack, exactly one other player alive** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Attack card, exactly 1 other player alive
  - **Expected output**: Card removed from hand, discarded, turn moves to that player, that player owes 2 turns, empty list returned

- **TC#176: Attack, current player owes 1 turn** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Attack card, current player owes 1 turn
  - **Expected output**: Turn moves to next player, next player owes 2 turns, empty list returned

### Method under test: `playSuperSkipCard()`

- **TC177: Player plays `Super Skip` but does not have one in hand** ( :white_check_mark: )
    - **State of the system**: Game is started, current player does not have a Super Skip card
    - **Expected output**: Throws `IllegalArgumentException`

- **TC178: Super Skip card appears in discard pile after play** ( :white_check_mark: )
    - **State of the system**: Game is started, current player plays a Super Skip card
    - **Expected output**: Super Skip card is present in discard pile

- **TC179: Player plays `Super Skip` card with turnsOwed = 1 (not targetted)** ( :white_check_mark: )
    - **State of the system**: Game is started, game is not over, current player has a Super Skip card, turnsOwed = 1
    - **Expected output**: turnsOwed decreases by 1 to 0, turn moves to next player

- **TC180:  Player plays` Super Skip` with turnsOwed = 2 (targeted)** ( :white_check_mark: )
    - **State of the system**: Game is started, game is not over, current player has a Super Skip card, turnsOwed = 2
    - **Expected output**:  turnsOwed decreases by 2 to 0, turn moves to next player


### Method under test: `playSeeTheFuture()`

- **TC#189: Deck has 0 cards** ( :white_check_mark: )
    - **State of the system**: Game is running, current player has See the Future, deck is empty
    - **Expected output**: Card removed from hand, discarded, throws `IllegalStateException`

- **TC#190: Deck has 1 card** ( :white_check_mark: )
    - **State of the system**: Game is running, current player has See the Future, deck has 1 card
    - **Expected output**: Card removed from hand, discarded, list of 1 card returned

- **TC#191: Deck has exactly 3 cards** ( :white_check_mark: )
    - **State of the system**: Game is running, current player has See the Future, deck has exactly 3 cards
    - **Expected output**: Card removed from hand, discarded, list of 3 cards returned

- **TC#192: Deck has more than 3 cards** ( :white_check_mark: )
    - **State of the system**: Game is running, current player has See the Future, deck has more than 3 cards
    - **Expected output**: Card removed from hand, discarded, list of exactly 3 cards returned

### Method under test: `playNosy(int targetPlayerIndex)`

- **TC193: Play Nosy but does not have one in hand** ( :white_check_mark: )
    - **State of the system**: Current player does not have a Nosy card
    - **Expected output**: Throws `IllegalArgumentException`

- **TC194: Play Nosy on invalid player index (negative)** ( :white_check_mark: )
    - **State of the system**: Target player index is -1
    - **Expected output**: Throws `IllegalArgumentException`

- **TC195: Play Nosy on invalid player index (too large)** ( :white_check_mark: )
    - **State of the system**: Target player index is >= number of players
    - **Expected output**: Throws `IllegalArgumentException`

- **TC196: Play Nosy on self** ( :white_check_mark: )
    - **State of the system**: Game is started, current player targets themselves
    - **Expected output**: Throws `IllegalArgumentException`

- **TC197: Play Nosy on dead player** ( :white_check_mark: )
    - **State of the system**: Game is started, target player is dead
    - **Expected output**: Throws `IllegalArgumentException`

- **TC198: Play Nosy on valid target player** ( :white_check_mark: )
    - **State of the system**: Game is started, current player has a Nosy card, target is a different alive player
    - **Expected output**: Card removed from hand, card added to discard, target player's hand is returned
