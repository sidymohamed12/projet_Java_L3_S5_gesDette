package com.dette.views.viewspe;

import com.dette.core.View;
import com.dette.entities.Client;
import com.dette.entities.Dette;
import com.dette.enums.Etat;

public interface IDetteView extends View<Dette> {
    Etat saisiEtat();

    void listerDetteNonSolde();

    void listerDetteSolde();

    void filtrerDetteByEtat(Etat etat);

    void createDetteClient(Dette dette);

    Dette getById();

    void traiterDette(Dette dette);

    void ListedetteOfClient(Client client);

    void ListeDemandeDetteClient(Client client);

}
