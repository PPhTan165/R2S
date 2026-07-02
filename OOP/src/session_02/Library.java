package session_02;

public class Library {
   

    static void main(String[] args){
        Book book1 = new Book("A","A",2018);
        displayBookInfo(book1);
    }

    public static void displayBookInfo(Book book){
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("Publication Year: " + book.getPublicationYear());
    }
}
