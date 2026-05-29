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
- **TC#: All other players have cards** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Bubonic Plague card, all other players have at least 1 card in hand
  - **Expected output**: Card removed from hand, discarded, one random card removed from each other player's hand and added to draw pile, draw pile shuffled, empty list returned

- **TC#: One other player has no cards** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Bubonic Plague card, one other player has no cards in hand
  - **Expected output**: Card removed from hand, discarded, player with no cards is skipped, one random card removed from each other player with cards, draw pile shuffled, empty list returned

- **TC#: All other players have no cards** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Bubonic Plague card, all other players have empty hands
  - **Expected output**: Card removed from hand, discarded, no cards moved to draw pile, draw pile shuffled, empty list returned

- **TC#: Exactly one other player alive** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Bubonic Plague card, exactly 1 other player alive with cards
  - **Expected output**: Card removed from hand, discarded, one random card removed from that player's hand and added to draw pile, draw pile shuffled, empty list returned

- **TC#: Current player is not affected** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Bubonic Plague card, all other players have cards
  - **Expected output**: Current player's hand size unchanged, other players each lose one card

### Method under test: `playTargetedAttack()`
- **TC#: Targeted Attack, target is the next player in order** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Targeted Attack card, target is the next player in turn order, target is alive
  - **Expected output**: Card removed from hand, discarded, turn moves to target player, target player owes 2 turns, empty list returned

- **TC#: Targeted Attack, target is not the next player in order** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Targeted Attack card, target is a player further ahead in turn order, target is alive
  - **Expected output**: Card removed from hand, discarded, turn moves to target player skipping players in between, target player owes 2 turns, empty list returned

- **TC#: Targeted Attack, target is the only other player alive** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Targeted Attack card, exactly 1 other player alive
  - **Expected output**: Card removed from hand, discarded, turn moves to target player, target player owes 2 turns, empty list returned

- **TC#: Targeted Attack, target is a dead player** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Targeted Attack card, target player is not alive
  - **Expected output**: Throws `IllegalArgumentException`

- **TC#: Targeted Attack, target is the current player** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Targeted Attack card, target is themselves
  - **Expected output**: Throws `IllegalArgumentException`

### Method under test: `playSeeTheFuture()`
- **TC#51: Deck has 0 cards** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has See the Future, deck is empty
  - **Expected output**: Card removed from hand, discarded, throws `IllegalStateException`

- **TC#52: Deck has 1 card** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has See the Future, deck has 1 card
  - **Expected output**: Card removed from hand, discarded, list of 1 card returned

- **TC#53: Deck has exactly 3 cards** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has See the Future, deck has exactly 3 cards
  - **Expected output**: Card removed from hand, discarded, list of 3 cards returned

- **TC#54: Deck has more than 3 cards** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has See the Future, deck has more than 3 cards
  - **Expected output**: Card removed from hand, discarded, list of exactly 3 cards returned

### Method under test: `playSwap()`
- **TC19: Swap when draw pile has no cards** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Swap, Draw pile has no cards
  - **Expected output**: Card removed from hand, discarded, Draw pile unchanged

- **TC19: Swap when draw pile has 1 card** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Swap, Draw pile has 1 card
  - **Expected output**: Card removed from hand, discarded, Draw pile unchanged

- **TC20: Swap when draw pile has 2 cards** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Swap, Draw pile has 2 cards
  - **Expected output**: Card removed from hand, discarded, Draw pile has first and second cards swapped

- **TC21: Swap when draw pile has more than 2 cards** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Swap, Draw pile has 3 cards
  - **Expected output**: Card removed from hand, discarded, Draw pile has first and last cards swapped
  
### Method under test: `isCatCard()`
- **TC#52: type is null** ( :white_check_mark: )
  - **State of the system**: Game is running, card type is null 
  - **Expected output**: IllegalArgumentException, "invalid card"

- **TC#53: type is TACOCAT** ( :white_check_mark: )
  - **State of the system**: Game is running, card type is TACOCAT
  - **Expected output**: true 
  
