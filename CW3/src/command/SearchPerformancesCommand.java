package command;

import controller.Controller;

/**
 * Searches for performances by keyword or category.
 */
public class SearchPerformancesCommand extends AbstractCommand {
    private final String queryText;

    public SearchPerformancesCommand(String queryText) {
        super("Search for performances");
        this.queryText = queryText;
    }

    public String getQueryText() {
        return queryText;
    }

    @Override
    public void execute(Controller controller) {
        throw notImplemented();
    }
}
