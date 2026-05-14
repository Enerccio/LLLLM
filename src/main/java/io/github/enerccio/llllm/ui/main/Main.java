package io.github.enerccio.llllm.ui.main;

import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.router.Route;
import io.github.enerccio.llllm.bound.SessionPoint;
import io.github.enerccio.llllm.model.domain.User;
import io.github.enerccio.llllm.ui.utils.UIUtils;
import io.github.enerccio.llllm.ui.workspace.Workspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Route("/")
@Configurable(preConstruction = true)
public class Main extends LoginCheckRoute {

    @Autowired
    private User user;

    @Autowired
    private SessionPoint sessionPoint;

    public Main() {
        showLogin();
    }

    @Override
    protected String getAppTitle() {
        return "LLLLM";
    }

    @Override
    protected String getAppDescription() {
        return "Language Learning LLM";
    }

    @Override
    protected boolean authenticate(String userName, String password) throws Exception {
        return userService.authenticate(userName, password);
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {

    }

    @Override
    protected void proceedWithLogin(String username) {
        try {
            User u = userService.findByName(username);

            user.setId(u.getId());
            user.setLogin(u.getLogin());
            user.setFullName(u.getFullName());

            Workspace workspace = sessionPoint.getWorkspace(user.getId());
            add(workspace.create());
            sessionPoint.registerRootContainer(user.getId(), this);
        } catch (Exception e) {
            UIUtils.internalServerError(loc, e);
        }
    }
}
