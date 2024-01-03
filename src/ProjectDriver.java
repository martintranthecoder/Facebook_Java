// Bryan Mundia, Eric Park, Sean Htet, Martin Tran, & Cyrus Foroudian
// Professor Abolghasemi
// Final Project

import java.util.*;

///////////////////////////////////////////////////////////////////////////////
//
//  Profesor, we moved the implementation of the viewFriendsFriends function
//  to be an additional option through the viewFriends function.
//
//  We did this because it makes more sense to have it be an extension of
//  the viewFriends function.
//
///////////////////////////////////////////////////////////////////////////////

public class ProjectDriver {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Profile user = new Profile();
        ProfileManager profileManager = new ProfileManager();

        profileManager.addProfile(user);
        int task = 0;
        int a = 0;
        boolean b = true;

        do {

            System.out.println("\nWhich action do you want to perform?\n");
            System.out.println("1. Add new profile");
            System.out.println("2. Update your friends' list");
            System.out.println("3. Search for another profile");
            System.out.println("4. Update and edit your profile");
            System.out.println("5. See your profile's information");
            System.out.println("6. See your friends list");
            System.out.println("7. Delete the profile");
            System.out.println("8. Quit");
            System.out.print("\nEnter your choice: ");

            if (input.hasNextInt()) {
                task = input.nextInt();
                if (task == 1) {
                    Profile newUser = new Profile();
                    profileManager.addProfile(newUser);
                } else if (task == 2) {
                    profileManager.modifyFriends(user);
                } else if (task == 3) {
                    profileManager.searchProfiles();
                } else if (task == 4) {
                    profileManager.modifyProfile(user);
                } else if (task == 5) {
                    System.out.println(user.printProfile());
                } else if (task == 6) {
                    profileManager.showFriends(user);
                } else {
                    System.out.println("Invalid command. Please enter the correct number.");
                    input.nextLine();
                }
            }
        } while (task >= 1 && task <= 8);

        System.out.println("\nBye!");
        input.close();
    }
}