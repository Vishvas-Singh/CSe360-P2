package guiUserUpdate;

public class Model {
    public static String evaluatePassword(String input) {
        // The following are the local variable used to perform the Directed Graph
        // simulation
        int passwordIndexofError = 0; // Initialize the IndexofError
        String inputLine = input; // Save the reference to the input line as a global
        int currentCharNdx = 0; // The index of the current character

        if (input == null || input.length() <= 0) {
            return "*** Error *** The password is empty!";
        }
		if(input.length() > 32){
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
