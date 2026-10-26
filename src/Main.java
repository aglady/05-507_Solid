import ru.gr05507.ui.TaskInputParser;
import ru.gr05507.ui.TaskPresenter;
import ru.gr05507.ui.TaskWindow;

void main() {
    TaskInputParser inputParser=new TaskInputParser();
    new TaskWindow(inputParser).setVisible(true);
}