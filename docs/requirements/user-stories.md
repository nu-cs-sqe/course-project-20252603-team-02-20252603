# User story: Start a new Game
As a player, I want the game to automatically set up the deck and players, so that we can start playing without manually organizing cards.
## Acceptance Criteria
* Game only starts with 3 to 5 players.
* Each player gets 7 random cards.
* Each player gets 1 Defuse card.
* Exploding Kitten cards added back = number of players - 1.
* Deck is shuffled after setup.
* Turn order is initialized.

# Use Case 1: Start New Game

Actor: Player

Preconditions:
 The game application is launched.

Main Flow:
* Player clicks “Start Game.”
* System asks for number of players.
* Player enters number of players.
* System validates player count.
* System shuffles deck.
* System removes Exploding Kitten cards.
* System deals 7 random cards to each player.
* System gives each player 1 Defuse card.
* System adds the correct number of Exploding Kitten cards.
* System shuffles deck again.
* System sets clockwise turn order.
* Game begins.

Alternate Flow:
4.a If player count is invalid, system shows an error.
4.b System asks for number of players again.

Postconditions:
Players have starting hands, deck is ready, and turn order is initialized.


# User Story: Set Up Game (2-Player Variant)
As a player, I want the game to follow the special 2-player setup rules, so that the game remains balanced.
## Acceptance Criteria
* The game starts with exactly 2 players.
* Each player receives 7 random cards.
* Each player receives 1 Defuse card.
* The system places only 2 additional Defuse cards back into the deck.
* The system adds 1 Exploding Kitten card to the deck.
* Any extra Exploding Kitten cards are removed from the game.
* The deck is shuffled after adding cards.
* The deck is placed face down as the draw pile.
* The system initializes the turn order.

# Use Case 2: Start 2-Player Game

Actor: Player
Preconditions:
The game application is launched.

Main Flow:
* Player clicks “Start Game.”
* Player enters 2 players.
* System deals 7 random cards to each player.
* System gives each player 1 Defuse card.
* System adds 2 extra Defuse cards to the deck.
* System adds 1 Exploding Kitten card.
* System removes extra Exploding Kitten cards.
* System shuffles deck.
* System sets clockwise turn order.

Postconditions:
2-player game is balanced and ready to begin.

# User story: Draw a Card
As a player, I want to draw a card when it is my turn to play.
## Acceptance Criteria
* The current player draws exactly 1 card from the deck.
* If the card is not an Exploding Kitten, it is added to the player’s hand.
* If the card is an Exploding Kitten, the system checks whether the player has a Defuse card.
* After drawing, the turn moves to the next player unless the player explodes.
* Use Case 3: Take a Turn
* Actor: Current Player
* Preconditions:
* Game is in progress and it is the player’s turn.

Main Flow:
* System displays current player’s hand.
* Player may play any number of action cards.
* System applies card effects.
* Player draws 1 card unless a card effect skips drawing.
* System updates hand/deck.
* Turn moves clockwise to next active player.
Alternate Flow:
4.a If player draws Exploding Kitten, system starts Defuse/Elimination use case.
Postconditions:
Turn is completed or player is eliminated.

# User Story: Defuse an Exploding Kitten
As a player, I want to use a Defuse card when I draw an Exploding Kitten, so that I can stay in the game.

## Acceptance Criteria
* If the player has a Defuse card, it is removed from their hand.
* The Exploding Kitten card is placed back into the deck.
* The player remains alive.
* The deck is updated after the kitten is returned.
* If the player has no Defuse card, they are eliminated.
* Use Case 4: Defuse Exploding Kitten
* Actor: Current Player
* Preconditions:
* Player draws an Exploding Kitten.
* Main Flow:
* System checks if player has Defuse.
* Player uses Defuse.
* System removes Defuse from hand.
* System places Defuse in discard pile.
* System lets player return Exploding Kitten to deck.
* Player remains active.
Alternate Flow:
1.a If player has no Defuse, system eliminates player.
Postconditions:
Player survives..

# User Story: Eliminate a Player
As a player, I want the game to eliminate players who explode without a Defuse card, so that the game follows the rules.
* ## Acceptance Criteria
* A player is eliminated if they draw an Exploding Kitten and cannot defuse it.
* Eliminated players are removed from the turn order.
* Eliminated players cannot take future turns.
* The Exploding Kitten card is removed from the game (not returned to the deck)
* The game continues if more than one player remains.
* The game ends if only one player remains.


# User story: Play an Action Card
As a player, I want to play action cards from my hand, so that I can affect the game (this is done before drawing).
## Acceptance Criteria
* The player can select a card from their hand.
* The selected card is removed from their hand after being played.
* The card’s effect is applied correctly.
* The played card is placed in the discard pile.
* The player still needs to draw unless the card effect says otherwise. Note below for the cards that allow you to skip drawings when it’s your turn to draw.
* Skip: This card immediately ends your turn, and you do not draw a card. Attack (2x): This card immediately ends your current turn (or turns) without drawing and forces the next player to take two turns in a row.


# User Story: Skip a Turn
As a player, I want to play a Skip card, so that I can end my turn without drawing.
* ## Acceptance Criteria
* The Skip card is removed from the player’s hand.
* The player does not draw a card.
* The turn immediately moves to the next player.
* The Skip card is placed in the discard pile.


