# BVA Analysis for Player

## Method under test: `isAlive()`

###  Step 1
- **input**: player's state (alive field)
- **output**: yes or no answer

###  Step 2
- **input**: boolean 
- **output**: boolean

###  Step 3
- **input**: true, false
- **output**: true, false

###  Step 4

|             | System under test             | Expected output | Implemented?       |
|-------------|-------------------------------|-----------------|--------------------|
| Test Case 1 | player's alive field is true  | true            | :white_check_mark: |
| Test Case 2 | player's alive field is false | false           | :white_check_mark: |


## Method under test: `die()`

###  Step 1
- **input**: player's state (alive field)
- **output**: updated player's state

###  Step 2
- **input**: boolean
- **output**: boolean

###  Step 3
- **input**: true, false
- **output**: false

###  Step 4

|             | System under test             | Expected output                                | Implemented?       |
|-------------|-------------------------------|------------------------------------------------|--------------------|
| Test Case 3 | player's alive field is true  | isAlive() returns false after die() call       | :white_check_mark: |
| Test Case 4 | player's alive field is false | isAlive() still returns false after die() call | :white_check_mark: |


## Method under test: `getHand()`

###  Step 1
- **input**: player’s hand field
- **output**: the player’s hand (list of cards)

###  Step 2
- **input**: collection
- **output**: collection

###  Step 3
- **input**: empty, one element, multiple elements, contains duplicate elements
- **output**: empty, one element, multiple elements, contains duplicate elements

###  Step 4

|             | System under test                           | Expected output                          | Implemented?       |
|-------------|---------------------------------------------|------------------------------------------|--------------------|
| Test Case 5 | player has empty hand                       | empty list returned                      | :white_check_mark: |
| Test Case 6 | player's hand is [DEFUSE]                   | [DEFUSE] hand returned                   | :white_check_mark: |
| Test Case 7 | player's hand is [DEFUSE, EXPLODING_KITTEN] | [DEFUSE, EXPLODING_KITTEN] hand returned | :white_check_mark: |
| Test Case 8 | player's hand is [ATTACK, ATTACK]           | [ATTACK, ATTACK] hand returned           | :white_check_mark: |


## Method under test: `addCard()`

###  Step 1
- **input #1**: Card object being added
- **input #2**: player's current hand
- **output #1**: successful or not successful
- **output #2**: player's updated hand

###  Step 2
- **input #1**: pointer/reference
- **input #2**: collection
- **output #1** cases
- **output #2**: collection

###  Step 3
- **input #1**: null pointer, pointer to Card object
- **input #2**: empty, one element, multiple elements, contain duplicates
- **output #1**: case 1: void | case 2: IllegalArgumentException, "invalid card"
- **output #2**: empty, one element, multiple elements, contain duplicates

###  Step 4

