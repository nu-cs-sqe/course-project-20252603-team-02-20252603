### Method under test: `shuffle()`
- **TC1: Shuffle a standard populated draw pile** ( :x: )
  - **State of the system**: Draw pile has 52 cards (standard deck, no exploding kittens yet)
  - **Expected output**: Draw pile contains the same 52 cards in a different order (statistical check); size unchanged

- **TC2: Shuffle a draw pile with exactly 1 card** ( :x: )
  - **State of the system**: Draw pile has exactly 1 card
  - **Expected output**: Draw pile still has 1 card; order trivially unchanged; no exception thrown

- **TC3: Shuffle an empty draw pile** ( :x: )
  - **State of the system**: Draw pile is empty (0 cards)
  - **Expected output**: No exception thrown; draw pile remains empty

### Method under test: `drawCard()`
- **TC4: Draw from a deck with many cards** ( :x: )
  - **State of the system**: Draw pile has 52 cards
  - **Expected output**: Returns the top card; draw pile size decreases to 51

- **TC5: Draw from a deck with exactly 1 card (lower boundary)** ( :x: )
  - **State of the system**: Draw pile has exactly 1 card
  - **Expected output**: Returns that card; draw pile size becomes 0

- **TC6: Draw from an empty deck (below lower boundary)** ( :x: )
  - **State of the system**: Draw pile has 0 cards
  - **Expected output**: Throws `IllegalStateException`

### Method under test: `discardCard()`
- **TC7: Discard a valid card to an empty discard pile** ( :x: )
  - **State of the system**: Discard pile is empty; a valid Card object is passed
  - **Expected output**: Card is added to top of discard pile; discard pile size becomes 1

- **TC8: Discard when discard pile already has cards** ( :x: )
  - **State of the system**: Discard pile has 10 cards
  - **Expected output**: Card added to top; discard pile size becomes 11

### Method under test: `addToDrawPile()`
- **TC9: Add a card to the top of a non-empty draw pile (upper boundary position)** ( :x: )
  - **State of the system**: Draw pile has 20 cards; position = 0 (top)
  - **Expected output**: Card inserted at top; size becomes 21; card is drawn first on next drawCard()

- **TC10: Add a card to the bottom of a non-empty draw pile (lower boundary position)** ( :x: )
  - **State of the system**: Draw pile has 20 cards; position = 20 (bottom)
  - **Expected output**: Card inserted at bottom; size becomes 21; card is last in draw order

- **TC11: Add a card to a middle position** ( :x: )
  - **State of the system**: Draw pile has 20 cards; position = 10
  - **Expected output**: Card inserted at index 10; size becomes 21; surrounding cards maintain relative order

- **TC12: Add a card to an empty draw pile** ( :x: )
  - **State of the system**: Draw pile has 0 cards; position = 0
  - **Expected output**: Draw pile has 1 card; that card is at the top and bottom; no exception thrown

- **TC13: Add a card at an out-of-bounds position (above upper boundary)** ( :x: )
  - **State of the system**: Draw pile has 20 cards; position = 21
  - **Expected output**: Throws `IllegalArgumentException`

- **TC14: Add a card at a negative position (below lower boundary)** ( :x: )
  - **State of the system**: Draw pile has 20 cards; position = -1
  - **Expected output**: Throws `IllegalArgumentException`

### Method under test: `removeAllExplodingKittens()`
- **TC15: Remove when multiple exploding kittens are present** ( :x: )
  - **State of the system**: Draw pile contains 4 exploding kittens among other cards
  - **Expected output**: All 4 exploding kittens removed; all other cards remain; pile size decreases by 4

- **TC16: Remove when exactly 1 exploding kitten is present (lower boundary)** ( :x: )
  - **State of the system**: Draw pile contains exactly 1 exploding kitten
  - **Expected output**: That 1 card removed; all other cards intact

- **TC17: Remove when no exploding kittens are present (below lower boundary)** ( :x: )
  - **State of the system**: Draw pile has cards but zero exploding kittens
  - **Expected output**: No cards removed; pile unchanged; no exception thrown

