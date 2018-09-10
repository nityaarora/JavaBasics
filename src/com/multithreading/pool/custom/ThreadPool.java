package com.multithreading.pool.custom;

class ThreadPool {
 
    private CustomBlockingQueue<Runnable> taskQueue;
   
    /*
     * Once pool shutDown will be initiated, poolShutDownInitiated will become true.
     */
    private boolean poolShutDownInitiated = false;
 
    /* Constructor of ThreadPool
     * nThreads= is a number of threads that exist in ThreadPool.
     * nThreads number of threads are created and started.  *
     */
    public ThreadPool(int nThreads){
        System.out.println("ThreadPool contructor called----this will now create threads");
        taskQueue = new CustomBlockingQueue<Runnable>(nThreads);
 
        //Create and start nThreads number of threads.
        for(int i=1; i<=nThreads; i++){
             ThreadPoolsThread threadPoolsThread=new ThreadPoolsThread(taskQueue,this);
             threadPoolsThread.setName("Thread-"+i);
             System.out.println("Thread-"+i +" created in ThreadPool with state---" + threadPoolsThread.getState().toString());
             threadPoolsThread.start();   
             System.out.println("Thread-"+i +" is now in ThreadPool with state---" + threadPoolsThread.getState().toString());
        }
       
    }
 
   
    /**
     * Execute the task, task must be of Runnable type.
     */
    public synchronized void  execute(Runnable task) throws Exception{
        if(this.poolShutDownInitiated)
           throw new Exception("ThreadPool has been shutDown, no further tasks can be added");
       
        /*
      * Add task in sharedQueue,
      * and notify all waiting threads that task is available.  
            */
        System.out.println("task has been added");
        this.taskQueue.put(task);
    }
 
 
    public boolean isPoolShutDownInitiated() {
           return poolShutDownInitiated;
    }
 
 
    /**
     * Initiates shutdown of ThreadPool, previously submitted tasks
     * are executed, but no new tasks will be accepted.
     */
    public synchronized void shutdown(){
       this.poolShutDownInitiated = true;
        System.out.println("ThreadPool SHUTDOWN initiated.");
    }
 
}
 
 