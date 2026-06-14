# Integration Test Features

## F1: Alter Future Reorders Draw Pile

As a player, I want Alter Future to reorder the top draw pile cards only after
the played action resolves, so that the card effect works correctly with the
game's pending action flow.

### BDD Scenario: Resolve Alter Future

- **Given** a started game with a controlled draw pile
- **And** the current player has an Alter Future card
- **When** the current player plays Alter Future and the pending action resolves
- **Then** the top draw pile cards are reordered
- **And** the Alter Future card is in the discard pile

### Integrated Modules

- `Game`
- `Deck`
- `Player`
- `Card`

## F2: Curse Returns Defuse Cards To Draw Pile

As a player, I want Curse to remove Defuse cards from the next active player
only after the played action resolves, so that Curse works correctly with the
game's pending action flow and draw pile updates.

### BDD Scenario: Resolve Curse

- **Given** a started game with a controlled draw pile
- **And** the current player has a Curse card
- **And** the next player has Defuse cards
- **When** the current player plays Curse and the pending action resolves
- **Then** the next player has no Defuse cards
- **And** the Defuse cards are returned to the draw pile
- **And** the Curse card is in the discard pile

### Integrated Modules

- `Game`
- `Deck`
- `Player`
- `Card`
