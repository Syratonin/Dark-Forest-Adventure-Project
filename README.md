# Forest Adventure --

A brief choose your own adventure game where the protagonist explores a dark forest and encounters conflict therein

**This program requires JDK, which was inside this folder alongside Main.java. Please run JavaCodingPack-0.4.2.exe first, and then run Main.java to play the game**

JDK was unable to be uploaded to github, so please download from Oracle: https://www.oracle.com/java/technologies/downloads/

## Once the game is started the user will be prompted to input their name and pronouns, and then the rest of the game will be decided by the players decisions. You may be asked to type your choices, please be sure to type exactly your choice as it is presented to you in the story.

### Ex: You have the option to jump, risky but fast. Or slowly climb down, the tree is beginning to creak under your weight.

This is prompting for input, either climb or jump, so you would type one of those to progress the story.

Features:

 - Variables: Sprinkled throughout, but notable use on line 57-63, and 81. Continued reference onwards

 - While loops: 133, used for input validation. Also used for input validation on line 98. Future plans for menu navigation

 - If statements: 135, used for decision and input validation. Also line 168, for conditional healing. Further uses in decision logic in public methods up top

 -  For loops: Line 159, to generate items to be given the hero. Additionally enhanced for loops in methods before the main method to handle inventory printing

 - Arrays: At line 217 a bucket is added to the players inventory, which is listed using an array which is printed using an enhanced for loop. This bucket will be used later in the story for liquid storage. It can be known that it is a new item by examining a previous standard for loop printing the inventory from line 196. The inventory is now also printable from the openInv method, written above the main method

 - Additional methods and overloaded methods: I programmed methods before the main method to calculate damage recieved by the player, with an overloaded variant to handle multiple enemies doing the same damage. Additionally I used one to handle input validation, and another to view the inventory. Next I created a short demo to test all methods. Lines 17-118
 
 - Static variables and scanner: I was having issues passing scanner everywhere, so I just made it static, and since stats are used for calculations I also made the player stats static.


- Refactored additionaly, added new chapter
- Added inventory item selection method



Plans for new features or improvements: Hoping to implement a combat tracker in the future, and maybe eventually a map. Short term more looking to implement new code statements and pad out dialogue options. 
