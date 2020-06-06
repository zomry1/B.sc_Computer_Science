#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <memory.h>
#include <sys/types.h>
#include <strings.h>
#define MAXC 512

typedef struct {
  int pid;
  char command[MAXC];
} job;

void jobRemove( job jobs[MAXC], int jobsIndex, int i) {
    int j;
    for(j = i; j < jobsIndex; j++) {
        strcpy(jobs[j].command,jobs[j+1].command);
        jobs[j].pid = jobs[j+1].pid;
    }
}

int main() {
    job jobs[MAXC];
    int jobsIndex = 0;
    
    char lastLocation[MAXC];
    char tempLocation[MAXC];
    getcwd(lastLocation, MAXC);
    getcwd(tempLocation, MAXC);

    //While always true, till exit entered.
    while (1) {
        //child_pid
        pid_t child_pid;
        char commandName[MAXC]; //Command name
        char line[MAXC]; //Input
        int backgroundFlag = 0;//If there is & for run in background
        int status;//For status of operations
        char *args[MAXC + 1];
        
        char newLine[MAXC];

        bzero(args, MAXC+1);

        printf("> ");//Print prompt
        fgets(line, MAXC,stdin);//Get line command from user
        strtok(line,"\n");//Remove \n

        //Edge conditions
        if(strcmp(line, "exit") == 0) {
            //If its "exit" close the program
            /* not needed already exit kill the childs
            //Close child process
            for(int i = 0; i < jobsIndex; i++) {
                //Check if job is alive
                waitpid(jobsPID[i],0,WNOHANG);
                if(waitpid(jobsPID[i],0, WNOHANG) != -1 ) {
                    //if it's not, kill it
                    kill(jobsPID[i],SIGKILL);
                }
            }*/
            //close the program
            printf("%d\n",getpid());
            exit(0);
        }
        else if(strcmp(line, "jobs") == 0) {
            //foreach job
            int i = 0;
            int lastJob = 0;
            for (; i < jobsIndex; i++) {
              waitpid(jobs[i].pid,0,WNOHANG);
              if(waitpid(jobs[i].pid,0,WNOHANG) != -1) {
                printf("%d %s\n",jobs[i].pid,jobs[i].command);
                if(lastJob != i) {
                  jobs[lastJob].pid = jobs[i].pid;
                  strcpy(jobs[lastJob].command, jobs[i].command);
                }
                lastJob++;
              }
            }
            jobsIndex = lastJob;
        }
        else if (strncmp(line,"cd",2) == 0) {
            //It's a cd command
           getcwd(tempLocation, MAXC);
           if (strcmp(line, "cd") == 0) { //cd
             status = chdir(getenv("HOME"));
           }
           else if(strcmp(line, "cd -") == 0) {  //cd - 
             status = chdir(lastLocation);
           }
           else if (strncmp(line,"cd ~",4) == 0) { //cd ~PATH
             status = chdir(getenv("HOME"));
             if(status < 0) {
               fprintf(stderr, "Error in system call\n");
               continue;
             }
             if(strlen(line) > 5) {
               status = chdir(line + 5);
             }
           }
           /*else if (strncmp(line, "cd \"~",5) == 0) { //cd "~PA TH" - path with ~ and spaces
             status = chdir(getenv("HOME"));
             if(status < 0) {
               fprintf(stderr, "Error in system call\n");
               continue;
             }
              line[strlen(line) - 1] = '\0';
             if(strlen(line) > 6) {
               status = chdir(line + 6);
             }
           }*/
           else if (strncmp(line, "cd \"",4) == 0) { //cd "PA TH" - path with spaces
             line[strlen(line) - 1] = '\0';
             status = chdir(line + 4);
           }
           else { //cd PATH
             status = chdir(line + 3);
           }
           if(status < 0 ) {
               fprintf(stderr, "Error in system call\n");
           }
           strcpy(lastLocation,tempLocation);
           printf("%d\n",getpid());
        }
        else {
            //It's normal command
            int i = 0;
            //Split to arguments
            strcpy(newLine,line); //Save it for adding later to the jobs
            char *arr1;
            for(arr1 = strtok(line, " "); arr1 != NULL; arr1 = strtok(NULL, " ")) {
                args[i] = arr1;
                i++;
            }
            //Check if there is a & at the end of the command (for background option)
            if(strcmp(args[i-1],"&") == 0) {
                args[i-1] = NULL;
                backgroundFlag = 1;
            }

            //Fork the process
            child_pid = fork();
            if (child_pid < 0) {
                //If there is an error while fork
                fprintf(stderr, "Error in system call\n");
                exit(1);
            }
            else if(child_pid == 0) {
                //Is the child
                //Execute the command
                status = execvp(args[0], args);
                if( status < 0 ) {
                    fprintf(stderr, "Error in system call\n");
                }
                exit(0);
            }
            else {
                //The parent
                printf("%d\n", child_pid);
                int jobEnded = -1;
                if(!backgroundFlag) {
                    //If it's not in the background - wait for the child
                    while(child_pid != jobEnded) {
                        jobEnded = wait(NULL);
                        if(jobEnded < 0 ) {
                            fprintf(stderr, "Error in system call");
                        }
                    }
                }
                else {
                    //It's run in the background - add it to the jobs array
                    newLine[strlen(newLine) - 2] = '\0'; //Delete & at the end of the command
                    strcpy(jobs[jobsIndex].command , newLine);
                    jobs[jobsIndex].pid =  child_pid;
                    jobsIndex++;
                }
            }
        }

    }
    return 0;
}
