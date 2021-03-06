package src;

import java.util.ArrayList;
import java.util.Scanner;

public class MainProgram {


    public static void main(String[] args) {




        DBManager dbM = new DBManager();
        LibraryManager lbm = new LibraryManager(dbM);
        Scanner input = new Scanner(System.in);
        Scanner textInput = new Scanner(System.in);
        int choice = 0;
        boolean validChoice = false;

        System.out.println("Log in");
        int id = 0;

        do {
            System.out.println("User ID: ");
            try {
                id = Integer.parseInt(input.nextLine());
            }catch (NumberFormatException e) {
                System.out.println("ID not valid. Enter an ID consisting of 4 digits.");
            }
            if (!lbm.validLibrarian(id)) {
                System.out.println("ID is not registered");
            }
        }while (!lbm.validLibrarian(id));

        System.out.println("You are logged in as " + lbm.getLibrarian(id).getName());
        System.out.println("Welcome to your library manager! Please enter a number below: ");

        do {
            System.out.println("----------- MENU ------------");
            System.out.println("\t1 - Register member");
            System.out.println("\t2 - Add book");
            System.out.println("\t3 - Lend book");
            System.out.println("\t4 - Return book");
            System.out.println("\t5 - Remove book");
            System.out.println("\t6 - Suspend member");
            System.out.println("\t7 - Ban member");
            System.out.println("\t8 - Remove member");
            System.out.println("\t9 - Show content");
            System.out.println("\t0 - Exit");
            System.out.println("-----------------------------");

            while (!validChoice) {
                try {
                    choice = Integer.parseInt(input.nextLine());
                    if (choice <= 9) {
                        validChoice = true;
                    }
                    else {
                        System.out.println("The number has to be in the range of 0-9.");
                        System.out.println("Enter number: ");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Enter a digit (0-9): ");
                }
            }

            switch (choice)
            {
                case 1:
                    System.out.println("Register Member");
                    String name;
                    long personalNum = 0L;
                    String type;

                    System.out.println("Enter personal number: ");

                    while (personalNum == 0L) {
                        try {
                            personalNum = Long.parseLong(input.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Not a valid number. Try again.");
                        }
                    }
                    if (!lbm.checkIfExistingMember(personalNum)){
                        System.out.println("Registration has been cancelled.");
                    }

                    else {
                        Member newMember = new Member();
                        int rndId = lbm.getRandId();
                        while (!lbm.idIsValid(rndId)) {
                            rndId = lbm.getRandId();
                        }
                        System.out.println("Enter name: ");
                        name = textInput.nextLine();
                        System.out.println("Enter type: ");
                        type = getAnswer();
                        lbm.addMember(rndId, name, personalNum, type);
                        System.out.println("Member (Name: " + name + ", ID number: " + rndId + ") was successfully added.");
                    }
                    break;
                case 2:
                    try {
                        System.out.println("Add book");
                        System.out.println("Please enter book ID: ");
                        int ID = Integer.parseInt(input.nextLine());
                        System.out.println("Please enter book ISBN: ");
                        int ISBN = Integer.parseInt(input.nextLine());
                        System.out.println("Please enter book title: ");
                        String Title = textInput.nextLine();

                        Book newBook = new Book(ID, ISBN, Title, true);
                        dbM.addBook(newBook);
                        System.out.println("Book successfully added.");
                    }catch (NumberFormatException nfe){
                        System.out.println("Wrong information input");
                    }break;

                case 3:
                    System.out.println("Lend Book");
                    System.out.println("Please enter your member ID: ");
                    int memID = Integer.parseInt(input.nextLine());
                    System.out.println("Please enter the ISBN of your requested book: ");
                    int borrowISBN = Integer.parseInt(input.nextLine());
                    lbm.lendItem(memID, borrowISBN);
                    break;
                case 4:
                    System.out.println("Return Book");
                    System.out.println("Please enter book ID: ");
                    int book_ID = Integer.parseInt(input.nextLine());
                    System.out.println("Please enter your member ID: ");
                    int memb_ID = Integer.parseInt(input.nextLine());
                    lbm.returnBook(book_ID, memb_ID);
                    break;
                case 5:
                    System.out.println("Remove Book");
                    System.out.println("Enter the book ID to remove it: ");
                    int bookID = Integer.parseInt(input.nextLine());
                    dbM.deleteBook(bookID);
                    System.out.println("Book successfully removed.");
                    break;
                case 6:
                    System.out.println("Suspend Member");
                    System.out.println("Enter member ID to suspend: ");
                    int suspendMemberId = Integer.parseInt(input.nextLine());
                    dbM.addSuspension(suspendMemberId);
                    System.out.println("Member suspended for 15 days.");
                    break;
                case 7:
                    System.out.println("Ban Member");
                    System.out.println("Enter member ID to ban: ");
                    int banMember = Integer.parseInt(input.nextLine());
                    lbm.ban(lbm.getMemberById(banMember));
                    System.out.println("Member successfully banned.");
                case 8:
                    System.out.println("Remove Member");
                    System.out.println("Enter the members ID to remove the member: ");
                    int usedMemberID = Integer.parseInt(input.nextLine());
                    dbM.deleteMember(usedMemberID);
                    System.out.println("Member successfully removed.");
                    break;
                case 9:
                    System.out.println("Show content");
                    System.out.println("\t1. Show members\n\t2. Show books\n\t3. Show loans");
                    int choiceShowContent = 0;
                    do {
                        try {
                            choiceShowContent = Integer.parseInt(input.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Type a digit (1-3)");
                        }
                        switch (choiceShowContent) {
                            case 1:
                                ArrayList<Member> members = dbM.getMemberArrayList();
                                for (Member m : members) {
                                    System.out.println(m.toString());
                                    System.out.println("-----------------------------");
                                }
                                break;
                            case 2:
                                ArrayList<Book> books = dbM.getBookArrayList();
                                for (Book b : books) {
                                    System.out.println(b.toString());
                                    System.out.println();
                                }
                                break;
                            case 3:
                                ArrayList<String[]> loans = dbM.getLoanArrayList();
                                for (String[] loan : loans) {
                                    System.out.println("Book ID: " + loan[0] + " Member ID: " + loan[1] + " Start date: " + loan[2] + " End date: " +loan[3]);
                                }
                                break;
                                default:
                                    System.out.println("Enter a number in the range of 1-3");

                        }

                    }while (choiceShowContent != 1 && choiceShowContent != 2 && choiceShowContent != 3);

                        break;
                default:
                    break;
                //  System.out.println("Choice must be a value between 1 and 9.");

            } validChoice = false;
        }
        while (choice != 0) ;
        System.out.println("Thanks for using library manager.");


    }

    private static String getAnswer() {
        Scanner input = new Scanner(System.in);
        int answer;
        System.out.println("1. Student\n2. Masterstudent\n3. PhD student\n4. Teacher");
        boolean validChoice = false;

        while (!validChoice) {
        try {
            answer = Integer.parseInt(input.nextLine());
            if (answer == 1) {
                return "Student";
            } else if (answer == 2) {
                return "Masterstudent";
            } else if (answer == 3) {
                return "PhD Student";
            } else if (answer == 4) {
                return "Teacher";
            }
        } catch (NumberFormatException ne) {
            System.out.println("Type a digit (1-4).");
        }
        }
        return "Invalid";
    }
}
