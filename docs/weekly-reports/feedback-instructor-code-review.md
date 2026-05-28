# Instructor Code Review Feedback

**Contact**: Dr. Yiji Zhang (yiji.zhang@northwestern.edu)

**Purpose of This Document**:
The instructor will perform code review with respect to software design, error handling, format and style on the main branch every week starting Week 6 using the letter grade A standards.
The following chapters of the textbook are considered: Chapter 1, 2, 3, 4, 5, 6, 7, 9, and 10. The corresponding lectures are considered, too.

Please note that this feedback does not include evaluation of your progress, the proper use of linters, the quality of your test cases, or your compliance of TDD/BDD workflow.  
You can find the weekly feedback from your dedicated PM/TA for that.

## Week 7-8 Code Review
This review is for the code your team developed in Week 7 and 8.
I apologize for this delayed code review (should have been given last Friday but I got really sick...).
As compensation, I will add one extra code review in Week 10 (around Thursday).

I have read all the code in the `main` branch. Good job overall! It is very obvious that the team is very aware of the coding standards and actively applying them.
A couple of comments:

1. The Deck constructor `public Deck(List<Player> players, Random random) `: Can be broken into many private methods. For instance, the for loop below can be encapsulated
into a private method called "setupStandardCards". Same principles can be apply to the rest of the constructor code too.
``` 
public Deck(List<Player> players, Random random) {
    this.numPlayers = players.size();
    this.random = new Random(random.nextLong());

    // set up deck
    for (int i = 0; i < STANDARD_CARD_COUNT; i++) { // 4 of each
      deck.add(new Card(CardType.FAVOR));
      deck.add(new Card(CardType.SHUFFLE));
      deck.add(new Card(CardType.NOPE));
      deck.add(new Card(CardType.BEARD_CAT));
      deck.add(new Card(CardType.CATTERMELON));
      deck.add(new Card(CardType.HAIRY_POTATO_CAT));
      deck.add(new Card(CardType.TACOCAT));
      deck.add(new Card(CardType.RAINBOW_RALPHING_CAT));
      deck.add(new Card(CardType.DRAW_FROM_BOTTOM));
      deck.add(new Card(CardType.ALTER_FUTURE));
      deck.add(new Card(CardType.FERAL_CAT));

    }
```
2. Your Game class is not unit testable right now. The test cases you have in GameTests are not unit tests but integration tests (integrating Game and Player). 
To make it unit testable, keep all the code you have in Game, and add a package private constructor to inject Player into Game. Like:
``` 
Game(List<Player> players){
  this.players = players;
}
```
And then mock or stub the players in your GameTests.
3. The method name `die()` made me chuckle :D (sorry for weird sense of humor).

The code quality is good overall! Look forward to seeing your final product!


## Week 6 Code Review
There is no code in the main branch yet so there's nothing for me to review. 

Look forward to more code in the next review!

Please approve and merge the PR once the team has read the feedback. Thanks!