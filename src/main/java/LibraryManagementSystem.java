import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;
class InvalidGenreException extends Exception{
    public InvalidGenreException(String message){
        super(message);
    }
}
class BookNotFoundException extends Exception{
public BookNotFoundException(String message){
    super(message);
}

   /* public BookNotFoundException(String title, String author, Genre genre) {
    }*/
}
enum Genre{
     FICTION("Fictional Story"),
    NON_FICTION("Non_fictional story"),
    SCIENCE("Scientific Content"),
     HISTORY("Historical Event"),
    TECHNOLOGY("Technology Related");

    private final String description;
Genre (String description){
    this.description=description;
}
public String getDescription(){
    return description;
}
public static Genre validateGenre(String genre) throws InvalidGenreException{
    try{
        return Genre.valueOf(genre.toUpperCase());
    }catch(IllegalArgumentException e){
        throw new InvalidGenreException("Invalid Genre: "+genre+" Valid Genres:FICTION,NON_FICTION,SCIENCE,HISTORY,TECHNOLOGY");
    }
}
}
interface Borrowable{
    void borrowBook() throws BookNotFoundException;
    void returnBook() throws BookNotFoundException;
}
abstract class Book{
   private Integer bookId;
   private String title;
   private String author;
   private Genre genre;
   private Boolean isBorrowed;
   private static Integer bookIdCounter=1000;
   public Book (String title,String author,Genre genre){
       this.bookId=bookIdCounter++;
       this.title=title;
       this.author=author;
       this.genre=genre;
       this.isBorrowed=false;
   }
   public Integer getBookId(){
       return bookId;
   }
   public String getTitle(){
       return title;
    }
    public Boolean getIsBorrowed(){
       return isBorrowed;
    }
    public void setIsBorrowed(Boolean isBorrowed){
       this.isBorrowed=isBorrowed;
    }
   public void displayBook(){
       StringBuffer sb= new StringBuffer();
       sb.append("Book ID: ").append(bookId)
               .append(",Title: ").append(title)
               .append(",Author: ").append(author)
               .append(",Genre: ").append(genre)
               .append(" - ").append(genre.getDescription())
               .append(",Status: ").append(isBorrowed ?"Borrowed ": "Available");
       System.out.println(sb);
   }
}
class PhysicalBook extends Book implements Borrowable{
    public PhysicalBook(String title,String author,Genre genre){
        super(title,author, genre);
    }
    public void borrowBook() throws BookNotFoundException {


        if (!getIsBorrowed()) {
            setIsBorrowed(true);
            System.out.println(getTitle() + " has been borrowed.");
        }else{
            throw new BookNotFoundException(getTitle()+"is already borrowed .");
        }
    }

    public void returnBook() throws BookNotFoundException {
        if (getIsBorrowed()) {
          setIsBorrowed(false);
            System.out.println(getTitle()+" has been returned");
        }else{
            throw new BookNotFoundException(getTitle()+" was not borrowed");
        }
    }
}
class EBook extends Book implements Borrowable{
    public EBook(String title,String author,Genre genre){
     super(title,author,genre);
    }
    public void borrowBook(){
        System.out.println(getTitle()+"(E-Book) has been downloaded");
    }
    public void returnBook(){
        System.out.println(getTitle()+"(E-Book) does not need to be returned");
    }

}
class Library{
    private ArrayList<Book> books=new ArrayList<>();
    private ArrayList<String> activityLog=new ArrayList<>();
    public void addBook(Book book){
        books.add(book);
        activityLog.add("Added Book: "+book.getTitle()+"(ID: "+book.getBookId()+")");
        System.out.println("Book added successfully");
    }
    public void displayBooks(){
        if(books.isEmpty()){
            System.out.println("No books available in the library");
        }else{
            for(Book book:books){
                book.displayBook();
            }
        }
    }
    public void borrowBook(Integer bookId)throws BookNotFoundException{
        for (Book book :books) {
            if (book.getBookId().equals(bookId)) {
                if (book instanceof Borrowable) {
                    ((Borrowable) book).borrowBook();
                    activityLog.add("Borrowed Book ID: " + bookId);
                    return;
                }
            }
        }
        throw new BookNotFoundException("Book with ID "+bookId);
    }
    public void returnBook(Integer bookId) throws BookNotFoundException{
        for(Book book:books){
            if(book.getBookId().equals(bookId)){
                if(book instanceof Borrowable){
                    if(book instanceof  Borrowable){
                        ((Borrowable)book).borrowBook();
                        activityLog.add("Returned Book ID: "+bookId+"not found");
                        return;
                }
            }
        }
            throw new BookNotFoundException("Book with ID "+bookId+" not found");
        }}
        public void displayActivityLog(){
            if(activityLog.isEmpty()){
                System.out.println("No activities recorded");
            }else{
                System.out.println("\nLibrary Activity Log.");
                for(String log :activityLog){
                    System.out.println(log);
                }
            }
            }
        }

public class LibraryManagementSystem {
  public static void main(String[] args){
      Scanner scanner= new Scanner(System.in);
  Library library= new Library();
  while (true){
      System.out.println("\nLibrary Management System");
      System.out.println("1 Add Physical Book");
      System.out.println("2 Add E-Book");
      System.out.println("3 Display Book");
      System.out.println("4 Borrow a Book");
      System.out.println("5 Return a Book");
      System.out.println("6 View Activity Log");
      System.out.println("7 Exit");
      int choice = Integer.parseInt(scanner.nextLine());
      try{
       switch(choice) {
           case 1:
               System.out.print("Enter book name: ");
               String title = scanner.nextLine();
               System.out.print("Enter author name");
               String author = scanner.nextLine();
               System.out.print("Enter genre: ");
               Genre genre = Genre.validateGenre(scanner.nextLine());
               library.addBook(new PhysicalBook(title, author, genre));
               break;
           case 2:
               System.out.print("Enter book name: ");
               String eTitle = scanner.nextLine();
               System.out.print("Enter author name");
               String eAuthor = scanner.nextLine();
               System.out.print("Enter eGenre: ");
               Genre eGenre = Genre.validateGenre(scanner.nextLine());
               library.addBook(new PhysicalBook(eTitle, eAuthor, eGenre));
               break;
           case 3:
               library.displayBooks();
               break;
           case 4:
               System.out.print("Enter book id borrow:");
               library.borrowBook(Integer.parseInt(scanner.nextLine()));
               break;
           case 5:
               System.out.print("Enter book id to return" );
               library.borrowBook(Integer.parseInt(scanner.nextLine()));
               break;
           case 6:
               library.displayActivityLog();
               break;
           case 7:
               System.out.println("Exiting library system");
               scanner.close();
               System.exit(0);
           default:
               System.out.println("Invalid choice! Try again");
       }}
       catch(InvalidGenreException e){
              System.out.println("Error: "+e.getMessage());
          }
      catch (BookNotFoundException e){
          System.out.println("Error: "+e.getMessage());
      }

  }
  }
}
