package de.secretcraft.voteStreaks.commands;


public class List<ContentType> {

    protected Node<ContentType> first;
    protected Node<ContentType> current;

    public boolean isEmpty() {
        return first == null;
    }

    public boolean hasAccess() {
        return current != null;
    }

    public void next() {
        if (!isEmpty() && hasAccess() && current.getNext() != null) {
            current = current.getNext();
        } else {
            current = null;
        }
    }
    public void removeP(ContentType pContent)  {
    	while(hasAccess()) {
    		if(current.equals(pContent)) {
    			remove();
    		}
    		next();
    	}
    }
    public void toFirst() {
        if (!isEmpty()) {
            current = first;
        }
    }

    public void toLast() {
        if (!isEmpty()) {
            current = first;
            while (current.getNext() != null) {
                current = current.getNext();
            }
        }
    }

    public ContentType getObject() {
        if (hasAccess()) {
            return current.getContent();
        } else {
            return null;
        }
    }

    public void setObject(ContentType pObject) {
        if (hasAccess() && pObject != null) {
            current.setContent(pObject);
        }
    }

    public void append(ContentType pObject) {
        if (pObject != null) {
            if (isEmpty()) {
                first = new Node<>(pObject);
            } else {
                Node<ContentType> temp = current;
                toLast();
                current.setNext(new Node<>(pObject));
                current = temp;
            }
        }
    }

    public void insert(ContentType pObject) {
        if (hasAccess()) {
            if (current == first) {
                first = new Node<>(pObject);
                first.setNext(current);
            } else {
                Node<ContentType> temp = first;
                while (temp.getNext() != current) {
                    temp = temp.getNext();
                }
                temp.setNext(new Node<>(pObject));
                temp.getNext().setNext(current);
            }
        } else if (isEmpty()) {
            first = new Node<>(pObject);
        }
    }
    
    public void insertAfter(ContentType pObject) {
        if(hasAccess()) {
            Node<ContentType> temp = current.getNext();
            current.setNext(new Node<>(pObject));
            current.getNext().setNext(temp);
        }
    }

    public void concat(List<ContentType> pList) {
        if (pList != null && !pList.isEmpty()) {
            Node<ContentType> temp = current;
            toLast();
            current.setNext(pList.first);
            current = temp;
            pList.first = null;
        }
    }

    public void remove() {
        if (hasAccess()) {
            if (current == first) {
                first = first.getNext();
                current = first;
            } else {
                Node<ContentType> temp = first;
                while (temp.getNext() != current) {
                    temp = temp.getNext();
                }
                temp.setNext(current.getNext());
                current = current.getNext();
            }
        }
    }
    
    public int getLaenge(){
        if(!isEmpty()){
            int zaehler=0;
            toFirst();
            while(current!=null){
                zaehler++;
                next();
            }
            return zaehler;
        }
        return 0;
    }
}