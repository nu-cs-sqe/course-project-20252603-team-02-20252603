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
