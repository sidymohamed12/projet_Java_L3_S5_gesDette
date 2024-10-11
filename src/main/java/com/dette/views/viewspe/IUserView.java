package com.dette.views.viewspe;

import com.dette.core.View;
import com.dette.entities.User;
import com.dette.enums.Role;

public interface IUserView extends View<User>{
    Role saisieRoleUser();
    void setEtatUser(boolean status);
    User authentification();
    void listerUserbyRole();
    void listerUserActif();
}