# User Story: Attack Another Player
As a player, I want to play an Attack card, so that the next player must take extra turns.
## Acceptance Criteria
* The Attack card is discarded after being played.
* The current player’s turn ends without drawing.
* The next player must take 2 turns.
* If the next player plays another Attack card, the remaining turns are passed forward plus 2 more turns.
* Attack can be canceled by Nope.

# Use Case 5: Play Attack Card

Actor: Current Player
Preconditions:
Player has an Attack card.
Main Flow:
* Player selects Attack.
* System places Attack in discard pile.
* Current player’s turn ends without drawing.
* Next clockwise player receives 2 required turns.
* System updates turn count.

Alternate Flow:

Postconditions:
Turn count is updated correctly.

# User Story: Targeted Attack (Attack Any Player)
As a player, I want to play a targeted Attack card, so that I can choose which player must take extra turns.
## Acceptance Criteria
* The player selects any active player as the target.
* The Attack card is discarded after being played.
* The current player’s turn ends without drawing.
* The chosen target must take 2 turns.
* If the target already has pending turns, the turns stack (existing + 2).
* The targeted player may respond with:
* Attack → pass all turns + 2 to another player
* Skip → reduce turns owed
* The effect can be canceled by a Nope card.
* The game correctly updates turn order based on the targeted player.


# Use Case 6: Play Targeted Attack
Actor: Current Player
Preconditions:
Player has Targeted Attack.
Main Flow:
* Player selects Targeted Attack.
* Player chooses any active player.
* System places card in discard pile.
* Current player’s turn ends without drawing.
* Chosen player must complete 2 required turns.

Alternate Flow:
5.a If target already has pending turns, system adds 2 more.
5.b If Nope is played, effect is canceled.
Postconditions:
Chosen player becomes responsible for the required turns.

# User Story: Play a Nope Card
As a player, I want to play a Nope card, so that I can cancel another player’s action card.
## Acceptance Criteria
* A Nope card can be played immediately after another action card is played.
* Nope cancels the effect of the action card.
* Nope cannot cancel an Exploding Kitten or Defuse.
* Another Nope can be played to cancel the first Nope.
* All played Nope cards go to the discard pile.

# Use Case 7: Play Nope Card
Actor: Any active player
Preconditions:
Another player has just played a cancelable action card.
Main Flow:
* Player plays Nope.
* System cancels the action card effect.
* System places Nope in discard pile.
Alternate Flow:
2.a Another player plays Nope on the Nope, so the original action becomes active again.
Postconditions:
Action is canceled or restored depending on number of Nopes.

# User Story: Ask for a Random Card / Favor
As a player, I want to play a Favor card, so that I can force another player to give me a card.
## Acceptance Criteria
* The current player chooses another active player.
* The chosen player gives one card from their hand to the current player.
* The card may be chosen randomly or by the chosen player, depending on the card’s instructions.
* The Favor card/cards go to the discard pile.
* Other players may play Nope to cancel the Favor.


# User Story: See the Future
As a player, I want to play See the Future, so that I can look at upcoming cards before deciding what to do.
## Acceptance Criteria
* The player sees the top 3 cards of the deck.
* The order of those cards does not change.
* Other players cannot see those cards.
* The card goes to the discard pile.
* Other players may play Nope to cancel it.


# User Story: Alter the Future
As a player, I want to play an Alter the Future card, so that I can look at and rearrange upcoming cards in the deck.
## Acceptance Criteria
* The player looks at the top 3 cards of the deck.
* The player may rearrange those 3 cards in any order.
* The rearranged cards are placed back on top of the deck.
* Other players cannot see the cards.
* The Alter the Future card goes to the discard pile.
* Other players may play Nope to cancel Alter the Future.

# User Story: Shuffle the Deck
As a player, I want to play Shuffle, so that I can randomize the deck when I think danger is coming.
## Acceptance Criteria
* The deck is shuffled randomly.
* The Shuffle card goes to the discard pile.
* Other players may play Nope to cancel the Shuffle.

# User Story: View Player Hand
As a player, I want to see the cards in my hand, so that I can decide what card to play.
## Acceptance Criteria
* The system displays all cards in the current player’s hand.
* The player can only play cards from their own hand.
* The hand updates after drawing, playing, or discarding cards.

# User Story: Manage Turn Order and Turns
As a player, I want the game to manage turn order and track multiple turns, so that gameplay flows correctly and Attack/Skip effects are handled properly.
## Acceptance Criteria
* Players are arranged in a clockwise order at the start of the game.
* The system selects a starting player and tracks the current player.
* Turns proceed clockwise to the next active player.
* Each player starts with 1 required turn.
* A turn consists of:
* Playing any number of action cards (optional), then
* Drawing a card (unless skipped)

# User Story: Check Game Winner
As a player, I want the game to detect when there is only one player left, so that the winner can be declared automatically.
## Acceptance Criteria
* The system checks the number of active players after each elimination.
* If only one player remains, the game ends.
* The remaining player is declared the winner.
* No more turns can be taken after the game ends.

# Use Case 8: Check Winner
Actor: System
Preconditions:
A player has been eliminated.
Main Flow:
* System counts active players.
* If more than one player remains, game continues.
* If one player remains, system declares winner.
* System ends game.
Postconditions:
Game either continues or ends with a winner.






