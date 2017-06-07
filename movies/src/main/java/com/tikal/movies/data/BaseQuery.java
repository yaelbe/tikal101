package com.tikal.movies.data;


import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

public abstract class BaseQuery extends Job {

    public static final int HIGH_PRIORITY = 1;
    public static final int RETRY_LIMIT = 0;
    public static final String NETWORK_ERROR = "Internet connection failure.";

    protected BaseQueryCallback callback;
    protected String errorReason;

    public BaseQuery(BaseQueryCallback callback) {
        super(new Params(HIGH_PRIORITY));
        this.callback = callback;
    }

    @Override
    public void onAdded() {/*EMPTY FOR NOW*/}

    @Override
    protected void onCancel() {
        callback.onError(errorReason);
    }

    @Override
    protected int getRetryLimit() {
        return RETRY_LIMIT;
    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        throwable.printStackTrace();
        errorReason = throwable.getMessage();
        onCancel();
        return false;
    }
}
