#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <ctype.h>
#define BUFFERSIZE 1
#define IDENTICAL 1
#define DIFFERENT 2
#define SIMILAR 3
#define ERROR_MESSAGE "Error in system call\n"
#define ERROR_MESSAGE_SIZE 21

int checkChar(char first, char second) {
    if (first  > 96 && first < 123) {//First its little letter
        if(second + 32 == first) {
            return 1;
        }
    }
    else if (first > 64 && first < 91) { //First its a big letter
        if (second - 32 == first) {
            return 1;
        }
    }
    return 0;
}

int main(int argc, char *argv[]) {
    if (argc != 3) {
        printf("invalid arguments");
        return -1;
    }
    //Open the files
    int firstFile = open(argv[1], O_RDONLY);
    int secondFile = open(argv[2], O_RDONLY);

    if (firstFile < 0 || secondFile < 0) {
        write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
        return -1;
    }
    char firstBuffer[BUFFERSIZE];
    char secondBuffer[BUFFERSIZE];
    int statusLoop = 1;
    int status = 1;
    int closeStatusFirstFile = 1;
    int closeStatusSecondFile = 1;
    int end = 1;
    int identical = IDENTICAL;
    //While there are chars to read
    while (((status = read(firstFile, firstBuffer, BUFFERSIZE)) != 0 && status > 0)
        && ((statusLoop = read(secondFile, secondBuffer, BUFFERSIZE)) != 0 && status > 0)) {
        if (firstBuffer[0] == secondBuffer[0]) { //Same char
            continue;
        } else if (isspace(firstBuffer[0]) && isspace(secondBuffer[0])) {//They are both whitesapce
            identical = SIMILAR;
            continue;
        } else if (isspace(firstBuffer[0])) { //Whitespace first buffer
            while ((status = read(firstFile, firstBuffer, BUFFERSIZE) != 0 && status > 0) && isspace(firstBuffer[0]));//Skip on whitespace
            identical = SIMILAR;
            if(firstBuffer[0] != secondBuffer[0] && checkChar(firstBuffer[0],secondBuffer[0]) == 0) { //Compere chars
                return DIFFERENT;
            }
            if (status == 0 || status < 0) { //File ended
                end = 1;
                break;
            }
            continue;
        } else if (isspace(secondBuffer[0])) { //Whitespace second buffer
            identical = SIMILAR;
            while ((status = read(secondFile, secondBuffer, BUFFERSIZE) != 0 && status > 0) &&
                   isspace(secondBuffer[0]));//Skip on whitespace
            if(firstBuffer[0] != secondBuffer[0] && checkChar(firstBuffer[0],secondBuffer[0]) == 0) { //Compere chars
                return DIFFERENT;
            }
            if (status == 0 || status < 0) { //File ended
                end = 2;
                break;
            }
            continue;
        } else if (checkChar(firstBuffer[0], secondBuffer[0])) { //Same letter, different size (big ot little)
            identical = SIMILAR;
            continue;
        } else {
            return DIFFERENT;
        }
    }
    if(statusLoop == 0) { //the second file ended and exit from the loop
        end = 2;
    }
    if (end == 1) {//first file ended
        while (((status = read(secondFile, secondBuffer, BUFFERSIZE)) != 0 && status > 0)) {
            if (!isspace(secondBuffer[0])) {
                closeStatusFirstFile = close(firstFile);
                closeStatusSecondFile = close(secondFile);
                if(closeStatusFirstFile == -1 || closeStatusSecondFile == -1) {
                    write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
                }
                return DIFFERENT;
            }
        }
    } else {//Second file ended
        while (((status = read(firstFile, firstBuffer, BUFFERSIZE)) != 0 && status > 0)) {
            if (!isspace(firstBuffer[0])) {
                closeStatusFirstFile = close(firstFile);
                closeStatusSecondFile = close(secondFile);
                if(closeStatusFirstFile == -1 || closeStatusSecondFile == -1) {
                    write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
                }
                return DIFFERENT;
            }
        }
    }
    closeStatusFirstFile = close(firstFile);
    closeStatusSecondFile = close(secondFile);
    if(closeStatusFirstFile == -1 || closeStatusSecondFile == -1) {
        write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
    }
    return identical;
}