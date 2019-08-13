package MVC.view;

import com.javarush.task.task36.task3608.bean.User;
import MVC.controller.Controller;
import MVC.model.ModelData;

public class UsersView implements View {
    private Controller controller;

    @Override
    public void refresh(ModelData modelData) {
        String head = !modelData.isDisplayDeletedUserList() ? "All users:" : "All deleted users:";
        System.out.println(head);
        for (User user : modelData.getUsers()) {
            System.out.println("\t" + user.toString());
        }
        System.out.println("===================================================");
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void fireEventShowAllUsers() {
        controller.onShowAllUsers();
    }

    public void fireEventShowDeletedUsers() {
        controller.onShowAllDeletedUsers();
    }

    public void fireEventOpenUserEditForm(long id) {
        controller.onOpenUserEditForm(id);
    }
}
