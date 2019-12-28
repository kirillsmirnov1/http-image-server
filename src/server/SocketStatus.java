package server;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

class SocketStatus {
    private PropertyChangeSupport changes = new PropertyChangeSupport(this);
    private boolean activeTransaction = false;
    private boolean socketOpen = true;

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

    public boolean isSocketOpen() {
        return socketOpen;
    }

    public void setSocketOpen(boolean socketOpen) {
        this.socketOpen = socketOpen;
    }
}
