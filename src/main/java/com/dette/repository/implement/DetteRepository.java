package com.dette.repository.implement;

import com.dette.core.Repository;
import com.dette.entities.Dette;

public interface DetteRepository extends Repository<Dette> {
    Dette selectBy(String name);

    Dette selectById(int id);

    void update(Dette dette);
}
