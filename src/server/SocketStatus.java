package server;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

class SocketStatus {
    private PropertyChangeSupport changes = new PropertyChangeSupport(this);
    private boolean activeTransaction = false;

    public boolean transactionIsNotActive(){
        return !activeTransaction;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
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
}
