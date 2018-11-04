package de.secretcraft.flagBuy.commands;

import de.secretcraft.flagBuy.commands.Node;

public class Node<ContentType> {

    private ContentType content;
    Node<ContentType> next;

    public Node(ContentType content) {
        this(content, null);
    }

    Node(ContentType content, Node<ContentType> next) {
        this.content = content;
        this.next = next;
    }

    Node<ContentType> getNext() {
        return next;
    }

    public void setNext(Node<ContentType> next) {
        this.next = next;
    }

    public ContentType getContent() {
        return content;
    }

    void setContent(ContentType content) {
        this.content = content;
    }
}
