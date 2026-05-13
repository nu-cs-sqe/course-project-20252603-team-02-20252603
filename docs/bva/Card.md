# BVA Analysis for Card

### Method under test: `Card()`
#### 1.
- Input: CardType
- Output: Initialized Card
#### 2.
- Input: Cases
- Output: Cases
#### 3.
- Input:
  - EXPLODING_KITTEN, DEFUSE, ATTACK, SKIP, FAVOR, SHUFFLE, NOPE, SEE_THE_FUTURE, TACOCAT, HAIRY_POTATO_CAT, RAINBOW_RALPHING_CAT, BEARD_CAT, CATTERMELON, null
- Output:
  - Cases:
    - IllegalArgumentException
    - Card w/ CardTypes(EXPLODING_KITTEN, DEFUSE, ATTACK, SKIP, FAVOR, SHUFFLE, NOPE, SEE_THE_FUTURE, TACOCAT, HAIRY_POTATO_CAT, RAINBOW_RALPHING_CAT, BEARD_CAT, CATTERMELON)
#### 4.
- **TC1: Valid Initializations** ( :white_check_mark: )
  - **Inputs**: EXPLODING_KITTEN, DEFUSE, ATTACK, SKIP, FAVOR, SHUFFLE, NOPE, SEE_THE_FUTURE, TACOCAT, HAIRY_POTATO_CAT, RAINBOW_RALPHING_CAT, BEARD_CAT, CATTERMELON
  - **Outputs**: Card that contains corresponding type (EXPLODING_KITTEN, DEFUSE, ATTACK, SKIP, FAVOR, SHUFFLE, NOPE, SEE_THE_FUTURE, TACOCAT, HAIRY_POTATO_CAT, RAINBOW_RALPHING_CAT, BEARD_CAT, CATTERMELON)

- **TC2: Invalid Initialization** ( :white_check_mark: )
  - **Input**: null
  - **Output**: IllegalArgumentException

### Method under test: `equals()`
#### 1.
- Input: Card, Card
- Output: Equal or not
#### 2.
- Input: Cases, Cases
- Output: Boolean
#### 3.
- Input:
  - EXPLODING_KITTEN, DEFUSE, ATTACK, SKIP, FAVOR, SHUFFLE, NOPE, SEE_THE_FUTURE, TACOCAT, HAIRY_POTATO_CAT, RAINBOW_RALPHING_CAT, BEARD_CAT, CATTERMELON,
  - EXPLODING_KITTEN, DEFUSE, ATTACK, SKIP, FAVOR, SHUFFLE, NOPE, SEE_THE_FUTURE, TACOCAT, HAIRY_POTATO_CAT, RAINBOW_RALPHING_CAT, BEARD_CAT, CATTERMELON,
- Output: True, False
#### 4.
- **TC3: Card Equals Itself** ( :white_check_mark: )
  - Inputs: Cards w/ matching CardTypes
  - Outputs: True

- **TC4: Card Doesn't Equal Another Card** ( :white_check_mark: )
  - Inputs: Cards w/o matching CardTypes
  - Outputs: False