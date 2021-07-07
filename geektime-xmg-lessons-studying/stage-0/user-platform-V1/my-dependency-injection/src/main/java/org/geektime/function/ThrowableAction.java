package org.geektime.function;

@FunctionalInterface
public interface ThrowableAction {

    /**
     * Executes the action
     *
     * @throws Throwable if meet with error
     */
    void execute() throws Throwable;

    /**
     * Executes {@link ThrowableAction}
     *
     * @param action {@link ThrowableAction}
     * @throws RuntimeException wrap {@link Exception} to {@link RuntimeException}
     */
    static void execute(ThrowableAction action)throws RuntimeException{
        try{
            action.execute();
        }catch(Throwable e){
            throw new RuntimeException(e);
        }

    }


}