|              | System under test                                           | Expected output                                     | Implemented?       |
|--------------|-------------------------------------------------------------|-----------------------------------------------------|--------------------|
| Test Case 9  | player has empty hand, add DEFUSE card                      | player's hand is [DEFUSE]                           | :white_check_mark: |
| Test Case 10 | player has [DEFUSE] hand, add SKIP card                     | player's hand is [DEFUSE, SKIP]                     | :white_check_mark: |
| Test Case 11 | player has [DEFUSE, EXPLODING_KITTEN] hand, add ATTACK card | player's hand is [DEFUSE, EXPLODING_KITTEN, ATTACK] | :white_check_mark: |
| Test Case 12 | player has [DEFUSE, EXPLODING_KITTEN] hand add DEFUSE card  | player's hand is [DEFUSE, EXPLODING_KITTEN, DEFUSE] | :white_check_mark: |
| Test Case 13 | player has [ATTACK, ATTACK] hand, add DEFUSE card           | player's hand is [ATTACK, ATTACK, DEFUSE]           | :white_check_mark: |
| Test Case 14 | player has [DEFUSE] hand, add null card                     | IllegalArgumentException, "invalid card"            | :white_check_mark: |
(test 12 vs 11: resultant vector has doesn't have duplicates vs does have duplicates after addCard())
(test 14: might modify later on when we revise error handling)


## Method under test: `removeCard()`

###  Step 1
- **input #1**: Card object being removed
- **input #2**: player's current hand
- **output #1**: successful or not successful
- **output #2**: player's updated hand

###  Step 2
- **input #1**: pointer/reference
- **input #2**: collection
- **output #1** cases
- **output #2**: collection

###  Step 3
- **input #1**: null pointer, pointer to Card object
- **input #2**: empty, one element, multiple elements, contain duplicates
- **output #1**: case 1: void | case 2: IllegalArgumentException, "card not in hand" | case 3 IllegalArgumentException, "invalid card" | case 4: IllegalStateException, "empty hand, cannot play a card" 
- **output #2**: empty, one element, multiple elements, contain duplicates

###  Step 4

|              | System under test                                                         | Expected output                                           | Implemented?       |
|--------------|---------------------------------------------------------------------------|-----------------------------------------------------------|--------------------|
| Test Case 15 | player has [DEFUSE] hand, remove DEFUSE card                              | player's hand is empty                                    | :white_check_mark: |
| Test Case 16 | player has [DEFUSE, EXPLODING_KITTEN] hand, remove EXPLODING_KITTENS card | player's hand is [DEFUSE]                                 | :white_check_mark: |
| Test Case 17 | player has [ATTACK, ATTACK, SKIP] hand, remove ATTACK card                | player's hand is [ATTACK, SKIP]                           | :white_check_mark: |
| Test Case 18 | player has [ATTACK, ATTACK, SKIP] hand, remove SKIP card                  | player's hand is [ATTACK, ATTACK]                         | :white_check_mark: |
| Test Case 19 | player has empty hand, remove DEFUSE card                                 | IllegalStateException, "empty hand, cannot remove a card" | :white_check_mark: |          |
| Test Case 20 | player has [DEFUSE, ATTACK] hand, remove SKIP card                        | IllegalArgumentException, "card not in hand"              | :white_check_mark:                |
| Test Case 21 | player has [DEFUSE, ATTACK] hand, remove null                             | IllegalArgumentException, "invalid card                   | :x:                |
(test 17 vs 18: resultant hand has duplicates vs does not have duplicates after removeCard()))


## Method under test: `hasDefuse()`

###  Step 1
- **input**: player's current hand
- **output**: yes, no answer

###  Step 2
- **input**: collection
- **output**: boolean

###  Step 3
- **input**: empty, one element, multiple elements, contain duplicates
- **output**: true, false

###  Step 4

|              | System under test                               | Expected output | Implemented? |
|--------------|-------------------------------------------------|-----------------|--------------|
| Test Case 22 | player has empty hand                           | false           | :x:          |
| Test Case 23 | player has [DEFUSE] hand                        | true            | :x:          |
| Test Case 24 | player has [ATTACK, DEFUSE] hand                | true            | :x:          |
| Test Case 25 | player has [EXPLODING_KITTENS, SKIP, SKIP] hand | false           | :x:          |
| Test Case 26 | player has [DEFUSE, SKIP, DEFUSE] hand          | true            | :x:          |

## Method under test: `addTurn()`

###  Step 1
- **input**: player's state (turnsOwed field)
- **output**: player's updated state (updated turnsOwed field)

###  Step 2
- **input**: count
- **output**: count

###  Step 3
- **input**: 0, 1, >1
- **output**: 1, 2, >2

###  Step 4

|              | System under test             | Expected output                       | Implemented? |
|--------------|-------------------------------|---------------------------------------|--------------|
| Test Case 27 | player's turnsOwed field is 0 | player's updated turnsOwed field is 1 | :x:          |
| Test Case 28 | player's turnsOwed field is 1 | player's updated turnsOwed field is 2 | :x:          |
| Test Case 29 | player's turnsOwed field is 2 | player's updated turnsOwed field is 2 | :x:          |


## Method under test: `removeTurn()`

###  Step 1
- **input**: player's state (turnsOwed field)
- **output**: player's updated state (updated turnsOwed field)

###  Step 2
- **input**: count
- **output**: cases (case 1: player's updated turnsOwed field | case 2: IllegalStateException, "player has no turns to remove")

###  Step 3
- **input**: 0, 1, >1
- **output**: 0, 1, >1

###  Step 4

|              | System under test             | Expected output                                        | Implemented? |
|--------------|-------------------------------|--------------------------------------------------------|--------------|
| Test Case 30 | player's turnsOwed field is 0 | IllegalStateException, "player has no turns to remove" | :x:          |
| Test Case 31 | player's turnsOwed field is 1 | player's updated turnsOwed field is 0                  | :x:          |
| Test Case 32 | player's turnsOwed field is 2 | player's updated turnsOwed field is 1                  | :x:          |
| Test Case 33 | player's turnsOwed field is 4 | player's updated turnsOwed field is 3                  | :x:          |

