package ua.edu.mobile.stepchat.data.repository;

import ua.edu.mobile.stepchat.shared.errorhandling.ErrorHandler;

public class RepositoryBase<T> {
    private RepositoryObserver<T> observer;
    private ErrorHandler errorHandler ;

    public RepositoryBase(RepositoryObserver<T> observer) {
        this.observer = observer;
    }

    public RepositoryObserver<T> getObserver(){
        return observer;
    }

    public ErrorHandler getErrorHandler() {
        if(errorHandler == null){
            errorHandler = new RepositoryBase.EmptyErrorHandler();
        }
        return errorHandler;
    }

    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public class EmptyErrorHandler implements ErrorHandler{
        @Override
        public void onError(Object error) {

        }
    }
}

