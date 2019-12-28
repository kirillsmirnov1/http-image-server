package server;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

class TransactionStatus {
    private PropertyChangeSupport changes = new PropertyChangeSupport(this);
    private boolean active = false;

    public boolean isNotActive(){
        return !active;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }

    public TransactionStatus(){

    }

    public void setActive(boolean active){
        this.active = active;
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
    }

    public boolean getActive(){
        return active;
    }
}
