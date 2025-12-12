import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//Stat tracking moved here
class GameState {
    private int hp;
    private int def;
    private int speed;
    private int atk;
    private int karma;
    private double sanity;
    private int gold;
    private NPC currentNPC;
    private String lastChoice;

    // Constructor, initializes game defaults
    public GameState() {
        this.hp = 10;
        this.def = 1;
        this.speed = 4;
        this.atk = 2;
        this.karma = 0;
        this.sanity = 100.0;
        this.gold = 12;

    }

    // Getters
    public int getHp() {
        return hp;
    }

    public int getDef() {
        return def;
    }

    public int getSpeed() {
        return speed;
    }

    public int getAtk() {
        return atk;
    }

    public int getKarma() {
        return karma;
    }

    public double getSanity() {
        return sanity;
    }

    public int getGold() {
        return gold;
    }

    public NPC getNPC() {
        return currentNPC;
    }

    public String getLastChoice() {
        return lastChoice;
    }

    // Setters
    public void addHp(int hp) {
        this.hp += hp;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public void setKarma(int karma) {
        this.karma = karma;
    }

    public void setSanity(double sanity) {
        this.sanity = sanity;
    }

    public void addGold(int gold) {
        this.gold += gold;
    }

    public void setNPC(NPC npc) {
        currentNPC = npc;
    }

    public void setLastChoice(String choice) {
        lastChoice = choice;
    }

    // Printing Stats method
    public void printState() {
        System.out.println("Current stats:");
        System.out.println("HP: " + hp);
        System.out.println("Defense: " + def);
        System.out.println("Speed: " + speed);
        System.out.println("Attack: " + atk);
        System.out.println("Karma: " + karma);
        System.out.println("Sanity: " + sanity);
    }

}

// NPC Builder. Just cause. I wanted to
class NPC {
    private int hp;
    private int atk;
    private int def;
    private int speed;
    private int gold;

    // Constructor
    public NPC(int hp, int atk, int def, int speed, int gold) {
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.speed = speed;
        this.gold = gold;
    }

    // Setters
    public void addHp(int hp) {
        this.hp += hp;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void addGold(int gold) {
        this.gold += gold;
    }

    // Getters
    public int getHp() {
        return hp;
    }

    public int getDef() {
        return def;
    }

    public int getAtk() {
        return atk;
    }

    public int getSpeed() {
        return speed;
    }

    public int getGold() {
        return gold;
    }

    // Stat Printing
    public void printStats() {
        System.out.println("Current stats:");
        System.out.println("HP: " + hp);
        System.out.println("Defense: " + def);
        System.out.println("Speed: " + speed);
        System.out.println("Attack: " + atk);
        System.out.println("Gold: " + gold);

    }

}

// Abstract definition/Linked list setup
abstract class StoryNode {
    protected StoryNode next;

    public abstract void display(GameState state);

    public StoryNode getNext() {
        return next;
    }

    public void setNext(StoryNode next) {
        this.next = next;
    }
}

// Initiating combat
class CombatNode extends StoryNode {
    @Override
    public void display(GameState state) {
        System.out.println("Your foes stand before you!");
        System.out.println("Your hp is:" + state.getHp());
    }
}

// Fork decision structure
class ChoiceNode extends StoryNode {
    @Override
    public void display(GameState state) {
        System.out.println("Choose adventurer! Right or left?");
        Scanner in = new Scanner(System.in);
        String returnVal = in.nextLine();
        if (returnVal.equalsIgnoreCase("right")) {
            state.setLastChoice(returnVal);
            return;
        } else if (returnVal.equalsIgnoreCase("left")) {
            state.setLastChoice(returnVal);
            return;
        } else {
            System.out.println("Oops, thats not quite right, try again.");
            display(state);
        }
    }
}

// For examining NPCs
class ExamineNode extends StoryNode {
    @Override
    public void display(GameState state) {
        System.out.println("You examine them closely, here are their stats compared to yours");
        state.printState();
        System.out.println("And here are their stats");
        state.getNPC().printStats();
    }
}

class DialogueNode extends StoryNode {
    private String dialogue;

