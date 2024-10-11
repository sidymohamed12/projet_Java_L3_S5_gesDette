package com.dette.views.viewspe;

import com.dette.core.View;
import com.dette.entities.Client;

public interface IClientView extends View<Client>{
    void linkClientUser(Client client);
    void listerClientUser(boolean statut);
    void searchByTelephone();
    void createUserForClient();
}
