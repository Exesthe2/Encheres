package dal;

import bo.Retrait;

public interface RetraitDAO {

    void insert(Retrait retrait);

    void update(Retrait retrait);

    Retrait selectById(int id);
}