    public void setDialogue(String dia) {
        dialogue = dia;
    }

    @Override
    public void display(GameState state) {
        System.out.println(dialogue);
    }
}

public class AdventureGame {
    static Scanner in = new Scanner(System.in);
    // I was... Experiencing moderate frustration... so I made in a static scanner
    // to avoid overcomplicating things

    static int hp = 10; // health
    static int def = 1; // defense
    static int speed = 4; // speed
    static int atk = 2; // attack
    static int karma = 0; // karma (luck)
    static double sanity = 100.0; // Sanity
    // I had to do the same with the stats

    // Looter
    public static void loot(GameState state, ArrayList<NPC> enemiesDead) {
        for (NPC enemy : enemiesDead) {
            state.addGold(enemy.getGold());
        }
    }

    // Combat: Her Turn
    public static void heroTurn(GameState state, ArrayList<NPC> enemies, ArrayList<NPC> enemiesDead) {
        if (enemies.isEmpty()) {
            System.out.println("Victory!");
            System.out.println("Here is your loot");
            loot(state, enemiesDead);
        } else if (state.getHp() <= 0) {
            System.out.println("Game Over, thanks for playing!");
            return;
        }

        System.out.println("It is your turn hero");
        System.out.println("Attack, or Flee");
        String choiceString = in.nextLine();
        if (choiceString.equalsIgnoreCase("Attack")) {
            NPC enemy = enemies.get(enemies.size() - 1);
            int dmg = calcDmgDealt(state.getAtk(), enemy.getDef());
            enemy.addHp(-dmg);
            enemyTurns(state, enemies, enemiesDead);
        } else if (choiceString.equalsIgnoreCase("Flee")) {
            return;
        } else {
            System.out.println("Sorry, thats not a valid choice, try again");
            heroTurn(state, enemies, enemiesDead);
        }
    }

    // Enemy turns in combat
    public static void enemyTurns(GameState state, ArrayList<NPC> enemies, ArrayList<NPC> enemiesDead) {
        for (int i = enemies.size() - 1; i >= 0; i--) {
            NPC enemy = enemies.get(i);
            if (enemy.getHp() <= 0) {
                enemiesDead.add(enemy);
                enemies.remove(i);
                System.out.println("An enemy is defeated!");
            }
        }
        NPC enemy = enemies.get(enemies.size() - 1);
        if (enemies.size() > 1) {
            int dmg = calcDmg(enemy.getAtk(), enemies.size());
            System.out.println("Your foes rush at you! You take " + dmg + " damage");
            state.addHp(-dmg);
            heroTurn(state, enemies, enemiesDead);
        } else if (enemies.size() == 1) {
            int dmg = calcDmg(enemy.getAtk());
            System.out.println("The enemy attacks for " + dmg + " damage");
            state.addHp(-dmg);
            heroTurn(state, enemies, enemiesDead);
        }
    }

    public static void demo(boolean test, String[] bagItems) {

        // Accepts boolean input to either run or leave demo mode
        // Accepts player inv to call methods
        // Accepts scanner to parse input

        if (test == true) {
            System.out.println(
                    "Calling inventory: Expected result, an array of 5 strings, mostly empty with 1 tinkering kit at index 1");
            openInv(bagItems);
            System.out.println("Press Enter to continue");
            in.nextLine();
            // Inventory test above

            System.out.println(
                    "Damage calculation method test: input value is 2, def is currently 1 and karma is currently 0, so the result should be 1");
            System.out.println(calcDmg(2) + " dmg");
            System.out.println("Press Enter to continue");
            in.nextLine();
            // Damage calculation test above

            System.out.println(
                    "Multiparamater damage calculation is overloaded, passing 2 for damge value and 2 for enemy value, using the same stats the result should be 2");
            System.out.println(calcDmg(2, 2) + " dmg");
            System.out.println("Press Enter to continue");
            in.nextLine();
            // Overloaded damage calculation test above

            System.out.println(
                    "The final method to demo is input validation, however, the input validation method was the source of the boolean input that opened this demo.");
            System.out.println(
                    "Therefore, calling that method here would be unnecessary, as it has already been run as can be proven by viewing this dialogue");
            System.out.println("Press Enter when you're done thinking how neat that is");
            in.nextLine();
            // Explains validation method

            return;
        } else if (test == false) {
            return;
        }

    }

