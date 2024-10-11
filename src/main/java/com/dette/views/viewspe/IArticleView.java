package com.dette.views.viewspe;

import com.dette.core.View;
import com.dette.entities.Article;
import com.dette.entities.Dette;

public interface IArticleView extends View<Article>{
    void listerArticleDispo();
    void updateQteArticle();
    void listerArticleDette(Dette dette);
}
