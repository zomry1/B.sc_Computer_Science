#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>
#include <memory.h>
#include <dirent.h>
#include <sys/wait.h>
#include <time.h>

#define  MAX_ARGS 150
#define CSV_FILE "results.csv"
#define GREAT ",100,GREAT_JOB"
#define BAD ",60,BAD_OUTPUT"
#define SIMILAR ",80,SIMILAR_OUTPUT"
#define NO_C ",0,NO_C_FILE"
#define TIMEOUT ",40,TIMEOUT"
#define COMPILATION ",20,COMPILATION_ERROR"
#define ERROR_MESSAGE "Error in system call\n"
#define ERROR_MESSAGE_SIZE 21

char* findCFile(char *path) { //File c file in the folder and return its path - if not found return "0"
    DIR *dir;
    struct dirent *entry;

    if(!(dir = opendir(path))) {
        write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
        return NULL;
    }

    //Foreach file or directory
    while((entry = readdir(dir)) != NULL) {
        //If it's directory
        if(entry->d_type == DT_DIR) {
            //If its the current directory or the previous directory
            if (strcmp(entry->d_name, ".") == 0 || strcmp(entry->d_name, "..") == 0) {
                continue;
            }
            char newPath[MAX_ARGS];
            //Create the new path for the foldet we found
            snprintf(newPath, sizeof(newPath), "%s/%s",path,entry->d_name);
            //search in the folder to
            char result[MAX_ARGS];
            strcpy(result,findCFile(newPath));
            if(strcmp(result,"0") == 0) {
                //not file with .c found
                continue;
            }
            else {
                sprintf(path,"%s",result);
                return path;
            }
        }
        //Its a file - check if it's c file
        if(strlen(entry->d_name) > 2 && entry->d_name[strlen(entry->d_name)-1] == 'c' && entry->d_name[strlen(entry->d_name)-2] == '.') { //strstr(entry->d_name, ".c")
            //char result;
            sprintf(path, "%s/%s",path,entry->d_name);
            closedir(dir);
            return path;
        }

    }
    closedir(dir);
    sprintf(path,"0");
    return path;
}

void printToCSV(char studentName[MAX_ARGS], int gradeCode, int *firstTime) {
    int csv;
    if ((csv = open(CSV_FILE, O_CREAT | O_WRONLY | O_APPEND, 0644)) < 0) {
        write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
    }
    if(!(*firstTime)) {
        write(csv,"\n",strlen("\n"));
    }
    else {
        (*firstTime) = 0;
    }
    write(csv,studentName,strlen(studentName));
    switch (gradeCode) {
        case 1:
            write(csv,GREAT,strlen(GREAT)); //outputs are the same
            break;


        case 3:
            write(csv,SIMILAR,strlen(SIMILAR)); //outputs are similiar
            break;

        case 2:
            write(csv,BAD,strlen(BAD)); //outputs are not the same
            break;

        case 4:
            write(csv,TIMEOUT,strlen(TIMEOUT)); //timeout
            break;

        case 5:
            write(csv,COMPILATION,strlen(COMPILATION)); //Complation error
            break;

        case 6:
            write(csv,NO_C,strlen(NO_C)); //there is no c file
            break;

        default:
            //perror("invalid option in switch");
            write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
            break;
    }
    if(close(csv) < 0) {
        write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
    }
}