- **TC#54: type is RAINBOW_RALPHING_CAT** ( :white_check_mark: )
  - **State of the system**: Game is running, card type is RAINBOW_RALPHING_CAT
  - **Expected output**: true

- **TC#55: type is BEARD_CAT** ( :white_check_mark: )
  - **State of the system**: Game is running, card type is BEARD_CAT
  - **Expected output**: true

- **TC#56: type is CATTERMELON** ( :white_check_mark: )
  - **State of the system**: Game is running, card type is CATTERMELON
  - **Expected output**: true

- **TC#57: type is FERAL_CAT** ( :white_check_mark: )
  - **State of the system**: Game is running, card type is FERAL_CAT
  - **Expected output**: true

- **TC#58: type is a non-cat playable card (ATTACK)** ( :white_check_mark: )
  - **State of the system**: Game is running, card type is ATTACK
  - **Expected output**: false

- **TC#59: type is EXPLODING_KITTEN** ( :white_check_mark: )
  - **State of the system**: Game is running, card type is EXPLODING_KITTEN
  - **Expected output**: false

- **TC#60: type is DEFUSE** ( :white_check_mark: )
  - **State of the system**: Game is running, card type is DEFUSE
  - **Expected output**: false

### Method under test: `isValidCatCombo()`
- **TC#61: list is null** ( :white_check_mark: )
  - **State of the system**: Game is running, cards list is null
  - **Expected output**: IllegalArgumentException

- **TC#62: list is empty (size 0)** ( :white_check_mark: )
  - **State of the system**: Game is running, cards list is empty
  - **Expected output**: IllegalArgumentException

- **TC#63: list has 1 card (below minimum valid size)** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT]
  - **Expected output**: false

- **TC#64: list has 2 cards — both same real cat** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT, TACOCAT]
  - **Expected output**: true

- **TC#65: list has 2 cards — two different real cats** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT, BEARD_CAT]
  - **Expected output**: false

- **TC#66: list has 2 cards — one FERAL_CAT + one real cat** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [FERAL_CAT, TACOCAT]
  - **Expected output**: true

- **TC#67: list has 2 cards — both FERAL_CAT** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [FERAL_CAT, FERAL_CAT]
  - **Expected output**: true

- **TC#68: list has 2 cards — one cat, one non-cat** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT, ATTACK]
  - **Expected output**: false

- **TC#69: list has 3 cards — all same real cat** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT, TACOCAT, TACOCAT]
  - **Expected output**: true

- **TC#70: list has 3 cards — 2 matching + 1 FERAL_CAT** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT, TACOCAT, FERAL_CAT]
  - **Expected output**: true

- **TC#71: list has 3 cards — 1 real cat + 2 FERAL_CAT** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT, FERAL_CAT, FERAL_CAT]
  - **Expected output**: true

- **TC#72: list has 3 cards — all FERAL_CAT** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [FERAL_CAT, FERAL_CAT, FERAL_CAT]
  - **Expected output**: true

- **TC#73: list has 3 cards — 3 different real cats** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT, BEARD_CAT, CATTERMELON]
  - **Expected output**: false

- **TC#74: list has 3 cards — 2 different real cats + 1 FERAL_CAT** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT, BEARD_CAT, FERAL_CAT]
  - **Expected output**: false

- **TC#75: list has 3 cards — includes a non-cat card** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT, TACOCAT, ATTACK]
  - **Expected output**: false

- **TC#76: list has 4 cards (gap between valid sizes)** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT, TACOCAT, TACOCAT, TACOCAT]
  - **Expected output**: false

- **TC#77: list has 5 cards — all 5 distinct real cat types** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT, HAIRY_POTATO_CAT, RAINBOW_RALPHING_CAT, BEARD_CAT, CATTERMELON]
  - **Expected output**: true

