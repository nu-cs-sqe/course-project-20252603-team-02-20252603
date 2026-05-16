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

- **TC12: Initialize turn order for 5 players** ( :white_check_mark: )
  - **State of the system**: Game has 5 players
  - **Expected output**: Current player index is 0 and all players are in turn order

- **TC13: Initialize turn order before players exist** ( :white_check_mark: )
  - **State of the system**: Player list is empty
  - **Expected output**: Throws `IllegalStateException`

### Method under test: `getCurrentPlayer()`
- **TC14: Get first current player** ( :white_check_mark: )
  - **State of the system**: Current player index is 0
  - **Expected output**: Returns player at index 0

- **TC15: Get last current player** (not implemented)
  - **State of the system**: Current player index is 2 in a 3-player game
  - **Expected output**: Returns player at index 2

- **TC16: Get current player when index is out of bounds** (not implemented)
  - **State of the system**: Current player index is outside the player list
  - **Expected output**: Throws `IllegalStateException`

### Method under test: `runGame()`
- **TC17: Run game while game is not over** (not implemented)
  - **State of the system**: Game is launched and more than 1 player is alive
  - **Expected output**: Game handles turns until the game is over

- **TC18: Run game when game is already over** (not implemented)
  - **State of the system**: Game is over before `runGame()` is called
  - **Expected output**: No turns are handled and winner can be declared

- **TC19: Run game before startGame** ( :white_check_mark: )
  - **State of the system**: Game has not launched
  - **Expected output**: Throws `IllegalStateException`

- **TC20: Run game when game becomes over during a turn** (not implemented)
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

- **TC37: Complete turn when zero turns are owed** (not implemented)
  - **State of the system**: Current player has `turnsOwed = 0`
  - **Expected output**: Throws `IllegalStateException`

### Method under test: `drawCard()`
- **TC38: Draw normal card for current player** (not implemented)
  - **State of the system**: Top card is not `EXPLODING_KITTEN`; current player owes 1 turn
  - **Expected output**: Current player receives 1 card and one turn is completed

- **TC39: Draw last card from deck** (not implemented)
  - **State of the system**: Draw pile has exactly 1 card
  - **Expected output**: Current player receives that card and draw pile becomes empty

- **TC40: Draw from empty deck** (not implemented)
  - **State of the system**: Draw pile has 0 cards
  - **Expected output**: Throws `IllegalStateException`

- **TC41: Draw Exploding Kitten with Defuse** (not implemented)
  - **State of the system**: Current player draws `EXPLODING_KITTEN` and has a `DEFUSE`
  - **Expected output**: Defuse is used, player stays alive, and Exploding Kitten returns to draw pile

- **TC42: Draw Exploding Kitten without Defuse** (not implemented)
  - **State of the system**: Current player draws `EXPLODING_KITTEN` and has no `DEFUSE`
  - **Expected output**: Current player dies and game checks for winner

### Method under test: `playCard()`
- **TC43: Player plays a playable card** (not implemented)
  - **State of the system**: Game is started, game is not over, and current player has a playable card
  - **Expected output**: Card is removed from hand, card is discarded, and turn flow continues

- **TC44: Player tries to play a card not in hand** (not implemented)
  - **State of the system**: Selected card is not in current player's hand
  - **Expected output**: Throws `IllegalArgumentException`

- **TC45: Player tries to play a non-playable card** (not implemented)
  - **State of the system**: Selected card is not playable as an action card
  - **Expected output**: Throws `IllegalArgumentException`

- **TC46: Player tries to play before game starts** (not implemented)
  - **State of the system**: Game has not launched
  - **Expected output**: Throws `IllegalStateException`

- **TC47: Player tries to play after game is over** (not implemented)
  - **State of the system**: Game is over
  - **Expected output**: Throws `IllegalStateException`

### Method under test: `checkWinner()`
- **TC48: More than one player alive** (not implemented)
  - **State of the system**: 2 or more players are alive
  - **Expected output**: Game is not over

- **TC49: Exactly one player alive** (not implemented)
  - **State of the system**: 1 player is alive
  - **Expected output**: Game is over and that player is the winner

- **TC50: No players alive** (not implemented)
  - **State of the system**: 0 players are alive
  - **Expected output**: Throws `IllegalStateException`
