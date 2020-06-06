#ifndef __THREAD_POOL__
#define __THREAD_POOL__

#include "osqueue.h"
#include <pthread.h>
#include <malloc.h>
#include <stdio.h>
#include <stdlib.h>

#define ERROR_MESSAGE "Error in system call\n"
#define ERROR_MESSAGE_SIZE 21

//Added enum to describe state of threadpool -Must be here in order to have a member of it in the thread_pool
typedef enum {
    STOP, //set this as the first value beacuase when free the state become the first one that it is STOP and the insert will not work
    QUEUE_AND_STOP,
    RUN,
} state;

typedef struct thread_pool
{
    int numThreads;
    state state;
    pthread_t *threads;
    OSQueue *queue;
    pthread_mutex_t mutex;
    pthread_cond_t cond;
}ThreadPool;


//Do not change below
ThreadPool* tpCreate(int numOfThreads);

void tpDestroy(ThreadPool* threadPool, int shouldWaitForTasks);

int tpInsertTask(ThreadPool* threadPool, void (*computeFunc) (void *), void* param);

#endif