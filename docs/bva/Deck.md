### Method under test: `shuffle()`
- **TC1: Shuffle a standard populated draw pile** ( :white_check_mark: )
  - **State of the system**: 3 players; draw pile has 32 cards ((4×11) + 1 + 1 - (6-3) defuses already added, 7×3 cards dealt out, after Exploding Kittens inserted)
  - **Expected output**: Draw pile contains the same 32 cards in a different order (statistical check); size unchanged

- **TC2: Shuffle a draw pile with exactly 1 card** ( :white_check_mark: )
  - **State of the system**: Draw pile has exactly 1 card
  - **Expected output**: Draw pile still has 1 card; order trivially unchanged; no exception thrown

- **TC3: Shuffle an empty draw pile** ( :white_check_mark: )
  - **State of the system**: Draw pile is empty (0 cards)
  - **Expected output**: No exception thrown; draw pile remains empty

### Method under test: `drawCard()`
- **TC4: Draw from a deck with many cards** ( :white_check_mark: )
  - **State of the system**: Draw pile has x cards
  - **Expected output**: Returns the top card; draw pile size decreases to x - 1

- **TC5: Draw from a deck with exactly 1 card (lower boundary)** ( :white_check_mark: )
  - **State of the system**: Draw pile has exactly 1 card
  - **Expected output**: Returns that card; draw pile size becomes 0

- **TC6: Draw from an empty deck (below lower boundary)** ( :white_check_mark: )
  - **State of the system**: Draw pile has 0 cards
  - **Expected output**: Throws `IllegalStateException`

### Method under test: `discardCard()`
- **TC7: Discard a valid card to an empty discard pile** ( :white_check_mark: )
  - **State of the system**: Discard pile is empty; a valid Card object is passed
  - **Expected output**: Card is added to top of discard pile; discard pile size becomes 1

- **TC8: Discard when discard pile already has cards** ( :white_check_mark: )
  - **State of the system**: Discard pile has 10 cards
  - **Expected output**: Card added to top; discard pile size becomes 11

### Method under test: `addToDrawPile()`
- **TC9: Add a card to the top of a non-empty draw pile (upper boundary position)** ( :white_check_mark: )
  - **State of the system**: Draw pile has 20 cards; position = 0 (top)
  - **Expected output**: Card inserted at top; size becomes 21; card is drawn first on next drawCard()

- **TC10: Add a card to the bottom of a non-empty draw pile (lower boundary position)** ( :white_check_mark: )
  - **State of the system**: Draw pile has 20 cards; position = 20 (bottom)
  - **Expected output**: Card inserted at bottom; size becomes 21; card is last in draw order

- **TC11: Add a card to a middle position** ( :white_check_mark: )
  - **State of the system**: Draw pile has 20 cards; position = 10
  - **Expected output**: Card inserted at index 10; size becomes 21; surrounding cards maintain relative order

- **TC12: Add a card to an empty draw pile** ( :white_check_mark: )
  - **State of the system**: Draw pile has 0 cards; position = 0
  - **Expected output**: Draw pile has 1 card; that card is at the top and bottom; no exception thrown

- **TC13: Add a card at an out-of-bounds position (above upper boundary)** ( :white_check_mark: )
  - **State of the system**: Draw pile has 20 cards; position = 21
  - **Expected output**: Throws `IllegalArgumentException`

- **TC14: Add a card at a negative position (below lower boundary)** ( :white_check_mark: )
  - **State of the system**: Draw pile has 20 cards; position = -1
  - **Expected output**: Throws `IllegalArgumentException`

### Method under test: `peekTopCards()`
- **TC15: Peek when draw pile has more than 3 cards** ( :white_check_mark: )
  - **State of the system**: Draw pile has x cards
  - **Expected output**: Returns list of top 3 cards in order; draw pile unchanged

- **TC16: Peek when draw pile has exactly 3 cards (boundary)** ( :x: )
  - **State of the system**: Draw pile has exactly 3 cards
  - **Expected output**: Returns all 3 cards in order; draw pile unchanged

- **TC17: Peek when draw pile has fewer than 3 cards (below boundary)** ( :x: )
  - **State of the system**: Draw pile has exactly 2 cards
  - **Expected output**: Returns only the 2 available cards

- **TC18: Peek from an empty draw pile** ( :x: )
  - **State of the system**: Draw pile has 0 cards
  - **Expected output**: Throws `IllegalStateException`
