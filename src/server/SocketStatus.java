package server;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

class SocketStatus {
    private PropertyChangeSupport changes = new PropertyChangeSupport(this);
    private boolean activeTransaction = false;
    private boolean socketOpen = true;
    private int id;

    public final static String PROPERTY_SOCKET_OPEN = "socketOpen";
    public final static String PROPERTY_ACTIVE_TRANSACTION = "activeTransaction";

    public boolean transactionIsNotActive(){
        return !activeTransaction;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }

    public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        changes.addPropertyChangeListener(property, listener);
    }

    public SocketStatus(){

    }

    public void setActiveTransaction(boolean activeTransaction){
        this.activeTransaction = activeTransaction;
        changes.firePropertyChange(PROPERTY_ACTIVE_TRANSACTION, !activeTransaction, activeTransaction);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
    }

    public boolean getActiveTransaction(){
        return activeTransaction;
    }

    public boolean socketClosed() {
        return !socketOpen;
    }

    public boolean getSocketOpen() {
        return socketOpen;
    }

    public void setSocketOpen(boolean socketOpen) {
        if(this.socketOpen) { // Socket can be closed only once
            this.socketOpen = socketOpen;
            changes.firePropertyChange(PROPERTY_SOCKET_OPEN, !socketOpen, socketOpen);
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
