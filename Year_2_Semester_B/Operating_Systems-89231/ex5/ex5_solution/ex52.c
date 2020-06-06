#include <stdio.h>
#include <signal.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>
#include <termios.h>
#include <time.h>
#define Size 20

//order = 1 => ---
//ShapeOrder = 0 =>  -
                   //-
                   //-
//enum symbols {* = 0, - = 1};
struct block {
    int row;
    int column;
    int order;
};

//Global
struct block bl = {1,3,0};

struct block createBlock() {
    //Create block at a random place
    srand(time(NULL));
    int y = 0;
    int x = rand() % 20;
    int order = rand() % 2;
    if (order == 1) {
        x++;
    }

    struct block bl = {y,x,order};
    return bl;
}

void printScreen(struct block bl) {
    //Print the table on the screen
    if (bl.row == -1) {
        return;
    }
    system("clear");
    int firstBlockX = bl.row;
    int firstBlockY = bl.column;

    int secondBlockX = bl.row + ((bl.order + 1) % 2);
    int secondBlockY = bl.column + ((bl.order + 0));

    int thirdBlockX = bl.row +  (-1 * ((bl.order + 1) % 2));
    int thirdBlockY = bl.column + (-1 * ((bl.order + 0)));
    for (int row = 0; row < Size; row++) {
        for (int column = 0; column < Size; column++) {
            if(row == Size -1 || column == Size-1 || column == 0) {
                write(2, "*", 1);
            }
            else if((row == firstBlockX && column == firstBlockY) || (row == secondBlockX && column == secondBlockY) ||
                    (row == thirdBlockX && column == thirdBlockY)) {
                write(2, "-", 1);
            }
            else {
                write(2, " ", 1);
            }
        }
        write(2, "\n", 1);
    }
}

struct block ChangeOrder(struct block bl) {
    //If the order change is not available
    if (((bl.column == 1 || bl.column == Size - 2) && bl.order == 0) ||
    ((bl.row == 0 || bl.row == Size - 2) && bl.order == 1)) {
        //printf("error while change order\n");
        //Do nothing
    }
    else {
        bl.order = (bl.order + 1) % 2;
    }
    return bl;
}

struct block moveDown(struct block bl) {
    //If block already at the bottom
    if((bl.order == 0 && bl.row == (Size - 3)) || (bl.order == 1 && bl.row == (Size - 2))) {
        bl = createBlock(); //Reset block
    }
    else {
        bl.row += 1;
    }
    return bl;
}

struct block moveLeft(struct block bl) {
    //If the block is at the leftest spot
    if((bl.order == 0 && bl.column == 1) || (bl.order == 1 && bl.column== 2)) {
        //Do nothing
    }
    else {
        bl.column -= 1;
    }
    return bl;
}

struct block moveRight(struct block bl) {
    //If the block is at the rightest spot
    if((bl.order == 0 && bl.column == (Size - 2)) || (bl.order == 1 && bl.column == (Size - 3))) {
        //Do nothing
    }
    else {
        bl.column += 1;
    }
    return bl;
}

struct block moveUp(struct block bl) {
    return ChangeOrder(bl);
}


void alarm_hand(int sig) {
    //Move block down
    if(bl.row == -1) {
        exit(0);
    }
    signal(SIGALRM, alarm_hand);

    bl = moveDown(bl);
    printScreen(bl);
    alarm(1);
}

void sigUsr2_hand(int sig) {
    //Check the input char
    char buffer[1];
    read(0,buffer, sizeof(buffer));
    char entered = buffer[0];

    if(entered == 'w') {
        bl = moveUp(bl);
    }
    else if(entered == 'a') {
        bl = moveLeft(bl);
    }
    else if(entered == 's') {
        bl = moveDown(bl);
    }
    else if(entered == 'd') {
        bl = moveRight(bl);
        printScreen(bl);
    }
    else if(entered == 'q') {
        bl.row = -1;
    }
    printScreen(bl);
    signal(SIGUSR2,sigUsr2_hand);

}

int main() {
    //Print screen
    printScreen(bl);
    //Set handle signals
    signal(SIGUSR2,sigUsr2_hand);
    signal(SIGALRM,alarm_hand);
    //set first alarm
    alarm(1);
    //keep the game alive while the row of the block is not -1
    while(bl.row != -1);
    return 1;
}