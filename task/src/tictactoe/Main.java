package tictactoe;

import javax.imageio.metadata.IIOMetadataFormatImpl;
import java.sql.SQLOutput;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void displayTable(String[][] userCellsArr) {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(userCellsArr[i][j] + " ");
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("---------");
    }
    public static String isFinished(String[][] userCellsArr) {
        int inRowCounter = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (userCellsArr[i][j].equals(" ") && isXWinner(userCellsArr)==false && isOWinner(userCellsArr)==false)
                    return "No";
            }
            if(userCellsArr[i][0].equals(userCellsArr[i][1]) &&
                    userCellsArr[i][0].equals(userCellsArr[i][2])) {
                return "Yes";
            }
        }
        return "Yes";
    }

    public static Boolean isXWinner(String[][] userCellsArr) {
        //check if rows
        for (int i = 0; i < 3; i++) {
            if ( userCellsArr[i][0].equals("X") && userCellsArr[i][1].equals("X") &&
                    userCellsArr[i][2].equals("X")) {
                return true;
            }
        }
        //check colums
        for (int i = 0; i < 3; i++) {
            if (userCellsArr[0][i].equals("X") && userCellsArr[1][i].equals("X") &&
                    userCellsArr[2][i].equals("X")) {
                return true;
            }
        }
//        (1, 1) (1, 2) (1, 3)
//        (2, 1) (2, 2) (2, 3)
//        (3, 1) (3, 2) (3, 3)
        //check diagonal
        if (userCellsArr[0][0].equals("X") && userCellsArr[1][1].equals("X") &&
                userCellsArr[2][2].equals("X")) {
            return true;
        }
        if (userCellsArr[0][2].equals("X") && userCellsArr[1][1].equals("X") &&
                userCellsArr[2][0].equals("X")) {
            return true;
        }
        return false;

    }
    public static Boolean isOWinner(String[][] userCellsArr) {
        //check if rows
        for (int i = 0; i < 3; i++) {
            if ( userCellsArr[i][0].equals("O") && userCellsArr[i][1].equals("O") &&
                    userCellsArr[i][2].equals("O")) {
                return true;
            }
        }
        //check colums
        for (int i = 0; i < 3; i++) {
            if (userCellsArr[0][i].equals("O") && userCellsArr[1][i].equals("O") &&
                    userCellsArr[2][i].equals("O")) {
                return true;
            }
        }
//        (0, 0) (0, 1) (0, 2)
//        (1, 0) (1, 1) (1, 2)
//        (2, 0) (2, 1) (2, 2)
        //check diagonal
        if ((userCellsArr[0][0].equals("O")) && (userCellsArr[1][1].equals("O")) &&
                (userCellsArr[2][2].equals("O"))) {
            return true;
        }
        if ((userCellsArr[0][2].equals("O")) && (userCellsArr[1][1].equals("O")) &&
                (userCellsArr[2][0].equals("O"))) {
            return true;
        }
        return false;
    }

    public static String[][] userMove(String[][] userCellsArr, String sign) {
        Scanner scan = new Scanner(System.in);
        int userX = 0;
        int userY = 0;
        int counter = 0;
        String userInputCoordinates = "";
        while(true) {

            scan.reset();
            System.out.println("Enter the coordinates: ");
            userInputCoordinates = scan.nextLine();
            //checking the length
            if(userInputCoordinates.equals("display")) {
                displayTable(userCellsArr);
            }
            if (userInputCoordinates.length() < 2) {
                System.out.println("You should enter numbers!");
                continue;
            }
            if ((!userInputCoordinates.contains(" ") && userInputCoordinates.length() > 2)
                    || (userInputCoordinates.contains(" ") && userInputCoordinates.length() > 3)) {
                System.out.println("You should enter numbers!");
                continue;
            }
            String[] userInputCoordinatesArr = userInputCoordinates.split("");
            //checking if ints
            Boolean isCharacter = false;
            counter = 0;
            for (String ch: userInputCoordinatesArr) {
                if (ch.equals(" ")) continue;
                try {
                    Integer.parseInt(ch);
                    if (counter == 0) userX = Integer.parseInt(ch);
                    if (counter == 1) userY = Integer.parseInt(ch);
                    isCharacter = false;
                    counter++;
                } catch (NumberFormatException e) {
                    System.out.println("You should enter numbers!");
                    isCharacter = true;
                    break;
                }
            }
            if(isCharacter == true) {
                continue;
            }
            //checking the range
            if((userX > 3 || userX < 1) || (userY > 3 || userY < 1)) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }
            //checking if occupied
            if(!userCellsArr[userX - 1][userY - 1].equals(" ")){
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }
            break;
        }
        userCellsArr[userX-1][userY-1] = sign;
        return userCellsArr;
    }
    public static String[][] aiMove(String[][] userCellsArr, String sign) {
        Random random = new Random();
        while(true) {
            int randomX = random.nextInt((3 - 1) + 1) + 1;
            int randomY = random.nextInt((3 - 1) + 1) + 1;
            if(userCellsArr[randomX - 1][randomY - 1].equals("X") || userCellsArr[randomX - 1][randomY - 1].equals("O")){
                //generate another rands
                continue;
            } else {
                userCellsArr[randomX - 1][randomY - 1] = sign;
                break;
            }
        }

        return userCellsArr;
    }

    public static String[][] aiMediumMove(String[][] userCellsArr, String sign) {
        //check for two in row
        for (int i = 0; i < 3; i++) {
            if(userCellsArr[i][0].equals(sign) && userCellsArr[i][1].equals(sign) && userCellsArr[i][2].equals(" ")) {
                userCellsArr[i][2] = sign;
                System.out.println("Making move level \"medium\"");
                return userCellsArr;
            }
            if(userCellsArr[i][1].equals(sign) && userCellsArr[i][2].equals(sign) && userCellsArr[i][0].equals(" ")) {
                userCellsArr[i][0] = sign;
                System.out.println("Making move level \"medium\"");
                return userCellsArr;
            }
            if(userCellsArr[i][0].equals(sign) && userCellsArr[i][2].equals(sign) && userCellsArr[i][1].equals(" ")) {
                userCellsArr[i][1] = sign;
                System.out.println("Making move level \"medium\"");
                return userCellsArr;
            }
        }
        String opponentSign = "";
        if(sign.equals("X")) opponentSign = "O";
        if(sign.equals("O")) opponentSign = "X";
        //check if opponent can win in row
        for (int i = 0; i < 3; i++) {
            if(userCellsArr[i][0].equals(opponentSign) && userCellsArr[i][1].equals(opponentSign) && userCellsArr[i][2].equals(" ")) {
                userCellsArr[i][2] = sign;
                System.out.println("Making move level \"medium\"");
                return userCellsArr;
            }
            if(userCellsArr[i][1].equals(opponentSign) && userCellsArr[i][2].equals(opponentSign) && userCellsArr[i][0].equals(" ")){
                userCellsArr[i][0] = sign;
                System.out.println("Making move level \"medium\"");
                return userCellsArr;
            }
            if(userCellsArr[i][0].equals(opponentSign) && userCellsArr[i][2].equals(opponentSign) && userCellsArr[i][1].equals(" ")) {
                userCellsArr[i][1] = sign;
                System.out.println("Making move level \"medium\"");
                return userCellsArr;
            }
        }
        //check if opponent can win in column
        for (int i = 0; i < 3; i++) {
            if(userCellsArr[0][i].equals(opponentSign) && userCellsArr[1][i].equals(opponentSign) && userCellsArr[2][i].equals(" ")) {
                userCellsArr[2][i] = sign;
                System.out.println("Making move level \"medium\"");
                return userCellsArr;
            }
            if(userCellsArr[1][i].equals(opponentSign) && userCellsArr[2][i].equals(opponentSign) && userCellsArr[0][i].equals(" ")){
                userCellsArr[0][i] = sign;
                System.out.println("Making move level \"medium\"");
                return userCellsArr;
            }
            if(userCellsArr[0][i].equals(opponentSign) && userCellsArr[2][i].equals(opponentSign) && userCellsArr[1][i].equals(" ")) {
                userCellsArr[1][i] = sign;
                System.out.println("Making move level \"medium\"");
                return userCellsArr;
            }
        }
        //check if opponent can win diagonally
        // 0 0   0 1   0 2
        // 1 0   1 1   1 2
        // 2 0   2 1   2 2
        if (userCellsArr[0][0].equals(opponentSign) && userCellsArr[1][1].equals(opponentSign) && userCellsArr[2][2].equals(" ")) {
            userCellsArr[2][2] = sign;
            System.out.println("Making move level \"medium\"");
            return userCellsArr;
        }
        if(userCellsArr[0][0].equals(opponentSign) && userCellsArr[2][2].equals(opponentSign) && userCellsArr[1][1].equals(" ")){
            userCellsArr[1][1] = sign;
            System.out.println("Making move level \"medium\"");
            return userCellsArr;
        }
        if(userCellsArr[1][1].equals(opponentSign) && userCellsArr[2][2].equals(opponentSign) && userCellsArr[0][0].equals(" ")){
            userCellsArr[0][0] = sign;
            System.out.println("Making move level \"medium\"");
            return userCellsArr;
        }
        if(userCellsArr[2][0].equals(opponentSign) && userCellsArr[1][1].equals(opponentSign) && userCellsArr[0][2].equals(" ")){
            userCellsArr[0][2] = sign;
            System.out.println("Making move level \"medium\"");
            return userCellsArr;
        }
        if(userCellsArr[2][0].equals(opponentSign) && userCellsArr[0][2].equals(opponentSign) && userCellsArr[1][1].equals(" ")){
            userCellsArr[1][1] = sign;
            System.out.println("Making move level \"medium\"");
            return userCellsArr;
        }
        if(userCellsArr[1][1].equals(opponentSign) && userCellsArr[0][2].equals(opponentSign) && userCellsArr[2][0].equals(" ")){
            userCellsArr[2][0] = sign;
            System.out.println("Making move level \"medium\"");
            return userCellsArr;
        }
        userCellsArr = aiMove(userCellsArr, sign);
        System.out.println("Making move level \"medium\"");
        return userCellsArr;
    }

    public static String[][] aiHardMove(String[][] userCellsArr, String sign) {
        String initialSign = sign;
        int bestScore = -10000;
        int moveX = 0;
        int moveY = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                //check available spots
                if(userCellsArr[i][j].equals(" ")) {
                    userCellsArr[i][j] = initialSign;

                    int score = minimax(userCellsArr, 0, false, sign, initialSign);

                    if (score > bestScore) {
                        bestScore = score;
                        moveX = i;
                        moveY = j;
                    }
                    userCellsArr[i][j] = " ";

                }
            }
        }
        userCellsArr[moveX][moveY] = initialSign;
        System.out.println("Making move level \"hard\"");
        return userCellsArr;
    }

    public static int minimax(String[][] userCellsArr, int depth, boolean isMaximizing, String sign, String initialSign) {


        if (isFinished(userCellsArr).equals("Yes")) {
            if (isXWinner(userCellsArr) == true) {
                if(initialSign.equals("X")) {
                    return 10;
                }
                if(initialSign.equals("O")) {
                    return -10;
                }
            }
            if (isOWinner(userCellsArr) == true) {
                if(initialSign.equals("X")) {
                    return -10;
                }
                if(initialSign.equals("O")) {
                    //win
                    return 10;
                }
            }
            if(!isOWinner(userCellsArr) && !isXWinner(userCellsArr)) {
                return 0;
            }
        }

        int bestScore;
        if (isMaximizing) {
            bestScore = -10000;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Is the spot available?
                    if (userCellsArr[i][j].equals(" ")) {

                        String opponentSign = "";
                        if(sign.equals("X")) {
                            opponentSign = "O";
                        }
                        if(sign.equals("O")) {
                            opponentSign = "X";
                        }
                        userCellsArr[i][j] = opponentSign;
                        int score = minimax(userCellsArr, depth + 1, false, opponentSign, initialSign);

                        bestScore = Math.max(score, bestScore);
                        userCellsArr[i][j] = " ";
                    }
                }
            }
        } else {
            bestScore = 10000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Is the spot available?
                    if (userCellsArr[i][j].equals(" ")) {
                        String opponentSign = "";
                        if(sign.equals("X")) {
                            opponentSign = "O";
                        }
                        if(sign.equals("O")) {
                            opponentSign = "X";
                        }
                        userCellsArr[i][j] = opponentSign;
                        int score = minimax(userCellsArr, depth + 1, true, opponentSign, initialSign);

                        bestScore = Math.min(score, bestScore);
                        userCellsArr[i][j] = " ";
                    }
                }
            }
        }
        return bestScore;
    }
    public static void start(String X, String O) {
        Scanner scan = new Scanner(System.in);
        String[][] userCellsArr = generateEmptyArray();
        boolean isXUser = false;
        boolean isOUser = false;
        if (X.equals("user")) {
            isXUser = true;
        }
        if (O.equals("user")) {
            isOUser = true;
        }
        String xLevel = "easy";
        String oLevel = "easy";
        if(O.equals("medium")) {
            oLevel = "medium";
        }
        if(X.equals("medium")) {
            xLevel = "medium";
        }
        if(O.equals("hard")) {
            oLevel = "hard";
        }
        if(X.equals("hard")) {
            xLevel = "hard";
        }


        while (true) {
            if (isXUser == false) {
                if(xLevel.equals("hard")) {
                    userCellsArr = aiHardMove(userCellsArr, "X");
                }
                if(xLevel.equals("medium")) {
                    userCellsArr = aiMediumMove(userCellsArr, "X");
                }
                if(xLevel.equals("easy")){
                    System.out.println("Making move level \"easy\"");
                    userCellsArr = aiMove(userCellsArr, "X");
                }
                displayTable(userCellsArr);
                if (gameState(userCellsArr).equals("end") == true) {
                    exit();
                }
            }
            if (isXUser == true) {
                userCellsArr = userMove(userCellsArr, "X");
                displayTable(userCellsArr);
                if (gameState(userCellsArr).equals("end") == true) {
                    exit();
                }
            }
            if (isOUser == false) {
                if(oLevel.equals("hard")) {
                    userCellsArr = aiHardMove(userCellsArr, "O");
                }
                if(oLevel.equals("medium")) {
                    userCellsArr = aiMediumMove(userCellsArr, "O");
                }
                if(oLevel.equals("easy")){
                    System.out.println("Making move level \"easy\"");
                    userCellsArr = aiMove(userCellsArr, "O");
                }
                displayTable(userCellsArr);
                if (gameState(userCellsArr).equals("end") == true) {
                    exit();
                }
            }
            if (isOUser == true) {
                userCellsArr = userMove(userCellsArr, "O");
                displayTable(userCellsArr);
                if (gameState(userCellsArr).equals("end") == true) {
                    exit();
                }
            }

        }
    }

    public static String gameState(String[][] userCellsArr) {
        String state = "";
        if (isFinished(userCellsArr).equals("No")) {
            return "continue";
        }
        if (isFinished(userCellsArr).equals("Draw")) {
            state = "Draw";
            System.out.println(state);
            return "end";
        }
        if (isFinished(userCellsArr).equals("Yes")) {
            if (isXWinner(userCellsArr) == true) {
                state = "X wins";
                System.out.println(state);
                return "end";
            }
            if (isOWinner(userCellsArr) == true) {
                state = "O wins";
                System.out.println(state);
                return "end";
            }
            state = "Draw";
            System.out.println(state);
            return "end";
        }
        return "";
    }

    public static String[][] generateEmptyArray() {
        String[][] userCellsArr = new String[3][3];
        //filling and displaying the empty array
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                userCellsArr[i][j] = " ";
                System.out.print(userCellsArr[i][j] + " ");
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("---------");
        return userCellsArr;
    }
    public static void exit(){
        System.exit(0);
    }
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("Input command: ");
            String params = scan.nextLine();
            String[] arr = params.split(" ");
            if (arr[0].equals("exit")) {
                exit();
            }
            if (arr.length != 3) {
                System.out.println("Bad parameters!");
                continue;
            }
            if (!arr[0].equals("start")) {
                System.out.println("Bad parameters!");
                continue;
            }
            if((arr[1].equals("easy") || arr[1].equals("medium") || arr[1].equals("hard") || arr[1].equals("user"))
                    && (arr[2].equals("easy") || arr[2].equals("medium") || arr[2].equals("hard") || arr[2].equals("user"))) {
                start(arr[1], arr[2]);
            }

        }

        }






    }

