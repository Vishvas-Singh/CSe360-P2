package guiFirstAdmin;

/*******
 * <p>
 * Title: ModelFirstAdmin Class.
 * </p>
 * 
 * <p>
 * Description: The First System Startup Page Model. This class is not used as
 * there is no data manipulated by this MVC beyond accepting a username and
 * password and then saving it in the database. When the code is enhanced for
 * input validation, this model may be needed.
 * </p>
 * 
 * <p>
 * Copyright: Lynn Robert Carter © 2025
 * </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @version 1.00 2025-08-15 Initial version
 * 
 */

public class ModelFirstAdmin {

	public static String checkForValidUserName(String input) {
		// Check to ensure that there is input to process
		if (input == null || input.length() <= 0) {
			return "\n*** ERROR *** The input is empty";
		}

		// The local variables used to perform the Finite State Machine simulation
		int state = 0; // This is the FSM state number
		int currentCharNdx = 0; // The index of the current character
		char currentChar = input.charAt(0); // The current character from above indexed position

		// The Finite State Machines continues until the end of the input is reached or
		// at some
		// state the current character does not match any valid transition to a next
		// state

		boolean running = true; // Start the loop
		int nextState = -1; // There is no next state

		// This is the place where semantic actions for a transition to the initial
		// state occur

		int userNameSize = 0; // Initialize the UserName size

		// The Finite State Machines continues until the end of the input is reached or
		// at some
		// state the current character does not match any valid transition to a next
		// state
		while (running) {
			// The switch statement takes the execution to the code for the current state,
			// where
			// that code sees whether or not the current character is valid to transition to
			// a
			// next state
			switch (state) {
			case 0:
				// State 0 has 1 valid transition that is addressed by an if statement.

				// The current character is checked against A-Z, a-z. If any are matched
				// the FSM goes to state 1

				// A-Z, a-z -> State 1
				if ((currentChar >= 'A' && currentChar <= 'Z') || // Check for A-Z
						(currentChar >= 'a' && currentChar <= 'z')) { // Check for a-z
					nextState = 1;

					// Count the character
					userNameSize++;

					// This only occurs once, so there is no need to check for the size getting
					// too large.
				}
				// If it is none of those characters, the FSM halts
				else
					running = false;

				// The execution of this state is finished
				break;

			case 1:
				// State 1 has two valid transitions,
				// 1: a A-Z, a-z, 0-9 that transitions back to state 1
				// 2: a period that transitions to state 2

				// A-Z, a-z, 0-9 -> State 1
				if ((currentChar >= 'A' && currentChar <= 'Z') || // Check for A-Z
						(currentChar >= 'a' && currentChar <= 'z') || // Check for a-z
						(currentChar >= '0' && currentChar <= '9')) { // Check for 0-9
					nextState = 1;

					// Count the character
					userNameSize++;
				}
				// . _ - -> State 2
				else if (currentChar == '.' || currentChar == '_' || currentChar == '-') { // Check for . _ -
					nextState = 2;

					// Count the .
					userNameSize++;
				}
				// If it is none of those characters, the FSM halts
				else
					running = false;

				// The execution of this state is finished
				// If the size is larger than 16, the loop must stop
				if (userNameSize > 16)
					running = false;
				break;

			case 2:
				// State 2 deals with a character after a period in the name.

				// A-Z, a-z, 0-9 -> State 1
				if ((currentChar >= 'A' && currentChar <= 'Z') || // Check for A-Z
						(currentChar >= 'a' && currentChar <= 'z') || // Check for a-z
						(currentChar >= '0' && currentChar <= '9')) { // Check for 0-9
					nextState = 1;

					// Count the odd digit
					userNameSize++;

				}
				// If it is none of those characters, the FSM halts
				else
					running = false;

				// The execution of this state is finished
				// If the size is larger than 16, the loop must stop
				if (userNameSize > 16)
					running = false;
				break;
			}

			if (running) {
				// When the processing of a state has finished, the FSM proceeds to the next
				// character in the input and if there is one, it fetches that character and
				// updates the currentChar. If there is no next character the currentChar is
				// set to a blank.

				// Move to the next state
				state = nextState;
				nextState = -1;

				// Checks if the pointer is outside length of input, if it is it stops the
				// validation, otherwise continues to next char
				currentCharNdx++;
				if (currentCharNdx >= input.length()) {
					running = false;
				} else {
					currentChar = input.charAt(currentCharNdx);
				}

			}
			// Should the FSM get here, the loop starts again

		}

		// When the FSM halts, we must determine if the situation is an error or not.
		// That depends
		// of the current state of the FSM and whether or not the whole string has been
		// consumed.
		// This switch directs the execution to separate code for each of the FSM states
		// and that
		// makes it possible for this code to display a very specific error message to
		// improve the
		// user experience.
		String userNameRecognizerErrorMessage = "\n*** ERROR *** ";

		// The following code is a slight variation to support just console output.
		switch (state) {
		case 0:
			// State 0 is not a final state, so we can return a very specific error message
			userNameRecognizerErrorMessage += "A UserName must start with A-Z, or a-z.\n";
			return userNameRecognizerErrorMessage;

		case 1:
			// State 1 is a final state. Check to see if the UserName length is valid. If so
			// we
			// we must ensure the whole string has been consumed.

			if (userNameSize < 4) {
				// UserName is too small
				userNameRecognizerErrorMessage += "A UserName must have at least 4 characters.\n";
				return userNameRecognizerErrorMessage;
			} else if (userNameSize > 16) {
				// UserName is too long
				userNameRecognizerErrorMessage += "A UserName must have no more than 16 characters.\n";
				return userNameRecognizerErrorMessage;
			} else if (currentCharNdx < input.length()) {
				// There are characters remaining in the input, so the input is not valid
				userNameRecognizerErrorMessage += "A UserName character may only contain the characters A-Z, a-z, 0-9.\n";
				return userNameRecognizerErrorMessage;
			} else {
				// UserName is valid
				userNameRecognizerErrorMessage = "";
				return userNameRecognizerErrorMessage;
			}

		case 2:
			// State 2 is not a final state, so we can return a very specific error message
			userNameRecognizerErrorMessage += "A UserName character after a period, underscore, or minus must be A-Z, a-z, 0-9.\n";
			return userNameRecognizerErrorMessage;

		default:
			// This is for the case where we have a state that is outside of the valid
			// range.
			// This should not happen
			return "";
		}
	}