- **TC#78: list has 5 cards — 4 distinct real cats + 1 FERAL_CAT** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT, HAIRY_POTATO_CAT, RAINBOW_RALPHING_CAT, BEARD_CAT, FERAL_CAT]
  - **Expected output**: true

- **TC#79: list has 5 cards — duplicate real cat type present** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT, TACOCAT, RAINBOW_RALPHING_CAT, BEARD_CAT, CATTERMELON]
  - **Expected output**: false

- **TC#80: list has 5 cards — 2 FERAL_CAT (duplicate feral)** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT, HAIRY_POTATO_CAT, RAINBOW_RALPHING_CAT, FERAL_CAT, FERAL_CAT]
  - **Expected output**: false

- **TC#81: list has 5 cards — includes a non-cat card** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT, HAIRY_POTATO_CAT, RAINBOW_RALPHING_CAT, BEARD_CAT, ATTACK]
  - **Expected output**: false

- **TC#82: list has 6 cards (above maximum valid size)** ( :white_check_mark: )
  - **State of the system**: Game is running, cards = [TACOCAT, TACOCAT, TACOCAT, TACOCAT, TACOCAT, TACOCAT]
  - **Expected output**: false

### Method under test: `playTwoMatchingCats()`
- **TC#83: target is null** ( :white_check_mark: )
  - **State of the system**: `current player has 2 matching cats, target is null
  - **Expected output**: Throws `IllegalArgumentException`, "target cannot be null or dead"

- **TC#84: target is dead** ( :white_check_mark: )
  - **State of the system**: current player has 2 matching cats, target player is not alive
  - **Expected output**: Throws `IllegalArgumentException`, "target cannot be null or dead"

- **TC#85: target is current player** ( :white_check_mark: )
  - **State of the system**: current player has 2 matching cats, target == currentPlayer
  - **Expected output**: Throws `IllegalArgumentException`, "cannot target yourself"

- **TC#86: combo is invalid because cats do not match** ( :white_check_mark: )
  - **State of the system**: cards = [TACOCAT, BEARD_CAT], target is valid
  - **Expected output**: Throws `IllegalArgumentException`, "invalid two-cat combo"

- **TC#87: combo is invalid because size is 3 instead of 2** ( :white_check_mark: )
  - **State of the system**: cards = [TACOCAT, TACOCAT, TACOCAT], target is valid
  - **Expected output**: Throws `IllegalArgumentException`, "invalid two-cat combo"

- **TC#88: cards are not in current player's hand** ( :white_check_mark: )
  - **State of the system**: current player's hand does not contain the passed cards, target is valid
  - **Expected output**: Throws `IllegalArgumentException`, "cards not in hand"

- **TC#89: current player has only 1 of the 2 required cards** ( :white_check_mark: )
  - **State of the system**: current player has 1 TACOCAT in hand, cards = [TACOCAT, TACOCAT], target is valid
  - **Expected output**: Throws `IllegalArgumentException`, "cards not in hand"

- **TC#90: target has exactly 1 card in hand** ( :white_check_mark: )
  - **State of the system**: current player has valid 2-card combo in hand, target hand = [SKIP]
  - **Expected output**: Both cat cards removed from current player's hand, both cards added to discard pile, SKIP transferred to current player, target hand is empty, returns SKIP

- **TC#91: target has exactly 2 cards in hand** ( :white_check_mark: )
  - **State of the system**: current player has valid 2-card combo in hand, target hand = [SKIP, ATTACK]
  - **Expected output**: Both cat cards removed from current player's hand, both cards added to discard pile, exactly 1 target card transferred to current player, target hand has 1 card remaining, returns stolen card

- **TC#92: target has many cards in hand** ( :white_check_mark: )
  - **State of the system**: current player has valid 2-card combo in hand, target hand has 5 cards
  - **Expected output**: Both cat cards removed from current player's hand, both cards added to discard pile, exactly 1 target card transferred to current player, target hand size goes from 5 to 4, returns stolen card

### Method under test: `playThreeMatchingCats()`
- **TC#94: target is null** ( :white_check_mark: )
  - **State of the system**: current player has valid 3-cat combo, named card = FAVOR
  - **Expected output**: Throws `IllegalArgumentException`, "target cannot be null or dead"

- **TC#95: target is dead** ( :white_check_mark: )
  - **State of the system**: current player has valid 3-cat combo, target player is not alive, named card = FAVOR
  - **Expected output**: Throws `IllegalArgumentException`, "target cannot be null or dead"

- **TC#96: target is current player** ( :white_check_mark: )
  - **State of the system**: current player has valid 3-cat combo, target == currentPlayer, named card = FAVOR
  - **Expected output**: Throws `IllegalArgumentException`, "cannot target yourself"

- **TC#97: named is null** ( :white_check_mark: )
  - **State of the system**: current player has valid 3-cat combo, target is valid, named card = null
  - **Expected output**: Throws `IllegalArgumentException`, "invalid wanted card type"

- **TC#98: named is EXPLODING_KITTEN** ( :white_check_mark: )
  - **State of the system**: current player has valid 3-cat combo, target is valid, named card = EXPLODING_KITTEN
  - **Expected output**: Throws `IllegalArgumentException`, "invalid wanted card type"

- **TC#99: combo is invalid because cats do not match** ( :white_check_mark: )
  - **State of the system**: cards = [TACOCAT, BEARD_CAT, CATTERMELON], target is valid, named card = FAVOR
  - **Expected output**: Throws `IllegalArgumentException`, "invalid three-cat combo"

- **TC#100: combo is invalid because size is 2 instead of 3** ( :white_check_mark: )
  - **State of the system**: cards = [TACOCAT, TACOCAT], target is valid, named = FAVOR
  - **Expected output**: Throws `IllegalArgumentException`, "invalid three-cat combo"

- **TC#101: cards are not in current player's hand** ( :white_check_mark: )
  - **State of the system**: combo cards are not held by current player, target is valid, named card = FAVOR
  - **Expected output**: Throws `IllegalArgumentException`, "card not in hand"

- **TC#102: target has 0 copies of named card** ( :white_check_mark: )
  - **State of the system**: current player has valid 3-cat combo in hand, target has no FAVOR, named card = FAVOR
  - **Expected output**: Three cats removed from current player's hand, three cats added to discard pile, no card transferred, returns false

- **TC#103: target has exactly 1 copy of named card** ( :white_check_mark: )
  - **State of the system**: current player has valid 3-cat combo in hand, target hand = [FAVOR], named card = FAVOR
  - **Expected output**: Three cats removed from current player's hand, three cats added to discard pile, FAVOR transferred to current player, target hand is empty, returns true

- **TC#104: target has exactly 2 copies of named card** ( :white_check_mark: )
  - **State of the system**: current player has valid 3-cat combo in hand, target hand = [FAVOR, FAVOR], named card = FAVOR
  - **Expected output**: Three cats removed from current player's hand, three cats added to discard pile, exactly 1 FAVOR transferred, target still has 1 FAVOR, returns true

- **TC#105: target has named card plus other cards** ( :white_check_mark: )
  - **State of the system**: current player has valid 3-cat combo in hand, target hand = [FAVOR, ATTACK, SKIP], named card = FAVOR
  - **Expected output**: Three cats removed from current player's hand, three cats added to discard pile, FAVOR transferred, target hand = [ATTACK, SKIP], returns true

### Method under test: `playFiveDifferentCats()`
- **TC#107: cards is null** ( :white_check_mark: )
  - **State of the system**: cards = null, wanted_card = FAVOR
  - **Expected output**: Throws `IllegalArgumentException`, "cards cannot be null"

- **TC#108: wanted_card is null** ( :white_check_mark: )
  - **State of the system**: Current player has valid 5-card combo, wanted_card = null
  - **Expected output**: Throws `IllegalArgumentException`, "invalid wanted card type"

- **TC#109: wanted_card is EXPLODING_KITTEN** ( :white_check_mark: )
  - **State of the system**: Current player has valid 5-card combo, wanted_card = EXPLODING_KITTEN
  - **Expected output**: Throws `IllegalArgumentException`, "invalid wanted card type"

- **TC#110: combo is invalid because duplicate cat type is present** ( :white_check_mark: )
  - **State of the system**: Cards = [TACOCAT, TACOCAT, RAINBOW_RALPHING_CAT, BEARD_CAT, CATTERMELON], wanted_card = FAVOR
  - **Expected output**: Throws `IllegalArgumentException`, "invalid five-cat combo"

- **TC#111: combo is invalid because size is 3 instead of 5** ( :white_check_mark: )
  - **State of the system**: Cards = [TACOCAT, TACOCAT, TACOCAT], wanted_card = FAVOR
  - **Expected output**: Throws `IllegalArgumentException`, "invalid five-cat combo"

- **TC#112: cards are not in current player's hand** ( :white_check_mark: )
  - **State of the system**: Valid 5-card combo shape, but current player does not hold those cards
  - **Expected output**: Throws `IllegalArgumentException`, "cards not in hand"

- **TC#113: discard pile is empty** ( :white_check_mark: )
  - **State of the system**: Valid combo in hand, discard pile is empty, wanted_card = FAVOR
  - **Expected output**: Throws `IllegalArgumentException`, "wanted card not in discard pile"

- **TC#114: discard has 1 card that does not match wanted_card** ( :white_check_mark: )
  - **State of the system**: Discard has [ATTACK], wanted_card = FAVOR
  - **Expected output**: Throws `IllegalArgumentException`, "wanted card not in discard pile"

- **TC#115: discard has 1 card that matches wanted_card** ( :white_check_mark: )
  - **State of the system**: Discard has [FAVOR], wanted_card = FAVOR
  - **Expected output**: Five cats removed from hand and discarded, FAVOR transferred to current player, returns FAVOR

- **TC#116: discard has 2 copies of wanted_card** ( :white_check_mark: )
  - **State of the system**: Discard has [FAVOR, FAVOR], wanted_card = FAVOR
  - **Expected output**: Five cats removed from hand and discarded, exactly 1 FAVOR transferred, discard still has 1 FAVOR remaining, returns FAVOR

- **TC#117: discard has wanted_card among other cards** ( :white_check_mark: )
  - **State of the system**: Discard has [ATTACK, FAVOR, SKIP], wanted_card = FAVOR
  - **Expected output**: Five cats removed from hand and discarded, FAVOR transferred, ATTACK and SKIP remain in discard, returns FAVOR

### Method under test: `playCatCards()`
- **TC#118: game has not been launched** ( :white_check_mark: )
  - **State of the system**: gameLaunched = false, cards = [TACOCAT, TACOCAT], target = player2, named = null
  - **Expected output**: Throws `IllegalStateException`, "game has not started"

- **TC#119: game is over** ( :white_check_mark: )
  - **State of the system**: gameOver = true, cards = [TACOCAT, TACOCAT], target = player2, named = null
  - **Expected output**: Throws `IllegalStateException`, "game is over"

- **TC#120: cards is null** ( :white_check_mark: )
  - **State of the system**: Game running, cards = null, target = player2, named = FAVOR
  - **Expected output**: Throws `IllegalArgumentException`, "cards cannot be null or empty"

- **TC#121: combo is invalid — size 1** ( :white_check_mark: )
  - **State of the system**: Game running, cards = [TACOCAT], target = player2, named = null
  - **Expected output**: Throws `IllegalArgumentException`, "invalid cat combo"

- **TC#122: combo is invalid — size 4** ( :white_check_mark: )
  - **State of the system**: Game running, cards = [TACOCAT, TACOCAT, TACOCAT, TACOCAT], target = player2, named = null
  - **Expected output**: Throws `IllegalArgumentException`, "invalid cat combo"

- **TC#123: valid 2-cat combo, target has 1 card — routes to playTwoMatchingCats** ( :white_check_mark: )
  - **State of the system**: Game running, current player has [TACOCAT, TACOCAT], target has [SKIP]
  - **Expected output**: Two `TACOCAT`s added to discard, `SKIP` transferred to current player, target hand empty

- **TC#124: valid 3-cat combo, target has named card — routes to playThreeMatchingCats** ( :white_check_mark: )
  - **State of the system**: Game running, current player has [TACOCAT, TACOCAT, TACOCAT], target has `FAVOR`, named = `FAVOR`
  - **Expected output**: Three `TACOCAT`s added to discard, `FAVOR` transferred to current player

- **TC#125: valid 3-cat combo, target lacks named card — routes to playThreeMatchingCats** ( :white_check_mark: )
  - **State of the system**: Game running, current player has [TACOCAT, TACOCAT, TACOCAT], target has no `FAVOR`, named = `FAVOR`
  - **Expected output**: Three `TACOCAT`s added to discard, nothing transferred

- **TC#126: valid 5-cat combo, named card in discard — routes to playFiveDifferentCats** ( :white_check_mark: )
  - **State of the system**: Game running, current player has [TACOCAT, HAIRY_POTATO_CAT, RAINBOW_RALPHING_CAT, BEARD_CAT, CATTERMELON], discard has `FAVOR`, named = `FAVOR`
  - **Expected output**: Five cats added to discard, `FAVOR` transferred to current player

- **TC#127: valid 5-cat combo, named card not in discard — routes to playFiveDifferentCats** ( :white_check_mark: )
  - **State of the system**: Game running, current player has [TACOCAT, HAIRY_POTATO_CAT, RAINBOW_RALPHING_CAT, BEARD_CAT, CATTERMELON], discard has no `FAVOR`, named = `FAVOR`
  - **Expected output**: Throws `IllegalArgumentException`, "card type not in discard pile"

### Method under test: `playNeko()`
- **TC#128: cards is null** ( :white_check_mark: )
  - **State of the system**: cards = null
  - **Expected output**: Throws `IllegalArgumentException`, "cards cannot be null"

- **TC#129: cards has 0 cards** ( :white_check_mark: )
  - **State of the system**: cards = []
  - **Expected output**: Throws `IllegalArgumentException`, "must play exactly 3 neko cards"

- **TC#130: cards has 1 NEKO** ( :white_check_mark: )
  - **State of the system**: current player has 1 NEKO, cards = [NEKO]
  - **Expected output**: Throws `IllegalArgumentException`, "must play exactly 3 neko cards"

- **TC#131: cards has 2 NEKOs** ( :white_check_mark: )
  - **State of the system**: current player has 2 NEKOs, cards = [NEKO, NEKO]
  - **Expected output**: Throws `IllegalArgumentException`, "must play exactly 3 neko cards"

- **TC#132: cards has exactly 3 NEKOs** ( :white_check_mark: )
  - **State of the system**: current player has 3 NEKOs in hand
  - **Expected output**: All 3 `NEKO`s discarded, all other players dead, `gameOver = true`

- **TC#133: cards has 4 NEKOs** ( :white_check_mark: )
  - **State of the system**: current player has 4 NEKOs, cards = [NEKO, NEKO, NEKO, NEKO]
  - **Expected output**: Throws `IllegalArgumentException`, "must play exactly 3 neko cards"

- **TC#134: cards has 3 cards but one is null** ( :white_check_mark: )
  - **State of the system**: cards = [NEKO, null, NEKO]
  - **Expected output**: Throws `IllegalArgumentException`, "all cards must be neko cards"

- **TC#135: cards has 3 cards but one is not NEKO** ( :white_check_mark: )
  - **State of the system**: cards = [NEKO, NEKO, ATTACK]
  - **Expected output**: Throws `IllegalArgumentException`, "all cards must be neko cards"

- **TC#136: cards has 3 NEKOs but current player only has 2 in hand** ( :white_check_mark: )
  - **State of the system**: current player hand has only 2 NEKO cards, cards = [NEKO, NEKO, NEKO]
  - **Expected output**: Throws `IllegalArgumentException`, "cards not in hand"

- **TC#137: cards has 3 NEKOs, current player has exactly 3 in hand, 2 other players alive** ( :white_check_mark: )
  - **State of the system**: with 3 players, current player has 3 NEKOs
  - **Expected output**: All 3 `NEKO`s discarded, both other players dead, `gameOver = true`, current player still alive

- **TC#138: cards has 3 NEKOs, current player has exactly 3 in hand, 4 other players alive** ( :white_check_mark: )
  - **State of the system**: with 5 players, current player has 3 NEKOs
  - **Expected output**: All 3 `NEKO`s discarded, all 4 other players dead, `gameOver = true`, current player still alive

- **TC#139: cards has 3 NEKOs, one other player is already dead** ( :white_check_mark: )
  - **State of the system**: with 3 players, one opponent already dead, current player has 3 NEKOs
  - **Expected output**: All 3 `NEKO`s discarded, remaining alive opponent now dead, `gameOver = true`, current player still alive

### Method under test: `playFavor()`
- **TC#140: target is null** ( :white_check_mark: )
  - **State of the system**: target = null, given = SKIP
  - **Expected output**: Throws `IllegalArgumentException`, "invalid target"

- **TC#141: target is dead** ( :white_check_mark: )
  - **State of the system**: target.alive = false, given = SKIP
  - **Expected output**: Throws `IllegalArgumentException`, "invalid target"

- **TC#142: target is current player** ( :white_check_mark: )
  - **State of the system**: target == currentPlayer, given = SKIP
  - **Expected output**: Throws `IllegalArgumentException`, "cannot target yourself"

- **TC#143: given is null** ( :white_check_mark: )
  - **State of the system**: valid target, given = null
  - **Expected output**: Throws `IllegalArgumentException`, "given card cannot be null"

- **TC#144: target has empty hand** ( :white_check_mark: )
  - **State of the system**: valid target with 0 cards, given = SKIP
  - **Expected output**: Throws `IllegalArgumentException`, "target does not have that card"

- **TC#145: target has 1 card, given does not match** ( :white_check_mark: )
  - **State of the system**: target hand = [ATTACK], given = SKIP
  - **Expected output**: Throws `IllegalArgumentException`, "target does not have that card"

- **TC#146: target has exactly 1 card, given matches** ( :white_check_mark: )
  - **State of the system**: target hand = [SKIP], given = SKIP
  - **Expected output**: `SKIP` transferred to current player, target hand is now empty

- **TC#147: target has 2 cards, given matches one of them** ( :white_check_mark: )
  - **State of the system**: target hand = [SKIP, ATTACK], given = SKIP
  - **Expected output**: `SKIP` transferred to current player, target hand = [ATTACK]

- **TC#148: target has 2 copies of the given card** ( :white_check_mark: )
  - **State of the system**: target hand = [SKIP, SKIP], given = SKIP
  - **Expected output**: Exactly 1 `SKIP` transferred to current player, target hand still has 1 `SKIP`

### Method under test: `playAttack()`
- **TC#147: Attack, more than one other player alive** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Attack card, 2+ other players alive
  - **Expected output**: Card removed from hand, discarded, turn moves to next player, next player owes 2 turns, empty list returned

- **TC#148: Attack, exactly one other player alive** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Attack card, exactly 1 other player alive
  - **Expected output**: Card removed from hand, discarded, turn moves to that player, that player owes 2 turns, empty list returned

- **TC#149: Attack, current player owes 1 turn** ( :white_check_mark: )
  - **State of the system**: Game is running, current player has Attack card, current player owes 1 turn
  - **Expected output**: Turn moves to next player, next player owes 2 turns, empty list returned
