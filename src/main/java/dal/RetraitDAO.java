package dal;

import bo.Retrait;

public interface RetraitDAO {

    void insert(Retrait retrait);

    Retrait selectById(int id);
}