    public static void openInv(String[] inv) {
        System.out.println("In your inventory you have: ");

        for (String item : inv) {
            System.out.println(item);
        }

        System.out.println();
        System.out.println("Press enter when you're ready to move on");
        in.nextLine();

        return;
        // Inventory printing method

    }

    public static int useInv(String[] inv) {
        System.out.println("In your inventory you have: ");

        for (String item : inv) {
            System.out.println(item);
        }

        System.out.println();

        int action;
        boolean valid = false;
        while (valid == false) {
            System.out.println("Please enter the number of the slot of the item you want to use");
            action = (in.nextInt() - 1);
            if (action == 0) {
                valid = true;

            } else if (action == 1) {
                valid = true;
            } else if (action == 2) {
                valid = true;
            } else if (action == 3) {
                valid = true;
            } else if (action == 4) {
                valid = true;
            } else {
                System.out.println("Sorry, please select a valid item to proceed");
            }
        }
        return action;
        // Inventory selection method

    }

    public static int calcDmg(int dmg) {
        dmg = dmg - def - karma;
        if (dmg <= 0) {
            dmg = 1;
        }
        return dmg;
        // Incoming damage calculation, primarily for duels or environmental sources
    }

    // Calculating damage against foes
    public static int calcDmgDealt(int atk, int enDef) {
        int dmg = (atk - enDef);
        if (dmg <= 0) {
            dmg = 1;
        }
        return dmg;
    }

    public static int calcDmg(int dmg, int enemies) {
        dmg = (dmg - def - karma);
        if (dmg <= 0) {
            dmg = 1;
        }
        dmg = dmg * enemies;
        return dmg;
        // Overloaded method for recieving damage from multiple enemies
    }

    public static boolean demoValidate() {

        // Validates demo decision and returns choice
        String choice = in.nextLine();
        boolean isANerd = true;
        boolean demoValid = false;
        while (demoValid == false) {
            if (choice.equalsIgnoreCase("y")) {
                isANerd = true;
                demoValid = true;
            } else if (choice.equalsIgnoreCase("n")) {
                isANerd = false;
                demoValid = true;
            } else {
                System.out.println("Please try again, do you want to enter the demo? y/n");
                choice = in.nextLine();
            }

        }
        return isANerd;

    }

    public static boolean inValidate() {

        // Validates y/n decision structures - 11/9
        String choice = in.nextLine();
        boolean affirm = true;
        boolean valid = false;
        while (valid == false) {
            if (choice.equalsIgnoreCase("y")) {
                affirm = true;
                valid = true;
            } else if (choice.equalsIgnoreCase("n")) {
                affirm = false;
                valid = true;
            } else {
                System.out.println("Please try again, y/n");
                choice = in.nextLine();
            }

        }
        return affirm;

    }

    // End bulk of proj 5 content, minor changes have been made throughout the main
    // program and noted

    // Scalar Recursion
    public static void eatCookies(int n) {
        if (n == 0) {
            System.out.println("You're stuffed, and the cookies are gone!");
            return;
        }
        System.out.println("You ate a cookie, there are " + (n - 1) + " leftover.");
        eatCookies(n - 1);
    }

    // Recursion =D
    public static void displayStoryNodes(StoryNode nodeLoc, GameState state) {
        if (nodeLoc == null) {
            return;
        }
        nodeLoc.display(state);
        displayStoryNodes(nodeLoc.getNext(), state);
    }

