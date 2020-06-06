#include <unistd.h>
#include "threadPool.h"
#include "osqueue.h"


//Added new struct define
typedef struct {
    void (*function)(void*);
    void *params;
} Task;


void* FrameThread(void* args) {
    //Cast void* args to the threadpool
    ThreadPool* threadPool = (ThreadPool*) args;

    //Error while casting
    if(threadPool == NULL) {
        write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
        exit(-1);
    }

    //While threadpool is not empty and is running or stop but need to finish the queue
    while(threadPool->state == RUN || (!osIsQueueEmpty(threadPool->queue) && threadPool->state == QUEUE_AND_STOP)) {
        //Lock mutex
        pthread_mutex_lock(&threadPool->mutex);

        while (threadPool->state == RUN && osIsQueueEmpty(threadPool->queue)) {
            pthread_cond_wait(&threadPool->cond,&threadPool->mutex);
        }

        //Dequeue from the queue and cast to task
        Task* task = (Task*) osDequeue(threadPool->queue);

        //Finish using the pthread - unlock mutex
        pthread_mutex_unlock(&(threadPool->mutex));

        //Cast succeed
        if(task != NULL) {
            //Run the function with the parameters
            (task->function)(task->params);
            //Not in use anymore
            free(task);
        }
    }
    //Finish using the pthread - unlock mutex
    pthread_mutex_unlock(&threadPool->mutex);
    //Finish with this thread
    pthread_exit(NULL);
}


ThreadPool* tpCreate(int numOfThreads) {
    //Allocate the threadpool
    ThreadPool *threadPool = (ThreadPool*) malloc(sizeof(ThreadPool));
    if(threadPool == NULL) {
        write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
        exit(-1);
    }

    threadPool->state = RUN;
    threadPool->numThreads = numOfThreads;

    //Allocate the threads array
    threadPool->threads = (pthread_t*) malloc (sizeof(pthread_t *) * numOfThreads);
    if(threadPool->threads == NULL) {
        write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
        free(threadPool);
        exit(-1);
    }

    //Allocate queue
    threadPool->queue = osCreateQueue();
    if(threadPool->queue == NULL) {
        write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
        free(threadPool->threads);
        free(threadPool);
        exit(-1);
    }

    //Initialize mutex
    if(pthread_mutex_init(&threadPool->mutex, NULL) != 0) {
        write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
        free(threadPool->threads);
        free(threadPool->queue);
        free(threadPool);
        exit(-1);
    }

    //Initialize condition
    if(pthread_cond_init(&threadPool->cond, NULL) != 0) {
        write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
        free(threadPool->threads);
        free(threadPool->queue);
        free(threadPool);
        exit(-1);
    }

    //Create Frame threads for the threadpool by his size
    int i;
    for(i = 0; i < threadPool->numThreads; i++) {
        if(pthread_create(&(threadPool->threads[i]),NULL,FrameThread,(void *) threadPool) != 0) {
            //Can destory all the threadpool
            tpDestroy(threadPool,0);
            write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
            exit(-1);
        }
    }
    return threadPool;
}

int tpInsertTask(ThreadPool* threadPool, void (*computeFunc) (void *), void* param) {
    //If threadpool is already destroyed
    if(threadPool->state != RUN) {
        return -1;
    }

    Task* task = (Task*) malloc(sizeof(Task));
    if(task == NULL) {
        write(2,ERROR_MESSAGE,ERROR_MESSAGE_SIZE);
        exit(-1);
        //Shoudl close all?
    }

    task->function = computeFunc;
    task->params = param;

    //Lock mutex before using the threadpool
    pthread_mutex_lock(&threadPool->mutex);
    //Enqueue task to the queue
    osEnqueue(threadPool->queue, task);

    //Unblock all threads
    pthread_cond_broadcast(&threadPool->cond);

    //Unlock mutex after finish using the threadpool
    pthread_mutex_unlock(&(threadPool->mutex));
    return 0;
}

void tpDestroy(ThreadPool* threadPool, int shouldWaitForTasks) {
    //Thread pool already in destroy progress
    if(threadPool->state != RUN) {
        return;
    }
    //Lock mutex before using the threadpool
    pthread_mutex_lock(&threadPool->mutex);

    //Change threadpool state by the parameter
    if(shouldWaitForTasks == 0) {
        threadPool->state = STOP;
    }
    else {
        threadPool->state = QUEUE_AND_STOP;
    }

    //Unlock all threads
    pthread_cond_broadcast(&threadPool->cond);

    //Unlock mutex after finish using the threadpool
    pthread_mutex_unlock(&threadPool->mutex);

    //Wait for join to all threads to finish
    int i;
    for(i = 0; i < threadPool->numThreads; i++) {
        pthread_join(threadPool->threads[i], NULL);
    }
    free(threadPool->threads);

    //While queue is not empty - Stop without queue chosed
    while(!osIsQueueEmpty(threadPool->queue)) {
        //Dequeue from the queue and cast to task
        Task* task = osDequeue(threadPool->queue);
        //free(task);

        //If the cast succeed, free the task
        if(task != NULL) {
            free(task);
        }
    }
    osDestroyQueue(threadPool->queue);


    //Destroy the mutex and the condition
    pthread_mutex_destroy(&threadPool->mutex);
    pthread_cond_destroy(&threadPool->cond);
    //Free the entire struct
    free(threadPool);
}