package com.ljb.test;

import androidx.annotation.NonNull;
import androidx.core.util.Pools;
import androidx.databinding.CallbackRegistry;

public class CustomListChangeRegistry   extends
        CallbackRegistry<CustomObservableList.OnListChangedCallback, CustomObservableList,
                CustomListChangeRegistry.ListChanges> {
    private static final Pools.SynchronizedPool<ListChanges> sListChanges =
            new Pools.SynchronizedPool<ListChanges>(10);

    private static final int ALL = 0;
    private static final int CHANGED = 1;
    private static final int INSERTED = 2;
    private static final int MOVED = 3;
    private static final int REMOVED = 4;

    private static final CallbackRegistry.NotifierCallback<CustomObservableList.OnListChangedCallback,
            CustomObservableList, ListChanges> NOTIFIER_CALLBACK = new CallbackRegistry.NotifierCallback<
            CustomObservableList.OnListChangedCallback, CustomObservableList, ListChanges>() {
        @Override
        public void onNotifyCallback(CustomObservableList.OnListChangedCallback callback,
                                     CustomObservableList sender, int notificationType, ListChanges listChanges) {
            switch (notificationType) {
                case CHANGED:
                    callback.onItemRangeChanged(sender, listChanges.start, listChanges.count);
                    break;
                case INSERTED:
                    callback.onItemRangeInserted(sender, listChanges.start, listChanges.count);
                    break;
                case MOVED:
                    callback.onItemRangeMoved(sender, listChanges.start, listChanges.to,
                            listChanges.count);
                    break;
                case REMOVED:
                    callback.onItemRangeRemoved(sender, listChanges.start, listChanges.count);
                    break;
                default:
                    callback.onChanged(sender);
                    break;
            }
        }
    };

    public CustomListChangeRegistry() {
        super(NOTIFIER_CALLBACK);
    }
    /**
     * Notify registered callbacks that there was an unknown or whole-list change.
     *
     * @param list The list that changed.
     */
    public void notifyChanged(@NonNull CustomObservableList list) {
        notifyCallbacks(list, ALL, null);
    }

    /**
     * Notify registered callbacks that some elements have changed.
     *
     * @param list The list that changed.
     * @param start The index of the first changed element.
     * @param count The number of changed elements.
     */
    public void notifyChanged(@NonNull CustomObservableList list, int start, int count) {
        ListChanges listChanges = acquire(start, 0, count);
        notifyCallbacks(list, CHANGED, listChanges);
    }

    /**
     * Notify registered callbacks that elements were inserted.
     *
     * @param list The list that changed.
     * @param start The index where the elements were inserted.
     * @param count The number of elements that were inserted.
     */
    public void notifyInserted(@NonNull CustomObservableList list, int start, int count) {
        ListChanges listChanges = acquire(start, 0, count);
        notifyCallbacks(list, INSERTED, listChanges);
    }

    /**
     * Notify registered callbacks that elements were moved.
     *
     * @param list The list that changed.
     * @param from The index of the first element moved.
     * @param to The index of where the element was moved to.
     * @param count The number of elements moved.
     */
    public void notifyMoved(@NonNull CustomObservableList list, int from, int to, int count) {
        ListChanges listChanges = acquire(from, to, count);
        notifyCallbacks(list, MOVED, listChanges);
    }

    /**
     * Notify registered callbacks that elements were deleted.
     *
     * @param list The list that changed.
     * @param start The index of the first element to be removed.
     * @param count The number of elements removed.
     */
    public void notifyRemoved(@NonNull CustomObservableList list, int start, int count) {
        ListChanges listChanges = acquire(start, 0, count);
        notifyCallbacks(list, REMOVED, listChanges);
    }

    private static ListChanges acquire(int start, int to, int count) {
        ListChanges listChanges = sListChanges.acquire();
        if (listChanges == null) {
            listChanges = new ListChanges();
        }
        listChanges.start = start;
        listChanges.to = to;
        listChanges.count = count;
        return listChanges;
    }

    @Override
    public synchronized void notifyCallbacks(@NonNull CustomObservableList sender, int notificationType,
                                             ListChanges listChanges) {
        super.notifyCallbacks(sender, notificationType, listChanges);
        if (listChanges != null) {
            sListChanges.release(listChanges);
        }
    }


    static class ListChanges {
        public int start;
        public int count;
        public int to;
    }
}
