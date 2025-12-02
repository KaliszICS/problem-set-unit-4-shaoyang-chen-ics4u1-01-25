import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ProblemSetTest {

    @Nested
    @DisplayName("Card Class Tests")
    class CardExecuteTests {

        @Test
        @DisplayName("Normal card creation")
        public void testCardCreation() {
            Card card = new Card("Ace", "Hearts", 1);
            assertNotNull(card);
            assertEquals("Ace", card.getName());
            assertEquals("Hearts", card.getSuit());
            assertEquals(1, card.getValue());
        }

        @Test
        @DisplayName("toString method")
        public void testCardToString() {
            Card card = new Card("Queen", "Diamonds", 12);
            assertEquals("Queen of Diamonds", card.toString());
        }

        @Test
        @DisplayName("equals method - same cards")
        public void testCardEqualsSame() {
            Card card1 = new Card("King", "Spades", 13);
            Card card2 = new Card("King", "Spades", 13);
            assertTrue(card1.equals(card2));
        }

        @Test
        @DisplayName("equals method - different cards")
        public void testCardEqualsDifferent() {
            Card card1 = new Card("10", "Hearts", 10);
            Card card2 = new Card("10", "Clubs", 10);
            assertFalse(card1.equals(card2));
        }

        @Test
        @DisplayName("equals method - null comparison")
        public void testCardEqualsNull() {
            Card card = new Card("Ace", "Diamonds", 1);
            assertFalse(card.equals(null));
        }

        @Test
        @DisplayName("Empty name exception")
        public void testCardEmptyName() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                new Card("", "Hearts", 1);
            });
            assertTrue(exception.getMessage().contains("Illegal input for card name"));
        }

        @Test
        @DisplayName("Null name exception")
        public void testCardNullName() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                new Card(null, "Hearts", 1);
            });
            assertTrue(exception.getMessage().contains("Illegal input for card name"));
        }

        @Test
        @DisplayName("Empty suit exception")
        public void testCardEmptySuit() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                new Card("Ace", "", 1);
            });
            assertTrue(exception.getMessage().contains("Illegal input for card suit"));
        }

        @Test
        @DisplayName("Null suit exception")
        public void testCardNullSuit() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                new Card("Ace", null, 1);
            });
            assertTrue(exception.getMessage().contains("Illegal input for card suit"));
        }

        @Test
        @DisplayName("Negative value exception")
        public void testCardNegativeValue() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                new Card("Ace", "Hearts", -1);
            });
            assertTrue(exception.getMessage().contains("Illegal input for value"));
        }

        @Nested
        @DisplayName("Card Exception Handling Tests")
        class CardExceptionTests {
            @Test
            @DisplayName("All invalid parameter combinations")
            void testCardAllInvalidParameters() {
                assertAll(
                        () -> assertThrows(IllegalArgumentException.class,
                                () -> new Card(null, null, -1)),
                        () -> assertThrows(IllegalArgumentException.class,
                                () -> new Card("", "", -5)),
                        () -> assertThrows(IllegalArgumentException.class,
                                () -> new Card("Ace", null, 1)),
                        () -> assertThrows(IllegalArgumentException.class,
                                () -> new Card(null, "Hearts", 1))
                );
            }
        }
    }

    @Nested
    @DisplayName("Deck Class Tests")
    class DeckExecuteTests {

        @Test
        @DisplayName("Default constructor creates standard deck")
        public void testDeckDefaultConstructor() {
            Deck deck = new Deck();
            assertEquals(52, deck.size());
        }

        @Test
        @DisplayName("Custom card array constructor")
        public void testDeckCustomConstructor() {
            Card[] cards = {
                    new Card("Ace", "Hearts", 1),
                    new Card("King", "Spades", 13)
            };
            Deck deck = new Deck(cards);
            assertEquals(2, deck.size());
        }

        @Test
        @DisplayName("Custom constructor null array exception")
        public void testDeckCustomConstructorNullArray() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                new Deck(null);
            });
            assertTrue(exception.getMessage().contains("Cards array is a null array!"));
        }

        @Test
        @DisplayName("draw method removes card from deck")
        public void testDeckDraw() {
            Deck deck = new Deck();
            int initialSize = deck.size();
            Card drawn = deck.draw();

            assertNotNull(drawn);
            assertEquals(initialSize - 1, deck.size());
        }

        @Test
        @DisplayName("draw from empty deck returns null")
        public void testDeckDrawFromEmpty() {
            Deck deck = new Deck(new Card[0]);
            Card drawn = deck.draw();
            assertNull(drawn);
        }

        @Test
        @DisplayName("shuffle method rearranges cards")
        public void testDeckShuffle() {
            Deck deck1 = new Deck();
            Deck deck2 = new Deck();

            List<Card> originalOrder = new ArrayList<>();
            while (deck1.size() > 0) {
                originalOrder.add(deck1.draw());
            }

            deck1 = new Deck();
            deck1.shuffle();

            boolean orderChanged = false;
            for (int i = 0; i < originalOrder.size(); i++) {
                Card drawn = deck1.draw();
                if (!drawn.equals(originalOrder.get(i))) {
                    orderChanged = true;
                    break;
                }
            }
            assertTrue(orderChanged);
        }

        @Test
        @DisplayName("Shuffle empty deck exception")
        public void testDeckShuffleEmpty() {
            Deck deck = new Deck(new Card[0]);
            Exception exception = assertThrows(IllegalStateException.class, () -> {
                deck.shuffle();
            });
            assertTrue(exception.getMessage().contains("Cannot shuffle an empty deck"));
        }

        @Test
        @DisplayName("addCard method")
        public void testDeckAddCard() {
            Deck deck = new Deck(new Card[0]);
            Card card = new Card("Joker", "Red", 0);

            deck.addCard(card);
            assertEquals(1, deck.size());
        }

        @Test
        @DisplayName("addCard null exception")
        public void testDeckAddNullCard() {
            Deck deck = new Deck();
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                deck.addCard(null);
            });
            assertTrue(exception.getMessage().contains("Cannot add null card to deck"));
        }

        @Test
        @DisplayName("reshuffle method")
        public void testDeckReshuffle() {
            Deck deck = new Deck(new Card[0]);
            Card[] cardsToAdd = {
                    new Card("Ace", "Hearts", 1),
                    new Card("King", "Spades", 13)
            };

            deck.reshuffle(cardsToAdd);
            assertEquals(2, deck.size());
        }

        @Test
        @DisplayName("reshuffle null array exception")
        public void testDeckReshuffleNullArray() {
            Deck deck = new Deck();
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                deck.reshuffle(null);
            });
            assertTrue(exception.getMessage().contains("Cards array cannot be null"));
        }

        @Nested
        @DisplayName("Deck Exception Handling Tests")
        class DeckExceptionTests {
            @Test
            @DisplayName("All invalid operations")
            void testDeckAllInvalidOperations() {
                Deck emptyDeck = new Deck(new Card[0]);

                assertAll(
                        () -> assertThrows(IllegalArgumentException.class,
                                () -> emptyDeck.addCard(null)),
                        () -> assertThrows(IllegalStateException.class,
                                () -> emptyDeck.shuffle()),
                        () -> assertThrows(IllegalArgumentException.class,
                                () -> emptyDeck.reshuffle(null))
                );
            }
        }
    }

    @Nested
    @DisplayName("DiscardPile Class Tests")
    class DiscardPileExecuteTests {

        @Test
        @DisplayName("Default constructor creates empty discard pile")
        public void testDiscardPileDefaultConstructor() {
            DiscardPile pile = new DiscardPile();
            assertEquals(0, pile.size());
        }

        @Test
        @DisplayName("Custom card array constructor")
        public void testDiscardPileCustomConstructor() {
            Card[] cards = {
                    new Card("Ace", "Hearts", 1),
                    new Card("Queen", "Diamonds", 12)
            };
            DiscardPile pile = new DiscardPile(cards);
            assertEquals(2, pile.size());
        }

        @Test
        @DisplayName("Custom constructor null array exception")
        public void testDiscardPileCustomConstructorNullArray() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                new DiscardPile(null);
            });
            assertTrue(exception.getMessage().contains("Cards array cannot be null"));
        }

        @Test
        @DisplayName("addCard method")
        public void testDiscardPileAddCard() {
            DiscardPile pile = new DiscardPile();
            Card card = new Card("10", "Clubs", 10);

            pile.addCard(card);
            assertEquals(1, pile.size());
        }

        @Test
        @DisplayName("addCard null exception")
        public void testDiscardPileAddNullCard() {
            DiscardPile pile = new DiscardPile();
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                pile.addCard(null);
            });
            assertTrue(exception.getMessage().contains("Cannot add null card to discard pile"));
        }

        @Test
        @DisplayName("removeCard method")
        public void testDiscardPileRemoveCard() {
            Card cardToRemove = new Card("King", "Hearts", 13);
            Card[] cards = {
                    new Card("Ace", "Spades", 1),
                    cardToRemove,
                    new Card("Queen", "Clubs", 12)
            };
            DiscardPile pile = new DiscardPile(cards);

            Card removed = pile.removeCard(cardToRemove);
            assertNotNull(removed);
            assertEquals(cardToRemove, removed);
            assertEquals(2, pile.size());
        }

        @Test
        @DisplayName("removeCard non-existent card")
        public void testDiscardPileRemoveNonExistentCard() {
            DiscardPile pile = new DiscardPile();
            Card nonExistentCard = new Card("Joker", "Black", 0);

            Card removed = pile.removeCard(nonExistentCard);
            assertNull(removed);
        }

        @Test
        @DisplayName("removeCard null card")
        public void testDiscardPileRemoveNullCard() {
            DiscardPile pile = new DiscardPile(new Card[]{new Card("Ace", "Hearts", 1)});
            Card removed = pile.removeCard(null);
            assertNull(removed);
        }

        @Test
        @DisplayName("removeAll method")
        public void testDiscardPileRemoveAll() {
            Card[] cards = {
                    new Card("2", "Hearts", 2),
                    new Card("3", "Diamonds", 3),
                    new Card("4", "Spades", 4)
            };
            DiscardPile pile = new DiscardPile(cards);

            Card[] removed = pile.removeAll();
            assertEquals(3, removed.length);
            assertEquals(0, pile.size());
        }

        @Test
        @DisplayName("removeAll empty discard pile")
        public void testDiscardPileRemoveAllEmpty() {
            DiscardPile pile = new DiscardPile();
            Card[] removed = pile.removeAll();
            assertEquals(0, removed.length);
        }

        @Test
        @DisplayName("toString method")
        public void testDiscardPileToString() {
            Card[] cards = {
                    new Card("Ace", "Hearts", 1),
                    new Card("King", "Spades", 13)
            };
            DiscardPile pile = new DiscardPile(cards);

            String result = pile.toString();
            assertTrue(result.contains("Ace of Hearts"));
            assertTrue(result.contains("King of Spades"));
        }

        @Test
        @DisplayName("toString empty discard pile")
        public void testDiscardPileToStringEmpty() {
            DiscardPile pile = new DiscardPile();
            assertEquals("Empty discard pile", pile.toString());
        }
    }

    @Nested
    @DisplayName("Player Class Tests")
    class PlayerExecuteTests {

        @Test
        @DisplayName("Constructor with hand")
        public void testPlayerWithHandConstructor() {
            Card[] hand = {
                    new Card("Ace", "Hearts", 1),
                    new Card("King", "Spades", 13)
            };
            Player player = new Player("Alice", 25, hand);

            assertEquals("Alice", player.getName());
            assertEquals(25, player.getAge());
            assertEquals(2, player.size());
        }

        @Test
        @DisplayName("Empty hand constructor")
        public void testPlayerEmptyHandConstructor() {
            Player player = new Player("Bob", 30);

            assertEquals("Bob", player.getName());
            assertEquals(30, player.getAge());
            assertEquals(0, player.size());
        }

        @Test
        @DisplayName("Empty name exception")
        public void testPlayerEmptyName() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                new Player("", 25);
            });
            assertTrue(exception.getMessage().contains("name cannot be null or empty"));
        }

        @Test
        @DisplayName("Null name exception")
        public void testPlayerNullName() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                new Player(null, 25);
            });
            assertTrue(exception.getMessage().contains("name cannot be null or empty"));
        }

        @Test
        @DisplayName("Null hand exception")
        public void testPlayerNullHand() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                new Player("David", 35, null);
            });
            assertTrue(exception.getMessage().contains("Hand array cannot be null"));
        }

        @Test
        @DisplayName("draw method")
        public void testPlayerDraw() {
            Player player = new Player("Eve", 28);
            Deck deck = new Deck();
            int initialDeckSize = deck.size();

            player.draw(deck);
            assertEquals(1, player.size());
            assertEquals(initialDeckSize - 1, deck.size());
        }

        @Test
        @DisplayName("draw null deck exception")
        public void testPlayerDrawNullDeck() {
            Player player = new Player("Frank", 32);
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                player.draw(null);
            });
            assertTrue(exception.getMessage().contains("Deck cannot be null"));
        }

        @Test
        @DisplayName("discardCard method")
        public void testPlayerDiscardCard() {
            Card cardToDiscard = new Card("Queen", "Hearts", 12);
            Card[] hand = {cardToDiscard, new Card("Jack", "Diamonds", 11)};
            Player player = new Player("Grace", 27, hand);
            DiscardPile discardPile = new DiscardPile();

            boolean result = player.discardCard(cardToDiscard, discardPile);
            assertTrue(result);
            assertEquals(1, player.size());
            assertEquals(1, discardPile.size());
        }

        @Test
        @DisplayName("discardCard non-existent card")
        public void testPlayerDiscardNonExistentCard() {
            Card[] hand = {new Card("10", "Clubs", 10)};
            Player player = new Player("Henry", 29, hand);
            DiscardPile discardPile = new DiscardPile();
            Card nonExistentCard = new Card("Ace", "Spades", 1);

            boolean result = player.discardCard(nonExistentCard, discardPile);
            assertFalse(result);
            assertEquals(1, player.size());
            assertEquals(0, discardPile.size());
        }

        @Test
        @DisplayName("discardCard null discard pile exception")
        public void testPlayerDiscardCardNullPile() {
            Card[] hand = {new Card("9", "Hearts", 9)};
            Player player = new Player("Ivy", 26, hand);

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                player.discardCard(hand[0], null);
            });
            assertTrue(exception.getMessage().contains("Discard pile cannot be null"));
        }

        @Test
        @DisplayName("returnCard method")
        public void testPlayerReturnCard() {
            Card cardToReturn = new Card("8", "Spades", 8);
            Card[] hand = {cardToReturn};
            Player player = new Player("Jack", 33, hand);
            Deck deck = new Deck(new Card[0]);

            boolean result = player.returnCard(cardToReturn, deck);
            assertTrue(result);
            assertEquals(0, player.size());
            assertEquals(1, deck.size());
        }

        @Test
        @DisplayName("returnCard null deck exception")
        public void testPlayerReturnCardNullDeck() {
            Card[] hand = {new Card("7", "Diamonds", 7)};
            Player player = new Player("Kate", 31, hand);

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                player.returnCard(hand[0], null);
            });
            assertTrue(exception.getMessage().contains("Deck cannot be null"));
        }

        @Test
        @DisplayName("getHighestValueCard method")
        public void testPlayerGetHighestValueCard() {
            Card lowCard = new Card("2", "Hearts", 2);
            Card mediumCard = new Card("7", "Clubs", 7);
            Card highCard = new Card("Ace", "Spades", 14);
            Card[] hand = {lowCard, mediumCard, highCard};
            Player player = new Player("Leo", 34, hand);

            Card highest = player.getHighestValueCard();
            assertEquals(highCard, highest);
        }

        @Test
        @DisplayName("getHighestValueCard empty hand")
        public void testPlayerGetHighestValueCardEmptyHand() {
            Player player = new Player("Mia", 36);
            Card highest = player.getHighestValueCard();
            assertNull(highest);
        }

        @Test
        @DisplayName("toString method")
        public void testPlayerToString() {
            Card[] hand = {
                    new Card("Ace", "Hearts", 1),
                    new Card("King", "Spades", 13)
            };
            Player player = new Player("Noah", 40, hand);

            String result = player.toString();
            assertTrue(result.contains("Noah"));
            assertTrue(result.contains("40"));
            assertTrue(result.contains("Ace of Hearts"));
            assertTrue(result.contains("King of Spades"));
        }

        @Nested
        @DisplayName("Player Exception Handling Tests")
        class PlayerExceptionTests {
            @Test
            @DisplayName("All invalid operations")
            void testPlayerAllInvalidOperations() {
                Player player = new Player("Test", 25);

                assertAll(
                        () -> assertThrows(IllegalArgumentException.class,
                                () -> player.draw(null)),
                        () -> assertThrows(IllegalArgumentException.class,
                                () -> player.discardCard(new Card("Ace", "Hearts", 1), null)),
                        () -> assertThrows(IllegalArgumentException.class,
                                () -> player.returnCard(new Card("Ace", "Hearts", 1), null))
                );
            }
        }
    }

    @Nested
    @DisplayName("SelfPlayedGame Class Tests")
    class SelfPlayedGameExecuteTests {

        @Test
        @DisplayName("Game completes and produces result")
        public void testSelfPlayedGameCompletes() {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            System.setOut(new PrintStream(outputStream));

            try {
                String simulatedInput = "Player1\nPlayer2\n";
                System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

                SelfPlayedGame.main(new String[]{});

                String output = outputStream.toString();

                assertTrue(output.contains("WINS THE GAME") || output.contains("TIE"));
                assertTrue(output.contains("FINAL RESULTS"));

            } finally {
                System.setOut(originalOut);
            }
        }

        @Test
        @DisplayName("Constructor creates game normally")
        public void testSelfPlayedGameConstructor() {
            SelfPlayedGame game = new SelfPlayedGame("TestPlayer1", "TestPlayer2");
            assertNotNull(game);
        }

        @Test
        @DisplayName("drawCards method deals cards correctly")
        public void testSelfPlayedGameDrawCards() {
            SelfPlayedGame game = new SelfPlayedGame("P1", "P2");

            try {
                Method dealCardsMethod = SelfPlayedGame.class.getDeclaredMethod("drawCards");
                dealCardsMethod.setAccessible(true);
                dealCardsMethod.invoke(game);

            } catch (Exception e) {
                fail("drawCards method invocation failed: " + e.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("Integration Tests")
    class IntegrationExecuteTests {

        @Test
        @DisplayName("Complete game flow")
        public void testIntegratedGameFlow() {
            Deck deck = new Deck();
            deck.shuffle();

            Player player1 = new Player("Player1", 20);
            Player player2 = new Player("Player2", 25);

            for (int i = 0; i < 5; i++) {
                player1.draw(deck);
                player2.draw(deck);
            }

            assertEquals(5, player1.size());
            assertEquals(5, player2.size());
            assertEquals(42, deck.size());

            DiscardPile discardPile = new DiscardPile();

            if (player1.size() > 0) {
                Card cardToDiscard = player1.getHand()[0];
                boolean discarded = player1.discardCard(cardToDiscard, discardPile);
                assertTrue(discarded);
                assertEquals(1, discardPile.size());
            }

            if (player2.size() > 0) {
                Card cardToReturn = player2.getHand()[0];
                boolean returned = player2.returnCard(cardToReturn, deck);
                assertTrue(returned);
                assertEquals(43, deck.size());
            }
        }

        @Test
        @DisplayName("Boundary conditions handling")
        public void testBoundaryConditions() {
            Deck emptyDeck = new Deck(new Card[0]);
            assertEquals(0, emptyDeck.size());

            DiscardPile emptyPile = new DiscardPile(new Card[0]);
            assertEquals(0, emptyPile.size());

            Player emptyHandPlayer = new Player("Empty", 30, new Card[0]);
            assertEquals(0, emptyHandPlayer.size());

            Card[] singleCard = {new Card("Ace", "Hearts", 1)};
            Deck singleDeck = new Deck(singleCard);
            assertEquals(1, singleDeck.size());

            Player minAgePlayer = new Player("MinAge", 0);
            assertEquals(0, minAgePlayer.getAge());
        }
    }
}