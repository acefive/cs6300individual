# Requirement

1. A user will be able to choose to log in as a specific player or log in as the administrator when starting the application. For simplicity, any authentication is optional, and you may assume there is a single system running the application.
	* Since the process of the login are not the purpose of this UML diagram, we will simply ignore what is before the login process and just focus on players and administrators.
2. The application will allow players to (1) choose a cryptogram to solve, (2) solve cryptograms, and (3) view the list of player statistics.
	* Here we can see that each player has a list of unsolved cryptograms and remaining attempts if already attempted. He can choose a cryptogram from the list to solve. When the player is solving cryptograms, he calles the function from cryptogram, and hands in the parameters. The function(operation) from the cryptogram returns if the solution is correct. A list of player statistics is a standalone object and is not a class.
3. The application will allow the administrator to (1) create a cryptogram, (2) create a player, and (3) view the list of player statistics.
4. A cryptogram will have a solution (the plaintext phrase) and a maximum number of allowed solution attempts for each of three difficulty categories.  
5. To add a player, the administrator will enter the following player information:
	* A first name
	* A last name
	* A unique username
	* A difficulty category: easy, normal or hard.
6. To add a new cryptogram, the administrator will:
	* Enter a unique cryptogram name.
	* Enter a solution (unencoded) phrase.
	* Enter the number of allowed incorrect solution attempts for the easy difficulty.
	* Enter the number of allowed incorrect solution attempts for the normal difficulty.
	* Enter the number of allowed incorrect solution attempts for the hard difficulty.
	* Edit any of the above steps as necessary.
	* Save the complete cryptogram.
	* View a confirmation that the name assigned to the cryptogram is unique and return to the main menu, or be returned to editing the cryptogram after any error is displayed.
7. The encrypted phrase for the cryptogram will be generated for each player starting a new cryptogram by:
	* Replacing each letter with another letter randomly, so that all of any particular letter are replaced with the same other letter, such as all A’s becoming C’s, and every letter is paired with a unique encrypted letter.
	* Preserving the capitalization in the original phrase.
	* Preserving any non-alphabetic characters (such as punctuation or white space) unaltered.
8. To choose and then solve a cryptogram, a player will:
	* View the list of all unsolved cryptograms alongside their status as in progress or unstarted, and choose a cryptogram to solve.
	* View the chosen cryptogram and number of incorrect solution attempts remaining (starting at whatever number is allowed for the player’s difficulty level for that cryptogram).  If the cryptogram has not been played by this player before, the fully encrypted phrase should be generated and displayed.  If the cryptogram is in progress, the previous state of the phrase should be displayed.
	* Match the replacement and encrypted letters together, and view the resulting potential solution.
	* When all letters in the cryptogram are replaced and they are satisfied with the potential solution, submit their answer.
	* Get a result indicating that the solution was successful, or decrementing the number of incorrect solution attempts remaining if it was unsuccessful.
	* At any point, the player may return to the list of unsolved cryptograms to try another.
	* If the number of incorrect solution attempts reaches zero, they will get a result that the cryptogram game was lost, and this cryptogram will be marked as complete, unavailable for this player to attempt again. They will then return to the menu.
	* If the player successfully solves the cryptogram, they will get a result that the cryptogram game was won, and this cryptogram will be marked as complete, unavailable for this player to attempt again.  They will then return to the menu.
9. The list of player statistics will display a list of players in descending order of number of cryptograms won.  The entry for each player will show their first name, the number of cryptograms won, and the number of cryptograms lost.  An administrator should also see the username and difficulty status of the player.
10. The user interface must be intuitive and responsive.
11. The performance of the game should be such that students do not experience any considerable lag between their actions and the response of the application.

# Extraction

1. Classes
2. Attributes
3. Operations
4. Relationships
* user
* player: first name, last name, username, category, view status, view list of unsolved, choose to solve, view the cryptogram and remaining attempts, submit answer, get previous state
* administrator: edit cryptogram and save, check name uniqueness, return to menu or editing
* player choices
* administrator choices
* cryptogram: name, solution, three maximum attempts, randomization each time, only change necessary, return match, return submission correction and decrease remaining attempts, previous state for a player
* list of players
* solution to cryptogram
* difficulty category
* maximum attempts
* 
* 
* 
* 
* 