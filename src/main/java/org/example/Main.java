package org.example;

import java.util.Random;
import java.util.Scanner;

class TicTacToe {
    final int SIZE = 3;                     //size of the table
    final int WIN_SIZE = 3;                 //size of win-line
    final String SIGN_X = "x";                //sign of human
    final String SIGN_O = "o";                //sign of AI
    final String SIGN_EMPTY = ".";            //sign of empty cell
    final String MSG_FOR_HUMAN = "Enter X and Y (1..3): ";
    final String MSG_YOU_WON = "YOU WON";
    final String MSG_AI_WON = "AI WON";
    final String MSG_GAME_OVER = "GAME OVER";
    final String MSG_DRAW = "Sorry, DRAW";
    Random random;
    Scanner sc;
    String[][] table;                                             //char это символ. двухмерный массив символ

    public static void main(String[] args) {
        new TicTacToe().game();                                  //вызов метода
    }

    TicTacToe() {                                                //конструктор. у конструктора всегда имя совпадает с именем класса
        random = new Random();
        sc = new Scanner(System.in);
        table = new String[SIZE][SIZE];
    }

    void game() {
        initTable();                                             //инициализация таблицы
        while (true) {
            printTable();
            turnHuman(SIGN_X);
            if (checkWin(SIGN_X)) {
                System.out.println(MSG_YOU_WON);
                break;
            }
            if (isTableFull()) {
                System.out.println(MSG_DRAW);
                break;
            }
            turnAI(SIGN_O, SIGN_X);
            //printTable();
            if (checkWin(SIGN_O)) {
                System.out.println(MSG_AI_WON);
                break;
            }
            if (isTableFull()) {
                System.out.println(MSG_DRAW);
                break;
            }
        }
        System.out.println(MSG_GAME_OVER);
        printTable();
    }

    void initTable() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                table[i][j] = SIGN_EMPTY;
            }
        }
    }

    void printTable() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    void turnHuman(String st) {
        int x, y;
        do {
            System.out.print(MSG_FOR_HUMAN);
            x = sc.nextInt() - 1;
            y = sc.nextInt() - 1;
        } while (!isCellValid(x, y));                    //метод вернет true, а поскольку while
        table[x][y] = st;                              //повторяет когда false то нам надо инвертировать
    }                                                   //isCellValid -> !isCellValid, тогда цикл повториться снова

    void turnAI(String ch, String enemyDot) {                 //AI action
        int x, y;
        for (x = 0; x < SIZE; x++) {                    //simple blocking
            for (y = 0; y < SIZE; y++) {
                if (isCellValid(x, y)) {                  // if cell empty
                    table[y][x] = enemyDot;             // try to be like enemy
                    if (checkWin(enemyDot)) {            // if win
                        table[y][x] = ch;               // block
                        return;                         //and exit
                    }
                    table[y][x] = SIGN_EMPTY;           //restore cell
                }
            }
        }
        do {
            x = random.nextInt(SIZE);
            y = random.nextInt(SIZE);
        } while (!isCellValid(x, y));
        table[y][x] = ch;
    }

    boolean isCellValid(int x, int y) {                 //проверка правильно ли пользователь ввел координаты
        if (x < 0 || y < 0 || x > 2 || y > 2) {
            return false;
        }
        return table[x][y] == " . ";
    }

    boolean checkWin(String ch) {
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                for (int dy = -1; dy < 2; dy++) {
                    for (int dx = -1; dx < 2; dx++) {
                        if (checkLine(x, y, dy, dx, ch) == WIN_SIZE) {
                            return true;
                        }
                    }
                }
            }
        }
        /*//
        if (table[0][0] == ch && table[0][1] == ch && table[0][2] == ch) return true;
        if (table[1][0] == ch && table[1][1] == ch && table[1][2] == ch) return true;
        if (table[2][0] == ch && table[2][1] == ch && table[2][2] == ch) return true;
        //
        if (table[0][0] == ch && table[1][0] == ch && table[2][0] == ch) return true;
        if (table[0][1] == ch && table[1][1] == ch && table[2][1] == ch) return true;
        if (table[0][2] == ch && table[1][2] == ch && table[2][2] == ch) return true;
        //
        if (table[0][0] == ch && table[1][1] == ch && table[2][2] == ch) return true;
        if (table[2][0] == ch && table[1][1] == ch && table[0][2] == ch) return true;
        */
        //
        /*
        for (int i = 0; i < SIZE; i++) {
            if ((table[i][0] == ch && table[i][1] == ch && table[i][2] == ch) ||
                    (table[0][i] == ch && table[1][i] == ch && table[2][i] == ch))
                return true;
            //
            if ((table[0][0] == ch && table[1][1] == ch && table[2][2] == ch) ||
                    (table[2][0] == ch && table[1][1] == ch && table[0][2] == ch)) {
                return true;
            }
            return false;
        }
        */
        return false;
    }

    int checkLine(int x, int y, int dx, int dy, String st) {
        int count = 0;                                              // check line for win
        if (dx == 0 && dy == 0) {
            return 0;
        }
        for (int i = 0, xi = x, yi = y;
             i < WIN_SIZE; i++, xi += dx, yi += dy) {
            if (xi >= 0 && yi >= 0 && xi < SIZE &&
                    yi < SIZE && table[yi][xi] == st) {
                count++;
            }
        }
        return count;
    }

    boolean isTableFull() {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                if (table[x][y] == " . ") {
                    return false;
                }
            }

        }
        return true;
    }
}