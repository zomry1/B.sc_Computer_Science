#include <unistd.h>
#include <termios.h>
#include <stdio.h>
#include <string.h>
#include <signal.h>
#include <stdlib.h>

char getch() {
    char buf = 0;
    struct termios old = {0};
    if (tcgetattr(0, &old) < 0)
        perror("tcsetattr()");
    old.c_lflag &= ~ICANON;
    old.c_lflag &= ~ECHO;
    old.c_cc[VMIN] = 1;
    old.c_cc[VTIME] = 0;
    if (tcsetattr(0, TCSANOW, &old) < 0)
        perror("tcsetattr ICANON");
    if (read(0, &buf, 1) < 0)
        perror ("read()");
    old.c_lflag |= ICANON;
    old.c_lflag |= ECHO;
    if (tcsetattr(0, TCSADRAIN, &old) < 0)
        perror ("tcsetattr ~ICANON");
    return (buf);
}

int main() {
    int pid;
    int fd[2];
    pipe(fd);

    if((pid = fork()) < 0) {
        //Error while fork
        write(2, "Error in system call\n", 21);
        exit(0);
    }
    else if(pid == 0) {
        //Its the child - need to run ex2
        dup2(fd[0],0); //Change input to the pipe read
        close(fd[1]); //Doesnt need to write to the pipe
        execlp("./draw.out","./draw.out",NULL);
    }
    else {
        close(fd[0]); //Doesnt need to read from the pipe
        //Keyboard
        char clicked = 0;
        while((clicked = getch()) != 'q') {
            if(clicked == 'w' || clicked == 'a' || clicked == 's' || clicked == 'd' ) {
                //Update the game
                char character[] = {clicked};
                write(fd[1],character, sizeof(character));
                kill(pid,SIGUSR2);
            }
            //Do nothing
        }
        //Update the game with 'q'
        char character[] = {clicked};
        write(fd[1],character, sizeof(character));
        kill(pid,SIGUSR2);
        //
        close(fd[1]);
        //
    }
}