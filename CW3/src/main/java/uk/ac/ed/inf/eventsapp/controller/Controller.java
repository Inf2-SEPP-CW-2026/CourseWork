package uk.ac.ed.inf.eventsapp.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import uk.ac.ed.inf.eventsapp.model.AdminStaff;
import uk.ac.ed.inf.eventsapp.model.EntertainmentProvider;
import uk.ac.ed.inf.eventsapp.model.Student;
import uk.ac.ed.inf.eventsapp.model.User;
import uk.ac.ed.inf.eventsapp.util.InputParsers;
import uk.ac.ed.inf.eventsapp.view.View;

/**
 * Shared base for the concrete controllers in the UML uk.ac.ed.inf.eventsapp.model.
 */
public abstract class Controller {
  protected final View view;
  protected User currentUser;

  protected Controller(View view) {
    this.view = view;
  }

  public User getCurrentUser() {
    return currentUser;
  }

  public void setCurrentUser(User currentUser) {
    this.currentUser = currentUser;
  }

  protected boolean checkCurrentUserIsGuest() {
    return currentUser == null;
  }

  protected boolean checkCurrentUserIsAdmin() {
    return currentUser instanceof AdminStaff;
  }

  protected boolean checkCurrentUserIsStudent() {
    return currentUser instanceof Student;
  }

  protected boolean checkCurrentUserIsEntertainmentProvider() {
    return currentUser instanceof EntertainmentProvider;
  }

  public <T> int selectFromMenu(Collection<T> options, String prompt) {
    List<T> optionList = new ArrayList<>(options);
    while (true) {
      StringBuilder menuPrompt =
          new StringBuilder(prompt).append(System.lineSeparator()).append("0. Exit");
      for (int index = 0; index < optionList.size(); index++) {
        menuPrompt.append(System.lineSeparator()).append(index + 1).append(". ")
            .append(formatMenuOption(optionList.get(index)));
      }

      Integer selectedOption = InputParsers.parseNonNegativeInteger(
          view.getInput(menuPrompt.toString() + System.lineSeparator(), ">>> "));
      if (selectedOption == null || selectedOption > optionList.size()) {
        view.displayError("Please select a valid menu option number.");
        continue;
      }

      if (selectedOption == 0) {
        return -1;
      }

      return selectedOption - 1;
    }
  }

  private String formatMenuOption(Object option) {
    String rawLabel = option.toString().toLowerCase().replace('_', ' ').replace("Ep", "EP");
    String[] words = rawLabel.split(" ");
    StringBuilder formattedLabel = new StringBuilder();
    for (String word : words) {
      if (word.isEmpty()) {
        continue;
      }

      if (!formattedLabel.isEmpty()) {
        formattedLabel.append(' ');
      }
      formattedLabel.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
    }
    return formattedLabel.toString();
  }
}
