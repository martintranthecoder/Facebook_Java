// Bryan Mundia, Eric Park, Sean Htet, Martin Tran, & Cyrus Foroudian
// Professor Abolghasemi
// Final Project

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ProfileManager {
    private UndirectedGraph<Profile> profiles;
    private Scanner sc;
    private String friendName;

    /**
     * A constructor of the class ProfileManager().
     * Using this constructor triggers Scanner for user input.
     */
    public ProfileManager() {
        profiles = new UndirectedGraph<>();
        sc = new Scanner(System.in);
    }

    /**
     * This method adds creates a new profile.
     *
     * @param newProfile
     */
    public void addProfile(Profile newProfile) {
        profiles.addVertex(newProfile);
        System.out.println("Profile created successfully!");
    }

    /**
     * This method removes the user's profile.
     *
     * @param user
     */
    public void removeProfile(Profile user) {
        profiles.removeVertex(user);
        System.out.println("Profile deleted successfully!");
    }

    /**
     * This method compares the two user's names.
     *
     * @param name
     * @return profile
     */
    private Profile getProfileByName(String name) {
        for (Profile profile : profiles.retainAll()) {
            if (profile.getName().equals(name)) {
                return profile;
            }
        }
        return null;
    }

    /**
     * This sends out several options for the user to modify the profile.
     *
     * @param profile
     */
    public void modifyProfile(Profile profile) {
        boolean done = false;
        while (!done) {
            System.out.println("\nHere are the options to modify your profile:");
            System.out.println("1. Change online status.");
            System.out.println("2. Change name or include a nickname.");
            System.out.println("3. Change user picture.");
            System.out.println("4. Add or remove friends.");
            System.out.println("5. Add personal information.");
            System.out.println("6. Disable/reactivate account.");
            System.out.println("7. Search for other profiles.");
            System.out.println("8. Show friends.");
            System.out.println("9. Exit.");
            System.out.print("Select an option to modify your profile: ");

            int option = sc.nextInt();
            sc.nextLine(); // Consume the newline character

            switch (option) {
                case 1:
                    profile.setStatus();
                    break;
                case 2:
                    profile.setName();
                    break;
                case 3:
                    profile.setProfileImage();
                    break;
                case 4:
                    modifyFriends(profile);
                    break;
                case 5:
                    addPersonalInformation(profile);
                    break;
                case 6:
                    toggleAccountStatus(profile);
                    break;
                case 7:
                    searchProfiles();
                    break;
                case 8:
                    showFriends(profile);
                    break;
                case 9:
                    done = true;
                    break;
                default:
                    System.out.println("Invalid option! Please try again: ");
                    break;
            }
        }
    }

    /**
     * This method creates an option to modify the the number of friends the user
     * has.
     *
     * @param profile
     */
    public void modifyFriends(Profile profile) {
        boolean done = false;
        while (!done) {
            System.out.println("\nHere are the options:");
            System.out.println("1. Add a friend.");
            System.out.println("2. Remove a friend.");
            System.out.println("3. Exit.");
            System.out.print("Select an option: ");

            int option = sc.nextInt();
            sc.nextLine(); // Consume the newline character

            switch (option) {
                case 1:
                    addFriend(profile);
                    break;
                case 2:
                    removeFriend(profile);
                    break;
                case 3:
                    done = true;
                    break;
                default:
                    System.out.println("Invalid option! Please try again: ");
                    break;
            }
        }
    }

    /**
     * This method adds a friend to the user's friends list.
     *
     * @param profile
     */
    private void addFriend(Profile profile) {
        boolean check = true;
        while (check) {
            System.out.print("\nEnter the name of the profile to add as a friend: ");
            String friendName = sc.nextLine();
            Profile friendProfile = getProfileByName(friendName);
            if (friendProfile != null) {
                if (!profile.getFriends().contains(friendProfile)) {
                    profile.getFriends().add(friendProfile);
                    System.out.println("\n" + friendProfile.getName() + " added as a friend.");
                    check = false;
                } else {
                    System.out.println("\n" + friendProfile.getName() + " is already a friend.");
                    check = false;
                }
            } else {
                System.out.print("Profile not found. Would you like to look up a different name (yes or no)?");
                boolean shouldContinue = true;
                while (shouldContinue) {
                    String validate = sc.nextLine().toLowerCase();
                    if (validate.equals("y") || validate.equals("yes")) {
                        shouldContinue = false;
                        continue;
                    } else if (validate.equals("n") || validate.equals("no")) {
                        shouldContinue = false;
                        check = false;
                    } else {
                        System.out.println("You have entered something other than 'yes' or 'no'. Please try again: ");
                        continue;
                    }
                }
            }
        }
    }

    /**
     * This method removes a friend to the user's friends list.
     *
     * @param profile
     */
    private void removeFriend(Profile profile) {
        System.out.print("\nEnter the name of the profile to remove from friends: ");
        String friendName = sc.nextLine();
        Profile friendProfile = getProfileByName(friendName);
        if (friendProfile != null) {
            if (profile.getFriends().contains(friendProfile)) {
                profile.getFriends().remove(friendProfile);
                System.out.println("\n" + friendProfile.getName() + " removed from friends.");
            } else {
                System.out.println("\n" + friendProfile.getName() + " is not a friend.");
            }
        } else {
            System.out.println("Profile not found.");
        }
    }

    /**
     * This method allows the user to add additional personal information about
     * themselves in their profile.
     *
     * @param profile
     */
    private void addPersonalInformation(Profile profile) {
        String music;
        String movies;
        String hobbies;

        do {
            System.out.print("\nEnter the music you like: ");
            music = sc.nextLine();
            if (music.isBlank()) {
                System.out.println("Invalid input. Please type in a character.\n");
            }
        } while (music.isBlank());

        do {
            System.out.print("\nEnter the movies you like: ");
            movies = sc.nextLine();
            if (movies.isBlank()) {
                System.out.println("Invalid input. Please type in a character.\n");
            }
        } while (movies.isBlank());

        do {

            System.out.print("\nEnter the things you like to do: ");
            hobbies = sc.nextLine();
            if (hobbies.isBlank()) {
                System.out.println("Invalid input. Please type in a character.\n");
            }
        } while (hobbies.isBlank());

        // Set the personal information
        // (Assuming the Profile class has appropriate setter methods for these fields)
        profile.setMusic(music);
        profile.setMovies(movies);
        profile.setHobbies(hobbies);

        System.out.println("\nPersonal information added successfully!");
    }

    /**
     * This method changes the activity of the user's account.
     *
     * @param profile
     */
    private void toggleAccountStatus(Profile profile) {
        profile.setActive(!profile.isActive());
        if (profile.isActive()) {
            System.out.println("Account reactivated.");
        } else {
            System.out.println("Account disabled.");
        }
    }

    /**
     * This method searches the profiles by the person's name.
     *
     */
    public void searchProfiles() {
        System.out.print("\nEnter the name of the profile to search: ");
        String profileName = sc.nextLine();
        Profile searchProfile = getProfileByName(profileName);
        if (searchProfile != null) {
            System.out.println("\nProfile found: " + searchProfile.printProfile());
        } else {
            System.out.println("\nProfile not found.");
        }
    }

    /**
     * This method shows a list of friends the user has.
     *
     * @param profile
     */
    public void showFriends(Profile profile) {
        int a;
        if (profile.getFriends().isEmpty()) {
            System.out.println("\nYou have no friends in your list.");
        } else {
            System.out.println("\nYour friends' list: ");
            for (Profile friend : profile.getFriends()) {
                System.out.println(friend.getName());
            }
        }
        System.out.println("\n\n1. View your friend's friend."
                + "\n2. Exit");
        System.out.print("Enter your choice: ");
        a = sc.nextInt();
        do {
            if (a == 1) {
                sc.nextLine();
                System.out.println("Enter the name of the friend whose friends you want to view (or press X to exit): ");

                boolean inputValid = true, reAsk = false;
                while (inputValid) {
                    String friendName = sc.nextLine();
                    if (friendName.equals("X") || friendName.equals("x")) {
                        return;
                    }
                    Profile friend = findFriend(profile, friendName);
                    if (friend != null) {
                        if (!friend.getFriends().isEmpty()) {
                            System.out.println("\n" + friendName + "'s friends: ");
                            for (Profile friendsFriend : friend.getFriends()) {
                                System.out.println(friendsFriend.getName());
                            }
                        } else {
                            System.out.println("\n" + friendName + " doesn't have any friend in their friends' list.");
                        }
                        inputValid = false;
                        reAsk = false;
                    } else {

                        reAsk = true;
                    }

                    if (reAsk) {
                        System.out.println(
                                "You have input a friend name who is not recognized from the list.  Please try again or press X to exit: ");
                    }

                }
            } else if (a == 2) {
                System.out.println("");
            } else {
                System.out.println("\nInvalid Input");
            }
        } while ((a == 1) || (a == 2));
    }

    /**
     * This method retrieves a list of a profile's friend' friends.
     *
     * @param profile The profile for which to retrieve the friends' friends.
     * @return
     */
    private Profile findFriend(Profile profile, String friendName) {
        for (Profile friend : profile.getFriends()) {
            if (friend.getName().equals(friendName)) {
                return friend;
            }
        }
        return null;
    }
}