	public static String evaluatePassword(String input) {
		// The following are the local variable used to perform the Directed Graph
		// simulation
		int passwordIndexofError = 0; // Initialize the IndexofError
		String inputLine = input; // Save the reference to the input line as a global
		int currentCharNdx = 0; // The index of the current character

		if (input == null || input.length() <= 0) {
			return "*** Error *** The password is empty!";
		}
		if (input.length() > 32) {
			return "*** ERROR *** The password must be at most 32 characters long!";
		}

		// The input is not empty, so we can access the first character
		char currentChar = input.charAt(0); // The current character from the above indexed position

		// The Directed Graph simulation continues until the end of the input is reached
		// or at some
		// state the current character does not match any valid transition to a next
		// state. This
		// local variable is a working copy of the input.

		// The following are the attributes associated with each of the requirements
		boolean foundUpperCase = false; // Reset the Boolean flag
		boolean foundLowerCase = false; // Reset the Boolean flag
		boolean foundNumericDigit = false; // Reset the Boolean flag
		boolean foundSpecialChar = false; // Reset the Boolean flag
		boolean foundLongEnough = false; // Reset the Boolean flag

		// This flag determines whether the directed graph (FSM) loop is operating or
		// not
		boolean running = true; // Start the loop

		// The Directed Graph simulation continues until the end of the input is reached
		// or at some
		// state the current character does not match any valid transition
		while (running) {
			// The cascading if statement sequentially tries the current character against
			// all of
			// the valid transitions, each associated with one of the requirements
			if (currentChar >= 'A' && currentChar <= 'Z') {
				foundUpperCase = true;
			} else if (currentChar >= 'a' && currentChar <= 'z') {
				foundLowerCase = true;
			} else if (currentChar >= '0' && currentChar <= '9') {
				foundNumericDigit = true;
			} else if ("~`!@#$%^&*()_-+={}[]|\\:;\"'<>,.?/".indexOf(currentChar) >= 0) {
				foundSpecialChar = true;
			} else {
				passwordIndexofError = currentCharNdx;
				return "*** Error *** An invalid character has been found!";
			}
			if (currentCharNdx > 31) {
				foundLongEnough = true;
			}
			if (currentCharNdx >= 7) {
				foundLongEnough = true;
			}

			// Go to the next character if there is one
			currentCharNdx++;
			if (currentCharNdx >= inputLine.length())
				running = false;
			else
				currentChar = input.charAt(currentCharNdx);
		}

		// Construct a String with a list of the requirement elements that were found.
		String errMessage = "";
		if (!foundUpperCase)
			errMessage += "Upper case; ";

		if (!foundLowerCase)
			errMessage += "Lower case; ";

		if (!foundNumericDigit)
			errMessage += "Numeric digits; ";

		if (!foundSpecialChar)
			errMessage += "Special character; ";

		if (!foundLongEnough)
			errMessage += "Length; ";

		if (errMessage == "")
			return "";

		// If it gets here, there something was not found, so return an appropriate
		// message
		passwordIndexofError = currentCharNdx;
		return errMessage + "conditions were not satisfied" + "; at character: " + passwordIndexofError;
	}

}