- **TC18: Remove from an empty draw pile** ( :x: )
  - **State of the system**: Draw pile is empty
  - **Expected output**: No exception thrown; pile remains empty

### Method under test: `addExplodingKittens(int count)`
- **TC19: Add exploding kittens for minimum players (lower boundary, count = 1)** ( :x: )
  - **State of the system**: 2 players; draw pile has 40 cards (56 - 2×8); count = 1
  - **Expected output**: 1 exploding kitten added; pile size becomes 41

- **TC20: Add exploding kittens for mid-range players (count = 2)** ( :x: )
  - **State of the system**: 3 players; draw pile has 32 cards (56 - 3×8); count = 2
  - **Expected output**: 2 exploding kittens added; pile size becomes 34

- **TC21: Add exploding kittens for maximum players (upper boundary, count = 4)** ( :x: )
  - **State of the system**: 5 players; draw pile has 16 cards (56 - 5×8); count = 4
  - **Expected output**: 4 exploding kittens added; pile size becomes 20

- **TC22: Add 0 exploding kittens (below lower boundary)** ( :x: )
  - **State of the system**: Any valid player count; count = 0
  - **Expected output**: Throws `IllegalArgumentException`

- **TC23: Add a negative count (invalid input)** ( :x: )
  - **State of the system**: Any valid player count; count = -1
  - **Expected output**: Throws `IllegalArgumentException`

- **TC24: Add count exceeding max players - 1 (above upper boundary, count = 5)** ( :x: )
  - **State of the system**: Any draw pile state; count = 5
  - **Expected output**: Throws `IllegalArgumentException`

### Method under test: `addDefuseCards()`
- **TC25: Add defuse cards for minimum players (lower boundary, 2 players)** ( :x: )
  - **State of the system**: 2 players; draw pile has 40 cards after dealing
  - **Expected output**: 4 extra defuse cards (6 - 2) inserted into draw pile; pile size becomes 44

- **TC26: Add defuse cards for mid-range players (3 players)** ( :x: )
  - **State of the system**: 3 players; draw pile has 32 cards after dealing
  - **Expected output**: 3 extra defuse cards (6 - 3) inserted into draw pile; pile size becomes 35

- **TC27: Add defuse cards for maximum players (upper boundary, 5 players)** ( :x: )
  - **State of the system**: 5 players; draw pile has 16 cards after dealing
  - **Expected output**: 1 extra defuse card (6 - 5) inserted into draw pile; pile size becomes 17

- **TC28: Add defuse cards to an empty draw pile** ( :x: )
  - **State of the system**: Draw pile is empty (all cards dealt out somehow)
  - **Expected output**: Defuse cards added successfully; size equals number of defuses inserted; no exception thrown

### Method under test: `peekTopCards()`
- **TC29: Peek when draw pile has more than 3 cards** ( :x: )
  - **State of the system**: Draw pile has 52 cards
  - **Expected output**: Returns list of top 3 cards in order; draw pile unchanged

- **TC30: Peek when draw pile has exactly 3 cards (boundary)** ( :x: )
  - **State of the system**: Draw pile has exactly 3 cards
  - **Expected output**: Returns all 3 cards in order; draw pile unchanged

- **TC31: Peek when draw pile has fewer than 3 cards (below boundary)** ( :x: )
  - **State of the system**: Draw pile has exactly 2 cards
  - **Expected output**: Returns only the 2 available cards

- **TC32: Peek from an empty draw pile** ( :x: )
  - **State of the system**: Draw pile has 0 cards
  - **Expected output**: Throws `IllegalStateException`

### Method under test: `getLastCard()`
- **TC33: Get last card from a populated draw pile** ( :x: )
  - **State of the system**: Draw pile has 52 cards
  - **Expected output**: Returns the bottom card; draw pile unchanged

- **TC34: Get last card when exactly 1 card remains (boundary)** ( :x: )
  - **State of the system**: Draw pile has exactly 1 card
  - **Expected output**: Returns that card (it is simultaneously the top and bottom); pile unchanged

- **TC35: Get last card from an empty draw pile** ( :x: )
  - **State of the system**: Draw pile has 0 cards
  - **Expected output**: Throws `IllegalStateException`