    public static void main(String[] args) {

        // Main program
        // Inventory initialization moved here

        // Added Gamestate initialization

        GameState gameState = new GameState();

        String[] bagItems = new String[5];

        bagItems[0] = "Empty";
        bagItems[1] = "Tinkering Kit";
        bagItems[2] = "Empty";
        bagItems[3] = "Empty";
        bagItems[4] = "Empty";

        // Imported a random number generator, for various functions
        Random randGen = new Random();

        // Demo program access
        System.out.println("Press y to open the demo program, or n to continue");

        boolean demoResult = demoValidate();
        // Boolean result passed to demo to confirm decision and validate input
        demo(demoResult, bagItems);

        // --- Collect basic character info ---
        System.out.print("Enter your character's name: ");
        String name = in.nextLine();

        System.out.print("Choose a pronoun subject (he/she/they): ");
        String proSubj = in.next().toLowerCase(); // e.g., he / she / they

        System.out.print("Choose a pronoun object (him/her/them): ");
        String proObj = in.next().toLowerCase(); // e.g., him / her / them

        System.out.print("Choose a possessive adjective (his/her/their): ");
        String proPossAdj = in.next().toLowerCase(); // e.g., his / her / their

        System.out.print("Enter your character's age: ");
        int age = in.nextInt();
        in.nextLine(); // In case enter is pressed this prevents issues later

        // An example integer you can reference later (feel free to rename/use
        // differently)
        int gold = 12;

        // --- Prologue paragraph (≥ 5 sentences using ≥ 5 variables) ---
        System.out.println();
        System.out.println("~ ~ ~ Adventure Prologue ~ ~ ~");
        System.out.println(name + " set out at dawn, " + proPossAdj + " pack light and hopes high.");
        System.out.println("At only " + age + " years old, " + proSubj
                + " already carries stories that most would never dare to tell.");
        System.out.println("In the pouch at " + proPossAdj + " side clinked " + gold + " gold coins- "
                + "not much, but enough for bread and a bed in a quiet inn.");
        System.out.println("A weathered sign pointed toward the Whispering Woods, and " + proSubj
                + " felt a shiver that had nothing to do with the cold.");
        System.out.println("Whatever waited beyond the treeline would test " + proObj + ", but " + name
                + " walked on without looking back.");

        // Added pauses, these will reoccur throughout the story

        System.out.println("Press Enter to continue");
        in.nextLine();

        // (You will add TWO more paragraphs below for your submission.)
        // TIP: Use more variables to store place names, items, stats, etc.

        // Begin my work, starting with a header for the title
        System.out.println();
        System.out.println("~ ~ ~ Chapter 1: The Deep Woods ~ ~ ~");
        System.out.println("As " + name + " trudged through the deep snow blocking the road to the woods, "
                + proPossAdj + " pulse quickened.");
        System.out.println("A deep howling rushed through the air, challenging "
                + proPossAdj + " courage.");

        // Magic Mirror Encounter
        System.out.println("Soon " + name + " encountered a strange mirror, embedded in a tree trunk.");
        System.out.println("Looking in the mirror shows clarity of self.");

        System.out.println("Press Enter to continue");
        in.nextLine();

        /*
         * Initialize player stats - moved to static to prevent issues
         * int hp = 10; // health
         * int def = 1; // defense
         * int speed = 4; // speed
         * int atk = 2; // attack
         * int karma = 0; // karma (luck)
         * double sanity = 100.0; // Sanity
         */

        // Mirror shows current stats
        System.out.println("");
        System.out.println("Health: " + hp);
        System.out.println("Fortitude: " + def);
        System.out.println("Agility: " + speed);
        System.out.println("Strength: " + atk);
        System.out.println("Balance: " + karma);
        System.out.println("Sanity: " + sanity);
        System.out.println("");

        System.out.println("Press Enter to continue");
        in.nextLine();

        // After inspecting the mirror, a wolf encounter will occur, and as we are
        // presently unarmed, we will flee
        int numWolves = (randGen.nextInt(3) + 4);
        System.out.println("Jerking away from the mirror, " + name + " sees " + numWolves + " wolves and freezes.");
        System.out.println("With no weapon our hero is left with no choice but to climb");

        System.out.println("Press Enter to continue");
        in.nextLine();

        // Tracking the damage and karma updates that happen in the next printed line
        int wolfDmg = 2;

        // As of 10/10 this has been consolidated in a method
        int nextHit = calcDmg(wolfDmg, wolfDmg);
        hp = hp - nextHit;
        if (hp <= 1) {
            hp = 1;
        }
        karma += nextHit;

        // Continued narration of escaping the wolves
        System.out.println("The wolves scratched and bit " + proObj + ", dealing "
                + nextHit + " damage, as " + name + " climbed they escaped.");
        System.out.println("Reaching the highest branch, " + name + " was able to rest, and touched "
                + proPossAdj + " wounds with a grimace.");
        System.out.println("");

        System.out.println("Press Enter to continue");
        in.nextLine();

        // We're going to create healing items here and use them immediately
        System.out.print("To " + proPossAdj + " surprise, " + proPossAdj
                + " favorite fruit was there. What is it?: ");
        String fullHeal = in.nextLine().toLowerCase();
        // to store players favorite fruit
        System.out.println("");

        // Now the number of these fruits that are found
        int numFruits = randGen.nextInt(3) + karma;

        // Healing and hook for the next chapter
        System.out.println("Carefully, " + name + " took " + numFruits + " " + fullHeal
                + " and ate one gratefully, storing the rest in " + proPossAdj + " bag.");
        System.out.println("The " + fullHeal + " began to work immediately, fulling restoring "
                + proPossAdj + " health");

        hp = 10;

        System.out.println("");
        System.out.println("Health Restored! Health: " + hp);
        System.out.println("");

        System.out.println("Press Enter to continue");
        in.nextLine();

        System.out.println("Soon the wolves howling subsided, and a soft voice called up to "
                + proObj + " saying 'Hello? Anyone up there?'");
        System.out.println("Apprehensive, " + proSubj
                + " leaned out of the tree to see who called for him, ready for what comes next.");

        System.out.println("Press Enter to continue");
        in.nextLine();

        // End proj 1 content

        // Cont ~~ for proj 2 ~~ Ik the project says to do chp 1 but we alr did so this
        // is chp 2 now

        System.out.println("~ ~ ~ Chapter 2: The Kind Farmboy ~ ~ ~");

        System.out.println("At the base of the tree was a small boy, in a white shirt");
        System.out.println("How do you plan to get down from there? He shouts up to you");
        System.out.println(
                "You have the option to jump, risky but fast. Or slowly climb down, the tree is beginning to creak under your weight.");

        // First branch statement. Literally :D... you fall from a tree. Maybe idk

        String treeDescent = in.nextLine();
        int invldTreeDscnt = 1;

        // Validates choice to descend tree and describes result
        while (invldTreeDscnt == 1) {

            if (treeDescent.equalsIgnoreCase("jump")) {
                int treeDmg = 5 - def - karma;
                if (treeDmg < 0) {
                    treeDmg = 1;
                }
                hp = hp - def + karma;

                System.out.println(name + " jumps, taking " + treeDmg + " damage.");
                invldTreeDscnt = 0;
            } else if (treeDescent.equalsIgnoreCase("climb")) {
                System.out.println(name + " climbs down, taking " + (10 - speed) + " minutes");
                invldTreeDscnt = 0;
            } else {
                System.out.println("Please choose, say 'jump' or 'climb'");
                treeDescent = in.nextLine();
            }

        }

        System.out.println("He hands " + name
                + " a blocky wood sword and says 'It's dangerous to go alone take this, I may have something else for you in my pack, let me check.");
        // Getting our first sword from the new character

        System.out.println("Press Enter to continue");
        in.nextLine();

        int i;
        for (i = 0; i < 3; ++i) {
            System.out.println("'I found one! Here take it.' The boy says, handing you a torch.");

        }
        // Closing the chapter with a rest stop
        System.out.println("'You can rest at that farm house my mum owns, follow me' says the boy.");
        System.out.println("You walk along a crunchy gravel path past several mooing cows and amber fields.");
        System.out.println("Eventually " + name + " come to a well, from which you drink gratefully");
        if (hp < 10) {
            hp = 10;
            System.out.println(name + " heals some damage and feels invigorated.");
        }
        System.out.println(name + " walks down the path, curious what " + proSubj + " would find inside");

        System.out.println("Press Enter to continue");
        in.nextLine();

        // End Proj 2 content

        // Begin Proj 3 content

        System.out.println(name + " checks " + proPossAdj + " bag.");

        bagItems[0] = "Wooden Sword";

        openInv(bagItems);

        // Inventory creation using arrays --- Moved access point to method name openInv
        // under main class 10/10

        // Printing the inventory - commented out because it was moved, but leaving the
        // loop for posterity, an enhanced loop is used to print it in the new method

        // for (i = 0; i < bagItems.length; ++i) {
        // System.out.println(bagItems[i]);
        // }

        System.out.println("Satisfied that nothing was missing, " + name + " heads inside");
        System.out.println("A large surly woman is washing dishes in the kitchen");
        System.out.println("'Eh? Who's this whelp boy?' Says the woman");
        System.out.println("'Ma I found " + proSubj
                + " running from the Wolves, they just need a bed for the night' The boy pleads");
        System.out.println(
                "She turns back to her washing. 'Put him up in the attic then, it's safe and dry.' The boy nods and leads "
                        + name + " upstairs");
        System.out.println("The boy hands " + name + " a bucket");
        System.out.println("'This is a bucket,' he says");
        System.out.println("'Dear God...' " + name + " says seriously");
        System.out.println("'Err yeah, so anyways keep it, you'll find it useful I'm sure' the boy says warily");

        // Adding the bucket to our inv

        System.out.println(name + " opens the bag and puts it inside");
        System.out.println("Inventory:");

        bagItems[2] = "Bucket";

        openInv(bagItems);

        // Preserved for posterity sake, consolidated outside the main method
        // for (String bagItem : bagItems) {
        // System.out.println(bagItem);
        // }

        // All tests passed, end proj 3 content

        // Begin next chapter 11/9 module 1 proj

        System.out.println("~ ~ ~ Chapter 3: This is a bucket ~ ~ ~");

        System.out.println("Sitting at the bed, " + name + " examines the bucket.");
        System.out.println();
        System.out.println("Press Enter to continue");
        in.nextLine();
        System.out.println("It's a bucket");
        System.out.println();
        System.out.println(name + " stands up to stretch.");
        System.out.println("The long night on the stiff bed in the attic hadn't done " + proPossAdj
                + " back any favors, but it meant some rest.");
        System.out.println("Gripping " + proPossAdj + " bag tightly, " + proSubj + " headed out into the forest again");

        System.out.println("Press Enter to continue");
        in.nextLine();

        System.out.println("While walking " + name + " passes a tree with a beehive, inspect it? y/n");
        if (inValidate() == true) {
            System.out.println(
                    "Upon close inspection, the beehive is silent, but dripping large amounts of delicious honey. Use the bucket to collect some?");
            System.out.println("y/n");
            if (inValidate() == true) {
                System.out.println("You got a bucket of honey, sweet.");
                bagItems[2] = "Bucket of Honey";
            } else {
                System.out.println(name + " reconsiders, and backs away instead.");
            }
        } else {
            System.out.println("For some reason, " + name + " is unsettled by the tree, and continues down the path");
        }

        System.out.println("Press Enter to continue");
        in.nextLine();

        System.out.println("Suddenly, a great bear appears, and it looks angry!");
        System.out.println("Quick hero! Which item should you use?");

        int bearEscape = useInv(bagItems);
        // Validation and decision structure for 'fighting' a wild bear
        if (bagItems[bearEscape].equals("Bucket")) {
            System.out.println(name
                    + " puts a bucket on their head and lays down. For some reason this is reassuring. After a moment the"
                    + " bear ignores them, and continues about it's business.");
        } else if (bagItems[bearEscape].equals("Bucket of Honey")) {
            System.out.println(
                    name + " throws the bucket of honey and runs, the bear drops pursuit, but the bucket is lost.");
            bagItems[bearEscape] = "Empty";
        } else if (bagItems[bearEscape].equals("Wooden Sword")) {
            System.out.println(name + " hits the bear with the wooden sword... That was perhaps not the best idea.");
            System.out.println(
                    "The bear snaps the wodden sword with one swipe of it's paw, and " + name + " is slain instantly");
            System.out.println("Game Over, thanks for playing!");
            return;
        } else if (bagItems[bearEscape].equals("Tinkering Kit")) {
            System.out.println("Panicked, and unsure how to calm a giant bear," + name
                    + " attempts to build something with the tinkering kit");
            System.out.println(
                    "The bear is so confused by this action, that it forgets why it was angry, and watches the tinkering process for a while.");
            System.out.println("Eventually it moves on");
        } else {
            System.out.println(name + " is too panicked to decide, and falls to the bear");
            System.out.println("Game Over, thanks for playing!");
            return;
        }

        // Assuming the player survived, we continue here to wrap up

        System.out.println("More than a little rattled by the experience " + name
                + " sets back down the road, eager to share the story, "
                + " and to put some distance behind them");

        System.out.println("~ ~ ~ Chapter 4: Dear God ~ ~ ~");

        // Now that the polymorphism is done building, we're going to demonstrate it in
        // this chapter

        System.out.println("On the way back " + name + " realized they were lost at a fork in the road");
        ChoiceNode roadToFarmNode = new ChoiceNode();
        CombatNode roadAmbushNode = new CombatNode();
        roadToFarmNode.display(gameState);
        boolean wasAmbushed = false;

        if (gameState.getLastChoice().equalsIgnoreCase("right")) {
            System.out.println("After wandering for some time, " + name + " wanders back to the farm safely");
        } else {
            System.out.println("Oh no! An Ambush!");
            wasAmbushed = true;
            roadAmbushNode.display(gameState);
            NPC wolf1 = new NPC(5, 2, 0, 8, 3);
            NPC wolf2 = new NPC(5, 2, 0, 8, 6);
            ArrayList<NPC> enemies = new ArrayList<>();
            enemies.add(wolf1);
            enemies.add(wolf2);
            ArrayList<NPC> enemiesDead = new ArrayList<>();
            // Combat emulation
            heroTurn(gameState, enemies, enemiesDead);
            /*
             * while (gameState.getHp() > 0 && !enemies.isEmpty()) {
             * heroTurn(gameState, enemies, enemiesDead);
             * enemyTurns(enemies, enemiesDead);
             * }
             */
        }

        System.out.println("After arriving back at the farm, " + name + " looks around for the farmboy");
        System.out.println("Excited to tell his new friend about his encounters");

        DialogueNode farmTalkNode = new DialogueNode();
        DialogueNode farmBoyTalkNode = new DialogueNode();
        DialogueNode motherResponseNode = new DialogueNode();

        farmTalkNode.setNext(farmBoyTalkNode);
        farmBoyTalkNode.setNext(motherResponseNode);

        farmTalkNode.setDialogue("With a tone of excitement, you tell your new friends about your exploits");
        farmBoyTalkNode.setDialogue("Wow, that sounds really scary, I can't believe you made it back");
        if (wasAmbushed == true) {
            motherResponseNode.setDialogue("Wolves? They've been so active lately");
        } else {
            motherResponseNode.setDialogue("Messing with bears is dangerous play. Glad you lived.");
        }

        displayStoryNodes(farmTalkNode, gameState);

        System.out.println("Famished, you sit and eat some cookies the family offers you");
        eatCookies(3);

        // End new content
    }
}