int main(int argc, char *argv[]) {
    if(argc != 2) {
        printf("invalid argument");
        _exit(-1);
    }

    //Clear results.csv file
    if (open(CSV_FILE, O_CREAT | O_WRONLY | O_TRUNC, 0644) < 0) {
        write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
        _exit(-1);
    }
    int firstTime = 1; //set first time for printing in results csv (for no new line symbol)

    //Open configurations file
    int confFile;
    if((confFile = open(argv[1],O_RDONLY)) < 0) {
        write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
    }

    char configures[3*MAX_ARGS];
    read(confFile,configures,3*MAX_ARGS);

    //Split to 3 args
    char* path = strtok(configures, "\n");;
    char* inputFile = strtok(NULL, "\n");;
    char* outputFile = strtok(NULL, "\n");;

    char output[MAX_ARGS];
    pid_t child_pid;
    pid_t child_pid2;
    pid_t child_pid3;

    DIR *dip;
    struct dirent *dit;

    //Open the folder of the students
    if((dip = opendir(path)) == NULL) {
        write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
        return -1;
    }

    //Foreach folder - student
    while ((dit = readdir(dip)) != NULL) {
        if(dit->d_type == DT_DIR && strcmp(dit->d_name,".") != 0 && strcmp(dit->d_name,"..") != 0) {

            char searchPath[MAX_ARGS];
            snprintf(searchPath, sizeof(searchPath), "%s/%s",path,dit->d_name);
            //find a c file
            char *result = findCFile(searchPath);
            if(strcmp(result,"0") == 0) {
                printToCSV(dit->d_name,6,&firstTime); //No file found grade
                continue;
            }
            //File found
            char filename[MAX_ARGS];
            sprintf(filename,"%s.out",result);
            char* args[MAX_ARGS] = {"gcc",result,"-o" ,filename,NULL};

            child_pid = fork();
            if(child_pid < 0) {
                write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
                return -1;
            }
            else if(child_pid == 0) {
                //Its the child
                //Compile the c file
                if(execvp(args[0],args) < 0) {
                    write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
                    _exit(-1);
                }
            }
            else {
                int status;
                while(waitpid(child_pid, &status, WNOHANG) != child_pid);
                if(WEXITSTATUS(status) == 1) {
                    printToCSV(dit->d_name,5,&firstTime); //Compile error grade
                    continue;
                }
                child_pid2 = fork();
                if (child_pid2 < 0) {
                    write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
                    _exit(-1);
                } else if (child_pid2 == 0) {
                    //Run the output file with the input file txt
                    sprintf(output, "%s_output.txt", result);
                    int out;
                    int in;

                    //Open the output and input file and set them as stdout and stdin
                    if ((out = open(output, O_CREAT | O_TRUNC | O_WRONLY, 0644)) < 0) {
                        write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
                        _exit(-1);
                    }
                    if(dup2(out, 1) == -1) {
                        write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
                        _exit(-1);
                    }
                    if(close(out) < 0) {
                        write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
                    }

                    if ((in = open(inputFile, O_RDONLY, 0444)) < 0) {
                        write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
                        _exit(-1);
                    }
                    if(dup2(in, 0) == -1) {
                        write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
                    }
                    if(close(in) < 0) {
                        write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
                    }
                    args[0] = filename;
                    args[1] = NULL;
                    if(execvp(args[0], args) < 0) {
                        write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
                    }
                }
                else {
                    //Check if compile time over 5 seconds
                    int timeout = 0;
                    time_t start, end;
                    time(&start);
                    while (waitpid(child_pid2, &status, WNOHANG) != child_pid2)  {
                        time(&end);
                        if(difftime(end,start) > 5) {
                            printToCSV(dit->d_name,4,&firstTime);//
                            timeout = 1;
                            break;
                        }
                    }

                    if(timeout) {
                        continue;
                    }
                    child_pid3 = fork();
                    if(child_pid3 < 0) {
                        write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
                        _exit(-1);
                    }
                    else if(child_pid3 == 0) {
                        //Compere output files
                        sprintf(output, "%s_output.txt", result);
                        args[0] = "./comp.out";
                        args[1] = output;
                        args[2] = outputFile;
                        args[3] = NULL;


                        if(execvp(args[0],args) < 0) {
                            write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
                            _exit(-1);
                        }
                    }
                    else {
                        while(waitpid(child_pid3, &status, WNOHANG) != child_pid3);
                        status = WEXITSTATUS(status);
                        //Delete new created files
                        if(unlink(filename) != 0) { //delete the compiled file
                            write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
                        }
                        sprintf(output, "%s_output.txt", result);
                        if(unlink(output) != 0) { //delete the output file
                            write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
                        }
                        printToCSV(dit->d_name,status,&firstTime);//Compere result 60-100
                        continue;
                    }
                }
            }
        }
    }
    if(closedir(dip) == -1) {
        write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
    }
}